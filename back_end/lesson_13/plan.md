
<h3 style="text-align: center; padding-bottom: 14px">2024-02-02</h3>

# Lesson plan No. 13 08/19/2024

## What we learned and did in the last lesson:
Spring Security is ...

- Authentication
- Authorization

We implemented **Spring Security** into the project:
- User
- Role
- changelog v.0.4.0
- password encryptor
- fill in the User, Role tables
- UserDetails
- Repository
- UserRepository
- RoleRepository
- UserService
- ProductController
- getAll
- getById
- security/config
- SecurityConfig
- testing in Postman

## Topic of today's lesson:
- Using JWT (Json Web Token)
  ![img.png](img.png)

## Practice
- we study the necessary amount of actions and code for implementation:
- adding dependencies to pom.xml
- sec_dto
- RefreshRequestsDto
- TokenResponseDto
- class AuthInfo
- security_service - TokenService - AuthService - security_controller - AuthController - /auth - /auth/login - /auth/refresh - security_config - SecurityConfig - part 1 - sec_filter - TokenFilter - security_config - SecurityConfig - part 2 - testing

___

# План занятия №13 19.08.2024

## Что мы узнали и сделали за прошлое занятие:
Spring Security - это для обеспечения аутентификации и авторизации пользователей
  - Authentication - проверка имени пользователя (уникально!) и пароля
  - Authorization - предоставление прав на основании правил по роли

Выполнили внедрение **Spring Security** в проект:
- User - мы его создали добавили "кучу" аннотаций для Spring, JPA 
- Role - аналогично User
- changelog v.0.4.0 - создали таблицы и связи 
- шифровальщик паролей - BCryptPasswordEncoder().encode()
- заполняем таблицы User, Role - ROLE_ADMIN, ROLE_USER
- UserDetails - им расширили User
- Repository - создаем
  - UserRepository
    - findByUsername()
  - RoleRepository
    - findByTitle()
- UserService - создаем 
  - метод для извлечения UserDetails из репозитория
- ProductController
  - getAll - переписали на endpoint /products/all
  - getById - переписали /products?id=5
- security/config
  - SecurityConfig - создаем, пишем SecurityFilterChain
- тестирование в Postman

## Тема сегодняшнего занятия:
- Использование JWT (Json Web Token)
![img.png](img.png)

## Практика
- изучаем необходимый объем действий и кода для реализации:
  - добавление зависимостей в pom.xml
  - sec_dto
    - RefreshRequestsDto
    - TokenResponseDto
  - class AuthInfo
  - security_service
    - TokenService
    - AuthService 
  - security_controller
    - AuthController
      - /auth
      - /auth/login
      - /auth/refresh
  - security_config
    - SecurityConfig - part 1
  - sec_filter
    - TokenFilter
  - security_config
    - SecurityConfig - part 2

- проводим тестирование 
