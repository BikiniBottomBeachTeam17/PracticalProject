package com.mustar.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mustar
 * @since 2022-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Datas implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物品唯一标识
     */
    private String uuid;

    /**
     * 物品现价
     */
    private Double price;

    /**
     * 查询时间
     */
    private String querytime;


}
