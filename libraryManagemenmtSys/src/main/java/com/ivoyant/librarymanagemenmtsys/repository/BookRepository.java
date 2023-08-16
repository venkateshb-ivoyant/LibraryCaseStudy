package com.ivoyant.librarymanagemenmtsys.repository;

import com.ivoyant.librarymanagemenmtsys.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Books,Long>{

}
