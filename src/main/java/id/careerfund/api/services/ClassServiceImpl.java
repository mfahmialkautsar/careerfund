package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Bootcamp;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.models.NewClass;
import id.careerfund.api.repositories.BootcampRepository;
import id.careerfund.api.repositories.ClassRepository;
import id.careerfund.api.repositories.InterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClassServiceImpl implements ClassService{
    private final ClassRepository classRepo;
    private final InterestRepository interestRepo;
    private final BootcampRepository bootcampRepo;

    @Override
    public Boolean classIsExist(Interest interest, Bootcamp bootcamp) {
        return ObjectUtils.isEmpty(classRepo.findByInterestAndBootcamp(interest, bootcamp));
    }

    @Override
    public void saveClass(NewClass newClass) {
        Class aClass = new Class();

        aClass.setInterest(interestRepo.findByName(newClass.getInterest()));
        aClass.setBootcamp(bootcampRepo.findByName(newClass.getBootcamp()));
        aClass.setDuration(newClass.getDuration());
        aClass.setFee(newClass.getFee());
        aClass.setQuota(newClass.getQuota());

        classRepo.save(aClass);
    }

    @Override
    public void saveClassIfNotExist(NewClass newClass) {
        Interest interest = interestRepo.findByName(newClass.getInterest());
        Bootcamp bootcamp = bootcampRepo.findByName(newClass.getBootcamp());

        Boolean classIsExist = classIsExist(interest, bootcamp);

        if (classIsExist){
            saveClass(newClass);
        }
    }

    @Override
    public List<Class> getAll() {
        return classRepo.findAll();
    }

//    @Override
//    public List<ClassTemplate> getAllClasses() {
//        List<Class> classes = getAll();
//
//        List<ClassTemplate> classTemplates = new
//
//        return classRepo.findAll();
//    }
}
