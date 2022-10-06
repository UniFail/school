package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class FacultyService {
    HashMap<Long, Faculty> mapFaculty = new HashMap();
    static Long id;

    static {
        Long id;
    }


    public Faculty add(Faculty faculty){
        mapFaculty.put(id,faculty);
        return faculty;
    }

    public boolean get(Faculty faculty){
        if(mapFaculty.containsValue(faculty)){
            return true;
        }
        return false;
    }
    public Faculty change(Long id,Faculty faculty){
        if (mapFaculty.containsKey(id)){
            mapFaculty.put(id,faculty);
        }
        return faculty;
    }
    public Faculty remove(Faculty faculty){
        if (mapFaculty.containsValue(faculty)){
           mapFaculty.remove(faculty);
        }
        return faculty;
    }
    public Collection<Faculty> color(String color){
        Set<Faculty> result = new HashSet<>();
        for (Faculty faculty: mapFaculty.values()) {
            if (faculty.getColor().equals(color)){
                result.add(faculty);
            }
        }
        return result;
    }
}
