package com.zungen.mp.modules.iot.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zungen.mp.modules.iot.model.MpTerminal;
import com.zungen.mp.modules.iot.mapper.MpTerminalMapper;
import com.zungen.mp.modules.iot.service.MpTerminalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zungen.mp.modules.ums.model.UmsAdmin;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 台区终端 服务实现类
 * </p>
 *
 * @author admin
 * @since 2020-12-31
 */
@Service
public class MpTerminalServiceImpl extends ServiceImpl<MpTerminalMapper, MpTerminal> implements MpTerminalService {

    /**
     * 保存台区终端，数据库存在则更新，反之则新建
     * @param mpTerminal
     * @return
     */
    @Override
    public boolean saveTerminal(MpTerminal mpTerminal) {
        MpTerminal dbTerminal = findByAddress(mpTerminal.getAddress());

        if(dbTerminal == null) {
            //新建台区终端
            mpTerminal.setCreatedTime(new Date());
            return save(mpTerminal);
        } else {
            dbTerminal.setCapacity(mpTerminal.getCapacity());
            dbTerminal.setHardwareDate(mpTerminal.getHardwareDate());
            dbTerminal.setHardwareVersion(mpTerminal.getHardwareVersion());
            dbTerminal.setProtocol(mpTerminal.getProtocol());
            dbTerminal.setSoftwareDate(mpTerminal.getSoftwareDate());
            dbTerminal.setSoftwareDate(mpTerminal.getSoftwareDate());
            dbTerminal.setUpdateTime(new Date());
            return updateById(dbTerminal);
        }
    }

    @Override
    public MpTerminal findByAddress(String address) {
        QueryWrapper<MpTerminal> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(MpTerminal::getAddress, address);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 分页查询台区终端列表
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @Override
    public Page<MpTerminal> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<MpTerminal> page = new Page<>(pageNum,pageSize);
        QueryWrapper<MpTerminal> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<MpTerminal> lambda = wrapper.lambda();
        if(StrUtil.isNotEmpty(keyword)){
            lambda.like(MpTerminal::getAddress,keyword);
            lambda.or().like(MpTerminal::getDeviceId,keyword);
        }
        return page(page,wrapper);
    }
}
