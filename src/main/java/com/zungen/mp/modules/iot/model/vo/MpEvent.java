package com.zungen.mp.modules.iot.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * 台区报告-事件的基类
 * @author luomingxing
 */
@Data
public class MpEvent {
    /**
     * 设备地址
     */
    public String address;
    /**
     * 事件发生时间
     */
    private Date start;
    /**
     * 事件原因
     */
    private String cause;
    /**
     * 处理建议
     */
    private String suggestion;
}
