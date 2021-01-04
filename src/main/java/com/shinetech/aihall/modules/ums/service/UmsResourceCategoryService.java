package com.shinetech.aihall.modules.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.shinetech.aihall.modules.ums.model.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类管理Service
 * Created by admin on 2020/2/5.
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 获取所有资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 创建资源分类
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
