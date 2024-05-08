package com.burgas.springbootauto.config;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.*;
import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.engine.Fuel;
import com.burgas.springbootauto.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadDatabase.class);

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
                                          EngineCharacteristicsRepository engineCharacteristicsRepository) {

        return _ -> {
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
            bClass.setImage("https://autoreview.ru/images/Article/1728/Article_172866_860_575.jpg");
            bClass.setDescription("""
                    Как и класс А, этот сегмент преимущественно ориентирован на городские поездки. Размеры моделей B-сегмента варьируются '
                             'от 3,7 до 4,2 м для хэтчбеков, но бывают седаны длиной более 4,4 м. А это значит, что в салоне больше места для '
                             'пассажиров и в багажник помещается пара чемоданов. Сегодня этот класс авто один из самых популярных, '
                             'в том числе в России. Автомобили класса B, как правило, находятся в среднем ценовом сегменте."""
            );
            Classification cClass = new Classification();
            cClass.setName("C class/Low Middle/Низший Средний");
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
            eClass.setName("E class/Middle High/Средний Высший/Business/Бизнес");
            eClass.setImage("https://www.bmw.ru/content/dam/bmw/marketRU/bmw_ru/all-models/5-series/sedan/2020/bmw-5-series-sedan-models-and-equipment-lines-01-01.jpg.asset.1598262155329.jpg");
            eClass.setDescription("""
                    Автомобили E-сегмента отличают внушительные размеры, более сложное техническое оснащение и, соответственно, цена.
                             'В этом классе можно встретить различные премиальные «навороты» в виде множества электронных ассистентов, добротной
                             'отделки салона и прочего."""
            );
            Classification fClass = new Classification();
            fClass.setName("F class/High/Высший/Luxury/Люкс");
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
            sClass.setImage("https://news.dupontregistry.com/wp-content/uploads/2023/12/2023-lamborghini-huracan-tecnic-9-915x570.jpg");
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
            jClass.setName("J class/Crossovers and SUV/Джипы и внедорожники");
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

            Brand bmw = new Brand();
            bmw.setTitle("BMW");
            bmw.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/BMW.svg/2048px-BMW.svg.png");
            bmw.setWebsite("https://www.bmw.ru/ru/index.html");
            bmw.setDescription("""
                    Концерн BMW Group, в который входят бренды BMW, Mini, Rolls-Royce и BMW Motorrad, является ведущим мировым
                            'производителем автомобилей и мотоциклов, а также поставщиком финансовых услуг и услуг по обеспечению
                            'мобильности премиум-класса. Штат компании насчитывает порядка 125 000 сотрудников в подразделениях по всему миру."""
            );
            Brand audi = new Brand();
            audi.setTitle("Audi");
            audi.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Audi_logo_detail.svg/1280px-Audi_logo_detail.svg.png");
            audi.setWebsite("https://www.audi.com/en.html");
            audi.setDescription("Audi AG is a German automotive manufacturer of luxury vehicles headquartered in Ingolstadt, Bavaria, Germany.\n" +
                    "        'A subsidiary of the Volkswagen Group, Audi produces vehicles in nine production facilities worldwide."
            );
            Brand mercedes = new Brand();
            mercedes.setTitle("Mercedes");
            mercedes.setImage("https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Mercedes-Logo.svg/2048px-Mercedes-Logo.svg.png");
            mercedes.setWebsite("https://group.mercedes-benz.com/en/");
            mercedes.setDescription("""
                    The Mercedes-Benz Group AG (former Daimler AG) is one of the world''s most successful automotive companies.
                            'With Mercedes-Benz AG, we are one of the leading global suppliers of high-end passenger cars and premium vans.
                            'Mercedes-Benz Mobility AG offers financing, leasing, car subscription and car rental, fleet management,
                            'digital services for charging and payment, insurance brokerage, as well as innovative mobility services."""
            );



            Car m5 = new Car();
            m5.setTitle("M5");
            m5.setImage("https://imgd.aeplcdn.com/1280x720/cw/ec/33136/BMW-M5-Exterior-172905.jpg?wm=0&q=80");
            m5.setWebpage("https://www.bmw.ru/ru/all-models/m-series/m5-sedan/2021/bmw-5-series-sedan-m-automobiles-overview.html");
            m5.setBrand(bmw);
            m5.setClassification(dClass);
            m5.setCategory(sedan);
            m5.setPrice(11450000);
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
            Car r8 = new Car();
            r8.setTitle("R8");
            r8.setImage("https://imgd.aeplcdn.com/664x374/cw/ec/21724/Audi-R8-Right-Front-Three-Quarter-66713.jpg?v=201711021421&q=80");
            r8.setWebpage("https://www.audiusa.com/us/web/en/models/r8/r8-heritage/2024/overview.html");
            r8.setBrand(audi);
            r8.setClassification(sClass);
            r8.setCategory(coupe);
            r8.setPrice(9000000);
            r8.setTags(
                    List.of(tag1,tag10,tag5,tag12,tag4,tag14)
            );
            r8.setDescription("""
                    The Audi R8 is a mid-engine, 2-seater sports car, which uses Audi''s trademark quattro permanent
                            'all-wheel drive system. It was introduced by the German car manufacturer Audi AG in 2006.
                            'Production ended in the first quarter of 2024."""
            );
            Car m4 = new Car();
            m4.setTitle("M4");
            m4.setImage("https://prod.cosy.bmw.cloud/bmwweb/cosySec?COSY-EU-100-7331cqgv2Z7d%25i02uCaY3MuO2kOHUtWPfbYf0jD110tLhu1XzWVo7puMLWFmdkAj5DOP5tpsZ8XgY1nTNIowJ4HO3zkyXq%25sGM8snGhMQSk%2508Xc9Vo74giU2NF1VgxNJ0%25lJfVZ8XJaFBVKLB9MEWO5GalL7GqGAt6GCrv0s9O9DJE4GA0ogRxQNF9OALUollkIogOybLKynvLUgChSXF5GybUEqgtc89ChbNmKXNPoEqhk7BRKMLNmqn1cSJDyk7m5VKJXYCn178zBPltE5V1PaHVqfN8zVMRJw9SkPazDxK07dnMRaYWB9EQ5DxRte2LcZ8YWxfjfEgcPteWS6GERKMfjedw9hbBDS6jQ%250502Ydw6Zuj8KptQ%25wc3lLMifZu%25KXsyTHSc3uBr0UTJdKX324BDpTQLXg7AF3D%25zhrLGwg6QuiVpRBn5Y3");
            m4.setWebpage("https://www.bmw.ru/ru/index.html");
            m4.setBrand(bmw);
            m4.setClassification(dClass);
            m4.setCategory(coupe);
            m4.setPrice(9000000);
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
            Car g63 = new Car();
            g63.setTitle("AMG G-63");
            g63.setImage("https://platform.cstatic-images.com/xlarge/in/v2/stock_photos/799a84ca-4aec-4459-8984-41bea8603148/34dd2126-c630-4221-a3e2-f711ab4149c2.png");
            g63.setWebpage("https://www.mercedes-benz.com/en/vehicles/g-class/");
            g63.setBrand(mercedes);
            g63.setClassification(jClass);
            g63.setCategory(crossOver);
            g63.setPrice(17099684);
            g63.setTags(
                    List.of(tag2,tag5,tag11,tag12,tag8)
            );
            g63.setDescription("""
                    Mercedes-Benz G 450 d | Energieverbrauch kombiniert: 10,0-8,7 l/100 km | CO2-Emissionen kombiniert: 261-227 g/km
                            '| CO2-Klasse: G; Mercedes-AMG G 63 | Energieverbrauch kombiniert: 15,7-14,7l/100 km | CO2-Emissionen kombiniert:
                            '358-335g/km | CO2-Klasse: G; Mercedes-Benz G 500 | Energieverbrauch kombiniert: 12,3-10,9l/100 km |CO2-Emissionen
                            'kombiniert: 281-248g/km | CO2-Klasse: G"""
            );
            Car cls63 = new Car();
            cls63.setTitle("CLS 63 AMG");
            cls63.setImage("https://platform.cstatic-images.com/xlarge/in/v2/stock_photos/d70fc0aa-3936-44d1-809b-d09a4bfaf3c5/4e731530-0992-41b9-bf7e-2ab2e70c2ff9.png");
            cls63.setWebpage("https://panavto-mercedes.ru/new_cars/cls-class/cls-63-amg-cls-class/");
            cls63.setBrand(mercedes);
            cls63.setClassification(fClass);
            cls63.setCategory(sedan);
            cls63.setPrice(10450000);
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

            Equipment equipM5 = new Equipment();
            equipM5.setCar(m5);
            Equipment equipM4 = new Equipment();
            equipM4.setCar(m4);

            Fuel petrol = new Fuel();
            petrol.setName("Petrol");
            Fuel diesel = new Fuel();
            diesel.setName("Diesel");

            Engine em51 = new Engine();
            em51.setName("M51");
            em51.setImage("https://otoba.ru/dvigatel/bmw/img/m51/dvigatel-bmw-m51-pod-kapotom.jpg");
            em51.setFuel(diesel);
            em51.setEquipment(equipM5);
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
            em57.setEquipment(equipM4);
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
            chem51.setPower("115 л.с.");
            chem51.setPiston("82.8 мм");
            chem51.setTorque("222 Нм");
            chem51.setVolume("2457 см3");

            EngineCharacteristics chem57 = new EngineCharacteristics();
            chem57.setCompression("16.5");
            chem57.setCylinders("6");
            chem57.setEngine(em57);
            chem57.setPower("204 л.с.");
            chem57.setPiston("90 мм");
            chem57.setTorque("430 Нм");
            chem57.setVolume("2993 см3");

            LOGGER.info("Preload: {}", categoryRepository.saveAll(
                    List.of(hatchBack, coupe, sedan, limousin, liftBack, fastBack, wagon, cabriolet, pickUp, crossOver, suv, minivan))
            );
            LOGGER.info("Preload: {}", classificationRepository.saveAll(
                    List.of(aClass, bClass, cClass, eClass, dClass, fClass, sClass, mClass, jClass)
            ));
            LOGGER.info("Preload: {}", tagRepository.saveAll(
                    List.of(tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9, tag10, tag11, tag12, tag13, tag14, tag15, tag16, tag17)
            ));
            LOGGER.info("Preload: {}", brandRepository.saveAll(
                    List.of(bmw, audi, mercedes)
            ));
            LOGGER.info("Preload: {}", carRepository.saveAll(
                    List.of(m5, m4, r8, g63, cls63)
            ));
            LOGGER.info("Preload: {}", engineEditionRepository.saveAll(
                    List.of(eeR6)
            ));
            LOGGER.info("Preload: {}", equipmentRepository.saveAll(
                    List.of(equipM5, equipM4)
            ));
            LOGGER.info("Preload: {}", fuelRepository.saveAll(
                    List.of(petrol, diesel)
            ));
            LOGGER.info("Preload: {}", engineRepository.saveAll(
                    List.of(em51, em57)
            ));
            LOGGER.info("Preload: {}", engineCharacteristicsRepository.saveAll(
                    List.of(chem51, chem57)
            ));
        };
    }
}
