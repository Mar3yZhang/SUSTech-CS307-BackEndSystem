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
    public class Enterprise implements Serializable {

    private static final long serialVersionUID=1L;

      private String name;

    private String country;

    private String city;

    private String supplyCenter;

    private String industry;


}
