package com.zungen.mp.modules.iot.model.vo;

import lombok.Data;

/**
 * 线损事件
 * @author luomingxing
 */
@Data
public class MpEventLineLost extends MpEvent{
    /**
     * 总售电量
     */
    private double energyTotalSale;
    /**
     * 总供电量
     */
    private double energyTotalSupply;
    /**
     * 线损电量
     */
    private double energyTotalLost;
    /**
     * 线损值(XX%)
     */
    private double linelostTotal;

}
