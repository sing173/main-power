package com.shinetech.aihall.modules.iot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shinetech.aihall.modules.iot.model.MpDevice;
import com.shinetech.aihall.modules.iot.mapper.MpDeviceMapper;
import com.shinetech.aihall.modules.iot.model.MpTerminal;
import com.shinetech.aihall.modules.iot.service.MpDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 台区设备 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Service
public class MpDeviceServiceImpl extends ServiceImpl<MpDeviceMapper, MpDevice> implements MpDeviceService {
    /**
     * 保存设备
     * @param mpDevice
     * @return
     */
    @Override
    public boolean saveDevice(MpDevice mpDevice) {
        QueryWrapper<MpDevice> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MpDevice::getAddress, mpDevice.getAddress());
        MpDevice dbDevice = baseMapper.selectOne(wrapper);

        if(dbDevice == null) {
            //新建台区设备
            mpDevice.setCreatedTime(new Date());
            return save(mpDevice);
        } else {
            dbDevice.setDistance(mpDevice.getDistance());
            dbDevice.setLastCourts(mpDevice.getLastCourts());
            dbDevice.setLatitude(mpDevice.getLatitude());
            dbDevice.setLevel(mpDevice.getLevel());
            dbDevice.setLine1(mpDevice.getLine1());
            dbDevice.setLine2(mpDevice.getLine2());
            dbDevice.setLine3(mpDevice.getLine3());
            dbDevice.setLocation(mpDevice.getLocation());
            dbDevice.setParent(mpDevice.getParent());
            dbDevice.setProtocol(mpDevice.getProtocol());
            dbDevice.setShortcut(mpDevice.getShortcut());
            dbDevice.setStatus(mpDevice.getStatus());

            dbDevice.setUpdateTime(mpDevice.getUpdateTime());
            return updateById(dbDevice);
        }
    }
}
