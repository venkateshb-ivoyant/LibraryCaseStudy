package com.ivoyant.librarymanagemenmtsys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String description;
    private String author;
    private String isbn;
    private LocalDate originalReleaseDate;
    private String uniqueBookId;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "books",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<BookIssue> bookIssues;

}
