package com.shinetech.aihall.modules.iot.service.impl;

import com.shinetech.aihall.modules.iot.model.MpTerminalReport;
import com.shinetech.aihall.modules.iot.mapper.MpTerminalReportMapper;
import com.shinetech.aihall.modules.iot.service.MpTerminalReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 台区终端报告 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Service
public class MpTerminalReportServiceImpl extends ServiceImpl<MpTerminalReportMapper, MpTerminalReport> implements MpTerminalReportService {

    @Override
    public boolean createTerminalReport(MpTerminalReport mpTerminalReport) {
        mpTerminalReport.setCreatedTime(new Date());

        return save(mpTerminalReport);
    }

}
