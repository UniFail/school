package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.service.StudentService;

import javax.validation.Valid;
import java.util.Collection;

//Контроллеры первый слой данных не правильно использовать Сущности в контроллере по этому используем рекорды
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {

        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public StudentRecord read(@PathVariable Long id) {
        return studentService.read(id);
    }

    @PostMapping
    public StudentRecord create(@RequestBody @Valid StudentRecord studentRecord) {
        return studentService.create(studentRecord);
    }

    @PutMapping("/{id}")
    public StudentRecord update(@PathVariable long id,
                               @RequestBody @Valid  StudentRecord student) {
        return studentService.update(id,student);
    }

    @DeleteMapping("/{id}")
    public StudentRecord delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping(params = "age")
    public Collection<StudentRecord> findByAge(@RequestParam Integer age){
        return studentService.findByAge(age);
    }
    @GetMapping(params = {"minAge", "maxAge"})
    public Collection<StudentRecord> findByAgeBetween(@RequestParam Integer min,
                                                      @RequestParam Integer max){
        return studentService.findByAgeBetween(min,max);
    }

    @GetMapping("/{id}/faculty")
    public FacultyRecord getFacultyByStudent(@RequestParam long id){
        return studentService.getFacultyByStudent(id);
    }

    @PatchMapping("/{id}/avatar")
    public StudentRecord patchStudentAvatar(@PathVariable long id,
                                            @RequestParam("avatarId") long avatarId){
        return studentService.patchStudentAvatar(id,avatarId);
    }
}