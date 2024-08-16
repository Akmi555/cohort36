<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

# Lesson 12

## Spring Security

## 01. Security in the application

* `Authentication` - user identification, who are you?

* Authentication - the process in which the application requests a login and password and checks their correctness
* checking the authenticity of user data

* `Authorization` - checking user rights, what actions can you perform?

* Authorization is a process where an application checks the user's rights to perform certain operations
* for example, checking whether the user can get all courses at `/courses`

* An HTTP session is some object that we store on the server, with which a specific user can be associated

## 02. The session-based authentication process

* First, the client sends a POST request to the server at `api/login`
* In the body of the request, the client transmits authentication data (for example, email and password)
* The server checks the correctness of this data (finds the user in the database, hashes the entered password, compares it with the one in the database)
* If the login data is correct, the server creates a session object in RAM
* associates the user's data with this object
* assigns an identifier to the object
* sends this identifier to the client, which is stored in cookies on the client

## 03. The session-based authorization process

* The client sends its request along with a cookie that contains the identifier sessions
* The server uses this identifier to find the session in the storage and get its attributes (in our case, it is the user)
* Having received the user, the server checks their role and access to a specific endpoint based on the rules (we will describe them below)
* Either the requested resource or the 403 status (Forbidden) is returned to the client

## 04. Configuring Spring Boot security with Spring Security

* When connecting Spring Boot Starter Security, you have:
* Login page
* Protection of all endpoints
* Login `user`
* Password is generated in the console
* But we want people to log in with their logins and passwords

### Steps to configure Spring Security

1. Create a class implementing the `UserDetails` interface
* This class is needed to adapt your user to Spring Security security
* In fact, this is an adapter of our `User` class for `Spring Security`
2. Create a class implementing the `UserDetailsService` interface
* This class is needed to show Spring Security where to get the user for verification
3. Setting up the Spring Security configuration
4. Putting things in order with responses to requests

* `Authentication` - an object that stores information about the user and their authentication status for each request.

* `SecurityContext` - security information that is associated with the current execution thread (Thread). Stores the Authentication object.

* `SecurityContextHolder` - binds the SecurityContext to the current execution thread. By default, ThreadLocal - the security context is available to all methods executed within this thread.

* I.e. when a request comes to the server, the server allocates one thread from the `Tomcat Thread Pool`
* Next, SecurityContextHolder looks at the current session and binds the `Authentication` object to the current execution thread
* Next, when a request comes to any controller or handler - it already comes with an authentication object

If you look at the Spring Security technology in more detail, Spring Security has many key components that provide its flexibility and power in managing application security.

Let's consider some of them in more detail:

### 1. HttpSecurity
`HttpSecurity` is the main class in the Spring Security configuration, which allows you to configure web security for specific HTTP requests. With its help, you can specify which URLs require authentication, configure the login form, logout, session management, and also define access rules to certain resources. An example would be a configuration that requires authentication for all requests, but excludes some static resources (like CSS or images):

```java
protected void configure(HttpSecurity http) throws Exception {
http
.authorizeRequests()
.antMatchers("/css/**", "/index").permitAll()
.antMatchers("/user/**").authenticated()
.and()
.formLogin();
}
```

### 2. SecurityContextHolder and SecurityContext
`SecurityContextHolder` is a class that provides access to `SecurityContext`. This context stores authentication details and is shared throughout the application, allowing information about the current user to be retrieved. By default, `SecurityContextHolder` uses `ThreadLocal` to store authentication data, making this data available anywhere in the current thread.

### 3. Authentication and Authorization
- **Authentication** refers to the process of verifying a user's credentials. In Spring Security, the `Authentication` object stores authentication information, including the username, password, and

</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

# Lesson 12

## Spring Security

## 01. Безопасность в приложении


* `Aутентификация` - идентификация пользователя, кто ты?

* Аутентификация - процесс, при котором приложение запрашивает логин и пароль и проверяет их корректность
    * проверка подлинности данных пользователя


* `Авторизация` - проверка прав пользователя, какие действия ты можешь выполнять?

