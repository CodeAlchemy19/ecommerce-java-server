package com.example.server.Student;

import java.time.LocalDate;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
@Entity
public class Student {
    @Id
    
    private Integer id;
    private String name;
    private String email;
    private LocalDate dob;
    public Student() {
    }
    public Student(Integer id, String name, String email, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
}
