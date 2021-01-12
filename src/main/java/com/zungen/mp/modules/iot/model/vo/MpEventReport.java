package com.zungen.mp.modules.iot.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 台区终端报告VO
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Data
@ApiModel(value="MpEventReport对象", description="mqtt返回的台区终端报告")
public class MpEventReport implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "台区终端地址")
    private String address;

    @ApiModelProperty(value = "台区售电量")
    private double energySale;

    @ApiModelProperty(value = "台区供电量")
    private double energySupply;

    @ApiModelProperty(value = "台区线损电量")
    private double energyLost;

    @ApiModelProperty(value = "台区线损值 XX%")
    private double linelost;

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


}