* Авторизация - процесс, при котором приложение проверяет права пользователя на выполнение каких-либо операций
    * например, проверка на возможность получения пользователем всех курсов по адресу `/courses`

* HTTP-сессия - это некоторый объект, который мы храним на сервере, с которым может быть ассоциирован конкретный пользователь

## 02. Процесс аутентификации на основе сессии

* Сначала клиент отправляет POST-запрос на сервер по адресу `api/login`
* В теле запроса клиент передает данные для аутентификации (например, email и пароль)
* Сервер проверяет корректность этих данных (находит пользователя в базе, хеширует введенный пароль, сравнивает с тем, который есть в базе)
* Если данные для входа корректные, то сервер в оперативной памяти создает объект сессии
    * ассоциирует с этим объектом данные пользователя
    * назначает объекту идентификатор
    * отправляет клиенту этот идентификатор, который на клиенте сохраняется в куках

## 03. Процесс авторизации на основе сессии

* Клиент посылает свой запрос вместе с кукой, которая содержит идентификатор сессии
* Сервер по этому идентификатору находит сессию в хранилище и получает ее атрибуты (в нашем случае это пользователь)
* Получив пользователя, сервер проверяет его роль и доступ к определенному endpoint на основе правил (опишем далее)
* Клиенту возвращается либо запрошенный ресурс, либо 403-статус (Запрещено)


## 04. Настройка безопасности Spring Boot с Spring Security

* При подключении Spring Boot Starter Security у вас есть:
    * Страница входа
    * Защита всех endpoints
    * Логин `user`
    * Пароль генерируется в консоли
* Но мы хотим, чтобы люди заходили под своими логинами и паролями

### Шаги по настройке Spring Security

1. Создать класс-реализацию интерфейса `UserDetails`
* Данный класс нужен для того, чтобы адаптировать вашего пользователя под безопасность Spring Security
* По сути, это адаптер нашего класса `User` для `Spring Security`
2. Создать класс-реализацию интерфейса `UserDetailsService`
* Данный класс нужен для того, чтобы показать Spring Security откуда брать пользователя для проверки
3. Настройка конфигурации Spring Security
4. Навести порядок с ответами на запросы


* `Authentication` - объект, который хранит для каждого запроса информацию о пользователе и статусе его аутентификации.

* `SecurityContext` - информация о безопасности, которая ассоциирована с текущим потоком исполнения (Thread). Хранит объект Authentication.

* `SecurityContextHolder` - привязывает SecurityContext к текущему потоку исполнения. По умолчанию ThreadLocal - контекст безопосности доступен всем методам, исполняемым в рамках данного потока.

* Т.е. когда приходит запрос на сервер, сервер выделяет ему один поток из `Tomcat Thread Pool`
* Далее, SecurityContextHolder смотрит текущую сессию и привязывает объект `Authentication` к текущему потоку исполнения
* Далее, когда запрос приходит в какой-либо контроллер или хендлер - он уже приходит с объектом аутентификации


Если посмотреть на технологию работы Spring Security подробнее, то в Spring Security есть множество ключевых компонентов, которые обеспечивают его гибкость и мощность в управлении безопасностью приложений.


Рассмотрим некоторые из них подробнее:

### 1. HttpSecurity
`HttpSecurity` является основным классом в конфигурации Spring Security, который позволяет настроить веб-безопасность для определенных HTTP-запросов. С его помощью можно задать, какие URL-адреса требуют аутентификации, настроить форму логина, разлогинивание, сессионное управление, а также определить правила доступа к определенным ресурсам. Примером может служить конфигурация, которая требует аутентификации для всех запросов, но исключает некоторые статичные ресурсы (например, CSS или изображения):

```java
protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/css/**", "/index").permitAll()
        .antMatchers("/user/**").authenticated()
        .and()
        .formLogin();
}
```

### 2. SecurityContextHolder и SecurityContext
`SecurityContextHolder` — это класс, который предоставляет доступ к `SecurityContext`. Этот контекст хранит детали аутентификации и предоставляется во всем приложении, позволяя получить информацию о текущем пользователе. По умолчанию `SecurityContextHolder` использует `ThreadLocal` для хранения данных аутентификации, что делает эти данные доступными в любом месте текущего потока.

