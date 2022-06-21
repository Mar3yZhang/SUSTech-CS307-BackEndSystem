package com.sustech.cs307.project2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sustech.cs307.project2.entity.StockInRecord;
import com.sustech.cs307.project2.mapper.*;
import com.sustech.cs307.project2.myUtils.typeTransform;
import com.sustech.cs307.project2.service.StockInRecordService;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Service
public class StockInRecordServiceImpl extends ServiceImpl<StockInRecordMapper, StockInRecord> implements StockInRecordService {

    private final StaffMapper staffMapper;
    private final ModelMapper modelMapper;
    private final SupplyCenterMapper centerRecordMapper;
    private final StockInRecordMapper stockInRecordMapper;
    private final InventoryMapper inventoryMapper;


    public StockInRecordServiceImpl(StaffMapper staffMapper, ModelMapper modelMapper,
                                    SupplyCenterMapper supplyCenterMapper, StockInRecordMapper stockInRecordMapper, InventoryMapper inventoryMapper) {
        this.staffMapper = staffMapper;
        this.modelMapper = modelMapper;
        this.centerRecordMapper = supplyCenterMapper;
        this.stockInRecordMapper = stockInRecordMapper;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public boolean stock_in(String path) {
        boolean MatchOfStaffAndCenter = false;
        boolean isValidType = false;
        boolean hasSuchCenter = false;
        boolean hasSuchProduct = false;
        boolean hasSuchStaff = false;

        String supply_center;
        String product_model;
        String supply_staff;
        String date;
        String purchase_price;
        String quantity;

        try (FileInputStream FileInputStream = new FileInputStream(path);
             InputStreamReader InputStreamReader = new InputStreamReader(FileInputStream, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(InputStreamReader)) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {

                supply_center = nextLine[1];
                product_model = nextLine[2];
                supply_staff = nextLine[3];
                date = nextLine[4];
                purchase_price = nextLine[5];
                quantity = nextLine[6];


                //检验数据是否合法

                hasSuchCenter = (centerRecordMapper.getCenterByName(supply_center) != null);
                hasSuchProduct = (modelMapper.getProductByProduct(typeTransform.ModelToProduct(product_model)).size() != 0);
                hasSuchStaff = (staffMapper.getStaffByNumber(supply_staff) != null);
                if (hasSuchStaff) {
                    isValidType = Objects.equals(staffMapper.getStaffByNumber(supply_staff).getType(), "Supply Staff");
                    MatchOfStaffAndCenter = Objects.equals(staffMapper.getStaffByNumber(supply_staff).getSupplyCenter(), supply_center);
                }

                if (isValidType && hasSuchCenter && hasSuchProduct && hasSuchStaff && MatchOfStaffAndCenter) {
                    stockInRecordMapper.insertStockInRecord(supply_center, product_model, supply_staff,
                            typeTransform.StringToDate(date), Long.parseLong(purchase_price), Long.parseLong(quantity));
                    boolean hasSuchModelInInventory = (inventoryMapper.selectModelByCenterAndModel(supply_center, product_model) != null);

                    int extraCost = Integer.parseInt(purchase_price) * Integer.parseInt(quantity);
                    if (hasSuchModelInInventory) {
                        inventoryMapper.updateWhenStockIn(Long.parseLong(quantity), extraCost, supply_center, product_model);
                    } else {
                        inventoryMapper.insertWhenStockIn(supply_center, product_model, Long.parseLong(quantity), 0, extraCost, 0, 1, 0);
                    }
                }
            }
            return true;
        } catch (IOException | CsvValidationException | SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
