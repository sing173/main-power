package com.zungen.mp.modules.iot.mapper;

import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 台区终端报告 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
public interface MpTerminalReportMapper extends BaseMapper<MpTerminalReport> {
    int insertReport(MpTerminalReport mpTerminalReport);
}
