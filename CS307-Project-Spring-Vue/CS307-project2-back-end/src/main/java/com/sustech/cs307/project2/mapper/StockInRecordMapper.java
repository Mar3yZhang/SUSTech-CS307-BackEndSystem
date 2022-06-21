package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.StockInRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Mapper
public interface StockInRecordMapper extends BaseMapper<StockInRecord> {

    @Insert("insert into " +
            "stock_in_record(supply_center,product_model,supply_staff," +
            "date,purchase_price,quantity) values(#{supply_center},#{product_model},#{supply_staff}," +
            "#{date},#{purchase_price},#{quantity})")
    void insertStockInRecord(@Param("supply_center") String supply_center, @Param("product_model") String product_model,
                             @Param("supply_staff") String supply_staff, @Param("date") Date date,
                             @Param("purchase_price") long purchase_price, @Param("quantity") long quantity);
}
