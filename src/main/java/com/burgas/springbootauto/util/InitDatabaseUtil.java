package com.burgas.springbootauto.util;

public class InitDatabaseUtil {

    public static final String ADMIN_ACCOUNT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/User-admin.svg/1200px-User-admin.svg.png";

    public static final String ADMIN_ACCOUNT_DESCRIPTION = "Hello everyone! I'm admin on this site!";

    public static final String USER_ACCOUNT_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/User_icon-cp.svg/828px-User_icon-cp.svg.png";

    public static final String USER_ACCOUNT_DESCRIPTION = "Hello everyone! I'm user on this site!";

    public static final String HATCHBACK_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/6ddf532daac4d0d1fbafe343f7624f8a.jpeg";

    public static final String HATCHBACK_CATEGORY_DESCRIPTION = """
            Хэтчбек (хетчбэк) — это универсал с коротким задним свесом: кузов автомобиля заканчивается почти сразу за колёсами.\s
                    'За счёт компактности такие машины не испытывают проблем с парковкой и очень удобны для городской эксплуатации.\s
                    'Хэтчбеки могут быть как 3-дверными, так и 5-дверными.Конечно, багажник хэтчбека невелик по сравнению с универсалом.\s
                    'Но всё же это двухобъёмная машина с отдельной задней дверью: если сложить сиденья второго ряда, то в хэтчбеке\s
                    'вполне можно перевезти велосипед или стиральную машину. Поэтому практичность хэтчбеков довольно высока — в Европе\s
                    'и Японии этот тип кузова очень популярен. Эталонный хэтчбек — Volkswagen Golf, название которого стало\s
                    'нарицательным для всего класса таких автомобилей (гольф-класс). Отдельная категория — «горячие хэтчбеки»,\s
                    'оборудованные мощными двигателями: они не только компактные и практичные, но и быстрые. Примеры: Honda Civic\s
                    'Type R, Toyota Yaris GR, Ford Fiesta ST.""";

    public static final String COUPE_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/ef200288dcee57ed3cd56f8a326580de.jpeg";

    public static final String COUPE_CATEGORY_DESCRIPTION = """
            Название происходит от французского «coupe» — «стриженный». Классический вариант купе — это '\s
                    'укороченный двухдверный седан, который отличается 1 рядом сидений и компактным багажником. '\s
                    'Позднее появились варианты с «детским» задним рядом — посадка 2+2. Сейчас купе называют '\s
                    'большинство автомобилей с 2 дверьми.""";

    public static final String SEDAN_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/fb139b302a163b25939226963a6cd3af.jpeg";

    public static final String SEDAN_CATEGORY_DESCRIPTION = """
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
                    'Простой пример: Volkswagen переделал свой бестселлер Polo (изначально хэтчбек) в Polo Sedan специально для российского рынка.""";

    public static final String LIFTBACK_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/61f45b26f447981ceebd03c5885fee18.jpeg";

    public static final String LIFTBACK_CATEGORY_DESCRIPTION = """
            Лифтбек (лифтбэк) — это попытка скрестить седан с универсалом: первого сделать практичнее, а второго — статуснее и стильнее. '\s
                    'Идея далеко не новая: даже в СССР был свой лифтбек — Иж-2125 «Комби», то есть с комбинированным кузовом. Но такое название '\s
                    'в автоиндустрии не прижилось даже на постсоветском пространстве.В лифтбеке нет структурного отделения багажника от '\s
                    'пассажирского салона, а заднее стекло поднимается вместе с крышкой багажника. Но это именно крышка (довольно покатая), '\s
                    'а не полноценная 5-я дверь, как у хэтчбека или универсала.У лифтбека может быть как 2, так и 3 визуальных объёма — '\s
                    'зависит от конкретной модели. Двухобъёмный лифтбек напоминает хэтчбек с длинным задним свесом. Трёхобъёмный похож '\s
                    'на седан, такие машины любит делать Skoda. Лифтбэки Rapid, Octavia, Superb — вылитые седаны, пока не откроешь багажник.""";

    public static final String FASTBACK_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/a6f5143bef7c16865ec638a1370832f5.jpeg";

