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
public class User {


   private String id;
   private String name;
   private Date dateOfBirth;
   private String sex;
   private String phoneNumber;
   private String address;
   private String role;
   private String email;
   private String username;
   private String password;
   private String branchID;


   {
       id = UUID.randomUUID().toString();
   }


   public User(String name, Date dateOfBirth, String sex, String phoneNumber, String address, String role, String email, String username, String password, String branchID) {
       this.name = name;
       this.dateOfBirth = dateOfBirth;
       this.sex = sex;
       this.phoneNumber = phoneNumber;
       this.address = address;
       this.role = role;
       this.email = email;
       this.username = username;
       this.password = password;
       this.branchID = branchID;
   }


   public User(String id, String name, Date dateOfBirth, String sex, String phoneNumber, String address, String role, String email, String username, String password, String branchID) {
       this.id = id;
       this.name = name;
       this.dateOfBirth = dateOfBirth;
       this.sex = sex;
       this.phoneNumber = phoneNumber;
       this.address = address;
       this.role = role;
       this.email = email;
       this.username = username;
       this.password = password;
       this.branchID = branchID;
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
   public String getAddress() {
       return address;
   }


   public void setAddress(String Address) {
       this.address = Address;
   }


   public Date getDateOfBirth() {
       return dateOfBirth;
   }


   public void setDateOfBirth(Date dateOfBirth) {
       this.dateOfBirth = dateOfBirth;
   }


   public String getSex() {
       return sex;
   }


   public void setSex(String sex) {
       this.sex = sex;
   }


   public String getPhoneNumber() {
       return phoneNumber;
   }


   public void setPhoneNumber(String phoneNumber) {
       this.phoneNumber = phoneNumber;
   }


   public String getRole() {
       return role;
   }


   public void setRole(String role) {
       this.role = role;
   }


   public String getEmail() {
       return email;
   }


   public void setEmail(String email) {
       this.email = email;
   }


   public String getUsername() {
       return username;
   }


   public void setUsername(String username) {
       this.username = username;
   }


   public String getPassword() {
       return password;
   }


   public void setPassword(String password) {
       this.password = password;
   }


   public String getBranchID() {
       return branchID;
   }


   public void setBranchID(String branchID) {
       this.branchID = branchID;
   }




}
