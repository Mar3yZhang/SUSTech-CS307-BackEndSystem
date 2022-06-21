package com.sustech.cs307.project2.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

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
public class OrderRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String contractNum;

    private String enterprise;

    private String productModel;

    private Long quantity;

    private String contractManager;

    private LocalDate contractDate;

    private LocalDate estimatedDeliveryDate;

    private LocalDate lodgementDate;

    private String salesmanNum;

    private String contractType;


}
