package com.sustech.cs307.project2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sustech.cs307.project2.entity.Staff;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
public interface StaffService extends IService<Staff> {

    boolean initTable();
}
