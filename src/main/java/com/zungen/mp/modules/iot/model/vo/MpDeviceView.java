package com.zungen.mp.modules.iot.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 台区设备显示vo
 * @author luomingxing
 */
@Data
public class MpDeviceView {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "台区终端ID")
    private Long terminalId;

    @ApiModelProperty(value = "台区终端地址")
    private String terminalAddress;

    @ApiModelProperty(value = "设备地址 设备地址/设备编号")
    private String address;

    @ApiModelProperty(value = "层级快捷索引 层级快捷索引，如K1、K1-1、K1-1-1")
    private String shortcut;

    @ApiModelProperty(value = "类型 0：未知， 1：终端，2：分支检测单元，3：表箱检测单元，4：电能表")
    private Integer role;

    @ApiModelProperty(value = "状态 0：未知，1：新增，2：变更，3：删除")
    private Integer status;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @JsonIgnore
    @ApiModelProperty(value = "安装地址")
    private String location;

    @JsonIgnore
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @JsonIgnore
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @JsonIgnore
    @ApiModelProperty(value = "与上级节点的距离")
    private Integer distance;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "子设备")
    private List<MpDeviceView> children;

    @ApiModelProperty(value = "是否发生事件")
    private boolean eventHappen = false;

    @ApiModelProperty(value = "停电事件")
    private List<MpEventPowerLost> eventPowerLost;

    @ApiModelProperty(value = "台区三相不平衡")
    private List<MpEventLoadBalance> eventLoadBalance;

    @ApiModelProperty(value = "线损事件")
    private List<MpEventLineLost> eventLineLost;

    @ApiModelProperty(value = "负荷超限事件")
    private List<MpEventOverLoad> eventOverLoad;

    @ApiModelProperty(value = "供电质量事件")
    private List<MpEventQuality> eventQuality;

    @ApiModelProperty(value = "电表事件")
    private List<MpEventMeter> eventMeter;

    public List<MpEventPowerLost> getEventPowerLost() {
        if(eventPowerLost == null) {
            eventPowerLost = new ArrayList<>();
        }
        return eventPowerLost;
    }

    public List<MpEventLoadBalance> getEventLoadBalance() {
        if(eventLoadBalance == null) {
            eventLoadBalance = new ArrayList<>();
        }
        return eventLoadBalance;
    }

    public List<MpEventLineLost> getEventLineLost() {
        if(eventLineLost == null) {
            eventLineLost = new ArrayList<>();
        }
        return eventLineLost;
    }

    public List<MpEventOverLoad> getEventOverLoad() {
        if(eventOverLoad == null) {
            eventOverLoad = new ArrayList<>();
        }
        return eventOverLoad;
    }

    public List<MpEventQuality> getEventQuality() {
        if(eventQuality == null) {
            eventQuality = new ArrayList<>();
        }
        return eventQuality;
    }

    public List<MpEventMeter> getEventMeter() {
        if(eventMeter == null) {
            eventMeter = new ArrayList<>();
        }
        return eventMeter;
    }
}
