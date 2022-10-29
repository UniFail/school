package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.records.FacultyRecord;
import ru.hogwarts.school.records.StudentRecord;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final RecordMapper recordmapper;

    public FacultyService(FacultyRepository facultyRepository, RecordMapper recordMapper) {

        this.facultyRepository = facultyRepository;
        this.recordmapper = recordMapper;
    }

    public FacultyRecord create(FacultyRecord facultyRecord){
        return recordmapper.toRecord(facultyRepository.save(recordmapper.toEntity(facultyRecord)));
    }

    public FacultyRecord read(Long id){
        return recordmapper.toRecord(facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException()));
    }
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {

        return facultyRepository.findById(id).get();
    }

    public FacultyRecord editFaculty(Long id, FacultyRecord facultyRecord) {
        Faculty oldFaculty = facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException());
        oldFaculty.setName(facultyRecord.getName());
        oldFaculty.setColor(facultyRecord.getColor());
        return recordmapper.toRecord(facultyRepository.save(oldFaculty));

    }

    public void deleteFaculty(long id) {

        facultyRepository.findById(id);
    }


    public Collection<FacultyRecord> findByColor(String color){
        return facultyRepository.findAllByColor(color).stream()
                .map(recordmapper::toRecord)
                .collect(Collectors.toList());
    }

    public Collection<FacultyRecord> findByNameOrColor(String colorOrName){
        return facultyRepository.findByNameOrColor(colorOrName,colorOrName).stream()
                .map(recordmapper::toRecord)
                .collect(Collectors.toList());
    }
    public Collection<StudentRecord> getStudentsByFaculty(Long id){
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .map(students ->
                        students.stream()
                                .map(recordmapper::toRecord)
                                .collect(Collectors.toList()))
                .orElseThrow(() -> new FacultyNotFoundException());
    }

}