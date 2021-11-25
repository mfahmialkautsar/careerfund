package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.ClassTemplate;
import id.careerfund.api.domains.models.NewClass;

import java.util.List;

public interface ClassService {
    Boolean classIsExist(Interest interest, Bootcamp bootcamp);

    void saveClass(NewClass newClass);

    void saveClassIfNotExist(NewClass newClass);

    List<Class> getAll();

//    public List<ClassTemplate> getAllClasses();
}
