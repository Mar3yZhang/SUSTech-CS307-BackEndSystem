package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sustech.cs307.project2.entity.Enterprise;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
public interface EnterpriseMapper extends BaseMapper<Enterprise> {

    @Select("select supply_center from enterprise where name = #{enterprise}")
    String getCenterByEnterprise(@Param("enterprise") String enterprise) throws SQLException;

    @Select("select * from enterprise where name = #{name}")
    Enterprise selectByName(@Param("name") String name);

    Page<Map<String, Object>> selectPage(Page<?> page);

    Page<Map<String, Object>> selectByNamePage(Page<?> page, @Param("name") String name);

    @Delete("delete from enterprise where name = #{enterpriseName}")
    boolean deleteByEnterpriseName(@Param("enterpriseName") String enterpriseName);

    @Update("update enterprise " +
            "set country = #{country}, " +
            "city = #{city}, " +
            "supply_center = #{supplyCenter}, " +
            "industry = #{industry} " +
            "where name = #{name}")
    int updateByEnterpriseName(Enterprise enterprise);

}
