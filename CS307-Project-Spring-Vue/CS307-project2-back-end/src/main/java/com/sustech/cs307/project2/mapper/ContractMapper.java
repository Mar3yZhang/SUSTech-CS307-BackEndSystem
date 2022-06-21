package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.Contract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
public interface ContractMapper extends BaseMapper<Contract> {
    @Select("select * from contract where contract_number = #{contract_number}")
    Contract getContractByNumber(@Param("contract_number") String contract_number);

    @Update("update contract set ordernum = ordernum + 1  where contract_number = #{contract_number}")
    void UpdateContractWhenPlaceOrder(@Param("contract_number") String contract_number);

    @Select("select quantity from order_record where product_model = #{product_model} and salesman_num = #{salesman_num} and contract_num = #{contract_num} ")
    long getQuantityBy3Attribute(@Param("product_model") String product_model, @Param("salesman_num") String salesman_num, @Param("contract_num") String contract_num);

    @Update("update contract set ordernum = ordernum - 1 where contract_number = #{contract_num}")
    void orderNumSubOne(@Param("contract_num") String contract_num);

    @Select("select count(*) from contract")
    long getCount();

    List<Map<String, Object>> getContractInfo(@Param("contract_number") String contract_number);
}

