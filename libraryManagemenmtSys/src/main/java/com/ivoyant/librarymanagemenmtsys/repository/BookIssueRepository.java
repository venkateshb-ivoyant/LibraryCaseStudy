package com.ivoyant.librarymanagemenmtsys.repository;

import com.ivoyant.librarymanagemenmtsys.entity.BookIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue,Long> {

}
