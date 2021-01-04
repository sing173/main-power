package com.shinetech.aihall.modules.iot.service;

import com.shinetech.aihall.modules.iot.model.MpTerminal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 台区终端 服务类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
public interface MpTerminalService extends IService<MpTerminal> {
    boolean saveTerminal(MpTerminal mpTerminal);
}