    public static final String DASTBACK_CATEGORY_DESCRIPTION = """
            Фастбек (фастбэк) нередко путают с лифтбеком. Дословно Fastback («стремительный задок») означает покатую крышу, '\s
                     'без граней заднего стекла переходящую в крышку багажника — у лифтбека эти «ступеньки» довольно выражены. '\s
                     'Также у каноничных фастбеков заднее стекло отделено от крышки багажника и неподвижно, но эта черта постепенно '\s
                     'уходит в прошлое — у современных фастбеков стекло зачастую совмещено с крышкой.Кузов фастбек был очень популярен '\s
                     'в середине прошлого века. Типичный пример фастбека — советская ГАЗ М-20 «Победа» с характерной каплевидной задней '\s
                     'частью. Тогда покатую крышу делали для лучшей обтекаемости кузова, но затем аэродинамика обычных седанов существенно '\s
                     'улучшилась, и мода на фастбеки сошла на нет. В последние годы покатая крыша вновь вернулась в автомобильный дизайн, '\s
                     'причём встречается она у самых разных кузовов: у седанов и купе (Mercedes-Benz CLS), лифтбеков (Porsche Panamera), '\s
                     'кроссоверов (Honda Crosstour). Все они по сути являются фастбеками.""";

    public static final String WAGON_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/f85db266315778dcb341fdd080a33db4.jpeg";

    public static final String WAGON_CATEGORY_DESCRIPTION = """
            «Почему бы не использовать место, пустующее над крышкой багажника у седана?» — видимо, решили разработчики универсала. '\s
                     'Его главное визуальное отличие — двухобъёмный кузов, в котором пассажирский салон объединён с багажным отсеком.'\s
                     'Крышка багажника универсала — это полноценная 5-я дверь, которая открывается вместе с задним стеклом, обеспечивая '\s
                     'удобную погрузку вещей. Кстати, задний свес универсала не короче (а зачастую длиннее), чем у седана той же модели, '\s
                     'чтобы багажный отсек был как можно просторнее. Ведь именно объём багажника — главный фактор выбора такого типа '\s
                     'кузова.Некоторые автомобилисты награждают универсалы нелестными эпитетами вроде «овощевозки» или «сарая». '\s
                     'Однако у таких машин много поклонников, и не только из-за размеров багажника. Ведь название не врёт: это '\s
                     'действительно универсальные автомобили, объединяющие практичность фургона и комфорт легковой машины. '\s
                     'Есть и отдельные «подвиды»: спортивные универсалы (Nissan Stagea, Audi RS4, Alfa Romeo 159 Sportwagon), '\s
                     'универсалы повышенной проходимости (Subaru Outback, Volvo XC70) и другие.'""";

    public static final String CABRIOLET_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/47ae70a6ae0660b9e1657aa6c70fb86f.jpeg";

    public static final String CABRIOLET_CATEGORY_DESCRIPTION = """
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
                     'или Mazda MX-5 Miata стоят на уровне средних седанов.""";

    public static final String PICKUP_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/3718cc7015d6a05c80df0b6122b84400.jpeg";

    public static final String PICKUP_CATEGORY_DESCRIPTION = """
            Пикап — это грузопассажирский автомобиль с открытым грузовым отсеком, структурно отделённым от кабины. '\s
                     'Как правило, он оснащён зависимой рессорной задней подвеской, обеспечивающей высокую грузоподъёмность.'\s
                     'Пикапы — сугубо утилитарные машины, которые в некоторых странах (прежде всего в США) стали культовыми '\s
                     'среди обычных автовладельцев и используются для повседневной езды, почти как внедорожники в России. Кстати, '\s
                     'в Америке пикапы называют траками (Truck), что дословно переводится как «грузовик». Обычно пикапы имеют внедорожную '\s
                     'платформу (нередко на ней же выпускается и «родственник»-джип), но существуют и легковые пикапы. '\s
                     'Такой кузов называется «ют» (Ute) или Utility Coupe — «утилитарное купе».""";

    public static final String CROSSOVER_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/c468205d03c9fbeb6a2ee0346368ad3b.jpeg";

