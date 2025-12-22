package com.example.server.Models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Account implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String fatherName;
    
    private LocalDate dob;

private String email;

private Long mobileNumber;
private String address;
private String password;

}