### 3. Authentication и Authorization
- **Authentication** относится к процессу верификации учетных данных пользователя. В Spring Security объект `Authentication` хранит информацию об аутентификации, включая имя пользователя, пароль и коллекцию `GrantedAuthority`, которая представляет разрешения пользователя.
- **Authorization** (авторизация) относится к процессу определения, разрешен ли доступ пользователю к определенному ресурсу. В Spring Security авторизация обычно осуществляется путем проверки, содержат ли объекты `GrantedAuthority` пользователя необходимые права доступа.

### 4. UserDetails и UserDetailsService
`UserDetails` — это интерфейс, который предоставляет необходимую информацию для построения объекта `Authentication`. Эта информация включает в себя имя пользователя, пароль, разрешения и другие аспекты, связанные с учетной записью пользователя. `UserDetailsService` — это интерфейс, используемый для загрузки данных пользователя по имени пользователя. Одной из наиболее частых реализаций `UserDetailsService` является загрузка данных пользователя из базы данных.

### 5. GrantedAuthority
`GrantedAuthority` представляет собой разрешение, предоставленное пользователю. В контексте Spring Security, разрешения используются для предоставления или ограничения доступа к функциональности или данным приложения. Обычно авторизация выполняется путем проверки, имеются ли у `Authentication` объекта соответствующие `GrantedAuthority`.

### Прочие элементы:
- **PasswordEncoder**: используется для шифрования и сравнения паролей для обеспечения безопасности учетных записей.
- **SessionManagementFilter**: обеспечивает управление сессиями, включая предотвращение фиксации сессии и параллельного доступа.
- **CsrfToken**: механизм защиты от CSRF-атак, которые могут заставить пользователя выполнить нежелательные действия на веб-сайте, на котором он аутентифицирован.

Каждый из этих компонентов играет ключевую роль в гибком и мощном управлении безопасностью в приложениях Spring, обеспечивая различные уровни защиты и контроля доступа.


### FilterChain в Spring Security

В контексте Spring Security `FilterChain` представляет собой центральный элемент в обработке входящих HTTP-запросов. Он состоит из серии фильтров, каждый из которых выполняет определенные задачи, связанные с аутентификацией и авторизацией, а также другими аспектами безопасности приложения. Эти фильтры взаимодействуют друг с другом, образуя цепочку, где каждый фильтр передает запрос следующему, если запрос не был полностью обработан или заблокирован.

#### Основная функциональность
`FilterChain` используется для применения различных безопасностных мер в определенном порядке. Например, может сначала произойти проверка CSRF-токена, затем аутентификация пользователя и после этого — авторизация доступа к ресурсам.

#### Компоненты FilterChain
Основные фильтры, включенные в `FilterChain` в Spring Security, включают, но не ограничиваются следующими:

1. **SecurityContextPersistenceFilter**: сохраняет и загружает `SecurityContext` для каждого запроса (например, из HTTP-сессии).
2. **LogoutFilter**: обрабатывает логаут пользователя.
3. **UsernamePasswordAuthenticationFilter**: отвечает за обработку попыток аутентификации через форму входа.
4. **DefaultLoginPageGeneratingFilter**: создает стандартную страницу входа, если разработчик не предоставил собственную.
5. **BasicAuthenticationFilter**: обеспечивает поддержку аутентификации с помощью HTTP Basic Authentication.
6. **RequestCacheAwareFilter**: использует кеш для сохранения запроса во время аутентификации, чтобы после входа можно было вернуть пользователя к изначально запрошенной странице.
7. **SecurityContextHolderAwareRequestFilter**: оборачивает входящий запрос для интеграции с `SecurityContext`.
8. **AnonymousAuthenticationFilter**: обеспечивает обработку пользователей, которые не прошли аутентификацию.
9. **SessionManagementFilter**: управляет сессионной политикой.
10. **ExceptionTranslationFilter**: перехватывает исключения безопасности и проводит их обработку, например, перенаправление на форму входа или страницу ошибки.
11. **FilterSecurityInterceptor**: последний фильтр в цепочке, отвечает за авторизацию выполнения операций с конкретными объектами.

