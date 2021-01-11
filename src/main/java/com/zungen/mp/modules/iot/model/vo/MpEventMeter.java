package com.zungen.mp.modules.iot.model.vo;

import lombok.Data;

/**
 * 电表事件
 * @author luomingxing
 */
@Data
public class MpEventMeter extends MpEvent{
    /**
     * 事件描述
     */
    private String data;
}
