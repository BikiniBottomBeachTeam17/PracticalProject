package com.mustar.entity;

import lombok.Data;

@Data
public class Product {
    private String uuid;
    private String name;
    private String subTitle;
    private String origin;
    private String img;
    private Double price;
    private String querytime;
}
