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
    public class Contract implements Serializable {

    private static final long serialVersionUID=1L;

      private String contractNumber;

    private String contractManagerName;

    private String enterprise;

    private String supplyCenter;

    private Long ordernum;


}
