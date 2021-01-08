package com.zungen.mp.modules.iot.service.impl;

import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.zungen.mp.modules.iot.mapper.MpTerminalReportMapper;
import com.zungen.mp.modules.iot.service.MpTerminalReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private MpTerminalReportMapper mapper;

    @Override
    public boolean createTerminalReport(MpTerminalReport mpTerminalReport) {
        mpTerminalReport.setCreatedTime(new Date());

        return mapper.insertReport(mpTerminalReport) > 0;
    }

    /**
     * 找出台区终端最新的分析报告
     * @param terminalId
     * @return
     */
    @Override
    public MpTerminalReport getLastReportByTerminalId(@Param("terminalId") Long terminalId) {
        return mapper.selectLastReportByTerminalId(terminalId);
    }
}
