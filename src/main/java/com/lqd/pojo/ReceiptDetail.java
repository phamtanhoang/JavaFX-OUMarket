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
public class ReceiptDetail {

    private String id;
    private int quantity;
    private String productID;
    private String receiptID;

    {
        id = UUID.randomUUID().toString();
    }
    public ReceiptDetail(){
        
    }
    public ReceiptDetail(int quantity, String productID, String receiptID) {

        this.quantity = quantity;
        this.productID = productID;
        this.receiptID = receiptID;
    }

    public ReceiptDetail(String id, int quantity, String productID, String receiptID) {
        this.id = id;
        this.quantity = quantity;
        this.productID = productID;
        this.receiptID = receiptID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getReceiptID() {
        return receiptID;
    }

    public void setReceiptID(String receiptID) {
        this.receiptID = receiptID;
    }

}
