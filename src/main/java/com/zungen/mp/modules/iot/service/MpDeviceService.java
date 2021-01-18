package com.zungen.mp.modules.iot.service;

import com.zungen.mp.modules.iot.model.MpDevice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 台区设备 服务类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
public interface MpDeviceService extends IService<MpDevice> {

    boolean saveDevice(MpDevice mpDevice);

    /**
     * 根据台区终端id查询其所有设备
     * @param terminalId
     * @return
     */
    List<MpDevice> findMpDevicesByTerminalId(long terminalId);
}
