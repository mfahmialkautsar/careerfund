package id.careerfund.api.runner;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.ERoleRegister;
import id.careerfund.api.domains.entities.Interest;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NewBootcamp;
import id.careerfund.api.domains.models.NewClass;
import id.careerfund.api.domains.models.NewInterest;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.services.BootcampService;
import id.careerfund.api.services.ClassService;
import id.careerfund.api.services.InterestService;
import id.careerfund.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Autowired
    InterestService interestService;

    @Autowired
    BootcampService bootcampService;

    @Autowired
    ClassService classService;

    @Override
    public void run(ApplicationArguments args) {
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_LENDER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_BORROWER));

        User admin = new User();
        admin.setName("Fahmi Al");
        admin.setEmail("aal@email.com");
        admin.setPassword("tothemoon");
        userService.registerAdminIfNotExists(admin);
        userService.registerUserIfNotExists(new UserRegister("Invoker", "invoker@email.com", "1234", ERoleRegister.LENDER));
        userService.registerUserIfNotExists(new UserRegister("Meepo", "meep@email.com", "1234", ERoleRegister.BORROWER));
        userService.registerUserIfNotExists(new UserRegister("Meepo", "dump.file17@gmail.com", "1234", ERoleRegister.BORROWER));

        interestService.saveIfNotExist(new NewInterest("Front End Development"));
        interestService.saveIfNotExist(new NewInterest("Back End Development"));
        interestService.saveIfNotExist(new NewInterest("UI UX Design"));
        interestService.saveIfNotExist(new NewInterest("Data Science"));
        interestService.saveIfNotExist(new NewInterest("Digital Marketing"));
        interestService.saveIfNotExist(new NewInterest("Android Development"));
        interestService.saveIfNotExist(new NewInterest("IOS Development"));

        bootcampService.saveBootcampIfNotExist(new NewBootcamp("SYNRGY", ""));
        bootcampService.saveBootcampIfNotExist(new NewBootcamp("HACKTIV-9", ""));
        bootcampService.saveBootcampIfNotExist(new NewBootcamp("ALTERRY", ""));

        classService.saveClassIfNotExist(new NewClass("SYNRGY", "IOS Development", 3, 20, 20000000));
        classService.saveClassIfNotExist(new NewClass("HACKTIV-9", "IOS Development", 3, 20, 15000000));
        classService.saveClassIfNotExist(new NewClass("ALTERRY", "IOS Development", 4, 20, 35000000));
    }
}
