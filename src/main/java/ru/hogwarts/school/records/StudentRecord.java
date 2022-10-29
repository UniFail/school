package ru.hogwarts.school.records;

import io.swagger.v3.oas.models.security.SecurityScheme;
import ru.hogwarts.school.model.Student;

import javax.validation.constraints.NotBlank;

public class StudentRecord {
        private Long id;

        @NotBlank(message = "Name of student is empty")
        private String name;

        @NotBlank(message = "Age of student is empty")
        private Integer age;

        public StudentRecord(){

        }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
