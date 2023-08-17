package com.ivoyant.librarymanagemenmtsys.repository;

import com.ivoyant.librarymanagemenmtsys.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepoitory extends JpaRepository<Department,Long> {
}
