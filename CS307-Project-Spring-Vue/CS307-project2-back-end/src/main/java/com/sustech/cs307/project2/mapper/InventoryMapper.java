package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.Inventory;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    @Select("select product_model from inventory where supply_center = #{supply_center} and product_model = #{product_model}")
    String selectModelByCenterAndModel(@Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Select("update inventory set importnum = importnum+ #{quantity} ,totalcost = totalcost+ #{extraCost} ," +
            "stockInNum = stockInNum + 1 where supply_center = #{supply_center} and product_model = #{product_model}")
    void updateWhenStockIn(@Param("quantity") long quantity, @Param("extraCost") long extraCost,
                           @Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Insert("insert into inventory(supply_center,product_model,importnum,exportnum,totalcost,totalinterest,stockInNum,placeOrderNum)" +
            " values(#{supply_center},#{product_model},#{importnum},#{exportnum},#{totalcost},#{totalinterest},#{stockInNum},#{placeOrderNum})")
    void insertWhenStockIn(@Param("supply_center") String supply_center, @Param("product_model") String product_model,
                           @Param("importnum") long importnum, @Param("exportnum") long exportnum,
                           @Param("totalcost") long totalcost, @Param("totalinterest") long totalinterest,
                           @Param("stockInNum") long stockInNum, @Param("placeOrderNum") long placeOrderNum) throws SQLException;


    @Select("select count(*) as num from inventory where supply_center = #{supply_center} and product_model = #{product_model}")
    long getValidNumByCenterAndModel(@Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;

    @Select("select (importnum - exportnum) as difference from inventory where supply_center = #{supply_center} and product_model = #{product_model}")
    long getInventoryByCenterAndModel(@Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Update("update inventory set exportNum = exportNum + #{quantity} , totalinterest = totalinterest + #{extracost},placeordernum = placeordernum +1  where supply_center = #{supply_center} and product_model = #{product_model}")
    void updateWhenPlaceOrder(@Param("quantity") long quantity, @Param("extracost") long extracost, @Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Update("update inventory set exportnum = exportnum - #{difference1}," +
            " totalinterest = totalinterest - #{difference2} * #{unit_price}  where product_model = #{product_model} and supply_center = #{supply_center} ")
    void updateWhenUpdateOrder(@Param("difference1") long difference1, @Param("difference2") long difference2, @Param("unit_price") long unit_price, @Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Update("update inventory set exportnum = exportnum - #{quantity1},totalinterest = totalinterest - #{quantity2} * #{unit_price} ,placeordernum = placeordernum -1  where product_model = #{product_model} and supply_center = #{supply_center}")
    void updateWhenDeleteOrder(@Param("quantity1") long quantity1, @Param("quantity2") long quantity2, @Param("unit_price") long unit_price, @Param("supply_center") String supply_center, @Param("product_model") String product_model) throws SQLException;


    @Update("update inventory set placeordernum = placeordernum -1  where product_model = #{product_model} and supply_center = #{supply_center}")
    void updatePlaceOrderNum(@Param("supply_center") String supply_center, @Param("product_model") String product_model);

    @Select("select count(a) from (select product_model, sum(placeOrderNum) as toatlNum from inventory where (importnum - exportnum) > 0 group by product_model) a where toatlNum = 0")
    long getNeverSoldModelCount();


    List<Map<String, Object>> getAvgStockByCenter();

    List<Map<String, Object>> getProductByNumber(@Param("number") String number);

}



