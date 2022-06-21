package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sustech.cs307.project2.entity.Model;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

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
@Component
@Mapper
public interface ModelMapper extends BaseMapper<Model> {

    //用于检验该product是否存在
    @Select("select product from model where product = #{product}")
    List<String> getProductByProduct(@Param("product") String product);

    @Select("select unit_price from model where model = #{product_model}")
    long getPriceByModel(@Param("product_model") String product_model);

    List<Map<String, Object>> getFavoriteProductModel();
}
