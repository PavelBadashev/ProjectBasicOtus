Проект
Проектная работа

Цель:
Практика в работе с WebDriver и Библиотеками Log2j2, web driver manager.


Описание/Пошаговая инструкция выполнения домашнего задания:
Что будем тестировать: Приложение https://otus.ru/ предоставляет информацию о курсах.

Требования к фреймворку:

Java
Maven/Gradle
TestNG/Junit
Настроено логирование
Код проекта хранится в Git
Для работы со страницами используется паттерн Page Object
Код оформлен согласно Java Code Conventions, комментарии в стиле Javadoc(http://codenet.ru/webmast/java/JavaDoc/) приветствуются
*8. Реализована возможность кроссбаузерного тестирования и удаленного запуска тестов

Разрешается подключение других библиотек, использование BDD подхода, Spring, Lombook

Обязательное тестовое покрытие:

Проверка количества курсов в разделе тестирование:

1) Пользователь переходит в разделе тестирование
2) На странице отображаются карточки курсов. 
   Количество карточек равно 11(В оригинальном ТЗ - 10, но по факту их 11)
3) Просмотр карточки курса:
4) Пользователь переходит на карточку курса
5) В карточке указана информация о курсе:
   - Название
   - Описание
   - Длительность обучения
   - Формат
Минимально достаточное - проверить одну карточку.


Валидация дат предстоящих мероприятий:

1) Пользователь переходит в раздел 
   События -> Календарь мероприятий
2) На странице отображаются карточки предстоящих мероприятий.
3) Даты проведения мероприятий больше или равны текущей дате


Просмотр мероприятий по типу:

1) Пользователь переходит в раздел 
   События -> Календарь мероприятий
2) Пользователь сортирует мероприятия по типу Открытые вебинары
3) На странице отображаются карточки предстоящих мероприятий. 
   На каждой карточке в типе указанно "Открытые вебинары"


Критерии оценки:
Баллов всего 10 проходной балл 10
+3 балла за каждое задание
+1 балл за использование log4j2, web driver manager
