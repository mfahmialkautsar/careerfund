package id.careerfund.api.services;

import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.Role;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.UserRegister;
import id.careerfund.api.repositories.RoleRepository;
import id.careerfund.api.repositories.UserRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            log.error("Email not found {}", email);
            throw new UsernameNotFoundException(String.format("User not found %s", email));
        } else {
            log.info("Email found {}", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName().name())));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public void registerLender(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);
        if (!getIsEmailAvailable(user.getEmail())) throw new Exception("EMAIL_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getEmail(), ERole.ROLE_LENDER);
        addRoleToUser(user.getEmail(), ERole.ROLE_USER);
    }

    @Override
    public void registerBorrower(UserRegister userRegister) throws Exception {
        User user = UserMapper.userRegisterToUser(userRegister);
        if (!getIsEmailAvailable(user.getEmail())) throw new Exception("EMAIL_UNAVAILABLE");
        saveUser(user);
        addRoleToUser(user.getEmail(), ERole.ROLE_BORROWER);
        addRoleToUser(user.getEmail(), ERole.ROLE_USER);
    }

    @Override
    public void registerLenderIfNotExists(UserRegister userRegister) {
        if (getIsEmailAvailable(userRegister.getEmail())) {
            try {
                registerLender(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerBorrowerIfNotExists(UserRegister userRegister) {
        if (getIsEmailAvailable(userRegister.getEmail())) {
            try {
                registerBorrower(userRegister);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void registerAdminIfNotExist(UserRegister userRegister) {
        if (getIsEmailAvailable(userRegister.getEmail())) {
            try {
                User user = UserMapper.userRegisterToUser(userRegister);
                if (!getIsEmailAvailable(user.getEmail())) throw new Exception("EMAIL_UNAVAILABLE");
                saveUser(user);
                addRoleToUser(user.getEmail(), ERole.ROLE_ADMIN);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public Role saveRoleIfNotExists(Role role) {
        if (roleRepo.findByName(role.getName()) == null) {
            return saveRole(role);
        }
        return null;
    }

    @Override
    public User getUser(String email) {
        log.info("Fetching user {}", email);
        return userRepo.findByEmail(email);
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public boolean getIsEmailAvailable(String email) {
        return getUser(email) == null;
    }

    private void saveUser(User user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    private void addRoleToUser(String email, ERole roleName) {
        log.info("Adding role {} to user {}", roleName, email);
        User user = userRepo.findByEmail(email);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }
}
