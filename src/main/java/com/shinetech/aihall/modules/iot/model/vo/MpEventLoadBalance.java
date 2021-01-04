package com.shinetech.aihall.modules.iot.model.vo;

import lombok.Data;

/**
 * 三相不平衡事件
 * @author luomingxing
 */
@Data
public class MpEventLoadBalance extends MpEvent{

    /**
     * 三相不平衡度
     */
    private float balance;
    /**
     * A相电流
     */
    private double currentA;
    /**
     * B相电流
     */
    private double currentB;
    /**
     * C相电流
     */
    private double currentC;
}
