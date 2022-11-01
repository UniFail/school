package ru.hogwarts.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.service.FacultyService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {

        this.facultyService = facultyService;

    }

    @GetMapping("/{id}")
    public FacultyRecord read(@PathVariable Long id) {
        return facultyService.read(id);
    }

    @PostMapping
    public FacultyRecord create(@RequestBody @Valid FacultyRecord facultyRecord) {
        return facultyService.create(facultyRecord);
    }

    @PutMapping("/{id}")
    public FacultyRecord update(@PathVariable Long id,
                               @RequestBody @Valid FacultyRecord facultyRecord) {
        return facultyService.update(id,facultyRecord);
    }

    @DeleteMapping("/{id}")
    public FacultyRecord delete(@PathVariable Long id) {
        return  facultyService.delete(id);

    }

    @GetMapping(params = "color")
    public Collection<FacultyRecord> findByColor(@RequestParam String color){
        return facultyService.findByColor(color);
    }

    @GetMapping(params = "nameOrColor")
    public Collection<FacultyRecord> findByNameOrColor(@RequestParam String nameOrName){
        return facultyService.findByNameOrColor(nameOrName);
    }

    @GetMapping("/{id}/students")
    public Collection<StudentRecord> getStudentByFaculty(@PathVariable long id){
        return facultyService.getStudentsByFaculty(id);
    }
}
