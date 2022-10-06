package ru.hogwarts.school.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/faculty")
public class FacultyController {
    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping(path = "/add")
    public Faculty add(Faculty faculty) {
        return facultyService.add(faculty);
    }

    @GetMapping(path = "/get")
    public boolean get(Faculty faculty) {
        return facultyService.get(faculty);
    }

    @GetMapping(path = "/change")
    public Faculty change(Long id, Faculty faculty) {
        return facultyService.change(id, faculty);
    }

    @GetMapping(path = "/remove")
    public Faculty remove(Faculty faculty) {
        return facultyService.remove(faculty);
    }

    @GetMapping (path = "/color")
    public Collection<Faculty> getColor(String color){
        return facultyService.color(color);
    }
}