#### Конфигурация FilterChain
Конфигурация `FilterChain` может быть настроена через Java Config в Spring Security. Разработчики могут добавлять или удалять определенные фильтры, а также изменять порядок их выполнения в зависимости от специфических требований приложения. Это позволяет точно настроить процесс безопасности в соответствии с нуждами бизнеса и особенностями приложения.

#### Пример конфигурации
```java
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .and()
            .httpBasic();
    }
}
```
В этом примере настраивается `HttpSecurity`, чтобы обеспечить базовую аутентификацию и форму логина, защищая при этом все URL-адреса приложения.

Использование `FilterChain` в Spring Security обеспечивает высокую степень контроля и гибкости в управлении безопасностью, позволяя адаптировать каждый аспект к конкретным условиям и требованиям приложения.


## Spring Security

Spring Security — мощный инструмент для обеспечения безопасности в приложениях на базе Spring. Он предоставляет широкий набор функциональностей, начиная от аутентификации и заканчивая авторизацией, и включает в себя поддержку для защиты от различных видов атак.


### Ключевые объекты контекста Spring Security:

- **SecurityContextHolder**: В нем содержится информация о текущем контексте безопасности приложения, который включает в себя подробную информацию о пользователе (Principal), работающем в настоящее время с приложением. По умолчанию, SecurityContextHolder использует `ThreadLocal` для хранения такой информации, что означает, что контекст безопасности всегда доступен для методов, исполняющихся в том же самом потоке. Для изменения стратегии хранения информации можно воспользоваться статическим методом класса `SecurityContextHolder.setStrategyName(String strategy)`.
- **SecurityContext**: Содержит объект `Authentication` и в случае необходимости информацию системы безопасности, связанную с запросом от пользователя.
- **Authentication**: Представляет пользователя (Principal) с точки зрения Spring Security.
- **GrantedAuthority**: Отражает разрешения, выданные пользователю в масштабе всего приложения, такие разрешения (как правило, называются «роли»), например `ROLE_ANONYMOUS`, `ROLE_USER`, `ROLE_ADMIN`.
- **UserDetails**: Предоставляет необходимую информацию для построения объекта `Authentication` из DAO объектов приложения или других источников данных системы безопасности. Объект `UserDetails` содержит имя пользователя, пароль, флаги: `isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled` и Collection — прав (ролей) пользователя.
- **UserDetailsService**: Используется, чтобы создать объект `UserDetails` путем реализации единственного метода этого интерфейса:
  ```java
  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
  ```

### Аутентификация

1. Пользователю будет предложено войти в систему, предоставив имя (логин или email) и пароль. Имя пользователя и пароль объединяются в экземпляр класса `UsernamePasswordAuthenticationToken` (экземпляр интерфейса `Authentication`) после чего он передается экземпляру `AuthenticationManager` для проверки.
2. В случае, если пароль не соответствует имени пользователя, будет выброшено исключение `BadCredentialsException` с сообщением “Bad Credentials”.
3. Если аутентификация прошла успешно, возвращается полностью заполненный экземпляр `Authentication`.
4. Для пользователя устанавливается контекст безопасности путем вызова метода `SecurityContextHolder.getContext().setAuthentication(…)`, куда передается объект, который вернул `AuthenticationManager`.


### SecurityContextHolder и SecurityContext

`SecurityContextHolder` используется для хранения деталей о текущем пользователе, что позволяет эти данные легко извлекать в любом месте приложения:

```java
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if (authentication != null) {
    String currentUserName = authentication.getName();
    // использование имени пользователя
}
```

`SecurityContext` хранит в себе объект `Authentication`, который представляет текущего пользователя и его права в системе.

### UserDetails и UserDetailsService

`UserDetailsService` интерфейс используется для загрузки данных пользователя по имени пользователя.
Реализация этого интерфейса возвращает объект `UserDetails`, который Spring Security использует для построения объекта `Authentication`.

Пример реализации `UserDetailsService`:

```java
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return authorities;
    }
}
```

## Аутентификация

