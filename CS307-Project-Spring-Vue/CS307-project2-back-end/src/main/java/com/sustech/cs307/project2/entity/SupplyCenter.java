package com.sustech.cs307.project2.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author Marcy ZHANG
 * @since 2022-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("supply_center")
public class SupplyCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;


}
