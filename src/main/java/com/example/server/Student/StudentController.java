package com.example.server.Student;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository repo;
    @PostMapping("/api/post")
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        if(student.getEmail()!=null && repo.existsByEmail(student.getEmail())){
           return ResponseEntity.badRequest().body("Student already exists with "+student.getEmail());
        }
        Student saved=repo.save(student);
        return ResponseEntity.ok(saved);
    }
    @GetMapping
    public List<Student> getAllStudents(){
        return repo.findAll();
    }
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id){
        return repo.findById(id).orElse(null);
    }
    @PutMapping("/update/{id}")
    public Student updateStudent(@PathVariable Integer id,@RequestBody Student updatedStudent){
        Student student=repo.findById(id).orElse(null);
        if(student!=null){
             student.setName(updatedStudent.getName());
        student.setEmail(updatedStudent.getEmail());
        student.setDob(updatedStudent.getDob());
        return repo.save(student);
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id){
        repo.deleteById(id);
        return "Student deleted "+id;
    }
    

}
