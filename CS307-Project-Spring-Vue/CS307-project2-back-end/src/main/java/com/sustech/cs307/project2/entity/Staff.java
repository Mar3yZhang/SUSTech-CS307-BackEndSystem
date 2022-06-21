package com.sustech.cs307.project2.entity;

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
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Long age;

    private String gender;

    private String number;

    private String supplyCenter;

    private String mobileNumber;

    private String type;


}
