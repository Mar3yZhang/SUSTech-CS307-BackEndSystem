package com.sustech.cs307.project2.controller;


import com.sustech.cs307.project2.entity.SupplyCenter;
import com.sustech.cs307.project2.service.SupplyCenterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@RestController
@RequestMapping("/supplyCenter")
public class SupplyCenterController {
    private final SupplyCenterService centerService;

    public SupplyCenterController(SupplyCenterService centerService) {
        this.centerService = centerService;
    }

    @GetMapping("/centerList")
    public List<SupplyCenter> centerList() {
        return this.centerService.list();
    }
}

