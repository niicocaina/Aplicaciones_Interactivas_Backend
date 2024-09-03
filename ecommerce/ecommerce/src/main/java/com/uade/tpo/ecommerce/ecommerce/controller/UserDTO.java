package com.uade.tpo.ecommerce.ecommerce.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

public class UserDTO {
 private Long id;
 private String name;
 private String lastName;
 private String userName;
 private String email;
 private String password;
 private Date birthday;

 public UserDTO(Long id) {
  this.id = id;
 }

 //////////////////////////////////////////////////

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public String getLastName() {
  return lastName;
 }

 public void setLastName(String lastName) {
  this.lastName = lastName;
 }

 public String getUserName() {
  return userName;
 }

 public void setUserName(String userName) {
  this.userName = userName;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getPassword() {
  return password;
 }

 public void setPassword(String password) {
  this.password = password;
 }

 public Date getBirthday() {
  return birthday;
 }

 public void setBirthday(Date birthday) {
  this.birthday = birthday;
 }
}
