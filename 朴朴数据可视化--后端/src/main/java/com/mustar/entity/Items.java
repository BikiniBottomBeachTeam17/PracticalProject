package com.mustar.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物品id
     */
    private String uuid;

    /**
     * 物品名称
     */
    private String name;

    /**
     * 物品简介
     */
    @TableField("subTitle")
    private String subtitle;

    /**
     * 产地
     */
    private String origin;

    /**
     * 图片地址
     */
    private String img;


}
