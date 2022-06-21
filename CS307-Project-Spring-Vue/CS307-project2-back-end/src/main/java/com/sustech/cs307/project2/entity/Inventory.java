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
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    private String supplyCenter;

    private String productModel;

    private Long importnum;

    private Long exportnum;

    private Long totalcost;

    private Long totalinterest;

    private Long stockinnum;

    private Long placeordernum;


}
