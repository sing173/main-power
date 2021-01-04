package com.shinetech.aihall.modules.iot.service;

import com.shinetech.aihall.modules.iot.model.MpTerminalReport;
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
}
