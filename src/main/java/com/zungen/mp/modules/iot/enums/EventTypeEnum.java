package com.zungen.mp.modules.iot.enums;

/**
 * 分析报告-事件类型
 * @author luomingxing
 */
public enum EventTypeEnum {
    /**
     * 未知
     */
    UnKnow(0, "未知"),
    /**
     * 停电事件
     */
    PowerLost(1, "停电事件"),
    /**
     * 台区三相不平衡
     */
    LoadBalance(2, "台区三相不平衡"),
    /**
     * 线损事件
     */
    LineLost(3, "线损事件"),
    /**
     * 负荷超限事件
     */
    OverLoad(4, "负荷超限事件"),
    /**
     * 供电质量事件
     */
    Quality(5, "供电质量事件"),
    /**
     * 电表事件
     */
    Meter(6, "电表事件");


    private Integer code;
    private String name;

    EventTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
