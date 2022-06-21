package com.sustech.cs307.project2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class StockInRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private String supplyCenter;

    private String productModel;

    private String supplyStaff;

    private LocalDate date;

    private Long purchasePrice;

    private Long quantity;


}
