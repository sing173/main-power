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
 * 台区设备事件
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mp_device_event")
@ApiModel(value="MpDeviceEvent对象", description="台区设备事件")
public class MpDeviceEvent implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "台区ID")
    private Long terminalId;

    @ApiModelProperty(value = "台区报告ID 对应每一次的台区分析报告")
    private Long terminalReportId;

    @ApiModelProperty(value = "设备地址")
    private String address;

    @ApiModelProperty(value = "事件类型 1 停电事件、2 台区三相不平衡、3 线损事件、4 负荷超限事件、5 供电质量事件、6 电表事件")
    private Integer type;

    @ApiModelProperty(value = "事件内容 每个事件类型有不同的json结构")
    private String eventJson;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;


}
