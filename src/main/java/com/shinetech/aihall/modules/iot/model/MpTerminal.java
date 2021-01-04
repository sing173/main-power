package com.shinetech.aihall.modules.iot.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 台区终端
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mp_terminal")
@ApiModel(value="MpTerminal对象", description="台区终端")
public class MpTerminal implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "终端设备编号")
    @TableField("deviceId")
    private String deviceId;

    @ApiModelProperty(value = "终端地址 每个终端唯一的编号")
    private String address;

    @ApiModelProperty(value = "终端软件版本号")
    @TableField("softwareVersion")
    private String softwareVersion;

    @ApiModelProperty(value = "终端软件发布日期")
    @TableField("softwareDate")
    private Date softwareDate;

    @ApiModelProperty(value = "容量信息")
    private String capacity;

    @ApiModelProperty(value = "终端通信协议")
    private String protocol;

    @ApiModelProperty(value = "终端硬件版本号")
    @TableField("hardwareVersion")
    private String hardwareVersion;

    @ApiModelProperty(value = "终端硬件发布日期")
    @TableField("hardwareDate")
    private Date hardwareDate;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