    public static final String CROSSOVER_CATEGORY_DESCRIPTION = """
            Кроссовер, он же «паркетник» или CUV в английской терминологии (Crossover Utility Vehicle) — промежуточная '\s
                     'ступень между легковыми машинами и внедорожниками. Отличается высокой посадкой водителя, увеличенным клиренсом '\s
                     'и большими колёсами. Пропорциями кроссовер напоминает внедорожник, но технически он ближе к универсалу повышенной '\s
                     'проходимости.Первым в мире кроссовером традиционно считается советская «Нива», хотя по сегодняшним меркам это '\s
                     'компактный внедорожник — нынешние кроссоверы стали намного более «асфальтовыми». По большей части их покупают '\s
                     'ради высокой посадки, возможности заезда на бордюры и большей, по сравнению с универсалами, престижности. '\s
                     'Хотя некоторые кроссоверы (Subaru Forester, Suzuki Grand Vitara, Renault Duster) обладают неплохими внедорожными '\s
                     'способностями и вполне успешно съезжают с асфальта.""";

    public static final String SUV_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/34e302a2b1b0577d9b17afb26e78a56d.jpeg";

    public static final String SUV_CATEGORY_DESCRIPTION = """
            SUV расшифровывается как Sport Utility Vehicle, «спортивно-утилитарный автомобиль». Но этот термин в ходу лишь '\s
                     'на западе — в русскоговорящих странах внедорожники традиционно называют джипами. Это слово вошло в обиход ещё '\s
                     'во время Второй мировой войны, в то время как торговую марку Jeep зарегистрировали только в 1950-м.По сравнению '\s
                     'с кроссоверами, у внедорожников более серьёзный полный привод, выше клиренс, больше колёса. Размеры и масса '\s
                     'автомобиля также превосходят «паркетные», как и стоимость эксплуатации. Однако и эти машины всё чаще покупают '\s
                     'ради комфорта и статуса, а не для реальных выездов на бездорожье — поэтому автовладельцы традиционно делят '\s
                     'внедорожники на «настоящие джипы» и «большие паркетники».""";

    public static final String LIMOUSIN_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/5579b76b6aaf77c982dbb3648ac3d111.jpeg";

    public static final String LIMOUSIN_CATEGORY_DESCRIPTION = """
            Назначение лимузина — возить пассажиров с особым шиком. И с определёнными опциями: так, отличительная черта '\s
                     'лимузина — перегородка между водителем и пассажирским салоном. Без неё это не лимузин, а просто удлинённый '\s
                     'седан (так называемая Long-версия).Большинство современных лимузинов переделываются специальными ателье из '\s
                     'стандартных автомобилей врезкой дополнительной секции — их называют стретчами (Stretch-Limousine). '\s
                     'Именно такие машины обычно встречаются в свадебных кортежах, причём делают их не только из седанов, '\s
                     'но и из внедорожников, кабриолетов и даже минивенов. Заводские лимузины проектируются под увеличенную '\s
                     'длину изначально, но это всегда мелкосерийные или штучные модели.""";

    public static final String MINIVAN_CATEGORY_IMAGE =
            "https://img.hyperauto.ru/applications/hyperauto-ru/e508e6fd314e0a90328a2efb5aa454f2.jpeg";

    public static final String MINIVAN_CATEGORY_DESCRIPTION = """
            Минивэн (минивен) — автомобиль с высокой крышей и большим количеством пассажирских мест. В салонах некоторых '\s
                     'минивэнов их больше 8 — в этом случае для управления автомобилем нужны водительские права категории D.Минивэны '\s
                     'могут быть как полукапотными («полутораобъёмными»), с вынесенным вперёд двигателем, так и однообъёмными — '\s
                     'с моторным отсеком под полом салона. Иногда минивэн может обладать способностями внедорожника, '\s
                     'классический пример — Mitsubishi Delica.""";

    public static final String A_CLASS_IMAGE =
            "https://class-car.ru/wp-content/uploads/2015/04/Volkswagen-Up-2-23.jpg";

    public static final String A_CLASS_DESCRIPTION = """
            Включает в себя самые маленькие автомобили. Их габариты редко не превышают 3,6-3,7 м в длину, а исполнение '
                    'кузова не слишком вариативно. Чаще это трехдверные хэтчбеки и за редким исключением — кабриолеты. '\s
                    'Комфорт водителя и пассажира в этом классе принесен в жертву компактности, поэтому долгая поездка или '\s
                    '«прогулки» по бездорожью — это не их история. Как правило, модели А-класса оснащаются малолитражными '\s
                    'двигателями и сегодня среди них есть даже электрифицированные экземпляры.'""";

