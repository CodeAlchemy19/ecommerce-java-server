package com.example.server.Accounts;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Account {
    @Id
    private String name;
    private String fatherName;
    
    private LocalDate dob;
private String email;
private Long mobileNumber;
private String address;
private String password;
public Account() {
}
public Account(String name, String fatherName, String userName, LocalDate dob, String email, Long mobileNumber,
        String address, String password) {
    this.name = name;
    this.fatherName = fatherName;
   
    this.dob = dob;
    this.email = email;
    this.mobileNumber = mobileNumber;
    this.address = address;
    this.password = password;
}
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getFatherName() {
    return fatherName;
}
public void setFatherName(String fatherName) {
    this.fatherName = fatherName;
}

public LocalDate getDob() {
    return dob;
}
public void setDob(LocalDate dob) {
    this.dob = dob;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public Long getMobileNumber() {
    return mobileNumber;
}
public void setMobileNumber(Long mobileNumber) {
    this.mobileNumber = mobileNumber;
}
public String getAddress() {
    return address;
}
public void setAddress(String address) {
    this.address = address;
}
public String getPassword() {
    return password;
}
public void setPassword(String password) {
    this.password = password;
}


}
