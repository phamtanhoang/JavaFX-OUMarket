/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.pojo;

import java.util.UUID;

/**
 *
 * @author Gol
 */
public class Product {

    private String id;
    private String name;
    private String unit;
    private float price;
    private String origin;
    private String categoryID;

    {
        id = UUID.randomUUID().toString();
    }
   public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Product(String name, String unit, float price, String origin, String categoryID) {
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.origin = origin;
        this.categoryID = categoryID;
    }

    public Product(String id, String name, String unit, float price, String origin, String categoryID) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.price = price;
        this.origin = origin;
        this.categoryID = categoryID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return name; 
    }
    
}
