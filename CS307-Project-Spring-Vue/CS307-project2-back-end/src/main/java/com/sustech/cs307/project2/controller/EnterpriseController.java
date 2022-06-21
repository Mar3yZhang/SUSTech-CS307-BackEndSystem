package com.sustech.cs307.project2.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sustech.cs307.project2.config.Result;
import com.sustech.cs307.project2.entity.Enterprise;
import com.sustech.cs307.project2.mapper.EnterpriseMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@RestController
@RequestMapping("/enterprise")
public class EnterpriseController {

    @Resource
    EnterpriseMapper enterpriseMapper;

    @GetMapping("/show")
    public Result<?> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "20") Integer pageSize,
                                @RequestParam(defaultValue = "") String enterpriseName) {
        LambdaQueryWrapper<Enterprise> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(enterpriseName)) {
            wrapper.like(Enterprise::getName, enterpriseName);
        }
        Page<Enterprise> page = enterpriseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(page);

    }

    @PostMapping("/insertOneEnterprise")
    public Result<?> save(@RequestBody Enterprise enterprise) {
        if (enterpriseMapper.insert(enterprise) == 1) {
            return Result.success();
        } else {
            return Result.error("233", "Insert failed");
        }
    }

    @DeleteMapping("/delete/{enterpriseName}")
    public boolean deleteByEnterpriseName(@PathVariable String enterpriseName) {
        return enterpriseMapper.deleteByEnterpriseName(enterpriseName);
    }

    @GetMapping("/query")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "20") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Enterprise> enterprisePage = enterpriseMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Enterprise>lambdaQuery().like(Enterprise::getName, search));
        return Result.success(enterprisePage);
    }


    @PutMapping("/updateEnterprise")
    public int updateEnterprise(@RequestBody Enterprise enterprise) {
        return enterpriseMapper.updateByEnterpriseName(enterprise);
    }
}

