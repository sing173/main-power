package com.zungen.mp.modules.iot.service;

import com.zungen.mp.modules.iot.model.MpTerminalReport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 台区终端报告 服务类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
public interface MpTerminalReportService extends IService<MpTerminalReport> {
    boolean createTerminalReport(MpTerminalReport mpTerminalReport);

    MpTerminalReport getLastReportByTerminalId(Long terminalId);
}
