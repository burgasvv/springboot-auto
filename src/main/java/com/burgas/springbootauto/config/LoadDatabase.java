package com.burgas.springbootauto.config;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.*;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.engine.Fuel;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.Role;
import com.burgas.springbootauto.entity.person.Status;
import com.burgas.springbootauto.entity.transmission.DriveType;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.car.*;
import com.burgas.springbootauto.repository.engine.EngineCharacteristicsRepository;
import com.burgas.springbootauto.repository.engine.EngineEditionRepository;
import com.burgas.springbootauto.repository.engine.EngineRepository;
import com.burgas.springbootauto.repository.engine.FuelRepository;
import com.burgas.springbootauto.repository.image.ImageRepository;
import com.burgas.springbootauto.repository.person.PersonRepository;
import com.burgas.springbootauto.repository.person.RoleRepository;
import com.burgas.springbootauto.repository.transmission.DriveTypeRepository;
import com.burgas.springbootauto.repository.transmission.GearboxRepository;
import com.burgas.springbootauto.repository.transmission.TransmissionRepository;
import com.burgas.springbootauto.repository.turbocharging.TurboTypeRepository;
import com.burgas.springbootauto.repository.turbocharging.TurbochargerRepository;
import com.burgas.springbootauto.util.InitDatabaseUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class LoadDatabase {

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    private byte[] readBytesByUrl(String url) {
        InputStream inputStream = new URI(url).toURL().openStream();
        byte[] bytes = inputStream.readAllBytes();
        inputStream.close();
        return bytes;
    }

    @SneakyThrows
    private byte[] readBytesFromFile(String filePath) {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                new FileInputStream(filePath)
        );
        byte[] bytes = bufferedInputStream.readAllBytes();
        bufferedInputStream.close();
        return bytes;
    }

    @Bean
    public CommandLineRunner initDatabase(BrandRepository brandRepository, CarRepository carRepository,
                                          CategoryRepository categoryRepository,
                                          ClassificationRepository classificationRepository, TagRepository tagRepository,
                                          FuelRepository fuelRepository, EngineRepository engineRepository,
                                          EquipmentRepository equipmentRepository,
                                          EngineEditionRepository engineEditionRepository,
                                          EngineCharacteristicsRepository engineCharacteristicsRepository,
                                          GearboxRepository gearboxRepository, DriveTypeRepository driveTypeRepository,
                                          TransmissionRepository transmissionRepository,
                                          TurbochargerRepository turbochargerRepository,
                                          TurboTypeRepository turboTypeRepository, PersonRepository personRepository,
                                          RoleRepository roleRepository, ImageRepository imageRepository,
                                          DriveUnitRepository driveUnitRepository) {

        return _ -> {

            Role adm = Role.builder().name("ADMIN").build();
            Role usr = Role.builder().name("USER").build();

            Image adminImage = Image.builder().isPreview(true).name("admin-image")
                    .data(readBytesByUrl(InitDatabaseUtil.ADMIN_ACCOUNT_IMAGE)).build();
            Person admin = Person.builder()
                    .image(adminImage).enabled(true).status(Status.OFFLINE).firstname("Admin").lastname("Admin").username("admin")
                    .password(passwordEncoder.encode("admin")).email("admin@admin.com").role(adm)
                    .description(InitDatabaseUtil.ADMIN_ACCOUNT_DESCRIPTION).build();

            Image userImage = Image.builder().isPreview(true).name("user-image")
                    .data(readBytesByUrl(InitDatabaseUtil.USER_ACCOUNT_IMAGE)).build();
            Person user = Person.builder().image(userImage).enabled(true).status(Status.OFFLINE).firstname("User").lastname("One").username("user")
                    .password(passwordEncoder.encode("user")).email("user@user.com").role(usr)
                    .description(InitDatabaseUtil.USER_ACCOUNT_DESCRIPTION).build();

            Category hatchBack = Category.builder().name("Хэтчбек").image(InitDatabaseUtil.HATCHBACK_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.HATCHBACK_CATEGORY_DESCRIPTION).build();
            Category coupe = Category.builder().name("Купе").image(InitDatabaseUtil.COUPE_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.COUPE_CATEGORY_DESCRIPTION).build();
            Category sedan = Category.builder().name("Седан").image(InitDatabaseUtil.SEDAN_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.SEDAN_CATEGORY_DESCRIPTION).build();
            Category liftBack = Category.builder().name("Лифтбек").image(InitDatabaseUtil.LIFTBACK_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.LIFTBACK_CATEGORY_DESCRIPTION).build();
            Category fastBack = Category.builder().name("Фастбек").image(InitDatabaseUtil.FASTBACK_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.DASTBACK_CATEGORY_DESCRIPTION).build();
            Category wagon = Category.builder().name("Универсал").image(InitDatabaseUtil.WAGON_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.WAGON_CATEGORY_DESCRIPTION).build();
            Category cabriolet = Category.builder().name("Кабриолет")
                    .image(InitDatabaseUtil.CABRIOLET_CATEGORY_IMAGE).description(InitDatabaseUtil.CABRIOLET_CATEGORY_DESCRIPTION).build();
            Category pickUp = Category.builder().name("Пикап").image(InitDatabaseUtil.PICKUP_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.PICKUP_CATEGORY_DESCRIPTION).build();
            Category crossOver = Category.builder().name("Кроссовер").image(InitDatabaseUtil.CROSSOVER_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.CROSSOVER_CATEGORY_DESCRIPTION).build();
            Category suv = Category.builder().name("Внедорожник/Джип").image(InitDatabaseUtil.SUV_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.SUV_CATEGORY_DESCRIPTION).build();
            Category minivan = Category.builder().name("/Минивэн/Микроавтобус").image(InitDatabaseUtil.MINIVAN_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.MINIVAN_CATEGORY_DESCRIPTION).build();
            Category limousin = Category.builder().name("Лимузин").image(InitDatabaseUtil.LIMOUSIN_CATEGORY_IMAGE)
                    .description(InitDatabaseUtil.LIMOUSIN_CATEGORY_DESCRIPTION).build();

            Classification aClass = Classification.builder().name("A class").image(InitDatabaseUtil.A_CLASS_IMAGE)
                    .description(InitDatabaseUtil.A_CLASS_DESCRIPTION).build();
            Classification bClass = Classification.builder().name("B class").image(InitDatabaseUtil.B_CLASS_IMAGE)
                    .description(InitDatabaseUtil.B_CLASS_DESCRIPTION).build();
            Classification cClass = Classification.builder().name("C class")
                    .image(InitDatabaseUtil.C_CLASS_IMAGE).description(InitDatabaseUtil.C_CLASS_DESCRIPTION).build();
            Classification dClass = Classification.builder().name("D class")
                    .image(InitDatabaseUtil.D_CLASS_IMAGE).description(InitDatabaseUtil.D_CLASS_DESCRIPTION).build();
            Classification eClass = Classification.builder().name("E class")
                    .image(InitDatabaseUtil.E_CLASS_IMAGE).description(InitDatabaseUtil.E_CLASS_DESCRIPTION).build();
            Classification fClass = Classification.builder().name("F class").image(InitDatabaseUtil.F_CLASS_IMAGE)
                    .description(InitDatabaseUtil.F_CLASS_DESCRIPTION).build();
            Classification sClass = Classification.builder().name("S class").image(InitDatabaseUtil.S_CLASS_IMAGE)
                    .description(InitDatabaseUtil.S_CLASS_DESCRIPTION).build();
            Classification mClass = Classification.builder().name("M class").image(InitDatabaseUtil.M_CLASS_IMAGE)
                    .description(InitDatabaseUtil.M_CLASS_DESCRIPTION).build();
            Classification jClass = Classification.builder().name("J class")
                    .image(InitDatabaseUtil.J_CLASS_IMAGE).description(InitDatabaseUtil.J_CLASS_DESCRIPTION).build();

            Tag tag1 = Tag.builder().name("спорт").build();
            Tag tag2 = Tag.builder().name("внедорожник").build();
            Tag tag3 = Tag.builder().name("седан").build();
            Tag tag4 = Tag.builder().name("купе").build();
            Tag tag5 = Tag.builder().name("полный привод").build();
            Tag tag6 = Tag.builder().name("задний привод").build();
            Tag tag7 = Tag.builder().name("передний привод").build();
            Tag tag8 = Tag.builder().name("механика").build();
            Tag tag9 = Tag.builder().name("автомат").build();
            Tag tag10 = Tag.builder().name("спорт кар").build();
            Tag tag11 = Tag.builder().name("комфорт").build();
            Tag tag12 = Tag.builder().name("стиль").build();
            Tag tag13 = Tag.builder().name("two doors").build();
            Tag tag14 = Tag.builder().name("speed").build();
            Tag tag15 = Tag.builder().name("solid").build();
            Tag tag16 = Tag.builder().name("beautiful").build();
            Tag tag17 = Tag.builder().name("phenomenal").build();

            Image bmwImage = Image.builder().name("bmw-image").isPreview(true)
                    .data(readBytesByUrl(InitDatabaseUtil.BMW_BRAND_IMAGE)).build();
            Brand bmw = Brand.builder().title("BMW").image(bmwImage).website(InitDatabaseUtil.BMW_BRAND_WEBSITE)
                    .description(InitDatabaseUtil.BMW_BRAND_DESCRIPTION).build();

            Image audiImage = Image.builder().name("audi-image").isPreview(true)
                    .data(readBytesByUrl(InitDatabaseUtil.AUDI_BRAND_IMAGE)).build();
            Brand audi = Brand.builder().title("Audi").image(audiImage).website(InitDatabaseUtil.AUDI_BRAND_WEBSITE)
                    .description(InitDatabaseUtil.AUDI_BRAND_DESCRIPTION).build();

            Image mercedesImage = Image.builder().name("mercedes-image").isPreview(true)
                    .data(readBytesByUrl(InitDatabaseUtil.MERCEDES_BRAND_IMAGE)).build();
            Brand mercedes = Brand.builder().title("Mercedes").image(mercedesImage)
                    .website(InitDatabaseUtil.MERCEDES_BRAND_WEBSITE).description(InitDatabaseUtil.MERCEDES_BRAND_DESCRIPTION).build();

            DriveUnit frontWheel = DriveUnit.builder().name("Передний привод").build();
            DriveUnit rear = DriveUnit.builder().name("Задний привод").build();
            DriveUnit fourWheel = DriveUnit.builder().name("Полный Привод").build();

            Image m5PreviewImage = Image.builder().name("m5-preview-image").isPreview(true)
                    .data(readBytesFromFile(InitDatabaseUtil.M5_PREVIEW_IMAGE)).build();
            Image m5Image1 = Image.builder().name("m5-image1").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M5_IMAGE_ONE)).build();
            Image m5Image2 = Image.builder().name("m5-image2").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M5_IMAGE_TWO)).build();
            Image m5Image3 = Image.builder().name("m5-image3").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M5_IMAGE_THREE)).build();
            Car m5 = new Car();
            m5.setPerson(admin);
            m5.setTitle("M5");
            m5.setWebpage(InitDatabaseUtil.M5_WEBPAGE);
            m5.setBrand(bmw);
            m5.setDriveUnit(frontWheel);
            m5.setClassification(dClass);
            m5.setCategory(sedan);
            m5.setWeight("2200");
            m5.setHasPreview(true);
            m5.setTags(List.of(tag3,tag9,tag11,tag1,tag12));
            m5.setDescription(InitDatabaseUtil.M5_DESCRIPTION);
            m5.addImages(List.of(m5PreviewImage, m5Image1, m5Image2, m5Image3));

            Image r8PreviewImage = Image.builder().name("r8-preview-image").isPreview(true)
                    .data(readBytesFromFile(InitDatabaseUtil.R8_PREVIEW_IMAGE)).build();
            Image r8Image1 = Image.builder().name("r8-image1").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.R8_IMAGE_ONE)).build();
            Image r8Image2 = Image.builder().name("r8-image2").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.R8_IMAGE_THREE)).build();
            Image r8Image3 = Image.builder().name("r8-image3").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.R8_IMAGE_THREE)).build();
            Car r8 = new Car();
            r8.setPerson(admin);
            r8.setTitle("R8");
            r8.setWebpage(InitDatabaseUtil.R8_WEBPAGE);
            r8.setBrand(audi);
            r8.setDriveUnit(fourWheel);
            r8.setClassification(sClass);
            r8.setCategory(coupe);
            r8.setWeight("1800");
            r8.setHasPreview(true);
            r8.setTags(List.of(tag1,tag10,tag5,tag12,tag4,tag14));
            r8.setDescription(InitDatabaseUtil.R8_DESCRIPTION);
            r8.addImages(List.of(r8PreviewImage, r8Image1, r8Image2, r8Image3));

            Image m4PreviewImage = Image.builder().name("m4-preview-image").isPreview(true)
                    .data(readBytesFromFile(InitDatabaseUtil.M4_PREVIEW_IMAGE)).build();
            Image m4Image1 = Image.builder().name("m4-image1").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M4_IMAGE_ONE)).build();
            Image m4Image2 = Image.builder().name("m4-image2").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M4_IMAGE_TWO)).build();
            Image m4Image3 = Image.builder().name("m4-image3").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.M4_IMAGE_THREE)).build();
            Car m4 = new Car();
            m4.setPerson(admin);
            m4.setTitle("M4");
            m4.setWebpage(InitDatabaseUtil.M4_WEBPAGE);
            m4.setBrand(bmw);
            m4.setDriveUnit(rear);
            m4.setClassification(dClass);
            m4.setCategory(coupe);
            m4.setWeight("2000");
            m4.setHasPreview(true);
            m4.setTags(List.of(tag1,tag4,tag5,tag9,tag12,tag13));
            m4.setDescription(InitDatabaseUtil.M4_DESCRIPTION);
            m4.addImages(List.of(m4PreviewImage, m4Image1, m4Image2, m4Image3));

            Image g63PreviewImage = Image.builder().name("g63-preview-image").isPreview(true)
                    .data(readBytesFromFile(InitDatabaseUtil.G63_PREVIEW_IMAGE)).build();
            Image g63Image1 = Image.builder().name("g63-image1").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.G63_IMAGE_ONE)).build();
            Image g63Image2 = Image.builder().name("g63-image2").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.G63_IMAGE_TWO)).build();
            Image g63Image3 = Image.builder().name("g63-image3").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.G63_IMAGE_THREE)).build();
            Car g63 = new Car();
            g63.setPerson(user);
            g63.setTitle("AMG G-63");
            g63.setWebpage(InitDatabaseUtil.G63_WEBPAGE);
            g63.setBrand(mercedes);
            g63.setDriveUnit(fourWheel);
            g63.setClassification(jClass);
            g63.setCategory(crossOver);
            g63.setWeight("3200");
            g63.setHasPreview(true);
            g63.setTags(List.of(tag2,tag5,tag11,tag12,tag8));
            g63.setDescription(InitDatabaseUtil.G63_DESCRIPTION);
            g63.addImages(List.of(g63PreviewImage, g63Image1, g63Image2, g63Image3));

            Image cls63PreviewImage = Image.builder().name("cls63-preview-image").isPreview(true)
                    .data(readBytesFromFile(InitDatabaseUtil.CLS63_PREVIEW_IMAGE)).build();
            Image cls63Image1 = Image.builder().name("cls63-image1").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.CLS63_IMAGE_ONE)).build();
            Image cls63Image2 = Image.builder().name("cls63-image2").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.CLS63_IMAGE_TWO)).build();
            Image cls63Image3 = Image.builder().name("cls63-image3").isPreview(false)
                    .data(readBytesFromFile(InitDatabaseUtil.CLS63_IMAGE_THREE)).build();
            Car cls63 = new Car();
            cls63.setPerson(user);
            cls63.setTitle("CLS 63 AMG");
            cls63.setWebpage(InitDatabaseUtil.CLS63_WEBPAGE);
            cls63.setBrand(mercedes);
            cls63.setDriveUnit(fourWheel);
            cls63.setClassification(fClass);
            cls63.setCategory(sedan);
            cls63.setWeight("2500");
            cls63.addImages(List.of(cls63PreviewImage,cls63Image1,cls63Image2,cls63Image3));
            cls63.setHasPreview(true);
            cls63.setTags(List.of(tag16,tag3,tag9,tag15,tag5,tag11));
            cls63.setDescription(InitDatabaseUtil.CLS63_DESCRIPTION);

            EngineEdition eeR6 = new EngineEdition();
            eeR6.setName("R6");
            eeR6.setImage(InitDatabaseUtil.R6_IMAGE);
            eeR6.setBrand(bmw);

            Fuel petrol = new Fuel();
            petrol.setName("Petrol");
            Fuel diesel = new Fuel();
            diesel.setName("Diesel");

            Engine en62 = new Engine();
            en62.setName("N62");
            en62.setImage(InitDatabaseUtil.EN62_IMAGE);
            en62.setFuel(petrol);
            en62.setEngineEdition(eeR6);
            en62.setDescription(InitDatabaseUtil.EN62_DESCRIPTION);
            Engine en63 = new Engine();
            en63.setName("N63");
            en63.setImage(InitDatabaseUtil.EN63_IMAGE);
            en63.setFuel(petrol);
            en63.setEngineEdition(eeR6);
            en63.setDescription(InitDatabaseUtil.EN63_DESCRIPTION);

            EngineCharacteristics chem51 = new EngineCharacteristics();
            chem51.setEngine(en62);
            chem51.setRpm("6500");
            chem51.setVolume("3600");
            chem51.setCylinders("8");
            chem51.setTorque("600");

            EngineCharacteristics chem57 = new EngineCharacteristics();
            chem57.setEngine(en63);
            chem57.setRpm("7000");
            chem57.setVolume("4000");
            chem57.setCylinders("8");
            chem57.setTorque("650");

            Gearbox st4 = new Gearbox();
            st4.setName("4-х ступенчатая");
            st4.setStairs(4);
            st4.setImage(InitDatabaseUtil.ST4_IMAGE);
            Gearbox st5 = new Gearbox();
            st5.setName("5-и ступенчатая");
            st5.setStairs(5);
            st5.setImage(InitDatabaseUtil.ST5_IMAGE);
            Gearbox st6 = new Gearbox();
            st6.setName("6-и ступенчатая");
            st6.setStairs(6);
            st6.setImage(InitDatabaseUtil.ST6_IMAGE);
            Gearbox st7 = new Gearbox();
            st7.setName("7-и ступенчатая");
            st7.setStairs(7);
            st7.setImage(InitDatabaseUtil.ST7_IMAGE);
            Gearbox st8 = new Gearbox();
            st8.setName("8-и ступенчатая");
            st8.setStairs(8);
            st8.setImage(InitDatabaseUtil.ST8_IMAGE);

            DriveType mechanic = new DriveType();
            mechanic.setName("Механическая Коробка");
            DriveType autoType = new DriveType();
            autoType.setName("Автоматическая Коробка");
            DriveType robot = new DriveType();
            robot.setName("Роботизированная Коробка");

            Transmission al950 = new Transmission();
            al950.setName("AL950");
            al950.setBrand(audi);
            al950.setGearbox(st6);
            al950.setDriveType(autoType);
            al950.setFinalRatio("4");
            al950.setRatio("2");
            al950.setImage(InitDatabaseUtil.AL950_IMAGE);
            al950.setDescription(InitDatabaseUtil.AL950_DESCRIPTION);
            Transmission al750 = new Transmission();
            al750.setName("AL750");
            al750.setBrand(audi);
            al750.setGearbox(st6);
            al750.setDriveType(autoType);
            al750.setFinalRatio("4");
            al750.setRatio("2");
            al750.setImage(InitDatabaseUtil.AL750_iMAGE);
            al750.setDescription(InitDatabaseUtil.AL750_DESCRIPTION);

            TurboType vtgTurbo = new TurboType();
            vtgTurbo.setName("Турбина с изменяемой геометрией");
            vtgTurbo.setImage(InitDatabaseUtil.VTG_IMAGE);
            vtgTurbo.setDescription(InitDatabaseUtil.VTG_DESCRIPTION);
            TurboType compressor = new TurboType();
            compressor.setName("Турбонаддув с двумя параллельными турбокомпрессорами");
            compressor.setImage(InitDatabaseUtil.COMPRESSOR_IMAGE);
            compressor.setDescription(InitDatabaseUtil.COMPRESSOR_DESCRIPTION);
            TurboType comboTurbo = new TurboType();
            comboTurbo.setName("Комбинированный наддув");
            comboTurbo.setImage(InitDatabaseUtil.COMBOTURBO_IMAGE);
            comboTurbo.setDescription(InitDatabaseUtil.COMBOTURBO_DESCRIPTION);

            Turbocharger gtb2260vk = new Turbocharger();
            gtb2260vk.setName("GTB2260vk");
            gtb2260vk.setBrand(mercedes);
            gtb2260vk.setTurboType(vtgTurbo);
            gtb2260vk.setRpm("1000");
            gtb2260vk.setTorque("200");
            gtb2260vk.setImage(InitDatabaseUtil.GTB2260VK_IMAGE);
            gtb2260vk.setDescription(InitDatabaseUtil.GTB2260VK_DESCRIPTION);
            Turbocharger vagis20 = new Turbocharger();
            vagis20.setName("VAG IS20");
            vagis20.setBrand(audi);
            vagis20.setTurboType(compressor);
            vagis20.setRpm("1300");
            vagis20.setTorque("230");
            vagis20.setImage(InitDatabaseUtil.VAGIS20_IMAGE);
            vagis20.setDescription(InitDatabaseUtil.VAGIS20_DESCRIPTION);
            Turbocharger sk3rr = new Turbocharger();
            sk3rr.setName("SK3RR");
            sk3rr.setBrand(bmw);
            sk3rr.setTurboType(comboTurbo);
            sk3rr.setRpm("1500");
            sk3rr.setTorque("250");
            sk3rr.setImage(InitDatabaseUtil.SK3RR_IMAGE);
            sk3rr.setDescription(InitDatabaseUtil.SK3RR_DESCRIPTION);

            audi.setGearboxes(List.of(st5,st6,st7,st8));
            mercedes.setGearboxes(List.of(st4,st5,st6,st7));
            bmw.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));
            audi.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));
            mercedes.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));

            Equipment equipM5 = new Equipment();
            equipM5.setName("Test 1");
            equipM5.setImage(InitDatabaseUtil.EQUIPM5_IMAGE);
            equipM5.setPerson(admin);
            equipM5.setCar(m5);
            equipM5.setEngine(en63);
            equipM5.setTransmission(al950);
            equipM5.setTurbocharger(gtb2260vk);
            equipM5.setAttached(true);
            Equipment equipM4 = new Equipment();
            equipM4.setName("Test 2");
            equipM4.setImage(InitDatabaseUtil.EQUIPM4_IMAGE);
            equipM4.setPerson(admin);
            equipM4.setCar(m4);
            equipM4.setEngine(en62);
            equipM4.setTransmission(al750);
            equipM4.setTurbocharger(vagis20);
            equipM4.setAttached(true);

            log.info("Preload: {}", roleRepository.saveAll(List.of(adm,usr)));
            log.info("Preload: {}", imageRepository.saveAll(List.of(adminImage, userImage,bmwImage,audiImage,mercedesImage)));
            log.info("Preload: {}", personRepository.saveAll(List.of(admin,user)));
            log.info("Preload: {}", categoryRepository.saveAll(
                    List.of(hatchBack, coupe, sedan, limousin, liftBack, fastBack, wagon, cabriolet, pickUp, crossOver, suv, minivan))
            );
            log.info("Preload: {}", classificationRepository.saveAll(
                    List.of(aClass, bClass, cClass, eClass, dClass, fClass, sClass, mClass, jClass)
            ));
            log.info("Preload: {}", tagRepository.saveAll(
                    List.of(tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10, tag11, tag12, tag13, tag14, tag15, tag16, tag17)
            ));
            log.info("Preload: {}", gearboxRepository.saveAll(List.of(st4, st5, st6, st7, st8)));
            log.info("Preload: {}", turboTypeRepository.saveAll(List.of(vtgTurbo, compressor, comboTurbo)));
            log.info("Preload: {}", brandRepository.saveAll(List.of(bmw, audi, mercedes)));
            log.info("Preload: {}", driveUnitRepository.saveAll(List.of(frontWheel,rear,fourWheel)));
            log.info("Preload: {}", carRepository.saveAll(List.of(m5, m4, r8, g63, cls63)));
            log.info("Preload: {}", engineEditionRepository.saveAll(List.of(eeR6)));
            log.info("Preload: {}", fuelRepository.saveAll(List.of(petrol, diesel)));
            log.info("Preload: {}", engineRepository.saveAll(List.of(en62, en63)));
            log.info("Preload: {}", engineCharacteristicsRepository.saveAll(List.of(chem51, chem57)));
            log.info("Preload: {}", driveTypeRepository.saveAll(List.of(mechanic, autoType, robot)));
            log.info("Preload: {}", transmissionRepository.saveAll(List.of(al950, al750)));
            log.info("Preload: {}", turbochargerRepository.saveAll(List.of(gtb2260vk, vagis20, sk3rr)));
            log.info("Preload: {}", equipmentRepository.saveAll(List.of(equipM5, equipM4)));
        };
    }
}
