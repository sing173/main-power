package com.zungen.mp.modules.iot.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zungen.mp.modules.iot.mapper.MpDeviceEventMapper;
import com.zungen.mp.modules.iot.model.MpDeviceEvent;
import com.zungen.mp.modules.iot.model.vo.*;
import com.zungen.mp.modules.iot.service.MpDeviceEventService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 台区设备事件 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Service
public class MpDeviceEventServiceImpl extends ServiceImpl<MpDeviceEventMapper, MpDeviceEvent> implements MpDeviceEventService {

    @Override
    public boolean createDeviceEvent(MpDeviceEvent mpDeviceEvent) {
        mpDeviceEvent.setCreatedTime(new Date());
        return save(mpDeviceEvent);
    }

    @Override
    public boolean batchCreateDeviceEvent(List<MpDeviceEvent> mpDeviceEventList, Long reportId) {
        mpDeviceEventList.forEach(mpDeviceEvent -> mpDeviceEvent.setTerminalReportId(reportId));

        return saveBatch(mpDeviceEventList);
    }

    /**
     * mqtt服务端分析报告中各子事件对象转换成本地存储对象（按事件类型）
     * @param mpEventList
     * @param terminalId
     * @return
     */
    @Override
    public List<MpDeviceEvent> map2MpDeviceEventByEventType(
            List<? extends MpEvent> mpEventList,
            Long terminalId,
            String terminalAddress) {
        return mpEventList.stream().map(mpEvent -> {
            MpDeviceEvent mpDeviceEvent = new MpDeviceEvent();
            mpDeviceEvent.setAddress(mpEvent.getAddress());
            mpDeviceEvent.setCreatedTime(new Date());
            //设置台区id
            mpDeviceEvent.setTerminalId(terminalId);
            //设置事件内容（json）
            mpDeviceEvent.setEventJson(JSONUtil.toJsonStr(mpEvent));
            //设置事件类型
            if (mpEvent instanceof MpEventPowerLost) {
                mpDeviceEvent.setType(1);
            } else if (mpEvent instanceof MpEventLoadBalance) {
                mpDeviceEvent.setType(2);
            } else if (mpEvent instanceof MpEventLineLost) {
                mpDeviceEvent.setType(3);
            } else if (mpEvent instanceof MpEventOverLoad) {
                mpDeviceEvent.setType(4);
            } else if (mpEvent instanceof MpEventQuality) {
                mpDeviceEvent.setType(5);
            } else if (mpEvent instanceof MpEventMeter) {
                mpDeviceEvent.setType(6);
            }
            return mpDeviceEvent;
        }).collect(Collectors.toList());

    }

}
