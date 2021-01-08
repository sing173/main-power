package com.zungen.mp.modules.iot.model.vo;

import com.zungen.mp.modules.iot.model.MpDevice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 拓扑数据vo
 * @author luomingxing
 */
@Data
@ApiModel(value="拓扑数据", description="mqtt返回的拓扑数据vo")
public class MpTopology implements Serializable {
    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "时间标签")
    private Date timeTag;

    @ApiModelProperty(value = "返回码 1：成功；0：失败")
    private Integer RT_F;

    @ApiModelProperty(value = "信息描述 ")
    private String RT_D;

    @ApiModelProperty(value = "台区终端地址 ")
    private String address;

    @ApiModelProperty(value = "拓扑节点内容")
    private List<MpDevice> topology;
}
