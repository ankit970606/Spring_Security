package org.kit.spring_security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.kit.spring_security.model.Student;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    List<Student> students= new ArrayList<Student>(List.of(
            new Student(1,"Naveen","Java"),
            new Student(2,"Chang'e","Fronted Developer")
    ));
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
       return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("students")
    public List<Student> getStudents() {
        return students;
    }
    @PostMapping("students")
    public void updateStudent(@RequestBody Student student) {
        students.add(student);
    }
}
