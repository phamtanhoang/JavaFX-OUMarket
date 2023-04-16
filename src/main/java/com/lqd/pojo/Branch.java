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
public class Branch {
   private String id;
   private String name;
   private String adress;
   {
       id = UUID.randomUUID().toString();
   }
   public Branch(String name, String adress) {
       this.name = name;
       this.adress = adress;
   }
   public Branch(String id, String name, String adress) {
       this.id=id;
       this.name = name;
       this.adress = adress;
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


   public String getAdress() {
       return adress;
   }


   public void setAdress(String adress) {
       this.adress = adress;
   }


   public String getID() {
       throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }


   @Override
   public String toString() {
       return this.name;
   }
}