Процесс аутентификации начинается с получения имени пользователя и пароля, которые затем преобразуются в `UsernamePasswordAuthenticationToken` и передаются `AuthenticationManager`:

```java
Authentication request = new UsernamePasswordAuthenticationToken(username, password);
Authentication result = authenticationManager.authenticate(request);
SecurityContextHolder.getContext().setAuthentication(result);
```

## Использование PasswordEncoder

### BCryptPasswordEncoder

`BCryptPasswordEncoder` является наиболее рекомендуемым `PasswordEncoder` за его способность эффективно защищать пароли с помощью bcrypt алгоритма:

```java
@Autowired
private PasswordEncoder passwordEncoder;

public void registerUser(String username, String rawPassword) {
    String encodedPassword = passwordEncoder.encode(rawPassword);
    User newUser = new User(username, encodedPassword);
    userRepository.save(newUser);
}
```

### DelegatingPasswordEncoder

`DelegatingPasswordEncoder` позволяет использовать несколько методов хеширования паролей одновременно, что удобно при переходе на новую систему хеширования:

```java
PasswordEncoder defaultEncoder = new BCryptPasswordEncoder();
Map<String, PasswordEncoder> encoders = new HashMap<>();
encoders.put("bcrypt", new BCryptPasswordEncoder());
encoders.put("noop", NoOpPasswordEncoder.getInstance());

PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt", encoders);
```

## Servlet Security и Filters

В Spring Security каждый `Filter` в цепочке фильтрации может изменять запрос или ответ, либо прерывать цепочку обработки. Очень важен порядок, в котором фильтры добавлены в цепочку:

```java
http.addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);
```


### Как работают фильтры в Spring Security

Фильтры в Spring Security играют ключевую роль в процессе обработки входящих запросов к вашему приложению.
Они обеспечивают не только аутентификацию и авторизацию, но и множество других функций безопасности, таких как CSRF-защита,
управление сессиями и многое другое.

Фильтры в Spring Security – это специализированные компоненты, которые встраиваются в стандартный цикл обработки запросов в веб-приложении на Java.
Каждый фильтр выполняет определенную задачу, связанную с безопасностью, и передает управление следующему фильтру в цепочке.
Все это происходит до того, как запрос достигает сервлета, который непосредственно обрабатывает бизнес-логику.

#### Классы и интерфейсы

Основным классом, с которым стоит ознакомиться, является `FilterChainProxy`. Этот класс отвечает за управление цепочкой фильтров.
Каждый фильтр реализует интерфейс `javax.servlet.Filter`.

##### Примеры ключевых фильтров:

1. **UsernamePasswordAuthenticationFilter** — обрабатывает формы аутентификации, извлекая имя пользователя и пароль из запроса.
2. **CsrfFilter** — предотвращает атаки межсайтовой подделки запросов (CSRF).
3. **LogoutFilter** — обрабатывает выход пользователя из системы.

#### Пример кода:

Давайте рассмотрим, как можно добавить кастомный фильтр, который будет логировать каждый входящий запрос:

```java
public class LoggingFilter extends GenericFilterBean {
    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        LOG.info("Request URI is: {}", req.getRequestURI());
        chain.doFilter(request, response);
    }
}
```

