package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.AvatarNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
//Сервисы второй слой данных выше этого слоя сущности входить не должны во избежании проблем
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    private final AvatarRepository avatarRepository;
    private final RecordMapper recordmapper;
    public StudentService(StudentRepository studentRepository,
                          FacultyRepository facultyRepository,
                          AvatarRepository avatarRepository,
                          RecordMapper recordmapper) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
        this.avatarRepository = avatarRepository;
        this.recordmapper = recordmapper;
    }
    public StudentRecord create(StudentRecord studentRecord){
        Student student = recordmapper.toEntity(studentRecord); //Ищем по сущности запись о факультете в бд если есть сохраняем
        student.setFaculty(
                Optional.ofNullable(student.getFaculty())
                        .map(Faculty::getId)
                        .flatMap(facultyRepository::findById)
                        .orElse(null)
        );
        return recordmapper.toRecord(studentRepository.save(student));
    }

    public StudentRecord read(long id){
        return recordmapper.toRecord(studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)));
    }

    public StudentRecord update(long id, StudentRecord studentRecord) {
            Student oldStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id)); //Так же как и в create
            oldStudent.setName(studentRecord.getName());
            oldStudent.setAge(studentRecord.getAge());
            oldStudent.setFaculty(
                    Optional.ofNullable(studentRecord.getFaculty())
                            .map(FacultyRecord::getId)
                            .flatMap(facultyRepository::findById)
                            .orElse(null)
            );
            return recordmapper.toRecord(studentRepository.save(oldStudent));
    }

    public StudentRecord delete(long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
        return recordmapper.toRecord(student);
    }

    public Collection<StudentRecord> findByAge(Integer age){
        return studentRepository.findAllByAge(age).stream()
                .map(recordmapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<StudentRecord> findByAgeBetween(int min, int max){
        return studentRepository.findByAgeBetween(min,max).stream()
                .map(recordmapper::toRecord)
                .collect(Collectors.toList());
    }

    public FacultyRecord getFacultyByStudent(long id){
        return read(id).getFaculty();
    }

    public StudentRecord patchStudentAvatar(long id, long avatarId){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        Optional<Avatar> optionalAvatar = avatarRepository.findById(avatarId);
        if (optionalStudent.isEmpty()){
            throw new StudentNotFoundException(id);
        }
        if (optionalAvatar.isEmpty()){
            throw new AvatarNotFoundException(id);
        }
        Student student = optionalStudent.get();
        student.setAvatar(optionalAvatar.get());
        return recordmapper.toRecord(studentRepository.save(studentRepository.save(student)));
    }

}

