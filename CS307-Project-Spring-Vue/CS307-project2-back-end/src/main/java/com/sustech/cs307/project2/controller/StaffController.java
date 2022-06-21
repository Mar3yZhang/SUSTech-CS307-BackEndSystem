package com.sustech.cs307.project2.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sustech.cs307.project2.config.Result;
import com.sustech.cs307.project2.entity.Staff;
import com.sustech.cs307.project2.mapper.StaffMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    StaffMapper staffMapper;

    @PostMapping("/insertOneStaff")
    public Result<?> save(@RequestBody Staff staff) {
        if (staffMapper.insert(staff) == 1) {
            return Result.success();
        } else {
            return Result.error("233", "Insert failed");
        }
    }

    @GetMapping("/query")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "20") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Staff> staffPage = staffMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Staff>lambdaQuery().like(Staff::getNumber, search));
        return Result.success(staffPage);
    }

    @GetMapping("/show")
    public Result<?> show(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "20") Integer pageSize,
                          @RequestParam(defaultValue = "") String number) {
        Page<Map<String, Object>> page = new Page<>(pageNum, pageSize);
        Result<Page<Map<String, Object>>> staffResult;
        if (StrUtil.isNotBlank(number)) {
            staffResult = Result.success(staffMapper.selectByNumberPage(page, number));
        } else {
            staffResult = Result.success(staffMapper.listPage(page));
        }
        return staffResult;

    }

    @GetMapping("/listStaffPage")
    public Result<?> listStaffPage(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "20") Integer pageSize, @RequestParam(defaultValue = "") String number) {
        QueryWrapper<Staff> wrapper = null;
        if (StrUtil.isNotBlank(number)) {
            wrapper = new QueryWrapper<Staff>().eq("number", number);
        }
        Page<Staff> page = staffMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(page);
    }

    @DeleteMapping("/delete/{number}")
    public boolean deleteByNumber(@PathVariable String number) {
        Staff staff = staffMapper.selectByNumber(number);
        return staffMapper.deleteByNumber(number);
    }

    @PutMapping("/updateStaff")
    public int updateStaff(@RequestBody Staff staff) {
        return staffMapper.updateByName(staff);
    }

}

