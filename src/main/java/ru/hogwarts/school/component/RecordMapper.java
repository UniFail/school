package ru.hogwarts.school.component;

import org.springframework.stereotype.Component;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.AvatarRecord;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;

//Осуществляется предача из сущности в класс и обратно что бы избежать ошибок
@Component
public class RecordMapper {
    public StudentRecord toRecord(Student student){
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setId(student.getId());
        studentRecord.setName(student.getName());
        studentRecord.setAge(student.getAge());
        if (student.getFaculty() != null){
            studentRecord.setFaculty(toRecord(student.getFaculty()));
        }
        return studentRecord;
    }
    public FacultyRecord toRecord(Faculty faculty){
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setId(faculty.getId());
        facultyRecord.setName(faculty.getName());
        facultyRecord.setColor(faculty.getColor());
    return facultyRecord;
    }

    /*  public AvatarRecord toRecord(Avatar avatar){
        return new AvatarRecord(
                avatar.getId(),
                avatar.getMediaType(),
                "http://localhost:8080/avatars" + avatar.getId() + "/form-db"
        );
    }

     */
    public Student toEntity(StudentRecord studentRecord){
        Student student = new Student();
        student.setId(studentRecord.getId());
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        if (student.getFaculty() != null){
            Faculty faculty = toEntity(studentRecord.getFaculty());
            faculty.setId(studentRecord.getFaculty().getId());
            student.setFaculty(faculty);
        }
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord){
        Faculty faculty = new Faculty();
        faculty.setId(facultyRecord.getId());
        faculty.setName(facultyRecord.getName());
        faculty.setColor(facultyRecord.getColor());
        return faculty;
    }

}
