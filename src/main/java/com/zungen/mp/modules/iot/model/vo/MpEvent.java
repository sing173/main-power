package com.zungen.mp.modules.iot.model.vo;

import lombok.Data;

import java.util.Date;

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
