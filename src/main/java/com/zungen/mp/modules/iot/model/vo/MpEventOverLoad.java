package com.zungen.mp.modules.iot.model.vo;


import lombok.Data;

/**
 * 负荷超限事件
 * @author luomingxing
 */
@Data
public class MpEventOverLoad extends MpEvent{
    private double currentA;
    private double currentB;
    private double currentC;
}
