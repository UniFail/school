package ru.hogwarts.school.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.records.FacultyRecord;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findAllByColor(String color);
    Collection<Faculty> findByNameOrColor(String name,String color);
}
