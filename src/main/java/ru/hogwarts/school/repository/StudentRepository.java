package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
//Репозитории третий слой
public interface StudentRepository extends JpaRepository <Student,Long> {
    Collection<Student> findAllByAge(Integer age);
    Collection<Student> findByAgeBetween(Integer min, Integer max);

}
