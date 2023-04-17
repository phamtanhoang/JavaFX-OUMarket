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

    private float quantity;
    private String productID;
    private String receiptID;


    public ReceiptDetail(){
        
    }
    public ReceiptDetail(float quantity, String productID, String receiptID) {
        this.quantity = quantity;
        this.productID = productID;
        this.receiptID = receiptID;
    }



    public float getQuantity() {
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
