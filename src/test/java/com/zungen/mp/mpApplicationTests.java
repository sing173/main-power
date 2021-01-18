package com.zungen.mp;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.zungen.mp.common.api.CommonResult;
import com.zungen.mp.modules.iot.model.MpDevice;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.model.vo.*;
import com.zungen.mp.modules.iot.service.MpDeviceService;
import com.zungen.mp.modules.iot.service.MpTerminalReportService;
import com.zungen.mp.modules.iot.service.MqttGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainPowerApplication.class)
public class mpApplicationTests {
    @Autowired
    MpTerminalReportService mpTerminalReportService;

    @Autowired
    MqttGateway mqttGateway;

    @Autowired
    MpDeviceService deviceService;

    @Resource
    private ThreadPoolTaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledFuture;

    @Test
    public void testReportInsert() {
        MpTerminalReport report = new MpTerminalReport();
        report.setTerminalAddress("123");
        report.setCreatedTime(new Date());
        if(mpTerminalReportService.createTerminalReport(report)) {
            Long id = report.getId();
            Assert.isTrue(id > 0);
        }
    }

    @Test
    public void testPubTerminal() {
        String topic = "server/ALL/zungen/get/request/terminalInfo";
        MqttMessage message = new MqttMessage<>();
        message.setToken(IdUtil.fastSimpleUUID());
        message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        mqttGateway.sendToMqtt(JSONObject.toJSONString(message), topic);
    }

    @Test
    public void testPubTerminalInfo() {
        String topic = "device/20181226/topo/notify/report/terminalInfo";

        MqttMessage<MpTerminal> message = new MqttMessage<>();
        message.setToken(IdUtil.fastSimpleUUID());
        message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        MpTerminal mpTerminal = new MpTerminal();
        mpTerminal.setAddress("012345");
        mpTerminal.setDeviceId("20181226");
        mpTerminal.setSoftwareVersion("1.0");
        mpTerminal.setSoftwareDate(new Date());
        mpTerminal.setCapacity("Capacity");
        mpTerminal.setProtocol("Protocol");
        mpTerminal.setHardwareDate(new Date());
        mpTerminal.setHardwareVersion("1.0");
        message.setBody(mpTerminal);

        mqttGateway.sendToMqtt(JSONObject.toJSONString(message), topic);
    }

    @Test
    public void testPubTopology() {
        String topic = "device/20181226/topo/action/response/topology";
        MqttMessage<MpTopology> message = new MqttMessage<>();
        message.setToken(IdUtil.fastSimpleUUID());
        message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        MpTopology mpTopology = new MpTopology();
        mpTopology.setAddress("012345");
        mpTopology.setRT_D("RT_D");
        mpTopology.setRT_F(1);
        List<MpDevice> mpDeviceList = new ArrayList<>();
        for(int i = 0;i < 5;i++) {
            MpDevice mpDevice = new MpDevice();
            mpDevice.setAddress("012345"+i);
            mpDevice.setRole(0);
            mpDevice.setProtocol(0);
            mpDevice.setStatus(1);
            if(i > 0) {
                mpDevice.setParent("0123450");
            }
            mpDeviceList.add(mpDevice);
        }
        mpTopology.setTopology(mpDeviceList);
        message.setBody(mpTopology);

        mqttGateway.sendToMqtt(JSONObject.toJSONString(message), topic);
    }

    @Test
    public void testPubReport() {
        String topic = "device/20181226/topo/get/response/analysisReport";

        MqttMessage<MpEventReport> message = new MqttMessage<>();
        message.setToken(IdUtil.fastSimpleUUID());
        message.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
        MpEventReport mpEventReport = new MpEventReport();
        mpEventReport.setAddress("0123450");
        mpEventReport.setEnergyLost(1);
        mpEventReport.setEnergySale(2);
        mpEventReport.setEnergySupply(3);
        mpEventReport.setLinelost(4);

        MpEventLineLost mpEventLineLost = new MpEventLineLost();
        mpEventLineLost.setAddress("0001");
        mpEventLineLost.setLinelostTotal(10);
        mpEventLineLost.setCause("none");
        mpEventReport.setEventLineLost(CollectionUtil.newArrayList(mpEventLineLost));

        MpEventLoadBalance mpEventLoadBalance = new MpEventLoadBalance();
        mpEventLoadBalance.setAddress("0002");
        mpEventLoadBalance.setCurrentA(20);
        mpEventLoadBalance.setCause("test");
        mpEventReport.setEventLoadBalance(CollectionUtil.newArrayList(mpEventLoadBalance));

        MpEventMeter mpEventMeter = new MpEventMeter();
        mpEventMeter.setAddress("0003");
        mpEventMeter.setCause("test2");
        mpEventReport.setEventMeter(CollectionUtil.newArrayList(mpEventMeter));

        message.setBody(mpEventReport);
        mqttGateway.sendToMqtt(JSONObject.toJSONString(message), topic);
    }

    @Test
    public void testTaskScheduler() {
        scheduledFuture = taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                if(deviceService.findMpDevicesByTerminalId(5).get(0).getStatus() == 1) {
                    scheduledFuture.cancel(false);
                }

                System.out.println(Thread.currentThread().getName() + "|schedule controller" + "|" + new Date().toLocaleString());
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                return new CronTrigger("0/1 * * * * ?").nextExecutionTime(triggerContext);
            }
        });

        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (scheduledFuture == null || !scheduledFuture.isCancelled());

    }
}