    public static final String B_CLASS_IMAGE =
            "https://class-car.ru/wp-content/uploads/2015/04/B-Volkswagen-Polo2.jpg";

    public static final String B_CLASS_DESCRIPTION = """
            Как и класс А, этот сегмент преимущественно ориентирован на городские поездки. Размеры моделей B-сегмента варьируются '
                     'от 3,7 до 4,2 м для хэтчбеков, но бывают седаны длиной более 4,4 м. А это значит, что в салоне больше места для '
                     'пассажиров и в багажник помещается пара чемоданов. Сегодня этот класс авто один из самых популярных, '
                     'в том числе в России. Автомобили класса B, как правило, находятся в среднем ценовом сегменте.""";

    public static final String C_CLASS_IMAGE =
            "https://class-car.ru/wp-content/uploads/2015/04/Volkswagen-Golf-11.jpg";

    public static final String C_CLASS_DESCRIPTION = """
            Также известен как гольф-класс, его родоначальником принято считать модель Volkswagen Golf. Машины С-класса —\s
                     'это тоже семейные автомобили, на которых с комфортом можно передвигаться как городу, так и трассам.\s
                     'Габариты авто колеблются от 4,1 м до 4,6 м (иногда чуть больше), а модельный ряд включает в себя седаны,\s
                     'универсалы, пятидверные хэтчбеки.""";

    public static final String D_CLASS_IMAGE = "https://class-car.ru/wp-content/uploads/2015/04/ford-mondeo-11.jpg";

    public static final String D_CLASS_DESCRIPTION = """
            Именно с этого сегмента начинаются настоящие большие седаны. Размеры моделей стремятся к пяти метрам и они\s
                     'одинаково подходят как для семьи, так и бизнеса. Здесь водителю предлагают гораздо больше комфорта и\s
                     'свободного пространства. В техническом плане также есть из чего выбрать: как правило,\s
                     'ассортимент моторов и трансмиссий довольно широк.""";

    public static final String E_CLASS_IMAGE =
            "https://class-car.ru/wp-content/uploads/2015/04/E-Mercedes-Benz-E-Class-Coupe2.jpg";

    public static final String E_CLASS_DESCRIPTION = """
            Автомобили E-сегмента отличают внушительные размеры, более сложное техническое оснащение и, соответственно, цена.
                     'В этом классе можно встретить различные премиальные «навороты» в виде множества электронных ассистентов, добротной
                     'отделки салона и прочего.""";

    public static final String F_CLASS_IMAGE =
            "https://class-car.ru/wp-content/uploads/2015/04/1.jpg";

    public static final String F_CLASS_DESCRIPTION = """
            Это автомобили класса люкс. Их выделяют не только высокотехнологичные характеристики, но и безусловная роскошь и комфорт.
                     'Внушительные размеры, которые превышают пять метров, обеспечивают максимальный простор в салоне — особенно на заднем диване.
                     'Статус владельца таких авто не предполагает поездку за рулем: за это, как правило, отвечает персональный водитель.
                     'Нередко производители в этом сегменте балуют своих клиентов различными «допами» в виде ящика для шампанского,
                     'брендированных наборов для пикника и прочими мелочами. Для тех, кто заботится о своей безопасности даже могут
                     'предложить дополнительно бронирование кузова.""";

    public static final String S_CLASS_IMAGE = "https://class-car.ru/wp-content/uploads/2015/04/audi_r82.jpg";

    public static final String S_CLASS_DESCRIPTION = """
            Этот класс концентрируется на спортивном характере и включает в себя родстеры, гранд-туреры, кабриолеты и маслкары.
                     'Это высокопроизводительные и высокотехнологичные автомобили рассчитаны на определенный круг покупателей,
                     'которые ценят мощность и дерзкий характер. Соответственно и цена на такие модели внушительная. В этом классе
                     'почти не встречаются вместительные и практичные седаны, поскольку в центре внимания водитель. От чего задний
                     'диван часто носит чисто номинальный характер.""";

    public static final String M_CLASS_IMAGE = "https://class-car.ru/wp-content/uploads/2015/04/13.jpg";

