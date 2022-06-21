package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.OrderRecord;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.util.ArrayList;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Mapper
public interface OrderRecordMapper extends BaseMapper<OrderRecord> {
    @Insert("insert into order_record(contract_num,enterprise,product_model," +
            "quantity,contract_manager,contract_date,estimated_delivery_date," +
            "lodgement_date,salesman_num,contract_type) " +
            "values(#{contract_num},#{enterprise},#{product_model}," +
            "#{quantity},#{contract_manager},#{contract_date}," +
            "#{estimated_delivery_date},#{lodgement_date},#{salesman_num},#{contract_type})")
    void insertOrderRecord(@Param("contract_num") String contract_num, @Param("enterprise") String enterprise, @Param("product_model") String product_model,
                           @Param("quantity") long quantity, @Param("contract_manager") String contract_manager, @Param("contract_date") Date contract_date,
                           @Param("estimated_delivery_date") Date estimated_delivery_date, @Param("lodgement_date") Date lodgement_date, @Param("salesman_num") String salesman_num,
                           @Param("contract_type") String contract_type);


    @Select("select * from order_record where product_model = #{product_model} and salesman_num = #{salesman_num} and contract_num = #{contract_num} ")
    OrderRecord getOrderRecordBy3Attribute(@Param("product_model") String product_model, @Param("salesman_num") String salesman_num, @Param("contract_num") String contract_num);


    @Delete("delete from order_record where contract_num = #{contract_num} and product_model = #{product_model} and salesman_num = #{salesman_num}")
    void deleteOrderBy3Attribute(@Param("product_model") String product_model, @Param("salesman_num") String salesman_num, @Param("contract_num") String contract_num);

    @Update("update order_record set quantity = #{quantity} , estimated_delivery_date = #{estimated_delivery_date}, lodgement_date = #{lodgement_date}  where contract_num = #{contract_num} and product_model = #{product_model} and salesman_num = #{salesman_num}")
    void updateOrderBy3Attribute(@Param("quantity") long quantity, @Param("estimated_delivery_date") Date estimated_delivery_date, @Param("lodgement_date") Date lodgement_date, @Param("contract_num") String contract_num, @Param("product_model") String product_model, @Param("salesman_num") String salesman_num);

    @Select("select * from order_record where contract_num = #{contract_num} and salesman_num = #{salesman_num} order by estimated_delivery_date, product_model")
    ArrayList<OrderRecord> getSortedOrderRecord(@Param("contract_num") String contract_num, @Param("salesman_num") String salesman_num);

    @Select("select count(*) from order_record")
    long getCount();
}
