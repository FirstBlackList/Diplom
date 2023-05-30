[![Build status](https://ci.appveyor.com/api/projects/status/1apkspc2sa4rnknl?svg=true)](https://ci.appveyor.com/project/FirstBlackList/diplom)
# __Дипломный проект профессии «Тестировщик»__
## *__Автоматизация тестирования веб-сервиса приложения по организации путешествий__*

## Клонирование проекта, запуск SUT, авто-тестов и генерация репорта

>### Склонировать проект из репозитория командой 

### `git clone`

>### Открыть склонированный проект в Intellij IDEA

>### Подключение SUT к PostgreSQL

1. Запустить Docker Desktop 
1. Открыть проект в IntelliJ IDEA
1. В терминале в корне проекта запустить контейнеры:

### __`docker-compose up -d`__
1. Запустить приложение:

### __`java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`__
1. Открыть второй терминал
1. Запустить тесты:

### __`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`__
1. Создать отчёт Allure и открыть в браузере

### __`.\gradlew allureServe`__
1. Закрыть отчёт:

   **CTRL + C -> y -> Enter**
1. Перейти в первый терминал
1. Остановить приложение:

   **CTRL + C**
1. Остановить контейнеры:

### __`docker-compose down`__
   </a>

>### Подключение SUT к MySQL

1. Запустить Docker Desktop
1. Открыть проект в IntelliJ IDEA
1. В терминале в корне проекта запустить контейнеры:

### __`docker-compose up -d`__
1. Запустить приложение:

### __`java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`__
1. Открыть второй терминал
1. Запустить тесты:

### __`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`__
1. Создать отчёт Allure и открыть в браузере

### __`.\gradlew allureServe`__
1. Закрыть отчёт:

   **CTRL + C -> y -> Enter**
1. Перейти в первый терминал
1. Остановить приложение:

   **CTRL + C**
1. Остановить контейнеры:

### __`docker-compose down`__
   </a>