    public static final String M_CLASS_DESCRIPTION = """
            Эти многоцелевые транспортные средства, которые перевозят как пассажиров, так и грузы.
                     'Наиболее распространенная аббревиатура этого класса MPV. Это вместительные фургоны и минивэны, отличающиеся
                     'гибкостью салона и наличием третьего ряда сидений. Это отличный выбор для большой семьи (от 5 человек и выше)
                     'или долгих поездок, отдельные модели также используются в бизнесе.""";

    public static final String J_CLASS_IMAGE = "https://class-car.ru/wp-content/uploads/2015/04/chevrolet_suburban_2007.jpg";

    public static final String J_CLASS_DESCRIPTION = """
            Как правило, в этот сегмент записывают все модели авто, которые отличаются внедорожными характеристиками
                     'в той или иной степени. Здесь можно встретить как полноразмерных представителей, так и более компактные модели.
                     'Такие авто, в первую очередь, отличает более сложное техническое устройство. В частности, в зависимости от
                     'производителя реализуются различные системы полного привода. При этом автомобили этого сегмента сами могут
                     'делиться на подкатегории в зависимости от размера машин, их возможностей на бездорожье и пр.""";

    public static final String BMW_BRAND_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/BMW.svg/2048px-BMW.svg.png";

    public static final String BMW_BRAND_WEBSITE = "https://www.bmw.ru/ru/index.html";

    public static final String BMW_BRAND_DESCRIPTION = """
            Концерн BMW Group, в который входят бренды BMW, Mini, Rolls-Royce и BMW Motorrad, является ведущим мировым
                    'производителем автомобилей и мотоциклов, а также поставщиком финансовых услуг и услуг по обеспечению
                    'мобильности премиум-класса. Штат компании насчитывает порядка 125 000 сотрудников в подразделениях по всему миру.""";

    public static final String AUDI_BRAND_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Audi_logo_detail.svg/1280px-Audi_logo_detail.svg.png";

    public static final String AUDI_BRAND_WEBSITE = "https://www.audi.com/en.html";

    public static final String AUDI_BRAND_DESCRIPTION = "Audi AG is a German automotive manufacturer o" +
            "f luxury vehicles headquartered in Ingolstadt, Bavaria, Germany.\n" +
            "        'A subsidiary of the Volkswagen Group, Audi produces vehicles in nine production facilities worldwide.";

    public static final String MERCEDES_BRAND_IMAGE =
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Mercedes-Logo.svg/2048px-Mercedes-Logo.svg.png";

    public static final String MERCEDES_BRAND_WEBSITE = "https://group.mercedes-benz.com/en/";

    public static final String MERCEDES_BRAND_DESCRIPTION = """
            The Mercedes-Benz Group AG (former Daimler AG) is one of the world''s most successful automotive companies.
                    'With Mercedes-Benz AG, we are one of the leading global suppliers of high-end passenger cars and premium vans.
                    'Mercedes-Benz Mobility AG offers financing, leasing, car subscription and car rental, fleet management,
                    'digital services for charging and payment, insurance brokerage, as well as innovative mobility services.""";

    public static final String M5_PREVIEW_IMAGE = "src/main/resources/static/resource/images/m5PreviewImage.png";

    public static final String M5_IMAGE_ONE = "src/main/resources/static/resource/images/m5Image1.png";

    public static final String M5_IMAGE_TWO = "src/main/resources/static/resource/images/m5Image2.jpg";

    public static final String M5_IMAGE_THREE = "src/main/resources/static/resource/images/m5Image3.jpg";

    public static final String M5_WEBPAGE =
            "https://www.bmw.ru/ru/all-models/m-series/m5-sedan/2021/bmw-5-series-sedan-m-automobiles-overview.html";

