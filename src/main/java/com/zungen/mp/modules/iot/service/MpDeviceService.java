package com.zungen.mp.modules.iot.service;

import com.zungen.mp.modules.iot.model.MpDevice;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
