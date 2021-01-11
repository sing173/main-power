package com.zungen.mp.modules.iot.model.vo;

import lombok.Data;

/**
 * 供电质量事件
 * @author luomingxing
 */
@Data
public class MpEventQuality extends MpEvent{
    /**
     * B相电压
     */
    private float voltageA;
    /**
     * A相电压
     */
    private float voltageB;
    /**
     * C相电压
     */
    private float voltageC;
}
