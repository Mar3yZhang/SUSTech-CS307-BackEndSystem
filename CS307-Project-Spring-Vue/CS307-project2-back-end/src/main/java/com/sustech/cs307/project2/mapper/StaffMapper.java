package com.sustech.cs307.project2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sustech.cs307.project2.entity.Staff;
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
@Service
public interface StaffMapper extends BaseMapper<Staff> {

    @Select("select * from staff where number = #{number}")
    Staff getStaffByNumber(@Param("number") String number) throws SQLException;

    @Select("select count(*) from staff where type = #{type}")
    long getCountByType(@Param("type") String type);


    Page<Map<String, Object>> listPage(Page<?> page);

    @Select("select * from staff where number = #{number}")
    Staff selectByNumber(@Param("number") String number);


    @Delete("delete from staff where number = #{number}")
    boolean deleteByNumber(@Param("number") String number);

    @Update("update staff " +
            "set name = #{name}, " +
            "age = #{age}, " +
            "gender = #{gender}, " +
            "mobile_number = #{mobileNumber}, " +
            "type = #{type}, " +
            "supply_center = #{supplyCenter} " +
            "where number = #{number}")
    int updateByName(Staff staff);

    Page<Map<String, Object>> selectByNumberPage(Page<?> page, @Param("number") String name);
}
