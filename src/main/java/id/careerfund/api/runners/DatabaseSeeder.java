package id.careerfund.api.runners;

import id.careerfund.api.domains.EPaymentType;
import id.careerfund.api.domains.ERole;
import id.careerfund.api.domains.entities.*;
import id.careerfund.api.domains.entities.Class;
import id.careerfund.api.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Transactional
@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final InterestRepository interestRepository;
    private final InstitutionRepository institutionRepository;
    private final BootcampRepository bootcampRepository;
    private final ClassRepository classRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final BankRepository bankRepository;
    private final BalanceRepository balanceRepository;

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
        admin.setEmail("aal@careerfund.com");
        admin.setPassword(passwordEncoder.encode("tothemoon"));
        registerAdminIfNotExists(admin);

        User lender = new User();
        lender.setIsEnabled(true);
        lender.setEmail("lender@careerfund.com");
        lender.setName("Lender");
        lender.setPassword(passwordEncoder.encode("1234"));
        lender.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_LENDER),
                roleRepository.findByName(ERole.ROLE_USER)));
        Balance lenderBalance = new Balance();
        balanceRepository.save(lenderBalance);
        lender.setBalance(lenderBalance);
        registerUserIfNotExists(lender);

        User Borrower = new User();
        Borrower.setIsEnabled(true);
        Borrower.setEmail("borrower@careerfund.com");
        Borrower.setName("Borrower");
        Borrower.setPassword(passwordEncoder.encode("1234"));
        Borrower.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_BORROWER),
                roleRepository.findByName(ERole.ROLE_USER)));
        Balance borrowerBalance = new Balance();
        balanceRepository.save(borrowerBalance);
        Borrower.setBalance(borrowerBalance);
        registerUserIfNotExists(Borrower);

        // User dump = new User();
        // dump.setEmail("dump.file17@gmail.com");
        // dump.setName("Dump");
        // dump.setPassword(passwordEncoder.encode("1234"));
        // dump.setRoles(Arrays.asList(roleRepository.findByName(ERole.ROLE_BORROWER),
        // roleRepository.findByName(ERole.ROLE_USER)));
        // registerUserIfNotExists(dump);
    }

    private void saveInterests() {
        Interest fed = new Interest();
        fed.setName("Front End Development");
        saveInterestIfNotExists(fed);

        Interest bed = new Interest();
        bed.setName("Back End Development");
        saveInterestIfNotExists(bed);

        Interest uid = new Interest();
        uid.setName("UI UX Design");
        saveInterestIfNotExists(uid);

        Interest ds = new Interest();
        ds.setName("Data Science");
        saveInterestIfNotExists(ds);

        Interest dm = new Interest();
        dm.setName("Digital Marketing");
        saveInterestIfNotExists(dm);

        Interest ad = new Interest();
        ad.setName("Android Development");
        saveInterestIfNotExists(ad);

        Interest ios = new Interest();
        ios.setName("IOS Development");
        saveInterestIfNotExists(ios);

        Interest pm = new Interest();
        pm.setName("Product Management");
        saveInterestIfNotExists(pm);

        Interest bi = new Interest();
        bi.setName("Business Intelligence");
        saveInterestIfNotExists(bi);

        Interest m = new Interest();
        m.setName("Marketing");
        saveInterestIfNotExists(m);

        Interest vd = new Interest();
        vd.setName("Visual Design");
        saveInterestIfNotExists(vd);

        Interest qa = new Interest();
        qa.setName("Quality Assurance");
        saveInterestIfNotExists(qa);

        Interest devops = new Interest();
        devops.setName("DevOps");
        saveInterestIfNotExists(devops);
    }

    private void saveInstitutions() {
        Institution binar = new Institution();
        binar.setId(1L);
        binar.setName("Binar Academy");
        binar.setLogoPath("https://kerjabilitas.com/user_image/user2/logo_7b6caab85699ca72e06917e9bad7512c.png");
        saveInstitutionIfNotExists(binar);

        Institution purwadhika = new Institution();
        purwadhika.setId(2L);
        purwadhika.setName("Purwadhika");
        purwadhika.setLogoPath(
                "https://yt3.ggpht.com/ytc/AKedOLTSK3UnfTjW8XHFgX-SiVs0ay1vQ5qYnFLj9aCnzg=s900-c-k-c0x00ffffff-no-rj");
        saveInstitutionIfNotExists(purwadhika);

        Institution hacktiv8 = new Institution();
        hacktiv8.setId(3L);
        hacktiv8.setName("Hacktiv8");
        hacktiv8.setLogoPath("https://pbs.twimg.com/profile_images/1303645505465974785/BAedfmOT_400x400.jpg");
        saveInstitutionIfNotExists(hacktiv8);

        Institution alterra = new Institution();
        alterra.setId(4L);
        alterra.setName("Alterra Academy");
        alterra.setLogoPath("https://i.scdn.co/image/26cb339ee5725cacb7285f9940b89dd7f401aeec");
        saveInstitutionIfNotExists(alterra);

        Institution dibimbing = new Institution();
        dibimbing.setId(5L);
        dibimbing.setName("Dibimbing");
        dibimbing.setLogoPath(
                "https://scontent.fcgk9-2.fna.fbcdn.net/v/t1.6435-9/120577459_106427087899427_1734021432716910770_n.png?_nc_cat=104&ccb=1-5&_nc_sid=09cbfe&_nc_eui2=AeHv3ylVlEu5ZbPIsUAvN15NVPEClu393WBU8QKW7f3dYG-SS8BbOXY49bQ7hNPze_y2JnRIEsTDVcdSTDi1jfdh&_nc_ohc=5gY0r8QR3JUAX_Mq9Mn&_nc_oc=AQlqp3TRF70u1N7NUZHs8bZpnlx2YH6ic77oavI-PVA5veLQasQbp0fwBdR3w_Cj094&tn=pkF0IaruVqY03w_s&_nc_ht=scontent.fcgk9-2.fna&oh=00_AT8ZVU1pT4YqYh1rqF6sOjXTeou86y54IhME53UAhpwntQ&oe=61E2334F");
        saveInstitutionIfNotExists(dibimbing);

        Institution rakamin = new Institution();
        rakamin.setId(6L);
        rakamin.setName("Rakamin Academy");
        rakamin.setLogoPath("https://idn-static-assets.s3-ap-southeast-1.amazonaws.com/school/10284.png");
        saveInstitutionIfNotExists(rakamin);

        Institution arkademy = new Institution();
        arkademy.setId(7L);
        arkademy.setName("Arkademy");
        arkademy.setLogoPath("https://cdn-images-1.medium.com/max/1200/1*7ugSMISUo8vYf9ILG6VmuQ.png");
        saveInstitutionIfNotExists(arkademy);
    }

    private void saveBootcamps() {
        Bootcamp baUIX = new Bootcamp();
        baUIX.setId(1L);
        baUIX.setName("UI/UX Research & Design");
        Bootcamp savedBaUIX = saveBootcampIfNotExists(baUIX);
        saveBootcampInstitutionIfNotExists(savedBaUIX, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaUIX, interestRepository.findByName("UI UX Design"));

        Bootcamp baFSW = new Bootcamp();
        baFSW.setId(2L);
        baFSW.setName("Full-Stack Web Development");
        Bootcamp savedBaFSW = saveBootcampIfNotExists(baFSW);
        saveBootcampInstitutionIfNotExists(savedBaFSW, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaFSW, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedBaFSW, interestRepository.findByName("Back End Development"));

        Bootcamp baAndroid = new Bootcamp();
        baAndroid.setId(3L);
        baAndroid.setName("Android Engineering");
        Bootcamp savedBaAndroid = saveBootcampIfNotExists(baAndroid);
        saveBootcampInstitutionIfNotExists(savedBaAndroid, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaAndroid, interestRepository.findByName("Android Development"));

        Bootcamp baPM = new Bootcamp();
        baPM.setId(4L);
        baPM.setName("Product Management");
        Bootcamp savedBaPM = saveBootcampIfNotExists(baPM);
        saveBootcampInstitutionIfNotExists(savedBaPM, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaPM, interestRepository.findByName("Product Management"));

        Bootcamp baBIA = new Bootcamp();
        baBIA.setId(5L);
        baBIA.setName("Business Intelligence Analysis");
        Bootcamp savedBaBIA = saveBootcampIfNotExists(baBIA);
        saveBootcampInstitutionIfNotExists(savedBaBIA, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaBIA, interestRepository.findByName("Business Intelligence"));

        Bootcamp baBIE = new Bootcamp();
        baBIE.setId(6L);
        baBIE.setName("Business Intelligence Engineering");
        Bootcamp savedBaBIE = saveBootcampIfNotExists(baBIE);
        saveBootcampInstitutionIfNotExists(savedBaBIE, institutionRepository.getById(1L));
        saveBootcampCategoryIfNotExists(savedBaBIE, interestRepository.findByName("Business Intelligence"));

        Bootcamp pDSML = new Bootcamp();
        pDSML.setId(7L);
        pDSML.setName("Job Connector Data Science & Machine Learning");
        Bootcamp savedPDSML = saveBootcampIfNotExists(pDSML);
        saveBootcampInstitutionIfNotExists(savedPDSML, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPDSML, interestRepository.findByName("Data Science"));

        Bootcamp pUIX = new Bootcamp();
        pUIX.setId(8L);
        pUIX.setName("Job Connector UI/UX Design");
        Bootcamp savedPUIX = saveBootcampIfNotExists(pUIX);
        saveBootcampInstitutionIfNotExists(savedPUIX, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPUIX, interestRepository.findByName("UI UX Design"));

        Bootcamp pFSW = new Bootcamp();
        pFSW.setId(9L);
        pFSW.setName("Job Connector Full Stack Web Development");
        Bootcamp savedPFSW = saveBootcampIfNotExists(pFSW);
        saveBootcampInstitutionIfNotExists(savedPFSW, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPFSW, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedPFSW, interestRepository.findByName("Back End Development"));

        Bootcamp pDM = new Bootcamp();
        pDM.setId(10L);
        pDM.setName("Job Connector Digital Marketing");
        Bootcamp savedPDM = saveBootcampIfNotExists(pDM);
        saveBootcampInstitutionIfNotExists(savedPDM, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPDM, interestRepository.findByName("Digital Marketing"));
        saveBootcampCategoryIfNotExists(savedPDM, interestRepository.findByName("Marketing"));

        Bootcamp pIDM = new Bootcamp();
        pIDM.setId(11L);
        pIDM.setName("Introduction to Digital Marketing");
        Bootcamp savedPIDM = saveBootcampIfNotExists(pIDM);
        saveBootcampInstitutionIfNotExists(savedPIDM, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPIDM, interestRepository.findByName("Digital Marketing"));
        saveBootcampCategoryIfNotExists(savedPIDM, interestRepository.findByName("Marketing"));

        Bootcamp pPerfM = new Bootcamp();
        pPerfM.setId(12L);
        pPerfM.setName("Performance Marketing");
        Bootcamp savedPPerfM = saveBootcampIfNotExists(pPerfM);
        saveBootcampInstitutionIfNotExists(savedPPerfM, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPPerfM, interestRepository.findByName("Marketing"));

        Bootcamp pSDM = new Bootcamp();
        pSDM.setId(13L);
        pSDM.setName("Strategic Digital Marketing for Leaders");
        Bootcamp savedPSDM = saveBootcampIfNotExists(pSDM);
        saveBootcampInstitutionIfNotExists(savedPSDM, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPSDM, interestRepository.findByName("Marketing"));
        saveBootcampCategoryIfNotExists(savedPSDM, interestRepository.findByName("Digital Marketing"));

        Bootcamp pDA = new Bootcamp();
        pDA.setId(14L);
        pDA.setName("Data Analysis");
        Bootcamp savedPDA = saveBootcampIfNotExists(pDA);
        saveBootcampInstitutionIfNotExists(savedPDA, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPDA, interestRepository.findByName("Data Science"));

        Bootcamp pVD = new Bootcamp();
        pVD.setId(15L);
        pVD.setName("Digital Visual Design");
        Bootcamp savedPVD = saveBootcampIfNotExists(pVD);
        saveBootcampInstitutionIfNotExists(savedPVD, institutionRepository.getById(2L));
        saveBootcampCategoryIfNotExists(savedPVD, interestRepository.findByName("Visual Design"));

        Bootcamp hDS = new Bootcamp();
        hDS.setId(16L);
        hDS.setName("Data Science");
        Bootcamp savedHDS = saveBootcampIfNotExists(hDS);
        saveBootcampInstitutionIfNotExists(savedHDS, institutionRepository.getById(3L));
        saveBootcampCategoryIfNotExists(savedHDS, interestRepository.findByName("Data Science"));

        Bootcamp hFSW = new Bootcamp();
        hFSW.setId(17L);
        hFSW.setName("Full Stack Java Script Immersive");
        Bootcamp savedHFSW = saveBootcampIfNotExists(hFSW);
        saveBootcampInstitutionIfNotExists(savedHFSW, institutionRepository.getById(3L));
        saveBootcampCategoryIfNotExists(savedHFSW, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedHFSW, interestRepository.findByName("Back End Development"));

        Bootcamp aaFE = new Bootcamp();
        aaFE.setId(18L);
        aaFE.setName("Front-end Engineering");
        Bootcamp savedAAFE = saveBootcampIfNotExists(aaFE);
        saveBootcampInstitutionIfNotExists(savedAAFE, institutionRepository.getById(4L));
        saveBootcampCategoryIfNotExists(savedAAFE, interestRepository.findByName("Front End Development"));

        Bootcamp aaBE = new Bootcamp();
        aaBE.setId(19L);
        aaBE.setName("Back-end Engineering");
        Bootcamp savedAABE = saveBootcampIfNotExists(aaBE);
        saveBootcampInstitutionIfNotExists(savedAABE, institutionRepository.getById(4L));
        saveBootcampCategoryIfNotExists(savedAABE, interestRepository.findByName("Back End Development"));

        Bootcamp aaQA = new Bootcamp();
        aaQA.setId(20L);
        aaQA.setName("Quality Assurance Engineering");
        Bootcamp savedAAQA = saveBootcampIfNotExists(aaQA);
        saveBootcampInstitutionIfNotExists(savedAAQA, institutionRepository.getById(4L));
        saveBootcampCategoryIfNotExists(savedAAQA, interestRepository.findByName("Quality Assurance"));

        Bootcamp dbDS = new Bootcamp();
        dbDS.setId(21L);
        dbDS.setName("Data Science");
        Bootcamp savedDBDS = saveBootcampIfNotExists(dbDS);
        saveBootcampInstitutionIfNotExists(savedDBDS, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedDBDS, interestRepository.findByName("Data Science"));

        Bootcamp dbDM = new Bootcamp();
        dbDM.setId(22L);
        dbDM.setName("Digital Marketing");
        Bootcamp savedDBDM = saveBootcampIfNotExists(dbDM);
        saveBootcampInstitutionIfNotExists(savedDBDM, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedDBDM, interestRepository.findByName("Digital Marketing"));
        saveBootcampCategoryIfNotExists(savedDBDM, interestRepository.findByName("Marketing"));

        Bootcamp dbUIX = new Bootcamp();
        dbUIX.setId(23L);
        dbUIX.setName("UI/UX Design");
        Bootcamp savedUIX = saveBootcampIfNotExists(dbUIX);
        saveBootcampInstitutionIfNotExists(savedUIX, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedUIX, interestRepository.findByName("UI UX Design"));

        Bootcamp dbPM = new Bootcamp();
        dbPM.setId(24L);
        dbPM.setName("Product Management");
        Bootcamp savedPM = saveBootcampIfNotExists(dbPM);
        saveBootcampInstitutionIfNotExists(savedPM, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedPM, interestRepository.findByName("Product Management"));

        Bootcamp dbFSW = new Bootcamp();
        dbFSW.setId(25L);
        dbFSW.setName("Fullstack Web Development");
        Bootcamp savedDBFSW = saveBootcampIfNotExists(dbFSW);
        saveBootcampInstitutionIfNotExists(savedDBFSW, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedDBFSW, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedDBFSW, interestRepository.findByName("Back End Development"));

        Bootcamp dbMD = new Bootcamp();
        dbMD.setId(26L);
        dbMD.setName("Mobile Development");
        Bootcamp savedDBMD = saveBootcampIfNotExists(dbMD);
        saveBootcampInstitutionIfNotExists(savedDBMD, institutionRepository.getById(5L));
        saveBootcampCategoryIfNotExists(savedDBMD, interestRepository.findByName("Android Development"));
        saveBootcampCategoryIfNotExists(savedDBMD, interestRepository.findByName("IOS Development"));

        Bootcamp rDS = new Bootcamp();
        rDS.setId(27L);
        rDS.setName("Data Science");
        Bootcamp savedRDS = saveBootcampIfNotExists(rDS);
        saveBootcampInstitutionIfNotExists(savedRDS, institutionRepository.getById(6L));
        saveBootcampCategoryIfNotExists(savedRDS, interestRepository.findByName("Data Science"));

        Bootcamp rDM = new Bootcamp();
        rDM.setId(28L);
        rDM.setName("Digital Marketing");
        Bootcamp savedRDM = saveBootcampIfNotExists(rDM);
        saveBootcampInstitutionIfNotExists(savedRDM, institutionRepository.getById(6L));
        saveBootcampCategoryIfNotExists(savedRDM, interestRepository.findByName("Digital Marketing"));
        saveBootcampCategoryIfNotExists(savedRDM, interestRepository.findByName("Marketing"));

        Bootcamp aFSW = new Bootcamp();
        aFSW.setId(29L);
        aFSW.setName("Fullstack Website Developer");
        Bootcamp savedAFSW = saveBootcampIfNotExists(aFSW);
        saveBootcampInstitutionIfNotExists(savedAFSW, institutionRepository.getById(7L));
        saveBootcampCategoryIfNotExists(savedAFSW, interestRepository.findByName("Front End Development"));
        saveBootcampCategoryIfNotExists(savedAFSW, interestRepository.findByName("Back End Development"));

        Bootcamp aDO = new Bootcamp();
        aDO.setId(30L);
        aDO.setName("DevOps Engineer");
        Bootcamp savedADO = saveBootcampIfNotExists(aDO);
        saveBootcampInstitutionIfNotExists(savedADO, institutionRepository.getById(7L));
        saveBootcampCategoryIfNotExists(savedADO, interestRepository.findByName("DevOps"));
    }

    private void saveClasses() {
        Class baUIX = new Class();
        baUIX.setId(1L);
        baUIX.setName("UI/UX Research & Design");
        baUIX.setStartDate(LocalDate.of(2022, 1, 1));
        baUIX.setEndDate(LocalDate.of(2022, 5, 31));
        baUIX.setQuota(80);
        baUIX.setPrice(4999999L);
        baUIX.setBootcamp(bootcampRepository.getById(1L));
        baUIX.setDurationMonth(baUIX.getStartDate().until(baUIX.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baUIX);

        Class baFSW = new Class();
        baFSW.setId(2L);
        baFSW.setName("Full-Stack Web Development");
        baFSW.setStartDate(LocalDate.of(2022, 1, 1));
        baFSW.setEndDate(LocalDate.of(2022, 7, 31));
        baFSW.setQuota(80);
        baFSW.setPrice(6599000L);
        baFSW.setBootcamp(bootcampRepository.getById(2L));
        baFSW.setDurationMonth(baFSW.getStartDate().until(baFSW.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baFSW);

        Class baAndroid = new Class();
        baAndroid.setId(3L);
        baAndroid.setName("Android Engineering");
        baAndroid.setStartDate(LocalDate.of(2022, 2, 1));
        baAndroid.setEndDate(LocalDate.of(2022, 6, 30));
        baAndroid.setQuota(89);
        baAndroid.setPrice(5499999L);
        baAndroid.setBootcamp(bootcampRepository.getById(3L));
        baAndroid.setDurationMonth(baAndroid.getStartDate().until(baAndroid.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baAndroid);

        Class baPM = new Class();
        baPM.setId(4L);
        baPM.setName("Product Management");
        baPM.setStartDate(LocalDate.of(2022, 3, 1));
        baPM.setEndDate(LocalDate.of(2022, 7, 31));
        baPM.setQuota(80);
        baPM.setPrice(5499999L);
        baPM.setBootcamp(bootcampRepository.getById(4L));
        baPM.setDurationMonth(baPM.getStartDate().until(baPM.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baPM);

        Class baBIA = new Class();
        baBIA.setId(5L);
        baBIA.setName("Business Intelligence Analysis");
        baBIA.setStartDate(LocalDate.of(2022, 3, 1));
        baBIA.setEndDate(LocalDate.of(2022, 5, 31));
        baBIA.setQuota(80);
        baBIA.setPrice(3899000L);
        baBIA.setBootcamp(bootcampRepository.getById(5L));
        baBIA.setDurationMonth(baBIA.getStartDate().until(baBIA.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baBIA);

        Class baBIE = new Class();
        baBIE.setId(6L);
        baBIE.setName("Business Intelligence Engineering");
        baBIE.setStartDate(LocalDate.of(2022, 4, 1));
        baBIE.setEndDate(LocalDate.of(2022, 6, 30));
        baBIE.setQuota(80);
        baBIE.setPrice(3999000L);
        baBIE.setBootcamp(bootcampRepository.getById(6L));
        baBIE.setDurationMonth(baBIE.getStartDate().until(baBIE.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(baBIE);

        Class pDSML = new Class();
        pDSML.setId(7L);
        pDSML.setName("Job Connector Data Science & Machine Learning");
        pDSML.setStartDate(LocalDate.of(2022, 5, 1));
        pDSML.setEndDate(LocalDate.of(2022, 8, 31));
        pDSML.setQuota(50);
        pDSML.setPrice(34100000L);
        pDSML.setBootcamp(bootcampRepository.getById(7L));
        pDSML.setDurationMonth(pDSML.getStartDate().until(pDSML.getEndDate(), ChronoUnit.MONTHS));
        saveClassIfNotExists(pDSML);
    }

    private void saveBank() {
        Bank bca = new Bank();
        bca.setId(1L);
        bca.setName("Bank Central Asia");
        bca.setLogoPath("https://statik.tempo.co/data/2019/04/23/id_836405/836405_720.jpg");
        saveBankIfNotExists(bca);

        Bank mandiri = new Bank();
        mandiri.setId(2L);
        mandiri.setName("Bank Mandiri");
        mandiri.setLogoPath("https://pbs.twimg.com/profile_images/829865693185138688/D1y2Ciyn_400x400.jpg");
        saveBankIfNotExists(mandiri);

        Bank bni = new Bank();
        bni.setId(3L);
        bni.setName("Bank BNI");
        bni.setLogoPath("https://pbs.twimg.com/profile_images/1219085693747490816/7CsOD8WC_400x400.jpg");
        saveBankIfNotExists(bni);

        Bank bri = new Bank();
        bri.setId(4L);
        bri.setName("Bank BRI");
        bri.setLogoPath("https://statik.tempo.co/data/2020/01/21/id_908083/908083_720.jpg");
        saveBankIfNotExists(bri);

        Bank bsi = new Bank();
        bsi.setId(5L);
        bsi.setName("Bank Syariah Indonesia");
        bsi.setLogoPath("https://statik.tempo.co/data/2021/02/02/id_997833/997833_720.jpg");
        saveBankIfNotExists(bsi);
    }

    private void savePaymentType() {
        PaymentType virtualAccount = new PaymentType();
        virtualAccount.setId(1L);
        virtualAccount.setName(EPaymentType.VIRTUAL_ACCOUNT);
        savePaymentTypeIfNotExists(virtualAccount);
    }

    private void savePaymentAccount() {
        PaymentAccount bcaVA = new PaymentAccount();
        bcaVA.setId(1L);
        bcaVA.setName("BCA Virtual Account");
        bcaVA.setPaymentType(paymentTypeRepository.getById(1L));
        bcaVA.setNumber(122000000000001L);
        bcaVA.setBank(bankRepository.getById(1L));
        savePaymentAccountIfNotExists(bcaVA);

        PaymentAccount mandiriVA = new PaymentAccount();
        mandiriVA.setId(2L);
        mandiriVA.setName("Mandiri Virtual Account");
        mandiriVA.setPaymentType(paymentTypeRepository.getById(1L));
        mandiriVA.setNumber(123000000000001L);
        mandiriVA.setBank(bankRepository.getById(2L));
        savePaymentAccountIfNotExists(mandiriVA);

        PaymentAccount bniVA = new PaymentAccount();
        bniVA.setId(3L);
        bniVA.setName("BNI Virtual Account");
        bniVA.setPaymentType(paymentTypeRepository.getById(1L));
        bniVA.setNumber(124000000000001L);
        bniVA.setBank(bankRepository.getById(3L));
        savePaymentAccountIfNotExists(bniVA);

        PaymentAccount briVA = new PaymentAccount();
        briVA.setId(4L);
        briVA.setName("BRI Virtual Account");
        briVA.setPaymentType(paymentTypeRepository.getById(1L));
        briVA.setNumber(125000000000001L);
        briVA.setBank(bankRepository.getById(4L));
        savePaymentAccountIfNotExists(briVA);

        PaymentAccount bsiVA = new PaymentAccount();
        bsiVA.setId(5L);
        bsiVA.setName("BSI Virtual Account");
        bsiVA.setPaymentType(paymentTypeRepository.getById(1L));
        bsiVA.setNumber(126000000000001L);
        bsiVA.setBank(bankRepository.getById(5L));
        savePaymentAccountIfNotExists(bsiVA);
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
            user.setIsEnabled(true);
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
        if (interestRepository.findByName(newInterest.getName()) == null) {
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