    public static final String M5_DESCRIPTION = """
            Автомобили M BMW 5 серии впечатляющим образом сочетают в себе фирменную спортивность BMW M с комфортом
                    'и элегантностью седана бизнес-класса. Познакомьтесь с тремя уникальными автомобилями BMW M с яркими характерами.
                    'Быстрейший в истории, новый BMW M5 CS с двигателем мощностью в 635 л.с. (467 кВт) и разгоном до 100 км/ч
                    'за рекордные 3 секунды. Оснащенный двигателем мощностью 625 л.с. (460 кВт) и подвеской с гоночными настройками
                    'BMW M5 Competition с системой полного привода M xDrive олицетворяет собой эталон динамики.
                    'Во внешнем облике высокомощного спортивного седана это проявляется за счет множества элементов цвета "Глянцевый Черный".
                    'В дополнение к системе полного привода M xDrive, спроектированной для максимальной динамики и устойчивости,
                    'BMW M5 оснащается подвеской, обеспечивающей спортивность и комфорт даже в продолжительных поездках.
                    'Завершает тройку BMW M550i xDrive. Этот спортивный седан BMW 5 серии обладает мощностью 530 л.с. (390 кВт)
                    'и впечатляет идеально сбалансированной комбинацией спортивности, комфорта и экономичности.""";

    public static final String R8_PREVIEW_IMAGE = "src/main/resources/static/resource/images/r8PreviewImage.png";

    public static final String R8_IMAGE_ONE = "src/main/resources/static/resource/images/r8Image1.png";

    public static final String R8_IMAGE_THREE = "src/main/resources/static/resource/images/r8Image2.jpg";

    public static final String R8_WEBPAGE = "https://www.audiusa.com/us/web/en/models/r8/r8-heritage/2024/overview.html";

    public static final String R8_DESCRIPTION = """
            The Audi R8 is a mid-engine, 2-seater sports car, which uses Audi''s trademark quattro permanent
                    'all-wheel drive system. It was introduced by the German car manufacturer Audi AG in 2006.
                    'Production ended in the first quarter of 2024.""";

    public static final String M4_PREVIEW_IMAGE = "src/main/resources/static/resource/images/m4PreviewImage.png";

    public static final String M4_IMAGE_ONE = "src/main/resources/static/resource/images/m4Image1.jpg";

    public static final String M4_IMAGE_TWO = "src/main/resources/static/resource/images/m4Image2.jpg";

    public static final String M4_IMAGE_THREE = "src/main/resources/static/resource/images/m4Image3.jpg";

    public static final String M4_WEBPAGE = "https://www.bmw.ru/ru/index.html";

    public static final String M4_DESCRIPTION = """
            Автомобили M BMW 4 серии Coupe впечатляющим образом сочетают в себе эстетическую привлекательность,
                    'харизму и фирменный спортивный стиль M. Возглавляет трио BMW M4 Competition Coupe с впечатляющими 510 л.с.
                    'мощности и 650 Нм крутящего момента. Этот автомобиль, оснащенный высокомощным двигателем BMW M TwinPower Turbo,
                    '8-ступенчатой АКПП M Steptronic с технологией Drivelogic, опциональной системой полного привода M xDrive*,
                    'активным дифференциалом M и многочисленными технологиями, позаимствованными в автоспорте, гарантирует
                    'максимальные динамические характеристики – как в повседневной эксплуатации, так и на гоночных трассах.
                    'Завершает модельный ряд BMW M440i xDrive Coupe: помимо привлекательного дизайна это спортивное купе
                    'мощностью 387 л.с. впечатляет особенно сбалансированным сочетанием атлетизма, экономичности и практичности
                    'в повседневной эксплуатации. *M xDrive предлагается ориентировочно с лета 2021 года.""";

    public static final String G63_PREVIEW_IMAGE = "src/main/resources/static/resource/images/g63PreviewImage.png";

    public static final String G63_IMAGE_ONE = "src/main/resources/static/resource/images/g63Image1.jpg";

    public static final String G63_IMAGE_TWO = "src/main/resources/static/resource/images/g63Image2.jpg";

    public static final String G63_IMAGE_THREE = "src/main/resources/static/resource/images/g63Image3.jpg";

    public static final String G63_WEBPAGE = "https://www.mercedes-benz.com/en/vehicles/g-class/";

    public static final String G63_DESCRIPTION = """
            Mercedes-Benz G 450 d | Energieverbrauch kombiniert: 10,0-8,7 l/100 km | CO2-Emissionen kombiniert: 261-227 g/km
                    '| CO2-Klasse: G; Mercedes-AMG G 63 | Energieverbrauch kombiniert: 15,7-14,7l/100 km | CO2-Emissionen kombiniert:
                    '358-335g/km | CO2-Klasse: G; Mercedes-Benz G 500 | Energieverbrauch kombiniert: 12,3-10,9l/100 km |CO2-Emissionen
                    'kombiniert: 281-248g/km | CO2-Klasse: G""";

