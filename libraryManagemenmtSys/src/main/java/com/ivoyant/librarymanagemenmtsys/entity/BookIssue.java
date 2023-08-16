package com.ivoyant.librarymanagemenmtsys.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
public class BookIssue extends BookAndStudentDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "book_id")
    private Books books;

    private String studentName;
    private String bookName;
    private LocalDate issueDate;
    private LocalDate returnDate;


    public BookIssue() {
        this.issueDate = LocalDate.now();
        this.returnDate = LocalDate.now().plusDays(15);
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

}


