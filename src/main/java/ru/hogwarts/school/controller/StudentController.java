package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(path = "/add")
    public Student add(Student student) {
        return studentService.add(student);
    }

    @GetMapping(path = "/get")
    public boolean get(Student student) {
        return studentService.get(student);
    }

    @GetMapping(path = "/change")
    public Student change(Long id, Student student) {
        return studentService.change(id, student);
    }

    @GetMapping(path = "/remove")
    public Student remove(Student student) {
        return studentService.remove(student);
    }

    @GetMapping(path = "/filterAge")
    public Collection<Student> filterAge(int age) {
        return studentService.age(age);
    }
}
