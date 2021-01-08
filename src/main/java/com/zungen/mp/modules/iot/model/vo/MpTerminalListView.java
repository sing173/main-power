package com.zungen.mp.modules.iot.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 台区终端列表显示vo
 * @author luomingxing
 * TODO 地区属性
 */
@Data
public class MpTerminalListView {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "台区分析报告id")
    private Long reportId;

    @ApiModelProperty(value = "终端设备编号")
    @TableField("deviceId")
    private String deviceId;

    @ApiModelProperty(value = "终端地址 每个终端唯一的编号")
    private String address;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "台区售电量")
    private double energySale;

    @ApiModelProperty(value = "台区供电量")
    private double energySupply;

    @ApiModelProperty(value = "台区线损电量")
    private double energyLost;

    @ApiModelProperty(value = "台区线损值 XX%")
    private double lineLost;

    @ApiModelProperty(value = "停电事件次数")
    private Integer eventPowerLost;

    @ApiModelProperty(value = "台区三相不平衡次数")
    private Integer eventLoadBalance;

    @ApiModelProperty(value = "线损事件次数")
    private Integer eventLineLost;

    @ApiModelProperty(value = "负荷超限事件次数")
    private Integer eventOverLoad;

    @ApiModelProperty(value = "供电质量事件次数")
    private Integer eventQuality;
}
