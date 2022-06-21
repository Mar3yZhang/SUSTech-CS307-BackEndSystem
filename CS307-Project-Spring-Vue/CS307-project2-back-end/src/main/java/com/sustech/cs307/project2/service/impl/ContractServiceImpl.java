package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sustech.cs307.project2.entity.Contract;
import com.sustech.cs307.project2.mapper.ContractMapper;
import com.sustech.cs307.project2.service.ContractService;
import org.springframework.stereotype.Service;

import java.sql.Date;
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
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements ContractService {
    private final ContractMapper contractMapper;

    public ContractServiceImpl(ContractMapper contractMapper) {
        this.contractMapper = contractMapper;
    }

    @Override
    public long getCount() {
        long num = contractMapper.getCount();
        System.out.println(num);
        return num;
    }

    @Override
    public boolean getContractInfo(String contract_number) {
        List<Map<String, Object>> result = contractMapper.getContractInfo(contract_number);
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s", "supply_center", "salesman", "quantity", "unit_price", "estimated_delivery_date", "lodgement_date");
        System.out.println();
        boolean check = false;
        for (Map<String, Object> item : result) {
            String product_model = (String) item.get("product_model");
            String name = (String) item.get("name");
            long quantity = (Long) item.get("quantity");
            long unit_price = (Long) item.get("unit_price");
            Date estimated_delivery_date = (Date) item.get("estimated_delivery_date");
            Date lodgement_date = (Date) item.get("lodgement_date");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s", product_model, name, quantity, unit_price, estimated_delivery_date, lodgement_date);
            System.out.println();
            check = true;
        }
        return check;
    }
}
