package com.zungen.mp.modules.iot.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zungen.mp.common.api.CommonPage;
import com.zungen.mp.common.api.CommonResult;
import com.zungen.mp.modules.iot.enums.EventTypeEnum;
import com.zungen.mp.modules.iot.model.MpDevice;
import com.zungen.mp.modules.iot.model.MpDeviceEvent;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.model.vo.*;
import com.zungen.mp.modules.iot.service.*;
import com.zungen.mp.modules.ums.model.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Api(tags = "MqttController", description = "电力智能管理终端交互接口")
@RequestMapping("/iot")
public class MqttController {
    @Autowired
    private MqttGateway mqttGateway;

    @Autowired
    private MpTerminalService terminalService;

    @Autowired
    private MpDeviceService deviceService;

    @Autowired
    private MpDeviceEventService deviceEventService;

    @Autowired
    private MpTerminalReportService reportService;

    @ApiOperation(value = "发送自定义主题")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult sendMqtt(@RequestParam(value = "data", required = false) String data,
                                 @RequestParam(value = "topic") String topic) {
        if (StrUtil.isEmpty(data)) {
            MqttMessage message = new MqttMessage<>();
            message.setToken(IdUtil.fastSimpleUUID());
            message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
            mqttGateway.sendToMqtt(JSONObject.toJSONString(message), topic);
        } else {
            mqttGateway.sendToMqtt(data, topic);
        }
        return CommonResult.success(null);
    }

    public CommonResult pubAllTerminal() {


        return CommonResult.failed();
    }

    @ApiOperation(value = "查找台区终端列表")
    @RequestMapping(value = "/terminal/list", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult listTerminal(@RequestParam(value = "keyword", required = false) String keyword,
                                     @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<MpTerminal> adminList = terminalService.list(keyword, pageSize, pageNum);
        //找出最新台区报告并组成vo返回前端
        if (adminList != null && CollUtil.isNotEmpty(adminList.getRecords())) {
            List<MpTerminalListView> terminalListViewList = new ArrayList<>();
            List<MpTerminal> mpTerminals = adminList.getRecords();
            mpTerminals.forEach(mpTerminal -> {
                MpTerminalReport mpTerminalReport = reportService.getLastReportByTerminalId(mpTerminal.getId());
                MpTerminalListView mpTerminalListView = new MpTerminalListView();
                if (mpTerminalReport != null) {
                    BeanUtil.copyProperties(mpTerminalReport, mpTerminalListView);
                    mpTerminalListView.setReportId(mpTerminalReport.getId());
                }
                mpTerminalListView.setDeviceId(mpTerminal.getDeviceId());
                mpTerminalListView.setId(mpTerminal.getId());
                mpTerminalListView.setAddress(mpTerminal.getAddress());
                mpTerminalListView.setCreatedTime(mpTerminal.getCreatedTime());
                mpTerminalListView.setUpdateTime(mpTerminal.getUpdateTime());
                terminalListViewList.add(mpTerminalListView);
            });
            //返回自定义分页对象
            return CommonResult.success(CommonPage.restPage(
                    Convert.toInt(adminList.getCurrent()),
                    Convert.toInt(adminList.getSize()),
                    adminList.getTotal(),
                    Convert.toInt(adminList.getTotal() / adminList.getSize() + 1),
                    terminalListViewList));
        } else {
            return CommonResult.failed("没有找到任何数据");
        }
    }

    @ApiOperation(value = "根据台区终端id获取拓扑数据")
    @RequestMapping(value = "/terminal/topology", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult terminalTopology(@RequestParam(value = "terminalId") Long terminalId,
                                         @RequestParam(value = "reportId", required = false) Long reportId) {
        //找出台区终端下的所有设备并组成设备树形结构vo
        List<MpDevice> mpDeviceList = deviceService.findMpDevicesByTerminalId(terminalId);
        if (CollUtil.isNotEmpty(mpDeviceList)) {
            //根据台区报告找出最新报告下的所有事件,并组合到设备树形结构vo
            List<MpDeviceEvent> mpDeviceEventList = reportId == null ? null : deviceEventService.findEventsByReportId(reportId);

            List<MpDeviceView> deviceViewList = mpDeviceList.stream()
                    .filter(mpDevice -> StrUtil.isEmpty(mpDevice.getParent()) || "000000000000".equals(mpDevice.getParent()))
                    .map(mpDevice -> getDeviceView(mpDevice, mpDeviceList, mpDeviceEventList))
                    .collect(Collectors.toList());

            return CommonResult.success(deviceViewList);
        } else {
            return CommonResult.failed("没有找到任何数据");
        }
    }

    /**
     * 采用递归方式转换设备vo、设置事件、子节点
     *
     * @param mpDevice
     * @param mpDeviceList
     * @param mpDeviceEventList
     * @return
     */
    private MpDeviceView getDeviceView(MpDevice mpDevice, List<MpDevice> mpDeviceList, List<MpDeviceEvent> mpDeviceEventList) {
        MpDeviceView deviceView = new MpDeviceView();
        BeanUtil.copyProperties(mpDevice, deviceView);
        //找出当前设备对应的事件
        if (mpDeviceEventList != null) {
            mpDeviceEventList.stream()
                    .filter(mpDeviceEvent -> mpDeviceEvent.getAddress().equals(deviceView.getAddress()))
                    .forEach(mpDeviceEvent -> {
                        //根据事件类型组成不同的事件vo
                        if (EventTypeEnum.LineLost.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventLineLost lineLost = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventLineLost.class);
                            deviceView.getEventLineLost().add(lineLost);
                        } else if (EventTypeEnum.LoadBalance.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventLoadBalance loadBalance = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventLoadBalance.class);
                            deviceView.getEventLoadBalance().add(loadBalance);
                        } else if (EventTypeEnum.PowerLost.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventPowerLost powerLost = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventPowerLost.class);
                            deviceView.getEventPowerLost().add(powerLost);
                        } else if (EventTypeEnum.OverLoad.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventOverLoad overLoad = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventOverLoad.class);
                            deviceView.getEventOverLoad().add(overLoad);
                        } else if (EventTypeEnum.Quality.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventQuality quality = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventQuality.class);
                            deviceView.getEventQuality().add(quality);
                        } else if (EventTypeEnum.Meter.getCode().equals(mpDeviceEvent.getType())) {
                            MpEventMeter meter = JSONObject.parseObject(mpDeviceEvent.getEventJson(), MpEventMeter.class);
                            deviceView.getEventMeter().add(meter);
                        }
                    });
            if (CollUtil.isNotEmpty(deviceView.getEventLineLost()) || CollUtil.isNotEmpty(deviceView.getEventPowerLost()) ||
                    CollUtil.isNotEmpty(deviceView.getEventMeter()) || CollUtil.isNotEmpty(deviceView.getEventQuality()) ||
                    CollUtil.isNotEmpty(deviceView.getEventLoadBalance()) || CollUtil.isNotEmpty(deviceView.getEventOverLoad())) {
                deviceView.setEventHappen(true);
            }
        }

        //查找是否有子节点-递归
        List<MpDeviceView> childrenDevice = mpDeviceList.stream()
                .filter(mpDeviceChild -> mpDeviceChild.getParent() != null && mpDeviceChild.getParent().equals(deviceView.getAddress()))
                .map(mpDeviceChild -> getDeviceView(mpDeviceChild, mpDeviceList, mpDeviceEventList))
                .collect(Collectors.toList());
        deviceView.setChildren(childrenDevice);

        return deviceView;
    }
}
