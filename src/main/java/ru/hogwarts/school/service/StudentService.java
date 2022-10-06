package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {
    HashMap<Long, Student> mapStudent = new HashMap();
    static Long id;

    static {
        Long id;
    }


    public Student add(Student student){
        mapStudent.put(id,student);
        return student;
    }

    public boolean get(Student student){
        if(mapStudent.containsValue(student)){
            return true;
        }
        return false;
    }
    public Student change(Long id,Student student){
        if (mapStudent.containsKey(id)){
            mapStudent.put(id,student);
        }
        return student;
    }
    public Student remove(Student student){
        if (mapStudent.containsValue(student)){
            mapStudent.remove(student);
        }
        return student;
    }
    public Collection<Student> age(int age){
        Set<Student> result = new HashSet<>();
        for (Student student: mapStudent.values()) {
            if (student.getAge() == age){
                result.add(student);
            }
        }
        return result;
    }

}
