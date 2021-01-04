package com.shinetech.aihall;

import cn.hutool.core.lang.Assert;
import com.zungen.mp.MainPowerApplication;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.service.MpTerminalReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainPowerApplication.class)
public class KmApplicationTests {
    @Autowired
    MpTerminalReportService mpTerminalReportService;

    @Test
    public void contextLoads() {
        MpTerminalReport report = new MpTerminalReport();
        report.setTerminalAddress("123");
        report.setCreatedTime(new Date());
        if(mpTerminalReportService.createTerminalReport(report)) {
            Long id = report.getId();
            Assert.isTrue(id > 0);
        }
    }

}
