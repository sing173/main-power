package com.zungen.mp.modules.iot.service;

import com.zungen.mp.modules.iot.model.MpDeviceEvent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zungen.mp.modules.iot.model.vo.MpEvent;

import java.util.List;

/**
 * <p>
 * 台区设备事件 服务类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
public interface MpDeviceEventService extends IService<MpDeviceEvent> {
    boolean createDeviceEvent(MpDeviceEvent mpDeviceEvent);

    boolean batchCreateDeviceEvent(List<MpDeviceEvent> mpDeviceEventList, Long reportId);

    List<MpDeviceEvent> map2MpDeviceEventByEventType(
            List<? extends MpEvent> mpEventList,
            Long terminalId, String terminalAddress
            );
}
