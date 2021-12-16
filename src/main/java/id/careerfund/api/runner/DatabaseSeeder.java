package id.careerfund.api.runner;

import id.careerfund.api.domains.EPaymentType;
import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.*;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Transactional
@Component
public class DatabaseSeeder implements ApplicationRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private BootcampRepository bootcampRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Autowired
    private PaymentAccountRepository paymentAccountRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Seeding DB");
        saveRoles();
        saveUsers();
        saveInterests();
        saveInstitutions();
        saveBootcamps();
        saveClasses();
        saveBank();
        savePaymentType();
        savePaymentAccount();
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
        admin.setPassword(passwordEncoder.encode("tothemoon"));
        admin.setIsEnabled(true);
        registerAdminIfNotExists(admin);

        User invoker = new User();
        invoker.setIsEnabled(true);
        invoker.setEmail("invoker@email.com");
        invoker.setName("Invoker");
        invoker.setPassword(passwordEncoder.encode("1234"));
        invoker.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_LENDER), roleRepository.findByName(ERole.ROLE_USER)));
        Balance invokerBalance = new Balance();
        balanceRepository.save(invokerBalance);
        invoker.setBalance(invokerBalance);
        registerUserIfNotExists(invoker);

        User meepo = new User();
        meepo.setIsEnabled(true);
        meepo.setEmail("meep@email.com");
        meepo.setName("Meepo");
        meepo.setPassword(passwordEncoder.encode("1234"));
        meepo.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_BORROWER), roleRepository.findByName(ERole.ROLE_USER)));
        Balance meepoBalance = new Balance();
        balanceRepository.save(meepoBalance);
        meepo.setBalance(meepoBalance);
        registerUserIfNotExists(meepo);

        User dump = new User();
        dump.setEmail("dump.file17@gmail.com");
        dump.setName("Dump");
        dump.setPassword(passwordEncoder.encode("1234"));
        dump.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_BORROWER), roleRepository.findByName(ERole.ROLE_USER)));
        registerUserIfNotExists(dump);
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
        Institution binar = new Institution();
        binar.setId(1L);
        binar.setName("Binar Academy");
        binar.setLogoPath("https://kerjabilitas.com/user_image/user2/logo_7b6caab85699ca72e06917e9bad7512c.png");
        saveInstitutionIfNotExists(binar);

        Institution ada = new Institution();
        ada.setId(2L);
        ada.setName("Apple Developer Academy");
        ada.setLogoPath("https://logique.s3.ap-southeast-1.amazonaws.com/2020/11/apple-developer-academy.jpg");
        saveInstitutionIfNotExists(ada);

        Institution gd = new Institution();
        gd.setId(3L);
        gd.setName("Google Developers");
        gd.setLogoPath("https://www.its.ac.id/matematika/wp-content/uploads/sites/42/2019/05/google-developers.jpg");
        saveInstitutionIfNotExists(gd);

        Institution hacktiv8 = new Institution();
        hacktiv8.setId(4L);
        hacktiv8.setName("Hacktiv8");
        hacktiv8.setLogoPath("https://pbs.twimg.com/profile_images/1303645505465974785/BAedfmOT_400x400.jpg");
        saveInstitutionIfNotExists(hacktiv8);
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

        Bootcamp hacktiv8Web = new Bootcamp(4L, "Hacktiv8 Full Stack", null, new ArrayList<>(), new ArrayList<>());
        Bootcamp savedHacktiv8Web = saveBootcampIfNotExists(hacktiv8Web);
        saveBootcampInstitutionIfNotExists(savedHacktiv8Web, institutionRepository.getById(4L));
        saveBootcampCategoryIfNotExists(savedHacktiv8Web, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedHacktiv8Web, interestRepository.findByName("Back End Development"));
    }

    private void saveClasses() {
        Class binarBED2021 = new Class();
        binarBED2021.setId(1L);
        binarBED2021.setStartDate(LocalDate.of(2021, 4, 1));
        binarBED2021.setEndDate(LocalDate.of(2021, 6, 30));
        binarBED2021.setQuota(80);
        binarBED2021.setPrice(25000000L);
        binarBED2021.setBootcamp(bootcampRepository.getById(1L));
        saveClassIfNotExists(binarBED2021);

        Class ada2022 = new Class();
        ada2022.setId(2L);
        ada2022.setStartDate(LocalDate.of(2022, 2, 1));
        ada2022.setEndDate(LocalDate.of(2022, 12, 31));
        ada2022.setQuota(200);
        ada2022.setPrice(0L);
        ada2022.setBootcamp(bootcampRepository.getById(2L));
        saveClassIfNotExists(ada2022);

        Class gdk2019 = new Class();
        gdk2019.setId(3L);
        gdk2019.setStartDate(LocalDate.of(2019, 6, 1));
        gdk2019.setEndDate(LocalDate.of(2019, 12, 31));
        gdk2019.setQuota(100000);
        gdk2019.setPrice(0L);
        gdk2019.setBootcamp(bootcampRepository.getById(3L));
        saveClassIfNotExists(gdk2019);

        Class hacktiv8Web2022 = new Class();
        hacktiv8Web2022.setId(4L);
        hacktiv8Web2022.setStartDate(LocalDate.of(2022, 7, 1));
        hacktiv8Web2022.setEndDate(LocalDate.of(2024, 6, 30));
        hacktiv8Web2022.setQuota(10000);
        hacktiv8Web2022.setPrice(20000000L);
        hacktiv8Web2022.setBootcamp(bootcampRepository.getById(4L));
        saveClassIfNotExists(hacktiv8Web2022);
    }

    private void saveBank() {
        Bank bank = new Bank(1L, "Bank Central Asia", null);
        saveBankIfNotExists(bank);
    }

    private void savePaymentType() {
        PaymentType virtualAccount = new PaymentType(1L, EPaymentType.VIRTUAL_ACCOUNT, null);
        savePaymentTypeIfNotExists(virtualAccount);
    }

    private void savePaymentAccount() {
        PaymentAccount paymentAccount = new PaymentAccount();
        paymentAccount.setId(1L);
        paymentAccount.setName("BCA");
        paymentAccount.setPaymentType(paymentTypeRepository.getById(1L));
        paymentAccount.setNumber(122000000000001L);
        paymentAccount.setBank(bankRepository.getById(1L));
        savePaymentAccountIfNotExists(paymentAccount);
    }

    private void registerUserIfNotExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            try {
                userRepository.save(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void registerAdminIfNotExists(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            Role role = roleRepository.findByName(ERole.ROLE_ADMIN);
            user.setRoles(Collections.singletonList(role));
            userRepository.save(user);
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

    private void saveBankIfNotExists(Bank bank) {
        if (!bankRepository.existsById(bank.getId())) {
            bankRepository.save(bank);
        }
    }

    private void savePaymentTypeIfNotExists(PaymentType paymentType) {
        if (!paymentTypeRepository.existsById(paymentType.getId())) {
            paymentTypeRepository.save(paymentType);
        }
    }

    private void savePaymentAccountIfNotExists(PaymentAccount paymentAccount) {
        if (!paymentAccountRepository.existsById(paymentAccount.getId())) {
            paymentAccountRepository.save(paymentAccount);
        }
    }
}
