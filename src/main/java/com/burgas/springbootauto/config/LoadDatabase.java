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
    public CommandLineRunner initDatabase(BrandRepository brandRepository,
                                          CarRepository carRepository,
                                          CategoryRepository categoryRepository,
                                          ClassificationRepository classificationRepository,
                                          TagRepository tagRepository,
                                          FuelRepository fuelRepository,
                                          EngineRepository engineRepository,
                                          EquipmentRepository equipmentRepository,
                                          EngineEditionRepository engineEditionRepository,
                                          EngineCharacteristicsRepository engineCharacteristicsRepository,
                                          GearboxRepository gearboxRepository, DriveTypeRepository driveTypeRepository,
                                          TransmissionRepository transmissionRepository,
                                          TurbochargerRepository turbochargerRepository,
                                          TurboTypeRepository turboTypeRepository,
                                          PersonRepository personRepository,
                                          RoleRepository roleRepository,
                                          ImageRepository imageRepository) {

        return _ -> {

            Role adm = new Role();
            adm.setName("ADMIN");
            Role usr = new Role();
            usr.setName("USER");

            Image adminImage = new Image();
            adminImage.setPreview(true);
            adminImage.setName("admin-image");
            adminImage.setData(
                    readBytesByUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/User-admin.svg/1200px-User-admin.svg.png")
            );
            Person admin = new Person();
            admin.setImage(adminImage);
            admin.setEnabled(true);
            admin.setFirstname("Admin");
            admin.setLastname("Admin");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.com");
            admin.setRole(adm);
            admin.setDescription("Hello everyone! I'm admin on this site!");

            Image userImage = new Image();
            userImage.setPreview(true);
            userImage.setName("user-image");
            userImage.setData(
                    readBytesByUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/828px-User_icon-cp.svg.png")
            );
            Person user = new Person();
            user.setImage(userImage);
            user.setEnabled(true);
            user.setFirstname("User");
            user.setLastname("One");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@user.com");
            user.setRole(usr);
            user.setDescription("Hello everyone! I'm user on this site!");

            Category hatchBack = new Category();
            hatchBack.setName("Hatchback/Хэтчбек");
            hatchBack.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/6ddf532daac4d0d1fbafe343f7624f8a.jpeg");
            hatchBack.setDescription("""
                    Хэтчбек (хетчбэк) — это универсал с коротким задним свесом: кузов автомобиля заканчивается почти сразу за колёсами.\s
                            'За счёт компактности такие машины не испытывают проблем с парковкой и очень удобны для городской эксплуатации.\s
                            'Хэтчбеки могут быть как 3-дверными, так и 5-дверными.Конечно, багажник хэтчбека невелик по сравнению с универсалом.\s
                            'Но всё же это двухобъёмная машина с отдельной задней дверью: если сложить сиденья второго ряда, то в хэтчбеке\s
                            'вполне можно перевезти велосипед или стиральную машину. Поэтому практичность хэтчбеков довольно высока — в Европе\s
                            'и Японии этот тип кузова очень популярен. Эталонный хэтчбек — Volkswagen Golf, название которого стало\s
                            'нарицательным для всего класса таких автомобилей (гольф-класс). Отдельная категория — «горячие хэтчбеки»,\s
                            'оборудованные мощными двигателями: они не только компактные и практичные, но и быстрые. Примеры: Honda Civic\s
                            'Type R, Toyota Yaris GR, Ford Fiesta ST."""
            );
            Category coupe = new Category();
            coupe.setName("Coupe/Купе");
            coupe.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/ef200288dcee57ed3cd56f8a326580de.jpeg");
            coupe.setDescription("""
                    Название происходит от французского «coupe» — «стриженный». Классический вариант купе — это '\s
                            'укороченный двухдверный седан, который отличается 1 рядом сидений и компактным багажником. '\s
                            'Позднее появились варианты с «детским» задним рядом — посадка 2+2. Сейчас купе называют '\s
                            'большинство автомобилей с 2 дверьми."""
            );
            Category sedan = new Category();
            sedan.setName("Sedan/Седан");
            sedan.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/fb139b302a163b25939226963a6cd3af.jpeg");
            sedan.setDescription("""
                    Седан — один из самых распространённых типов кузова. Это 4-дверный (в большинстве случаев) легковой автомобиль '\s
                            'с тремя выраженными объёмами, то есть отдельными отсеками: моторным, пассажирским и багажным. '\s
                            'Заднее стекло седана вклеено в кузов и не двигается при открывании крышки багажника.Структурное отделение '\s
                            'багажника от салона подчёркивает назначение седана — с комфортом возить пассажиров, не докучая им посторонними '\s
                            'звуками вроде скрипа или бряканья груза. Это главное отличие и весомое преимущество такого типа кузова.Кстати, '\s
                            'термин «седан» — далеко не англицизм. Он происходит от итальянских слов sede/sedia (кресло, стул) или, по другой '\s
                            'версии, от названия французского города Седан, где в XIX веке производили кареты. Англичане называют такой кузов '\s
                            'Saloon, а американцы — вообще Notchback. В Европе же нотчбэками именуют седаны с покатой линией крыши и сильно '\s
                            'наклонённой задней стойкой. Очевидный минус седанов — низкая практичность. В них сложно перевозить крупные грузы '\s
                            '(даже велосипеды, о мебели нет и речи), неудобно парковаться из-за выступающей сзади крышки багажника. '\s
                            'Поэтому вместо седанов в мире всё чаще покупают хэтчбеки, лифтбеки, универсалы и кроссоверы. '\s
                            'Но не везде — в России седаны традиционно уважают и любят за имидж премиального автомобиля. '\s
                            'Простой пример: Volkswagen переделал свой бестселлер Polo (изначально хэтчбек) в Polo Sedan специально для российского рынка."""
            );
            Category liftBack = new Category();
            liftBack.setName("Liftback/Лифтбек");
            liftBack.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/61f45b26f447981ceebd03c5885fee18.jpeg");
            liftBack.setDescription("""
                    Лифтбек (лифтбэк) — это попытка скрестить седан с универсалом: первого сделать практичнее, а второго — статуснее и стильнее. '\s
                            'Идея далеко не новая: даже в СССР был свой лифтбек — Иж-2125 «Комби», то есть с комбинированным кузовом. Но такое название '\s
                            'в автоиндустрии не прижилось даже на постсоветском пространстве.В лифтбеке нет структурного отделения багажника от '\s
                            'пассажирского салона, а заднее стекло поднимается вместе с крышкой багажника. Но это именно крышка (довольно покатая), '\s
                            'а не полноценная 5-я дверь, как у хэтчбека или универсала.У лифтбека может быть как 2, так и 3 визуальных объёма — '\s
                            'зависит от конкретной модели. Двухобъёмный лифтбек напоминает хэтчбек с длинным задним свесом. Трёхобъёмный похож '\s
                            'на седан, такие машины любит делать Skoda. Лифтбэки Rapid, Octavia, Superb — вылитые седаны, пока не откроешь багажник."""
            );
            Category fastBack = new Category();
            fastBack.setName("Fastback/Фастбек");
            fastBack.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/a6f5143bef7c16865ec638a1370832f5.jpeg");
            fastBack.setDescription("""
                    Фастбек (фастбэк) нередко путают с лифтбеком. Дословно Fastback («стремительный задок») означает покатую крышу, '\s
                             'без граней заднего стекла переходящую в крышку багажника — у лифтбека эти «ступеньки» довольно выражены. '\s
                             'Также у каноничных фастбеков заднее стекло отделено от крышки багажника и неподвижно, но эта черта постепенно '\s
                             'уходит в прошлое — у современных фастбеков стекло зачастую совмещено с крышкой.Кузов фастбек был очень популярен '\s
                             'в середине прошлого века. Типичный пример фастбека — советская ГАЗ М-20 «Победа» с характерной каплевидной задней '\s
                             'частью. Тогда покатую крышу делали для лучшей обтекаемости кузова, но затем аэродинамика обычных седанов существенно '\s
                             'улучшилась, и мода на фастбеки сошла на нет. В последние годы покатая крыша вновь вернулась в автомобильный дизайн, '\s
                             'причём встречается она у самых разных кузовов: у седанов и купе (Mercedes-Benz CLS), лифтбеков (Porsche Panamera), '\s
                             'кроссоверов (Honda Crosstour). Все они по сути являются фастбеками."""
            );
            Category wagon = new Category();
            wagon.setName("Wagon/Универсал");
            wagon.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/f85db266315778dcb341fdd080a33db4.jpeg");
            wagon.setDescription("""
                    «Почему бы не использовать место, пустующее над крышкой багажника у седана?» — видимо, решили разработчики универсала. '\s
                             'Его главное визуальное отличие — двухобъёмный кузов, в котором пассажирский салон объединён с багажным отсеком.'\s
                             'Крышка багажника универсала — это полноценная 5-я дверь, которая открывается вместе с задним стеклом, обеспечивая '\s
                             'удобную погрузку вещей. Кстати, задний свес универсала не короче (а зачастую длиннее), чем у седана той же модели, '\s
                             'чтобы багажный отсек был как можно просторнее. Ведь именно объём багажника — главный фактор выбора такого типа '\s
                             'кузова.Некоторые автомобилисты награждают универсалы нелестными эпитетами вроде «овощевозки» или «сарая». '\s
                             'Однако у таких машин много поклонников, и не только из-за размеров багажника. Ведь название не врёт: это '\s
                             'действительно универсальные автомобили, объединяющие практичность фургона и комфорт легковой машины. '\s
                             'Есть и отдельные «подвиды»: спортивные универсалы (Nissan Stagea, Audi RS4, Alfa Romeo 159 Sportwagon), '\s
                             'универсалы повышенной проходимости (Subaru Outback, Volvo XC70) и другие.'"""
            );
            Category cabriolet = new Category();
            cabriolet.setName("Cabriolet/Convertible/Roadster/Spider/Кабриолет/Родстер/Спайдер'");
            cabriolet.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/47ae70a6ae0660b9e1657aa6c70fb86f.jpeg");
            cabriolet.setDescription("""
                    В общем смысле кабриолет — любая машина с открытым или откидным верхом. В зависимости от нюансов конструкции '\s
                             'кабриолеты разделяют на несколько видов.Хардтоп-кабриолет — автомобиль с жёсткой складной крышей. '\s
                             'Если в классическом кабриолете верх мягкий, то здесь он металлический, состоит из нескольких секций и '\s
                             'складывается в багажник, подобно трансформеру.Родстер (в некоторых странах его называют «спайдер») — '\s
                             'строго двухместный спортивный кабриолет. Здесь второго ряда сидений нет вообще, даже «детского».'\s
                             'Назначение любого кабриолета понятно — это машина выходного дня. Удовольствие от езды в кабриолете с '\s
                             'открытым верхом сравнимо разве что с поездкой на мотоцикле, только с намного большим комфортом. '\s
                             'Минусов у кабриолета тоже хватает: помимо очевидных проблем с безопасностью и зависимостью от погоды, '\s
                             'отсутствие крыши негативно сказывается на жёсткости кузова. Из-за этого кабриолет обычно проигрывает '\s
                             'купе в управляемости.Кстати, кабриолет — не обязательно очень дорогая машина. «Народные» родстеры Toyota MR-S '\s
                             'или Mazda MX-5 Miata стоят на уровне средних седанов."""
            );
            Category pickUp = new Category();
            pickUp.setName("Pickup/Пикап");
            pickUp.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/3718cc7015d6a05c80df0b6122b84400.jpeg");
            pickUp.setDescription("""
                    Пикап — это грузопассажирский автомобиль с открытым грузовым отсеком, структурно отделённым от кабины. '\s
                             'Как правило, он оснащён зависимой рессорной задней подвеской, обеспечивающей высокую грузоподъёмность.'\s
                             'Пикапы — сугубо утилитарные машины, которые в некоторых странах (прежде всего в США) стали культовыми '\s
                             'среди обычных автовладельцев и используются для повседневной езды, почти как внедорожники в России. Кстати, '\s
                             'в Америке пикапы называют траками (Truck), что дословно переводится как «грузовик». Обычно пикапы имеют внедорожную '\s
                             'платформу (нередко на ней же выпускается и «родственник»-джип), но существуют и легковые пикапы. '\s
                             'Такой кузов называется «ют» (Ute) или Utility Coupe — «утилитарное купе»."""
            );
            Category crossOver = new Category();
            crossOver.setName("Crossover/Кроссовер");
            crossOver.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/c468205d03c9fbeb6a2ee0346368ad3b.jpeg");
            crossOver.setDescription("""
                    Кроссовер, он же «паркетник» или CUV в английской терминологии (Crossover Utility Vehicle) — промежуточная '\s
                             'ступень между легковыми машинами и внедорожниками. Отличается высокой посадкой водителя, увеличенным клиренсом '\s
                             'и большими колёсами. Пропорциями кроссовер напоминает внедорожник, но технически он ближе к универсалу повышенной '\s
                             'проходимости.Первым в мире кроссовером традиционно считается советская «Нива», хотя по сегодняшним меркам это '\s
                             'компактный внедорожник — нынешние кроссоверы стали намного более «асфальтовыми». По большей части их покупают '\s
                             'ради высокой посадки, возможности заезда на бордюры и большей, по сравнению с универсалами, престижности. '\s
                             'Хотя некоторые кроссоверы (Subaru Forester, Suzuki Grand Vitara, Renault Duster) обладают неплохими внедорожными '\s
                             'способностями и вполне успешно съезжают с асфальта."""
            );
            Category suv = new Category();
            suv.setName("SUV/Внедорожник/Джип");
            suv.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/34e302a2b1b0577d9b17afb26e78a56d.jpeg");
            suv.setDescription("""
                    SUV расшифровывается как Sport Utility Vehicle, «спортивно-утилитарный автомобиль». Но этот термин в ходу лишь '\s
                             'на западе — в русскоговорящих странах внедорожники традиционно называют джипами. Это слово вошло в обиход ещё '\s
                             'во время Второй мировой войны, в то время как торговую марку Jeep зарегистрировали только в 1950-м.По сравнению '\s
                             'с кроссоверами, у внедорожников более серьёзный полный привод, выше клиренс, больше колёса. Размеры и масса '\s
                             'автомобиля также превосходят «паркетные», как и стоимость эксплуатации. Однако и эти машины всё чаще покупают '\s
                             'ради комфорта и статуса, а не для реальных выездов на бездорожье — поэтому автовладельцы традиционно делят '\s
                             'внедорожники на «настоящие джипы» и «большие паркетники»."""
            );
            Category minivan = new Category();
            minivan.setName("Minivan/Минивэн/Микроавтобус");
            minivan.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/e508e6fd314e0a90328a2efb5aa454f2.jpeg");
            minivan.setDescription("""
                    Минивэн (минивен) — автомобиль с высокой крышей и большим количеством пассажирских мест. В салонах некоторых '\s
                             'минивэнов их больше 8 — в этом случае для управления автомобилем нужны водительские права категории D.Минивэны '\s
                             'могут быть как полукапотными («полутораобъёмными»), с вынесенным вперёд двигателем, так и однообъёмными — '\s
                             'с моторным отсеком под полом салона. Иногда минивэн может обладать способностями внедорожника, '\s
                             'классический пример — Mitsubishi Delica."""
            );
            Category limousin = new Category();
            limousin.setName("Limousin/Лимузин");
            limousin.setImage("https://img.hyperauto.ru/applications/hyperauto-ru/5579b76b6aaf77c982dbb3648ac3d111.jpeg");
            limousin.setDescription("""
                    Назначение лимузина — возить пассажиров с особым шиком. И с определёнными опциями: так, отличительная черта '\s
                             'лимузина — перегородка между водителем и пассажирским салоном. Без неё это не лимузин, а просто удлинённый '\s
                             'седан (так называемая Long-версия).Большинство современных лимузинов переделываются специальными ателье из '\s
                             'стандартных автомобилей врезкой дополнительной секции — их называют стретчами (Stretch-Limousine). '\s
                             'Именно такие машины обычно встречаются в свадебных кортежах, причём делают их не только из седанов, '\s
                             'но и из внедорожников, кабриолетов и даже минивенов. Заводские лимузины проектируются под увеличенную '\s
                             'длину изначально, но это всегда мелкосерийные или штучные модели."""
            );


            Classification aClass = new Classification();
            aClass.setName("A class/Mini/Мини");
            aClass.setImage("https://image.made-in-china.com/2f0j00GUMcnAVKbYgR/Chery-Ant-251km-True-Love-Version-Chinese-Small-EV-Cars-with-Hatchback-a-Class-Electric-Vehicle-New-Used-Car-Green-Fashion-New-Car-Price-Mini-Car.webp");
            aClass.setDescription("""
                    Включает в себя самые маленькие автомобили. Их габариты редко не превышают 3,6-3,7 м в длину, а исполнение '
                            'кузова не слишком вариативно. Чаще это трехдверные хэтчбеки и за редким исключением — кабриолеты. '\s
                            'Комфорт водителя и пассажира в этом классе принесен в жертву компактности, поэтому долгая поездка или '\s
                            '«прогулки» по бездорожью — это не их история. Как правило, модели А-класса оснащаются малолитражными '\s
                            'двигателями и сегодня среди них есть даже электрифицированные экземпляры.'"""
            );
            Classification bClass = new Classification();
            bClass.setName("B class/Small/Небольшой");
            bClass.setImage("https://www.mercedes-benz.co.uk/content/dam/hq/passengercars/cars/B-Class/b-class-sports-tourer-w247-pi/modeloverview/09-2022/images/mercedes-benz-b-class-w247-modeloverview-696x392-09-2022.png");
            bClass.setDescription("""
                    Как и класс А, этот сегмент преимущественно ориентирован на городские поездки. Размеры моделей B-сегмента варьируются '
                             'от 3,7 до 4,2 м для хэтчбеков, но бывают седаны длиной более 4,4 м. А это значит, что в салоне больше места для '
                             'пассажиров и в багажник помещается пара чемоданов. Сегодня этот класс авто один из самых популярных, '
                             'в том числе в России. Автомобили класса B, как правило, находятся в среднем ценовом сегменте."""
            );
            Classification cClass = new Classification();
            cClass.setName("C class/Low/Middle/Низкий/Средний");
            cClass.setImage("https://images.hgmsites.net/lrg/2024-toyota-corolla-hybrid-le-fwd-natl-angular-front-exterior-view_100915609_l.webp");
            cClass.setDescription("""
                    Также известен как гольф-класс, его родоначальником принято считать модель Volkswagen Golf. Машины С-класса —\s
                             'это тоже семейные автомобили, на которых с комфортом можно передвигаться как городу, так и трассам.\s
                             'Габариты авто колеблются от 4,1 м до 4,6 м (иногда чуть больше), а модельный ряд включает в себя седаны,\s
                             'универсалы, пятидверные хэтчбеки."""
            );
            Classification dClass = new Classification();
            dClass.setName("D class/Middle/Средний");
            dClass.setImage("https://mosautoshina.ru/i/auto/audi_a4_2024.jpg");
            dClass.setDescription("""
                    Именно с этого сегмента начинаются настоящие большие седаны. Размеры моделей стремятся к пяти метрам и они\s
                             'одинаково подходят как для семьи, так и бизнеса. Здесь водителю предлагают гораздо больше комфорта и\s
                             'свободного пространства. В техническом плане также есть из чего выбрать: как правило,\s
                             'ассортимент моторов и трансмиссий довольно широк."""
            );
            Classification eClass = new Classification();
            eClass.setName("E class/Business/Бизнес");
            eClass.setImage("https://www.bmw.ru/content/dam/bmw/marketRU/bmw_ru/all-models/5-series/sedan/2020/bmw-5-series-sedan-models-and-equipment-lines-01-01.jpg.asset.1598262155329.jpg");
            eClass.setDescription("""
                    Автомобили E-сегмента отличают внушительные размеры, более сложное техническое оснащение и, соответственно, цена.
                             'В этом классе можно встретить различные премиальные «навороты» в виде множества электронных ассистентов, добротной
                             'отделки салона и прочего."""
            );
            Classification fClass = new Classification();
            fClass.setName("F class/Luxury/Люкс");
            fClass.setImage("https://images.hgmsites.net/lrg/2024-mercedes-benz-s-class-maybach-s-580-4matic-sedan-angular-front-exterior-view_100915802_l.webp");
            fClass.setDescription("""
                    Это автомобили класса люкс. Их выделяют не только высокотехнологичные характеристики, но и безусловная роскошь и комфорт.
                             'Внушительные размеры, которые превышают пять метров, обеспечивают максимальный простор в салоне — особенно на заднем диване.
                             'Статус владельца таких авто не предполагает поездку за рулем: за это, как правило, отвечает персональный водитель.
                             'Нередко производители в этом сегменте балуют своих клиентов различными «допами» в виде ящика для шампанского,
                             'брендированных наборов для пикника и прочими мелочами. Для тех, кто заботится о своей безопасности даже могут
                             'предложить дополнительно бронирование кузова."""
            );
            Classification sClass = new Classification();
            sClass.setName("S class/Sport/Спорт");
            sClass.setImage("https://inv.assets.ansira.net/ChromeColorMatch/us/TRANSPARENT_cc_2023AUC170052_01_1280_L5L5.png");
            sClass.setDescription("""
                    Этот класс концентрируется на спортивном характере и включает в себя родстеры, гранд-туреры, кабриолеты и маслкары.
                             'Это высокопроизводительные и высокотехнологичные автомобили рассчитаны на определенный круг покупателей,
                             'которые ценят мощность и дерзкий характер. Соответственно и цена на такие модели внушительная. В этом классе
                             'почти не встречаются вместительные и практичные седаны, поскольку в центре внимания водитель. От чего задний
                             'диван часто носит чисто номинальный характер."""
            );
            Classification mClass = new Classification();
            mClass.setName("M class/Minivans/Минивэны");
            mClass.setImage("https://cdn.24.co.za/files/Cms/General/d/122/978d1e26dcf54f12b5fb3ed09e79192d.jpg");
            mClass.setDescription("""
                    Эти многоцелевые транспортные средства, которые перевозят как пассажиров, так и грузы.
                             'Наиболее распространенная аббревиатура этого класса MPV. Это вместительные фургоны и минивэны, отличающиеся
                             'гибкостью салона и наличием третьего ряда сидений. Это отличный выбор для большой семьи (от 5 человек и выше)
                             'или долгих поездок, отдельные модели также используются в бизнесе."""
            );
            Classification jClass = new Classification();
            jClass.setName("J class/Crossovers/SUV/Джипы/Внедорожники");
            jClass.setImage("https://bilweb.se/i?u=55366585&w=836&h=530&c=1");
            jClass.setDescription("""
                    Как правило, в этот сегмент записывают все модели авто, которые отличаются внедорожными характеристиками
                             'в той или иной степени. Здесь можно встретить как полноразмерных представителей, так и более компактные модели.
                             'Такие авто, в первую очередь, отличает более сложное техническое устройство. В частности, в зависимости от
                             'производителя реализуются различные системы полного привода. При этом автомобили этого сегмента сами могут
                             'делиться на подкатегории в зависимости от размера машин, их возможностей на бездорожье и пр."""
            );

            Tag tag1 = new Tag();
            tag1.setName("спорт");
            Tag tag2 = new Tag();
            tag2.setName("внедорожник");
            Tag tag3 = new Tag();
            tag3.setName("седан");
            Tag tag4 = new Tag();
            tag4.setName("купе");
            Tag tag5 = new Tag();
            tag5.setName("полный привод");
            Tag tag6 = new Tag();
            tag6.setName("задний привод");
            Tag tag7 = new Tag();
            tag7.setName("передний привод");
            Tag tag8 = new Tag();
            tag8.setName("механика");
            Tag tag9 = new Tag();
            tag9.setName("автомат");
            Tag tag10 = new Tag();
            tag10.setName("спорт кар");
            Tag tag11 = new Tag();
            tag11.setName("комфорт");
            Tag tag12 = new Tag();
            tag12.setName("стиль");
            Tag tag13 = new Tag();
            tag13.setName("two doors");
            Tag tag14 = new Tag();
            tag14.setName("speed");
            Tag tag15 = new Tag();
            tag15.setName("solid");
            Tag tag16 = new Tag();
            tag16.setName("beautiful");
            Tag tag17 = new Tag();
            tag17.setName("phenomenal");

            Image bmwImage = new Image();
            bmwImage.setName("bmw-image");
            bmwImage.setPreview(true);
            bmwImage.setData(readBytesByUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/BMW.svg/2048px-BMW.svg.png"));
            Brand bmw = new Brand();
            bmw.setTitle("BMW");
            bmw.setImage(bmwImage);
            bmw.setWebsite("https://www.bmw.ru/ru/index.html");
            bmw.setDescription("""
                    Концерн BMW Group, в который входят бренды BMW, Mini, Rolls-Royce и BMW Motorrad, является ведущим мировым
                            'производителем автомобилей и мотоциклов, а также поставщиком финансовых услуг и услуг по обеспечению
                            'мобильности премиум-класса. Штат компании насчитывает порядка 125 000 сотрудников в подразделениях по всему миру."""
            );

            Image audiImage = new Image();
            audiImage.setName("audi-image");
            audiImage.setPreview(true);
            audiImage.setData(readBytesByUrl(
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Audi_logo_detail.svg/1280px-Audi_logo_detail.svg.png"));
            Brand audi = new Brand();
            audi.setTitle("Audi");
            audi.setImage(audiImage);
            audi.setWebsite("https://www.audi.com/en.html");
            audi.setDescription("Audi AG is a German automotive manufacturer of luxury vehicles headquartered in Ingolstadt, Bavaria, Germany.\n" +
                    "        'A subsidiary of the Volkswagen Group, Audi produces vehicles in nine production facilities worldwide."
            );

            Image mercedesImage = new Image();
            mercedesImage.setName("mercedes-image");
            mercedesImage.setPreview(true);
            mercedesImage.setData(readBytesByUrl(
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Mercedes-Logo.svg/2048px-Mercedes-Logo.svg.png"
            ));
            Brand mercedes = new Brand();
            mercedes.setTitle("Mercedes");
            mercedes.setImage(mercedesImage);
            mercedes.setWebsite("https://group.mercedes-benz.com/en/");
            mercedes.setDescription("""
                    The Mercedes-Benz Group AG (former Daimler AG) is one of the world''s most successful automotive companies.
                            'With Mercedes-Benz AG, we are one of the leading global suppliers of high-end passenger cars and premium vans.
                            'Mercedes-Benz Mobility AG offers financing, leasing, car subscription and car rental, fleet management,
                            'digital services for charging and payment, insurance brokerage, as well as innovative mobility services."""
            );

            DriveUnit frontWheel = new DriveUnit();
            frontWheel.setName("Front Wheel/Передний привод");

            DriveUnit rear = new DriveUnit();
            rear.setName("Rear/Задний привод");

            DriveUnit fourWheel = new DriveUnit();
            fourWheel.setName("Four Wheel/Полный Привод");


            Image m5PreviewImage = new Image();
            m5PreviewImage.setName("m5-preview-image");
            m5PreviewImage.setPreview(true);
            m5PreviewImage.setData(readBytesFromFile("src/main/resources/static/resource/images/m5PreviewImage.png"));
            Image m5Image1 = new Image();
            m5Image1.setName("m5-image1");
            m5Image1.setPreview(false);
            m5Image1.setData(readBytesFromFile("src/main/resources/static/resource/images/m5Image1.png"));
            Image m5Image2 = new Image();
            m5Image2.setName("m5-image2");
            m5Image2.setPreview(false);
            m5Image2.setData(readBytesFromFile("src/main/resources/static/resource/images/m5Image2.jpg"));
            Image m5Image3 = new Image();
            m5Image3.setName("m5-image3");
            m5Image3.setPreview(false);
            m5Image3.setData(readBytesFromFile("src/main/resources/static/resource/images/m5Image3.jpg"));
            Car m5 = new Car();
            m5.setPerson(admin);
            m5.setTitle("M5");
            m5.setWebpage("https://www.bmw.ru/ru/all-models/m-series/m5-sedan/2021/bmw-5-series-sedan-m-automobiles-overview.html");
            m5.setBrand(bmw);
            m5.setDriveUnit(frontWheel);
            m5.setClassification(dClass);
            m5.setCategory(sedan);
            m5.setWeight("2200 кг");
            m5.addImages(List.of(m5PreviewImage, m5Image1, m5Image2, m5Image3));
            m5.setHasPreview(true);
            m5.setTags(
                    List.of(tag3,tag9,tag11,tag1,tag12)
            );
            m5.setDescription("""
                    Автомобили M BMW 5 серии впечатляющим образом сочетают в себе фирменную спортивность BMW M с комфортом
                            'и элегантностью седана бизнес-класса. Познакомьтесь с тремя уникальными автомобилями BMW M с яркими характерами.
                            'Быстрейший в истории, новый BMW M5 CS с двигателем мощностью в 635 л.с. (467 кВт) и разгоном до 100 км/ч
                            'за рекордные 3 секунды. Оснащенный двигателем мощностью 625 л.с. (460 кВт) и подвеской с гоночными настройками
                            'BMW M5 Competition с системой полного привода M xDrive олицетворяет собой эталон динамики.
                            'Во внешнем облике высокомощного спортивного седана это проявляется за счет множества элементов цвета "Глянцевый Черный".
                            'В дополнение к системе полного привода M xDrive, спроектированной для максимальной динамики и устойчивости,
                            'BMW M5 оснащается подвеской, обеспечивающей спортивность и комфорт даже в продолжительных поездках.
                            'Завершает тройку BMW M550i xDrive. Этот спортивный седан BMW 5 серии обладает мощностью 530 л.с. (390 кВт)
                            'и впечатляет идеально сбалансированной комбинацией спортивности, комфорта и экономичности."""
            );

            Image r8PreviewImage = new Image();
            r8PreviewImage.setName("r8-preview-image");
            r8PreviewImage.setPreview(true);
            r8PreviewImage.setData(readBytesFromFile("src/main/resources/static/resource/images/r8PreviewImage.png"));
            Image r8Image1 = new Image();
            r8Image1.setName("r8-image1");
            r8Image1.setPreview(false);
            r8Image1.setData(readBytesFromFile("src/main/resources/static/resource/images/r8Image1.png"));
            Image r8Image2 = new Image();
            r8Image2.setName("r8-image2");
            r8Image2.setPreview(false);
            r8Image2.setData(readBytesFromFile("src/main/resources/static/resource/images/r8Image2.jpg"));
            Image r8Image3 = new Image();
            r8Image3.setName("r8-image3");
            r8Image3.setPreview(false);
            r8Image3.setData(readBytesFromFile("src/main/resources/static/resource/images/r8Image3.jpg"));
            Car r8 = new Car();
            r8.setPerson(admin);
            r8.setTitle("R8");
            r8.setWebpage("https://www.audiusa.com/us/web/en/models/r8/r8-heritage/2024/overview.html");
            r8.setBrand(audi);
            r8.setDriveUnit(fourWheel);
            r8.setClassification(sClass);
            r8.setCategory(coupe);
            r8.setWeight("1800 кг");
            r8.addImages(List.of(r8PreviewImage, r8Image1, r8Image2, r8Image3));
            r8.setHasPreview(true);
            r8.setTags(
                    List.of(tag1,tag10,tag5,tag12,tag4,tag14)
            );
            r8.setDescription("""
                    The Audi R8 is a mid-engine, 2-seater sports car, which uses Audi''s trademark quattro permanent
                            'all-wheel drive system. It was introduced by the German car manufacturer Audi AG in 2006.
                            'Production ended in the first quarter of 2024."""
            );

            Image m4PreviewImage = new Image();
            m4PreviewImage.setName("m4-preview-image");
            m4PreviewImage.setPreview(true);
            m4PreviewImage.setData(readBytesFromFile("src/main/resources/static/resource/images/m4PreviewImage.png"));
            Image m4Image1 = new Image();
            m4Image1.setName("m4-image1");
            m4Image1.setPreview(false);
            m4Image1.setData(readBytesFromFile("src/main/resources/static/resource/images/m4Image1.jpg"));
            Image m4Image2 = new Image();
            m4Image2.setName("m4-image2");
            m4Image2.setPreview(false);
            m4Image2.setData(readBytesFromFile("src/main/resources/static/resource/images/m4Image2.jpg"));
            Image m4Image3 = new Image();
            m4Image3.setName("m4-image3");
            m4Image3.setPreview(false);
            m4Image3.setData(readBytesFromFile("src/main/resources/static/resource/images/m4Image3.jpg"));
            Car m4 = new Car();
            m4.setPerson(admin);
            m4.setTitle("M4");
            m4.setWebpage("https://www.bmw.ru/ru/index.html");
            m4.setBrand(bmw);
            m4.setDriveUnit(rear);
            m4.setClassification(dClass);
            m4.setCategory(coupe);
            m4.setWeight("2000 кг");
            m4.addImages(List.of(m4PreviewImage, m4Image1, m4Image2, m4Image3));
            m4.setHasPreview(true);
            m4.setTags(
                    List.of(tag1,tag4,tag5,tag9,tag12,tag13)
            );
            m4.setDescription("""
                    Автомобили M BMW 4 серии Coupe впечатляющим образом сочетают в себе эстетическую привлекательность,
                            'харизму и фирменный спортивный стиль M. Возглавляет трио BMW M4 Competition Coupe с впечатляющими 510 л.с.
                            'мощности и 650 Нм крутящего момента. Этот автомобиль, оснащенный высокомощным двигателем BMW M TwinPower Turbo,
                            '8-ступенчатой АКПП M Steptronic с технологией Drivelogic, опциональной системой полного привода M xDrive*,
                            'активным дифференциалом M и многочисленными технологиями, позаимствованными в автоспорте, гарантирует
                            'максимальные динамические характеристики – как в повседневной эксплуатации, так и на гоночных трассах.
                            'Завершает модельный ряд BMW M440i xDrive Coupe: помимо привлекательного дизайна это спортивное купе
                            'мощностью 387 л.с. впечатляет особенно сбалансированным сочетанием атлетизма, экономичности и практичности
                            'в повседневной эксплуатации. *M xDrive предлагается ориентировочно с лета 2021 года."""
            );

            Image g63PreviewImage = new Image();
            g63PreviewImage.setName("g63-preview-image");
            g63PreviewImage.setPreview(true);
            g63PreviewImage.setData(readBytesFromFile("src/main/resources/static/resource/images/g63PreviewImage.png"));
            Image g63Image1 = new Image();
            g63Image1.setName("g63-image1");
            g63Image1.setPreview(false);
            g63Image1.setData(readBytesFromFile("src/main/resources/static/resource/images/g63Image1.jpg"));
            Image g63Image2 = new Image();
            g63Image2.setName("g63-image2");
            g63Image2.setPreview(false);
            g63Image2.setData(readBytesFromFile("src/main/resources/static/resource/images/g63Image2.jpg"));
            Image g63Image3 = new Image();
            g63Image3.setName("g63-image3");
            g63Image3.setPreview(false);
            g63Image3.setData(readBytesFromFile("src/main/resources/static/resource/images/g63Image3.jpg"));
            Car g63 = new Car();
            g63.setPerson(user);
            g63.setTitle("AMG G-63");
            g63.setWebpage("https://www.mercedes-benz.com/en/vehicles/g-class/");
            g63.setBrand(mercedes);
            g63.setDriveUnit(fourWheel);
            g63.setClassification(jClass);
            g63.setCategory(crossOver);
            g63.setWeight("3200 кг");
            g63.addImages(List.of(g63PreviewImage, g63Image1, g63Image2, g63Image3));
            g63.setHasPreview(true);
            g63.setTags(
                    List.of(tag2,tag5,tag11,tag12,tag8)
            );
            g63.setDescription("""
                    Mercedes-Benz G 450 d | Energieverbrauch kombiniert: 10,0-8,7 l/100 km | CO2-Emissionen kombiniert: 261-227 g/km
                            '| CO2-Klasse: G; Mercedes-AMG G 63 | Energieverbrauch kombiniert: 15,7-14,7l/100 km | CO2-Emissionen kombiniert:
                            '358-335g/km | CO2-Klasse: G; Mercedes-Benz G 500 | Energieverbrauch kombiniert: 12,3-10,9l/100 km |CO2-Emissionen
                            'kombiniert: 281-248g/km | CO2-Klasse: G"""
            );

            Image cls63PreviewImage = new Image();
            cls63PreviewImage.setName("cls63-preview-image");
            cls63PreviewImage.setPreview(true);
            cls63PreviewImage.setData(readBytesFromFile("src/main/resources/static/resource/images/cls63PreviewImage.png"));
            Image cls63Image1 = new Image();
            cls63Image1.setName("cls63-image1");
            cls63Image1.setPreview(false);
            cls63Image1.setData(readBytesFromFile("src/main/resources/static/resource/images/cls63Image1.jpg"));
            Image cls63Image2 = new Image();
            cls63Image2.setName("cls63-image2");
            cls63Image2.setPreview(false);
            cls63Image2.setData(readBytesFromFile("src/main/resources/static/resource/images/cls63Image2.jpg"));
            Image cls63Image3 = new Image();
            cls63Image3.setName("cls63-image3");
            cls63Image3.setPreview(false);
            cls63Image3.setData(readBytesFromFile("src/main/resources/static/resource/images/cls63Image3.jpg"));
            Car cls63 = new Car();
            cls63.setPerson(user);
            cls63.setTitle("CLS 63 AMG");
            cls63.setWebpage("https://panavto-mercedes.ru/new_cars/cls-class/cls-63-amg-cls-class/");
            cls63.setBrand(mercedes);
            cls63.setDriveUnit(fourWheel);
            cls63.setClassification(fClass);
            cls63.setCategory(sedan);
            cls63.setWeight("2500 кг");
            cls63.addImages(List.of(cls63PreviewImage,cls63Image1,cls63Image2,cls63Image3));
            cls63.setHasPreview(true);
            cls63.setTags(
                    List.of(tag16,tag3,tag9,tag15,tag5,tag11)
            );
            cls63.setDescription("""
                    CLS 63 AMG отличается динамическими характеристиками экстра-класса: автомобиль разгоняется с нуля до 100 км/ч
                            'за 4,4 секунды, а максимальная скорость составляет 250 км/ч. Для покупателей CLS 63 AMG дополнительно
                            'предлагается эксклюзивный пакет оснащения AMG Performance Package."""
            );

            EngineEdition eeR6 = new EngineEdition();
            eeR6.setName("R6");
            eeR6.setImage("https://otoba.ru/dvigatel/servis/img/icons/bmw/r6-dizel.png");
            eeR6.setBrand(bmw);

            Fuel petrol = new Fuel();
            petrol.setName("Petrol");
            Fuel diesel = new Fuel();
            diesel.setName("Diesel");

            Engine em51 = new Engine();
            em51.setName("M51");
            em51.setImage("https://otoba.ru/dvigatel/bmw/img/m51/dvigatel-bmw-m51-pod-kapotom.jpg");
            em51.setFuel(diesel);
            em51.setEngineEdition(eeR6);
            em51.setDescription(
                    "2.5-литровый дизельный двигатель БМВ М51 собирали на заводе в Австрии с 1991 по 2001 год " +
                            "и ставили на такие популярные модели, как 3-Series в кузове E36 и 5-Series в кузовах E34 и E39. " +
                            "Данный силовой агрегат модернизировали в 1996 году и он получил себе новый индекс M51TU."
            );
            Engine em57 = new Engine();
            em57.setName("M57");
            em57.setImage("https://otoba.ru/dvigatel/bmw/img/m57/dvs-bmw-m57-pod-kapotom.jpg");
            em57.setFuel(diesel);
            em57.setEngineEdition(eeR6);
            em57.setDescription(
                    "6-цилиндровые дизельные двигатели БМВ М57 на 2.5 и 3.0 литра собирали с 1998 по 2012 " +
                            "год и устанавливали практически на все более или менее крупные модели концерна своего времени. " +
                            "Существует три поколения этих силовых агрегатов обычно именуемых как M57, M57N и M57N2."
            );
            EngineCharacteristics chem51 = new EngineCharacteristics();
            chem51.setCompression("17.5");
            chem51.setCylinders("6");
            chem51.setEngine(em51);
            chem51.setStartPower("100 л.с.");
            chem51.setPower("480 л.с.");
            chem51.setPiston("82.8 мм");
            chem51.setTorque("222 Нм");
            chem51.setVolume("2457 см3");

            EngineCharacteristics chem57 = new EngineCharacteristics();
            chem57.setCompression("16.5");
            chem57.setCylinders("6");
            chem57.setEngine(em57);
            chem57.setStartPower("120 л.с.");
            chem57.setPower("550 л.с.");
            chem57.setPiston("90 мм");
            chem57.setTorque("430 Нм");
            chem57.setVolume("2993 см3");

            Gearbox st4 = new Gearbox();
            st4.setName("4-х ступенчатая");
            st4.setStairs(4);
            st4.setImage("https://otoba.ru/transmissii/servis/img/icons/vag/rubriki/akpp-4st.png");
            Gearbox st5 = new Gearbox();
            st5.setName("5-и ступенчатая");
            st5.setStairs(5);
            st5.setImage("https://otoba.ru/transmissii/servis/img/icons/zf/rubriki/akpp-5st.png");
            Gearbox st6 = new Gearbox();
            st6.setName("6-и ступенчатая");
            st6.setStairs(6);
            st6.setImage("https://otoba.ru/transmissii/servis/img/icons/aisin/rubriki/akpp-6st-pp.png");
            Gearbox st7 = new Gearbox();
            st7.setName("7-и ступенчатая");
            st7.setStairs(7);
            st7.setImage("https://otoba.ru/transmissii/servis/img/icons/vag/rubriki/robot-7st.png");
            Gearbox st8 = new Gearbox();
            st8.setName("8-и ступенчатая");
            st8.setStairs(8);
            st8.setImage("https://otoba.ru/transmissii/servis/img/icons/aisin/rubriki/akpp-8st-zp.png");

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
            al950.setImage("https://otoba.ru/transmissii/vag/img/al950/transmissiya-vag-al950-v-salone.jpg");
            al950.setDescription("""
                    6-ступенчатая автоматическая коробка VW AL950 производилась концерном с 2002 по 2012 год \
                    и ставилась на VW Phaeton V10 TDI под индексом HDQ либо Audi Q7 V12 TDI под индексом 0BQ. \
                    Такой автомат по своей сути является одной из разновидностей особо мощной акпп ZF 6HP32X.
                    """);
            Transmission al750 = new Transmission();
            al750.setName("AL750");
            al750.setBrand(audi);
            al750.setGearbox(st6);
            al750.setDriveType(autoType);
            al750.setImage("https://otoba.ru/transmissii/vag/img/al750/transmissiya-vag-al750-v-salone.jpg");
            al750.setDescription("""
                    6-ступенчатая автоматическая коробка VW AL750 производилась компанией с 2002 по 2010 год \
                    и ставилась на кроссоверы VW Touareg, Porsche Cayenne либо Audi Q7 под своим индексом 09D. \
                    Этот автомат существует в двух версиях и по сути являлся разновидностью акпп Aisin TR-60SN.
                    """);

            TurboType vtgTurbo = new TurboType();
            vtgTurbo.setName("Турбина с изменяемой геометрией");
            vtgTurbo.setImage("https://a.d-cd.net/xCAAAgCcy-A-960.jpg");
            vtgTurbo.setDescription("VNT турбина, или турбина с изменяемой геометрией, чаще всего устанавливается на автомобили " +
                    "с дизельным мотором. Установка VNT турбины позволяет оптимизировать движение отработанных газов и " +
                    "устранить неприятные эффекты «турбоямы» и «турбоподхвата».");
            TurboType compressor = new TurboType();
            compressor.setName("Турбонаддув с двумя параллельными турбокомпрессорами");
            compressor.setImage("https://www.tomiokaracing.com/cdn/shop/products/DSC_7839_med.jpg?v=1679596676");
            compressor.setDescription("Турбонаддув с двумя параллельными турбокомпрессорами – как правило используется " +
                    "для двигателей V-типа. Комбинированный наддув – система, при которой совместно используются турбонаддув " +
                    "и механически наддув. На низких оборотах работает механический нагнетатель, а на высоких – турбокомпрессор.");
            TurboType comboTurbo = new TurboType();
            comboTurbo.setName("Комбинированный наддув");
            comboTurbo.setImage("https://www.turbozentrum.de/media/image/product/211231/lg/audi-s3-8y-20-tfsi-stage-upgrade-turbo-06q145703f.jpg");
            comboTurbo.setDescription("Комбинированный наддув – система, при которой совместно используются турбонаддув " +
                    "и механически наддув. На низких оборотах работает механический нагнетатель, а на высоких – турбокомпрессор.");

            Turbocharger gtb2260vk = new Turbocharger();
            gtb2260vk.setName("GTB2260vk");
            gtb2260vk.setBrand(mercedes);
            gtb2260vk.setTurboType(vtgTurbo);
            gtb2260vk.setPowerGeneration("150 л.с.");
            gtb2260vk.setPowerIntake("25 л.с.");
            gtb2260vk.setImage("https://cdn11.bigcommerce.com/s-f4487/images/stencil/original/products/1255/18962/DSD8632.1_-_DSD4010.1_-_Converted_Reconditioned_GTB2260VK_with_Billet_Compressor_2_new__22166.1627481916.jpg?c=2&imbypass=on&imbypass=on");
            gtb2260vk.setDescription("Journal Bearing GTB2260VK hybrid based on the turbochargers from 3.0TDI VAG or 3.0D " +
                    "BMW M57/N57 engines is the ultimate upgrade for the 1.9 & 2.0 TDI for someone looking for a fast daily driver.");
            Turbocharger vagis20 = new Turbocharger();
            vagis20.setName("VAG IS20");
            vagis20.setBrand(audi);
            vagis20.setTurboType(compressor);
            vagis20.setPowerGeneration("180 л.с.");
            vagis20.setPowerIntake("30 л.с.");
            vagis20.setImage("https://urbanracers.shop/upload/iblock/b79/shhcy152c2k4439p4oz88ads6yntdf6f/1tervdfj2esz4mp2h1b1dgtd4h6hc0ls.jpg");
            vagis20.setDescription("The VAG IS20 turbo is a popular upgrade for the IS12 turbo due to its improved performance and efficiency. " +
                    "With a larger compressor and turbine inlet, the IS20 can push more air into the engine and deliver more power than the IS12. " +
                    "Additionally, the IS20 has better cooling and lubrication, which extends its lifespan and reduces the likelihood of " +
                    "malfunctions. Because of these benefits, the IS20 turbo is a popular choice for VW and Audi owners looking for more " +
                    "power and reliability.");
            Turbocharger sk3rr = new Turbocharger();
            sk3rr.setName("SK3RR");
            sk3rr.setBrand(bmw);
            sk3rr.setTurboType(comboTurbo);
            sk3rr.setPowerGeneration("220 л.с.");
            sk3rr.setPowerIntake("40 л.с.");
            sk3rr.setImage("https://pkfst.ru/800/600/https/www.automachi.com/wp-content/uploads/2017/06/tt-blowertbodyintake-lg.gif");
            sk3rr.setDescription("SK3RR увеличивает максимальную отдачу, снижает турболаг и обостряет реакцию двигателя " +
                    "на нажатие педали газа. Это объясняется тем, что высокий уровень давления развивается ранее на впуске, " +
                    "нежели при штатной системе. Разработчики предусмотрели пять видов форсировки для трехлитрового агрегата с Hybrid Charger.");

            audi.setGearboxes(List.of(st5,st6,st7,st8));
            mercedes.setGearboxes(List.of(st4,st5,st6,st7));
            bmw.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));
            audi.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));
            mercedes.setTurboTypes(List.of(vtgTurbo,compressor,comboTurbo));

            Equipment equipM5 = new Equipment();
            equipM5.setName("Test 1");
            equipM5.setImage("https://yt3.googleusercontent.com/hH4Xd006S0TDIblxoWjQQyykaKJDt_Zwn5YmAbQMUsKnxpI3Vm3-H7xLzuQjPwYvBds1u-U89A=s900-c-k-c0x00ffffff-no-rj");
            equipM5.setPerson(admin);
            equipM5.setCar(m5);
            equipM5.setEngine(em57);
            equipM5.setTransmission(al950);
            equipM5.setTurbocharger(gtb2260vk);
            equipM5.setAttached(true);
            Equipment equipM4 = new Equipment();
            equipM4.setName("Test 2");
            equipM4.setImage("https://yt3.googleusercontent.com/ytc/AIdro_kCMYAHys71pMwA60E-2waY13Uii8C5yLmvas117_g7vw=s900-c-k-c0x00ffffff-no-rj");
            equipM4.setPerson(admin);
            equipM4.setCar(m4);
            equipM4.setEngine(em51);
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
            log.info("Preload: {}", gearboxRepository.saveAll(
                    List.of(st4, st5, st6, st7, st8)
            ));
            log.info("Preload: {}", turboTypeRepository.saveAll(
                    List.of(vtgTurbo, compressor, comboTurbo)
            ));
            log.info("Preload: {}", brandRepository.saveAll(
                    List.of(bmw, audi, mercedes)
            ));
            log.info("Preload: {}", carRepository.saveAll(
                    List.of(m5, m4, r8, g63, cls63)
            ));
            log.info("Preload: {}", engineEditionRepository.saveAll(
                    List.of(eeR6)
            ));
            log.info("Preload: {}", fuelRepository.saveAll(
                    List.of(petrol, diesel)
            ));
            log.info("Preload: {}", engineRepository.saveAll(
                    List.of(em51, em57)
            ));
            log.info("Preload: {}", engineCharacteristicsRepository.saveAll(
                    List.of(chem51, chem57)
            ));
            log.info("Preload: {}", driveTypeRepository.saveAll(
                    List.of(mechanic, autoType, robot)
            ));
            log.info("Preload: {}", transmissionRepository.saveAll(
                    List.of(al950, al750)
            ));
            log.info("Preload: {}", turbochargerRepository.saveAll(
                    List.of(gtb2260vk, vagis20, sk3rr)
            ));
            log.info("Preload: {}", equipmentRepository.saveAll(
                    List.of(equipM5, equipM4)
            ));
        };
    }
}
