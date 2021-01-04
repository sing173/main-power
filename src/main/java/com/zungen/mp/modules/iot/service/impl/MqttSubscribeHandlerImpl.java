package com.zungen.mp.modules.iot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.zungen.mp.modules.iot.model.MpDevice;
import com.zungen.mp.modules.iot.model.MpDeviceEvent;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.model.vo.MpEventReport;
import com.zungen.mp.modules.iot.model.vo.MpTopology;
import com.zungen.mp.modules.iot.model.vo.MqttMessage;
import com.zungen.mp.modules.iot.service.*;
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

    @Autowired
    private MpTerminalReportService terminalReportService;

    @Autowired
    private MpDeviceEventService deviceEventService;

    @Autowired
    private MpTerminalService terminalService;

    @Autowired
    private MpDeviceService deviceService;

    @Autowired
    private MqttGateway mqttGateway;

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
        System.out.println("handleTerminalInfo mqtt:"+payLoad);

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
        System.out.println("handleTopology mqtt:"+payLoad);
        MqttMessage<MpTopology> topologyMqttMessage = JSONObject.parseObject(payLoad,
                new TypeReference<MqttMessage<MpTopology>>() {});
        MpTopology topology = topologyMqttMessage.getBody();

        MpTerminal mpTerminal = terminalService.findByAddress(topology.getAddress());
        if(mpTerminal != null) {
            List<MpDevice> mpDeviceList = topology.getTopology();

            if(CollectionUtil.isNotEmpty(mpDeviceList)) {
                mpDeviceList.forEach(mpDevice -> {
                    //设置台区终端编号、地址
                    mpDevice.setTerminalId(mpTerminal.getId());
                    mpDevice.setTerminalAddress(mpTerminal.getAddress());
                    deviceService.saveDevice(mpDevice);
                });

                //TODO 发布主题请求获取该终端的分析报告
            }

        } else {
//            logger.error("没有找到对应的台区终端");
            System.out.println("没有找到对应的台区终端");
        }


    }

    /**
     * 处理终端分析报告、事件
     * @param payLoad
     */
    private void handleAnalysisReport(String payLoad) {
        System.out.println("handleAnalysisReport mqtt:"+payLoad);

        MqttMessage<MpEventReport> eventReportMqttMessage = JSONObject.parseObject(payLoad,
                new TypeReference<MqttMessage<MpEventReport>>() {});
        MpEventReport mpEventReport = eventReportMqttMessage.getBody();
        MpTerminal mpTerminal = terminalService.findByAddress(mpEventReport.getAddress());

        if(mpTerminal != null) {
            //1.先保存台区报告
            MpTerminalReport mpTerminalReport = new MpTerminalReport();
            BeanUtil.copyProperties(mpEventReport, mpTerminalReport);
            mpTerminalReport.setTerminalId(mpTerminal.getId());
            mpTerminalReport.setTerminalAddress(mpTerminal.getAddress());
            mpTerminalReport.setCreatedTime(new Date());
            //准备保存的事件列表
            List<MpDeviceEvent> deviceEventList = new ArrayList<>();
            //电表事件
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventMeter())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventMeter().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventMeter(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            //线损事件
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventLineLost())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventLineLost().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventLineLost(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            //供电质量
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventQuality())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventQuality().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventQuality(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            //三相不平衡
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventLoadBalance())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventLoadBalance().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventLoadBalance(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            //停电事件
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventPowerLost())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventPowerLost().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventPowerLost(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            //负荷超限事件
            if (CollectionUtil.isNotEmpty(mpEventReport.getEventOverLoad())) {
                mpTerminalReport.setEventMeter(mpEventReport.getEventOverLoad().size());
                deviceEventList.addAll(deviceEventService.
                        map2MpDeviceEventByEventType(mpEventReport.getEventOverLoad(), mpTerminal.getId(), mpTerminal.getAddress()));
            }
            terminalReportService.createTerminalReport(mpTerminalReport);

            //2.再批量保存台区设备事件报告 TODO 设备id
            deviceEventService.batchCreateDeviceEvent(deviceEventList, mpTerminalReport.getId());
        }
        else {
//            logger.error("没有找到对应的台区终端");
            System.out.println("没有找到对应的台区终端");
        }
    }
}
