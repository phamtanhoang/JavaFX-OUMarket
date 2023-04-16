/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.pojo;

import java.sql.Date;
import java.util.UUID;

/**
 *
 * @author Gol
 */
public class Promotion {

    private String id;
    private Date fromDate;
    private Date toDate;
    private float newPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public float getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(float newPrice) {
        this.newPrice = newPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
    private String productID;

    {
        id = UUID.randomUUID().toString();
    }

    public Promotion(Date fromDate, Date toDate, float newPrice, String productID) {

        this.fromDate = fromDate;
        this.toDate = toDate;
        this.newPrice = newPrice;
        this.productID = productID;
    }

    public Promotion(String id, Date fromDate, Date toDate, float newPrice, String productID) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.newPrice = newPrice;
        this.productID = productID;
    }

}