    public static final String CLS63_PREVIEW_IMAGE = "src/main/resources/static/resource/images/cls63PreviewImage.png";

    public static final String CLS63_IMAGE_ONE = "src/main/resources/static/resource/images/cls63Image1.jpg";

    public static final String CLS63_IMAGE_TWO = "src/main/resources/static/resource/images/cls63Image2.jpg";

    public static final String CLS63_IMAGE_THREE = "src/main/resources/static/resource/images/cls63Image3.jpg";

    public static final String CLS63_WEBPAGE = "https://panavto-mercedes.ru/new_cars/cls-class/cls-63-amg-cls-class/";

    public static final String CLS63_DESCRIPTION = """
            CLS 63 AMG отличается динамическими характеристиками экстра-класса: автомобиль разгоняется с нуля до 100 км/ч
                    'за 4,4 секунды, а максимальная скорость составляет 250 км/ч. Для покупателей CLS 63 AMG дополнительно
                    'предлагается эксклюзивный пакет оснащения AMG Performance Package.""";

    public static final String R6_IMAGE = "https://otoba.ru/dvigatel/servis/img/icons/bmw/r6-dizel.png";

    public static final String EN62_IMAGE = "https://otoba.ru/dvigatel/bmw/img/n62/dvigatel-bmw-n62-pod-kapotom.jpg";

    public static final String EN62_DESCRIPTION =
            "Серия 8-цилиндровых двигателей БМВ N62 от 3.6 до 4.8 литра собиралась с 2001 по 2010 годы " +
            "и устанавливалась на такие модели компании, как 5-Series в кузове E60 и 7-Series в кузове E65. " +
            "Выпускались и продвинутые версии данного силового агрегата от Alpina мощностью до 530 л.с.";

    public static final String EN63_IMAGE = "https://otoba.ru/dvigatel/bmw/img/n63/dvigatel-bmw-n63-pod-kapotom.jpg";

    public static final String EN63_DESCRIPTION =
            "Серия 8-цилиндровых двигателей БМВ N63 объемом 4.0 и 4.4 литров производится с 2008 года и " +
            "устанавливается практически на все современные крупные модели немецкого автоконцерна. " +
            "Существует ряд спортивных версий данного мотора под индексом S63 и мощностью до 600 л.с.";

    public static final String ST4_IMAGE = "https://otoba.ru/transmissii/servis/img/icons/vag/rubriki/akpp-4st.png";

    public static final String ST5_IMAGE = "https://otoba.ru/transmissii/servis/img/icons/zf/rubriki/akpp-5st.png";

    public static final String ST6_IMAGE = "https://otoba.ru/transmissii/servis/img/icons/aisin/rubriki/akpp-6st-pp.png";

    public static final String ST7_IMAGE = "https://otoba.ru/transmissii/servis/img/icons/vag/rubriki/robot-7st.png";

    public static final String ST8_IMAGE = "https://otoba.ru/transmissii/servis/img/icons/aisin/rubriki/akpp-8st-zp.png";

    public static final String AL950_IMAGE = "https://otoba.ru/transmissii/vag/img/al950/transmissiya-vag-al950-v-salone.jpg";

    public static final String AL950_DESCRIPTION = """
            6-ступенчатая автоматическая коробка VW AL950 производилась концерном с 2002 по 2012 год \
            и ставилась на VW Phaeton V10 TDI под индексом HDQ либо Audi Q7 V12 TDI под индексом 0BQ. \
            Такой автомат по своей сути является одной из разновидностей особо мощной акпп ZF 6HP32X.
            """;

    public static final String AL750_iMAGE = "https://otoba.ru/transmissii/vag/img/al750/transmissiya-vag-al750-v-salone.jpg";

    public static final String AL750_DESCRIPTION = """
            6-ступенчатая автоматическая коробка VW AL750 производилась компанией с 2002 по 2010 год \
            и ставилась на кроссоверы VW Touareg, Porsche Cayenne либо Audi Q7 под своим индексом 09D. \
            Этот автомат существует в двух версиях и по сути являлся разновидностью акпп Aisin TR-60SN.
            """;

