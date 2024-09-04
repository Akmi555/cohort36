<h3 style="text-align: center; padding-bottom: 14px">2024-02-02</h3>

# Lesson plan No. 20 09/04/2024



___

# План занятия №19 02.09.2024

## Что мы узнали и сделали за прошлое занятие:
Lesson 17:
- ящик для отправки мейлов на Gmail.com:
  - двухфакторная идентификация
  - получение пароля для приложения
- изменение таблиц в БД:
  - user: добавили поля - 
  - таблица для кодов
- создали класс ConfirmationCode
- расширили полями класс User
- сделали файл с шаблоном письма
- сделали репозиторий ConfirmationCodeRepository
- создали интерфейс ConfirmationService.java
- реализовали ConfirmationServiceImpl.java
-----------------------------------------------
Lesson 18:
- interface EmailService
- class EmailServiceImpl implements EmailService, методы:
  - sendConfirmationEmail(User user)
  - generateEmailText(User user)

- interface RoleService
- class RoleServiceImpl implements RoleService
  - getRoleUser()

- interface UserService extends UserDetailsService (!!!)
- class UserService implements UserService
  - loadUserByUsername(String username)
  - register(User user)

- class RegistrationController
  - /register - endpoint
  - register(@RequestBody User user)

## Тема сегодняшнего занятия:
1. Реализация загрузки файлов на Digital Ocean.
2. Реализация функционала добавления изображения к продукту.

## Практика
- добавляем в pom.xml зависимость на библиотеку aws
- создаем новый проект Spaces на Digital Ocean
- changelog v.0.7.0 - добавляем колонку image для продукта
- класс Product - добавляем поле image
- ProductRepository - добавляем метод findByTitle
- ProductService - добавляем метод attachImage
  - @Transactional - чтобы "все произошло"

- получаем доступы в DO
- вносим переменные окружения
- класс AppConfig

- FileController
- interface FileService
- class FileServiceImpl
- проверяем сохранение картинки в БД

# 15 важных изученных технологий

1. Spring + Spring Boot, DI + IC
2. Spring Initializr https://start.spring.io/
3. Maven , pom.xml
4. Liquibase
5. Jackson, json
6. Tomcat - http Server, http - протокол
7. POST, GET,  PUT, DELETE ..., CRUD
8. Postman - для тестирования BE, Swagger
9. REST API - endpoints,...
10. JWT, Spring Security, аутентификация + авторизация
11. JDBC, ORM, Hybernate, JPA
12. многослойный монолит, DTO
13. интеграционное тестирование
14. отправка писем
15. работа с файлами






