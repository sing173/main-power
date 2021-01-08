package com.zungen.mp.modules.iot.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zungen.mp.modules.iot.enums.EventTypeEnum;
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
            mpDeviceEvent.setTerminalAddress(terminalAddress);
            //设置事件内容（json）
            mpDeviceEvent.setEventJson(JSONUtil.toJsonStr(mpEvent));
            //设置事件类型
            if (mpEvent instanceof MpEventPowerLost) {
                mpDeviceEvent.setType(EventTypeEnum.PowerLost.getCode());
            } else if (mpEvent instanceof MpEventLoadBalance) {
                mpDeviceEvent.setType(EventTypeEnum.LoadBalance.getCode());
            } else if (mpEvent instanceof MpEventLineLost) {
                mpDeviceEvent.setType(EventTypeEnum.LineLost.getCode());
            } else if (mpEvent instanceof MpEventOverLoad) {
                mpDeviceEvent.setType(EventTypeEnum.OverLoad.getCode());
            } else if (mpEvent instanceof MpEventQuality) {
                mpDeviceEvent.setType(EventTypeEnum.Quality.getCode());
            } else if (mpEvent instanceof MpEventMeter) {
                mpDeviceEvent.setType(EventTypeEnum.Meter.getCode());
            }
            return mpDeviceEvent;
        }).collect(Collectors.toList());

    }

    /**
     * 根据台区报告id查询所有设备当前的事件
     * @param reportId
     * @return
     */
    @Override
    public List<MpDeviceEvent> findEventsByReportId(Long reportId) {
        QueryWrapper<MpDeviceEvent> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MpDeviceEvent::getTerminalReportId, reportId);

        return list(wrapper);
    }
}
