package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final RecordMapper recordmapper;
    public StudentService(StudentRepository studentRepository,RecordMapper recordmapper) {
        this.studentRepository = studentRepository;
        this.recordmapper = recordmapper;
    }
    public StudentRecord create(StudentRecord studentRecord){
        return recordmapper.toRecord(studentRepository.save(recordmapper.toEntity(studentRecord)));
    }

    public StudentRecord read(Long id){
        return recordmapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException()));
    }
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findById(id).get();
    }

    public StudentRecord editStudent(Long id, StudentRecord studentRecord) {
            Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException());
            oldStudent.setName(studentRecord.getName());
            oldStudent.setAge(studentRecord.getAge());
            return recordmapper.toRecord(studentRepository.save(oldStudent));
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }
    public Collection<StudentRecord> findByAge(Integer age){
        return studentRepository.findAllByAge(age).stream()
                .map(recordmapper::toRecord)
                .collect(Collectors.toList());
    }

}

