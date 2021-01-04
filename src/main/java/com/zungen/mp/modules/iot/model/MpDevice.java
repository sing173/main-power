package com.zungen.mp.modules.iot.model;

import java.math.BigDecimal;

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
 * 台区设备
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("mp_device")
@ApiModel(value="MpDevice对象", description="台区设备")
public class MpDevice implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "台区终端ID")
    private String terminalId;

    @ApiModelProperty(value = "台区终端地址")
    private String terminalAddress;

    @ApiModelProperty(value = "设备地址 设备地址/设备编号")
    private String address;

    @ApiModelProperty(value = "层级快捷索引 层级快捷索引，如K1、K1-1、K1-1-1")
    private String shortcut;

    @ApiModelProperty(value = "类型 0：未知， 1：终端，2：分支检测单元，3：表箱检测单元，4：电能表")
    private Integer role;

    @ApiModelProperty(value = "协议 0：未知， 1：DL/T645-97， 2:DL/T645-07， 3:DL/T698")
    private Integer protocol;

    @ApiModelProperty(value = "状态 0：未知，1：新增，2：变更，3：删除")
    private Integer status;

    @ApiModelProperty(value = "上级节点地址")
    private String parent;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "L1线序 L1线序，0代表未知，1～3代表终端的三根相线")
    private Integer line1;

    @ApiModelProperty(value = "L2线序 L2线序，0代表未知，1～3代表终端的三根相线")
    private Integer line2;

    @ApiModelProperty(value = "L3线序 L3线序，0代表未知，1～3代表终端的三根相线")
    private Integer line3;

    @ApiModelProperty(value = "安装地址")
    private String location;

    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "上个台区")
    private String lastCourts;

    @ApiModelProperty(value = "与上级节点的距离")
    private Integer distance;

    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;


}
