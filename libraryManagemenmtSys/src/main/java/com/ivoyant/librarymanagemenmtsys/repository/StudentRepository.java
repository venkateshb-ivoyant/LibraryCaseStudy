package com.ivoyant.librarymanagemenmtsys.repository;

import com.ivoyant.librarymanagemenmtsys.entity.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    @EntityGraph(attributePaths = "bookIssues")
    Optional<Student> findStudentWithBookIssuesById(Long id);

}
