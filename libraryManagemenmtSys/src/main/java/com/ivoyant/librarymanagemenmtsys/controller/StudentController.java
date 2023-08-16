package com.ivoyant.librarymanagemenmtsys.controller;

import com.ivoyant.librarymanagemenmtsys.entity.Student;
import com.ivoyant.librarymanagemenmtsys.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Student/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/addStudent")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        LocalDate currentDate = LocalDate.now();
        Period age = Period.between(student.getDob(), currentDate);

        int years = age.getYears();

        if (years > 18 && years < 24) {
            studentRepository.save(student);
            return ResponseEntity.ok("Student Successfully Added");
        } else {
            return ResponseEntity.badRequest().body("Student age should be greater than 18 and less than 24.");
        }
    }
    @GetMapping("getAllStudents")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("getStudent/{id}")
    public Optional<Student> getStudentByID(@PathVariable Long id){
        return studentRepository.findById(id);
    }

    @DeleteMapping("deleteStudent/{id}")
    public void deleteStudent(@PathVariable Long id){
       studentRepository.deleteById(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateStudent(@RequestBody Student updatedStudent, @PathVariable Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);

        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();

            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setDob(updatedStudent.getDob());
            existingStudent.setDepartmentName(updatedStudent.getDepartmentName());

            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(existingStudent.getDob(), currentDate);
            int years = age.getYears();

            if (years > 18 && years < 24) {
                studentRepository.save(existingStudent);
                return ResponseEntity.ok("Student updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Student age should be greater than 18 and less than 24.");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
