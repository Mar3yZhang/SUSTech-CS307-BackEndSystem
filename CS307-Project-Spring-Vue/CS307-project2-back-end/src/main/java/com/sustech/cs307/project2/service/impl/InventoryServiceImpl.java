package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.cs307.project2.entity.Inventory;
import com.sustech.cs307.project2.mapper.InventoryMapper;
import com.sustech.cs307.project2.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public long getNeverSoldModelCount() {
        long count = inventoryMapper.getNeverSoldModelCount();
        System.out.println(count);
        return count;
    }

    @Override
    public void getAvgStockByCenter() {
        List<Map<String, Object>> result = inventoryMapper.getAvgStockByCenter();
        for (Map<String, Object> item : result) {
            System.out.printf("%-50s %-20s %.1f", item.get("supply_center"), "", (Double) item.get("avg"));
            System.out.println();
        }
    }

    @Override
    public boolean getProductByNumber(String number) {
        List<Map<String, Object>> result = inventoryMapper.getProductByNumber(number);
        boolean check = false;
        System.out.printf("%-30s %-30s %-30s", "supply_center", "product_model", "quantity");
        System.out.println();
        for (Map<String, Object> item : result) {
            String supply_center = (String) item.get("supply_center");
            String product_model = (String) item.get("product_model");
            long quantity = (Long) item.get("quantity");
            System.out.printf("%-30s %-30s %-30s", supply_center, product_model, quantity);
            System.out.println();
            check = true;
        }
        return check;
    }
}
