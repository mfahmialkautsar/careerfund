package id.careerfund.api.runner;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements ApplicationRunner {

    @Autowired
    UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_ADMIN));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_USER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_LENDER));
        userService.saveRoleIfNotExists(new Role(null, ERole.ROLE_BORROWER));

        userService.registerUserIfNotExists(new UserRegister("Fahmi Al", "aal@email.com", "tothemars", ERole.ROLE_ADMIN));
        userService.registerUserIfNotExists(new UserRegister("Invoker", "invoker@email.com", "1234", ERole.ROLE_LENDER));
        userService.registerUserIfNotExists(new UserRegister("Meepo", "meep@email.com", "1234", ERole.ROLE_BORROWER));
    }
}
