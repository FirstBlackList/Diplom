
[![Build status](https://ci.appveyor.com/api/projects/status/1apkspc2sa4rnknl?svg=true)](https://ci.appveyor.com/project/FirstBlackList/diplom)

----
# __Дипломный проект профессии «Тестировщик ПО»__

## *__Автоматизация тестирования веб-сервиса приложения по организации путешествий__*

## Клонирование проекта, запуск SUT, авто-тестов и генерация репорта
 
  - ## Склонировать проект из репозитория командой: 
    - ### `git clone`

  - ## Открыть склонированный проект в Intellij IDEA

### Подключение SUT к PostgreSQL

1. __Запустить Docker Desktop__
1. __В терминале в корне проекта запустить контейнеры:__

    - ### __`docker-compose up -d`__
1. __Запустить приложение:__

    - ### __`java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app`__
1. __Открыть второй терминал__
1. __Запустить тесты:__

    - ### __`.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app`__
1. __Создать отчёт Allure и открыть в браузере__

    - ### __`.\gradlew allureServe`__
1. __Закрыть отчёт:__

   **CTRL + C -> y -> Enter**
1. __Перейти в первый терминал__
1. __Остановить приложение:__

   **CTRL + C**
1. __Остановить контейнеры:__

    - ### __`docker-compose down`__
   </a>

### Подключение SUT к MySQL

1. __Запустить Docker Desktop__
1. __В терминале в корне проекта запустить контейнеры:__

    - ### __`docker-compose up -d`__
1. __Запустить приложение:__

    - ### __`java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app`__
1. __Открыть второй терминал__
1. __Запустить тесты:__

    - ### __`.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app`__
1. __Создать отчёт Allure и открыть в браузере__

    - ### __`.\gradlew allureServe`__
1. __Закрыть отчёт:__

   **CTRL + C -> y -> Enter**
1. __Перейти в первый терминал__
1. __Остановить приложение:__

   **CTRL + C**
1. __Остановить контейнеры:__

    - ### __`docker-compose down`__
   </a>
   
----
## **Документация по проекту**

- ### [__План автоматизации__](documents/Plan.md)

- ### [__Отчётные документы по итогам тестирования веб-сервиса__](documents/Report.md)

- ### [__Отчёт по итогам автоматизации__](documents/Summary.md)
   </a>
----