#### Как добавить фильтр в конфигурацию:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // Добавляем кастомный фильтр перед фильтром UsernamePasswordAuthenticationFilter
            .addFilterBefore(new LoggingFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/public/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .logout();
    }
}
```

### Комментарии к примеру кода:

- **LoggingFilter** - этот фильтр логирует URI каждого запроса к серверу.
- В методе `configure` класса `SecurityConfig` фильтр `LoggingFilter` добавляется перед `UsernamePasswordAuthenticationFilter`, что позволяет ему обрабатывать запрос до начала процесса аутентификации.


###  Классы фильтров

#### а. **UsernamePasswordAuthenticationFilter**

Этот фильтр обрабатывает аутентификационные запросы типа `POST` с параметрами `username` и `password`.
Он создает объект `UsernamePasswordAuthenticationToken` и передает его `AuthenticationManager` для аутентификации.

Пример конфигурации:

```java
http.addFilterBefore(new UsernamePasswordAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
```

#### б. **BasicAuthenticationFilter**

Этот фильтр обрабатывает HTTP Basic Authentication. Он извлекает заголовок `Authorization` из запроса и передает его `AuthenticationManager` для аутентификации.

Пример конфигурации:

```java
http.addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
```

#### в. **FilterChainProxy**

Этот фильтр является основной точкой входа в цепочку фильтров Spring Security. Он вызывает все остальные фильтры в заданном порядке.

Пример конфигурации (обычно не требуется, так как он автоматически добавляется в контекст безопасности):

```java
http.addFilterBefore(new FilterChainProxy(), BasicAuthenticationFilter.class);
```

#### г. Пользовательские фильтры

Вы также можете создавать собственные фильтры, расширяя класс `GenericFilterBean` или реализуя интерфейс `javax.servlet.Filter`.
Эти фильтры могут выполнять различные действия, например, проверять наличие аутентификации, устанавливать права доступа и т. д.

Пример пользовательского фильтра:

```java
public class CustomFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // выполнение действий перед передачей запроса дальше по цепочке
        chain.doFilter(request, response); // передача запроса следующему фильтру
        // выполнение действий после обработки запроса другими фильтрами
    }
}
```

### 3. Порядок выполнения фильтров

Порядок выполнения фильтров очень важен, так как каждый фильтр может влиять на запрос или ответ перед его передачей следующему фильтру.
Фильтры обрабатываются в том порядке, в котором они добавлены в цепочку.

### Пример конфигурации

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new UsernamePasswordAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new BasicAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .and()
        .httpBasic();
}
```

В этом примере фильтры добавляются в цепочку и определяется порядок их выполнения. Сначала будет выполнен пользовательский фильтр, затем фильтр аутентификации по имени пользователя и паролю, а затем фильтр базовой аутентификации. После этого выполняется авторизация запросов.


### Основные фильтры

В список фильтров Spring Security входят многие элементы, каждый из которых играет свою роль в обеспечении безопасности приложения.
Но некоторые из них являются ключевыми в большинстве конфигураций, так как они выполняют основные функции безопасности, такие как аутентификация, авторизация и защита от атак.

Вот несколько из самых важных фильтров:

1. **SecurityContextPersistenceFilter** – этот фильтр управляет контекстом безопасности для каждого запроса. Он обеспечивает, что информация о текущем пользователе (аутентификация и возможно его права) сохраняется в начале обработки запроса и очищается в конце.

2. **UsernamePasswordAuthenticationFilter** – обрабатывает формы входа в систему, где пользователи вводят имя пользователя и пароль. Этот фильтр пытается аутентифицировать запросы с учетными данными.

3. **BasicAuthenticationFilter** – предоставляет поддержку для HTTP Basic Authentication, что является простым методом передачи учетных данных через заголовки HTTP.

4. **BearerTokenAuthenticationFilter** – обрабатывает аутентификацию с использованием токенов JWT или других механизмов Bearer Token, часто используемых в REST API.

5. **LogoutFilter** – обеспечивает возможность выхода из системы. При получении определенного запроса на выход он уничтожает сессию пользователя.

6. **CsrfFilter** – защищает от атак типа Cross-Site Request Forgery (CSRF), проверяя наличие специального токена CSRF в каждом запросе на изменение данных.

7. **ExceptionTranslationFilter** – перехватывает исключения доступа (Spring Security AccessDeniedException и AuthenticationException), инициируя процесс аутентификации или информируя пользователя о нехватке прав.

8. **FilterSecurityInterceptor** – последний фильтр в цепочке, который использует конфигурацию доступа к URL или методам для принятия решения о предоставлении доступа текущему пользователю. Это ядро авторизации Spring Security.

9. **OAuth2LoginAuthenticationFilter** и **OAuth2AuthorizationRequestRedirectFilter** – обеспечивают поддержку OAuth 2.0, позволяя пользователям аутентифицироваться через внешние сервисы (например, Google, Facebook).

Эти фильтры в совокупности обеспечивают основную функциональность любой защищенной системы на основе Spring Security, обрабатывая все аспекты безопасности от аутентификации до авторизации и защиты от веб-атак.


</details>