    public static final String VTG_IMAGE = "https://a.d-cd.net/xCAAAgCcy-A-960.jpg";

    public static final String VTG_DESCRIPTION =
            "VNT турбина, или турбина с изменяемой геометрией, чаще всего устанавливается на автомобили " +
            "с дизельным мотором. Установка VNT турбины позволяет оптимизировать движение отработанных газов и " +
            "устранить неприятные эффекты «турбоямы» и «турбоподхвата».";

    public static final String COMPRESSOR_IMAGE =
            "https://www.tomiokaracing.com/cdn/shop/products/DSC_7839_med.jpg?v=1679596676";

    public static final String COMPRESSOR_DESCRIPTION =
            "Турбонаддув с двумя параллельными турбокомпрессорами – как правило используется " +
            "для двигателей V-типа. Комбинированный наддув – система, при которой совместно используются турбонаддув " +
            "и механически наддув. На низких оборотах работает механический нагнетатель, а на высоких – турбокомпрессор.";

    public static final String COMBOTURBO_IMAGE =
            "https://www.turbozentrum.de/media/image/product/211231/lg/audi-s3-8y-20-tfsi-stage-upgrade-turbo-06q145703f.jpg";

    public static final String COMBOTURBO_DESCRIPTION =
            "Комбинированный наддув – система, при которой совместно используются турбонаддув " +
            "и механически наддув. На низких оборотах работает механический нагнетатель, а на высоких – турбокомпрессор.";

    public static final String GTB2260VK_IMAGE =
            "https://cdn11.bigcommerce.com/s-f4487/images/stencil/original/products/1255/18962/DSD8632.1_-_" +
                    "DSD4010.1_-_Converted_Reconditioned_GTB2260VK_with_Billet_Compressor_2_new__22166.1627481916.jpg?c=2" +
                    "&imbypass=on&imbypass=on";

    public static final String GTB2260VK_DESCRIPTION =
            "Journal Bearing GTB2260VK hybrid based on the turbochargers from 3.0TDI VAG or 3.0D " +
            "BMW M57/N57 engines is the ultimate upgrade for the 1.9 & 2.0 TDI for someone looking for a fast daily driver.";

    public static final String VAGIS20_IMAGE =
            "https://urbanracers.shop/upload/iblock/b79/shhcy152c2k4439p4oz88ads6yntdf6f/1tervdfj2esz4mp2h1b1dgtd4h6hc0ls.jpg";

    public static final String VAGIS20_DESCRIPTION =
            "The VAG IS20 turbo is a popular upgrade for the IS12 turbo due to its improved performance and efficiency. " +
            "With a larger compressor and turbine inlet, the IS20 can push more air into the engine and deliver more power than the IS12. " +
            "Additionally, the IS20 has better cooling and lubrication, which extends its lifespan and reduces the likelihood of " +
            "malfunctions. Because of these benefits, the IS20 turbo is a popular choice for VW and Audi owners looking for more " +
            "power and reliability.";

    public static final String SK3RR_IMAGE =
            "https://pkfst.ru/800/600/https/www.automachi.com/wp-content/uploads/2017/06/tt-blowertbodyintake-lg.gif";

    public static final String SK3RR_DESCRIPTION =
            "SK3RR увеличивает максимальную отдачу, снижает турболаг и обостряет реакцию двигателя " +
            "на нажатие педали газа. Это объясняется тем, что высокий уровень давления развивается ранее на впуске, " +
            "нежели при штатной системе. Разработчики предусмотрели пять видов форсировки для трехлитрового агрегата с Hybrid Charger.";

    public static final String EQUIPM5_IMAGE =
            "https://yt3.googleusercontent.com/hH4Xd006S0TDIblxoWjQQyykaKJDt_Zwn5YmAbQMUsKnxpI3Vm3-" +
                    "H7xLzuQjPwYvBds1u-U89A=s900-c-k-c0x00ffffff-no-rj";

    public static final String EQUIPM4_IMAGE =
            "https://yt3.googleusercontent.com/ytc/AIdro_kCMYAHys71pMwA60E-2waY13Uii8C5yLmvas117_g7vw=s900-c-k-c0x00ffffff-no-rj";
}