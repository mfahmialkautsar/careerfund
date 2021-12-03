package id.careerfund.api.runner;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.ERoleRegister;
import id.careerfund.api.domains.entities.*;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.repositories.*;
import id.careerfund.api.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@Transactional
@Component
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    InterestRepository interestRepository;
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    BootcampRepository bootcampRepository;
    @Autowired
    ClassRepository classRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveInterests();
        saveInstitutions();
        saveBootcamps();
        saveClasses();
    }

    private void saveRoles() {
        saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_LENDER));
        saveRoleIfNotExists(new Role(null, ERole.ROLE_BORROWER));
    }


    private void saveUsers() {
        User admin = new User();
        admin.setName("Fahmi Al");
        admin.setEmail("aal@email.com");
        admin.setPassword("tothemoon");
        registerAdminIfNotExists(admin);
        registerUserIfNotExists(new UserRegister("Invoker", "invoker@email.com", "1234", ERoleRegister.LENDER));
        registerUserIfNotExists(new UserRegister("Meepo", "meep@email.com", "1234", ERoleRegister.BORROWER));
        registerUserIfNotExists(new UserRegister("Meepo", "dump.file17@gmail.com", "1234", ERoleRegister.BORROWER));

    }

    private void saveInterests() {
        saveInterestIfNotExists(new Interest(null, "Front End Development"));
        saveInterestIfNotExists(new Interest(null, "Back End Development"));
        saveInterestIfNotExists(new Interest(null, "UI UX Design"));
        saveInterestIfNotExists(new Interest(null, "Data Science"));
        saveInterestIfNotExists(new Interest(null, "Digital Marketing"));
        saveInterestIfNotExists(new Interest(null, "Android Development"));
        saveInterestIfNotExists(new Interest(null, "IOS Development"));
    }

    private void saveInstitutions() {
        saveInstitutionIfNotExists(new Institution(1L, "Binar Academy", "https://kerjabilitas.com/user_image/user2/logo_7b6caab85699ca72e06917e9bad7512c.png", new ArrayList<>()));
        saveInstitutionIfNotExists(new Institution(2L, "Apple Developer Academy", "https://logique.s3.ap-southeast-1.amazonaws.com/2020/11/apple-developer-academy.jpg", new ArrayList<>()));
        saveInstitutionIfNotExists(new Institution(3L, "Google Developers", "https://www.its.ac.id/matematika/wp-content/uploads/sites/42/2019/05/google-developers.jpg", new ArrayList<>()));
        saveInstitutionIfNotExists(new Institution(4L, "Hacktiv8", "https://pbs.twimg.com/profile_images/1303645505465974785/BAedfmOT_400x400.jpg", new ArrayList<>()));
    }

    private void saveBootcamps() {
        Bootcamp bed = new Bootcamp(1L, "Back End Development", null, new ArrayList<>(), new ArrayList<>());
        Bootcamp savedBed = saveBootcampIfNotExists(bed);
        saveBootcampInstitutionIfNotExists(savedBed, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBed, interestRepository.findByName("Back End Development"));

        Bootcamp ada = new Bootcamp(2L, "Apple Developer Academy", null, new ArrayList<>(), new ArrayList<>());
        Bootcamp savedAda = saveBootcampIfNotExists(ada);
        saveBootcampInstitutionIfNotExists(savedAda, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedAda, interestRepository.findByName("IOS Development"));

        Bootcamp gdk = new Bootcamp(3L, "Google Developers Kejar", null, new ArrayList<>(), new ArrayList<>());
        Bootcamp savedGdk = saveBootcampIfNotExists(gdk);
        saveBootcampInstitutionIfNotExists(savedGdk, institutionRepository.getById(3L));
        saveBootcampCategoryIfNotExists(savedGdk, interestRepository.findByName("Android Development"));
    }

    private void saveClasses() {
        Class binarBED2021 = new Class(1L, null, LocalDate.of(2021, 4, 1), LocalDate.of(2021, 6, 30), 80, 25000000.0, bootcampRepository.getById(1L));
        saveClassIfNotExists(binarBED2021);

        Class ada2022 = new Class(2L, null, LocalDate.of(2022, 2, 1), LocalDate.of(2022, 12, 31), 200, 0.0, bootcampRepository.getById(2L));
        saveClassIfNotExists(ada2022);

        Class gdk2019 = new Class(3L, null, LocalDate.of(2019, 6, 1), LocalDate.of(2019, 12, 31), 100000, 0.0, bootcampRepository.getById(3L));
        saveClassIfNotExists(gdk2019);
    }

    private void registerUserIfNotExists(UserRegister userRegister) {
        if (userRepository.findByEmail(userRegister.getEmail()) == null) {
            try {
                userService.registerUser(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerAdminIfNotExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN);
            user.getRoles().add(role);
        }
    }

    private void saveRoleIfNotExists(Role role) {
        if (roleRepository.findByName(role.getName()) == null) {
            roleRepository.save(role);
        }
    }

    private void saveInterestIfNotExists(Interest newInterest) {
        Interest interest = interestRepository.findByName(newInterest.getName());
        if (ObjectUtils.isEmpty(interest)) {
            interestRepository.save(newInterest);
        }
    }

    private void saveInstitutionIfNotExists(Institution institution) {
        if (institutionRepository.getByName(institution.getName()) == null) {
            institutionRepository.save(institution);
        }
    }

    private Bootcamp saveBootcampIfNotExists(Bootcamp bootcamp) {
        if (!bootcampRepository.existsById(bootcamp.getId())) {
            bootcampRepository.save(bootcamp);
        }
        return bootcampRepository.getById(bootcamp.getId());
    }

    private void saveClassIfNotExists(Class aClass) {
        if (!classRepository.existsById(aClass.getId())) {
            classRepository.save(aClass);
        }
//        return classRepository.getById(aClass.getId());
    }

    private void saveBootcampInstitutionIfNotExists(Bootcamp bootcamp, Institution institution) {
        if (!bootcamp.getInstitutions().contains(institution)) {
            bootcamp.getInstitutions().add(institution);
        }
    }

    private void saveBootcampCategoryIfNotExists(Bootcamp bootcamp, Interest interest) {
        if (!bootcamp.getCategories().contains(interest)) {
            bootcamp.getCategories().add(interest);
        }
    }
}
