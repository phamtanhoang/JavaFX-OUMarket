/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lqd.pojo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author Gol
 */
public class Receipt {

    private String id;
    private Date createdDate;
    private float total;
    private float tempTotal;
    private float promotionTotal;
    private float birthDay;
    private String staffID;
    private String customerID;

    {
        id = UUID.randomUUID().toString();
        createdDate = Date.valueOf(LocalDateTime.now().toLocalDate());
    }

    public Receipt() {

    }

    public Receipt(float total, String staffID, String customerID) {

        this.total = total;
        this.staffID = staffID;
        this.customerID = customerID;
    }

    public void setReceipt(float birthDay,float tempTotal, float promotionTotal, float total ) {
        this.total = total;
        this.birthDay = birthDay;
        this.promotionTotal = promotionTotal;
        this.tempTotal = tempTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public float getTempTotal() {
        return tempTotal;
    }

    public void setTempTotal(float tempTotal) {
        this.tempTotal = tempTotal;
    }

    public float getPromotionTotal() {
        return promotionTotal;
    }

    public void setPromotionTotal(float promotionTotal) {
        this.promotionTotal = promotionTotal;
    }

    public float getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(float birthDay) {
        this.birthDay = birthDay;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

}
