package com.zungen.mp.modules.iot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zungen.mp.modules.iot.model.MpDevice;
import com.zungen.mp.modules.iot.model.MpDeviceEvent;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.model.vo.MpEventReport;
import com.zungen.mp.modules.iot.model.vo.MqttMessage;
import com.zungen.mp.modules.iot.service.MpDeviceEventService;
import com.zungen.mp.modules.iot.service.MpDeviceService;
import com.zungen.mp.modules.iot.service.MpTerminalReportService;
import com.zungen.mp.modules.iot.service.MpTerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理 台区智能终端发送的主题事件
 * @author luomingxing
 */
public class MqttSubscribeHandlerImpl implements MessageHandler {
    private static final Log logger = LogFactory.get();

    @Autowired
    private MpTerminalReportService terminalReportService;

    @Autowired
    private MpDeviceEventService deviceEventService;

    @Autowired
    private MpTerminalService terminalService;

    @Autowired
    private MpDeviceService deviceService;

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        //监听的主题
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
        //监听的主题类型（接口）
        String type = topic.substring(topic.lastIndexOf("/") + 1);

        String payLoad = message.getPayload().toString();
        switch (type) {
            case "terminalInfo":
                handleTerminalInfo(payLoad);
                break;
            case "topology":
                handleTopology(payLoad);
                break;
            case "analysisReport":
                handleAnalysisReport(payLoad);
                break;
            case "zungen":
//                logger.debug("zungen mqtt:"+payLoad);
                System.out.println("zungen mqtt:"+payLoad);
                break;
            default:
                throw new NotImplementedException();
        }

    }

    /**
     * 处理终端版本信息事件
     * @param payLoad
     */
    private void handleTerminalInfo(String payLoad) {
        logger.debug("handleTerminalInfo mqtt:"+payLoad);

        MqttMessage<MpTerminal> terminalMqttMessage = JSONObject.parseObject(payLoad,
                new TypeReference<MqttMessage<MpTerminal>>() {});
        MpTerminal mpTerminal = terminalMqttMessage.getBody();

        terminalService.saveTerminal(mpTerminal);

        //TODO 发布主题请求获取该终端的拓扑数据
    }

    /**
     * 处理终端拓扑数据事件
     * @param payLoad
     */
    private void handleTopology(String payLoad) {
        logger.debug("handleTopology mqtt:"+payLoad);

        MqttMessage<MpDevice> deviceMqttMessage = JSONObject.parseObject(payLoad,
                new TypeReference<MqttMessage<MpDevice>>() {});
        MpDevice mpDevice = deviceMqttMessage.getBody();
        //TODO 设置台区终端地址
        deviceService.saveDevice(mpDevice);

        //TODO 发布主题请求获取该终端的分析报告
    }

    /**
     * 处理终端分析报告、事件
     * @param payLoad
     */
    private void handleAnalysisReport(String payLoad) {
        logger.debug("handleAnalysisReport mqtt:"+payLoad);

        MqttMessage<MpEventReport> eventReportMqttMessage = JSONObject.parseObject(payLoad,
                new TypeReference<MqttMessage<MpEventReport>>() {});
        MpEventReport mpEventReport = eventReportMqttMessage.getBody();
        //1.先保存台区报告 //TODO 设置台区id
        MpTerminalReport mpTerminalReport = new MpTerminalReport();
        BeanUtil.copyProperties(mpEventReport, mpTerminalReport);
        mpTerminalReport.setCreatedTime(new Date());
        //准备保存的事件列表
        List<MpDeviceEvent> deviceEventList = new ArrayList<>();
        //电表事件
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventMeter())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventMeter().size());
            //TODO 台区id
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventMeter(), 1L));
        }
        //线损事件
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventLineLost())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventLineLost().size());
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventLineLost(), 1L));
        }
        //供电质量
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventQuality())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventQuality().size());
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventQuality(), 1L));
        }
        //三相不平衡
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventLoadBalance())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventLoadBalance().size());
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventLoadBalance(), 1L));
        }
        //停电事件
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventPowerLost())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventPowerLost().size());
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventPowerLost(), 1L));
        }
        //负荷超限事件
        if(CollectionUtil.isNotEmpty(mpEventReport.getEventOverLoad())) {
            mpTerminalReport.setEventMeter(mpEventReport.getEventOverLoad().size());
            deviceEventList.addAll(deviceEventService.
                    map2MpDeviceEventByEventType(mpEventReport.getEventOverLoad(), 1L));
        }
        //TODO 获取报告id
        terminalReportService.createTerminalReport(mpTerminalReport);

        //2.再批量保存台区设备事件报告 TODO 设置报告id，设备id
        deviceEventService.batchCreateDeviceEvent(deviceEventList);

    }
}
