package com.example.server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.Models.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
boolean existsByEmail(String email);
}
