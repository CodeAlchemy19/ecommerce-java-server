package com.example.server.Models;

import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fatherName;
    
    private LocalDate dob;

private String email;
@Column(unique=true)
private Long mobileNumber;
private String address;
private String password;
public Account() {
}
public Account(Long id,String name, String fatherName, String userName, LocalDate dob, String email, Long mobileNumber,
        String address, String password) {
    this.id=id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
