package com.zungen.mp.modules.iot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 台区终端报告
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mp_terminal_report")
@ApiModel(value="MpTerminalReport对象", description="台区终端报告")
public class MpTerminalReport implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "台区ID")
    private Long terminalId;

    @ApiModelProperty(value = "台区售电量")
    private Double energySale;

    @ApiModelProperty(value = "台区供电量")
    private Double energySupply;

    @ApiModelProperty(value = "台区线损电量")
    private Double energyLost;

    @ApiModelProperty(value = "台区线损值 XX%")
    private Double lineLost;

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

    @ApiModelProperty(value = "电表事件次数")
    private Integer eventMeter;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
