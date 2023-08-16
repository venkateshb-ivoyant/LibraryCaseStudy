package com.ivoyant.librarymanagemenmtsys.controller;

import com.ivoyant.librarymanagemenmtsys.repository.DepartmentRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/department")
public class DepartmentController {
    @Autowired
    private DepartmentRepoitory departmentRepoitory;
    

}
