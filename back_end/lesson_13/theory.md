<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

## Spring Security
JWT (JSON Web Token) consists of three parts separated by dots: header, payload, and signature.

1. **Header:** This is a JSON object containing two elements: the token type (typ) and the hashing algorithm used (alg). For example:
```json
{
"alg": "HS256",
"typ": "JWT"
}
```

2. **Payload:** This is also a JSON object that contains claims about the user or other information. There are three types of claims:
- **Registered claims:** Their values ​​are standardized and include, for example, the user identifier (sub), the token issuance time (iat), and the token expiration date (exp).
- **Public Claims:** These are custom claims that can be used by agreement between the parties.
- **Private Claims:** These are custom claims that are used by a specific application.

3. **Signature:** This is the result of encrypting the encoded header string, payload, and secret key (or certificate) using the specified algorithm from the header. This ensures that the data is authenticated and protected from being modified.

The process of creating a JWT is as follows:

1. **Create Header:** Generate a JSON object with the token type and the chosen hashing algorithm.
2. **Create Payload:** Generate a JSON object with the necessary information (user ID, expiration date, etc.).
3. **Encode Header and Payload:** Both JSON objects are encoded in Base64Url format.
4. **Create Signature:** The header and payload are concatenated with the secret key and hashed with the specified algorithm from the header.

5. **Create JWT:** The resulting encoded header and payload strings, as well as the signature, are concatenated using dots.

Here is an example of a JWT: `header.payload.signature` (encoded as `xxxxx.yyyyy.zzzzz`).

Public claims and private claims in the context of JWT (JSON Web Token) are not official terms or standards. Instead, there are standard and reserved claims, as well as custom claims.

1. **Registered Claims:** These claims have standardized names and are used for common scenarios. Some of them include:

- `iss` (Issuer): Specifies who issued the token.

- `sub` (Subject): The subject (user) identifier.
- `iat` (Issued At): The time the token was issued.
- `exp` (Expiration Time): The expiration time of the token.

2. **Public Claims:** These are claims that can be defined and used in applications by agreement between the parties. They are not standard and do not have fixed names. Examples of such claims can include information about the user's role or other application data.

3. **Private Claims:** There may be some confusion here as well, since typically JWT claims can be public and accessible to anyone who has access to the token. If you mean claims that should be hidden from third parties, then the JWT should be signed using a private key or certificate to ensure privacy. This does not hide the claims themselves, but it does make the token unforgeable and tamper-proof.

It is important to remember that any data included in a JWT can be read by anyone with access to the token. If privacy is important to the data, it should be encrypted instead of simply signed.

The authentication and authorization process using JWT (JSON Web Tokens) in a Spring Security-based system can be divided into several key steps. I will describe each step of this process in detail below.

### Step 1: Register and Create a User
1. **User Registration**: The user sends a registration request (`/signup`) including a username, email, and password.
2. **Uniqueness Check**: The system checks if a user with the same username or email already exists.
3. **Password Hashing**: If the checks are successful, the user's password is hashed for secure storage in the database.
4. **Save User**: A new user record is added to the database.

### Step 2: Authenticate and Issue JWT
1. **Login**: The user submits a login request (`/signin`) with their username and password.
2. **Find User**: The system uses `UserDetailsService` to load the user details from the database.
3. **Verify Password**: The `PasswordEncoder` is used to check if the provided password matches the hashed version stored in the database.
4. **Generate Token**: If the details are correct, the system generates a JWT, which includes

</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

## Spring Security
JWT (JSON Web Token) состоит из трех частей, разделенных точками: заголовок (header), полезная нагрузка (payload) и подпись (signature).

1. **Заголовок (Header):** Это JSON-объект, содержащий два элемента: тип токена (typ) и используемый алгоритм хеширования (alg). Например:
    ```json
    {
      "alg": "HS256",
      "typ": "JWT"
    }
    ```

2. **Полезная нагрузка (Payload):** Это также JSON-объект, который содержит утверждения (claims) о пользователе или другую информацию. Существуют три типа утверждений:
- **Утверждения о пользователе (registered claims):** Их значения стандартизированы и включают, например, идентификатор пользователя (sub), время выдачи токена (iat) и срок действия токена (exp).
- **Приватные утверждения (public claims):** Это пользовательские утверждения, которые могут использоваться по соглашению между сторонами.
- **Защищенные утверждения (private claims):** Это кастомные утверждения, которые применяются конкретным приложением.

3. **Подпись (Signature):** Это результат шифрования кодированной строки заголовка, полезной нагрузки и секретного ключа (или сертификата), используя указанный алгоритм из заголовка. Это обеспечивает проверку подлинности данных и защиту от их изменения.

Процесс создания JWT выглядит следующим образом:
1. **Создание заголовка:** Формируется JSON-объект с типом токена и выбранным алгоритмом хеширования.
2. **Создание полезной нагрузки:** Формируется JSON-объект с необходимой информацией (идентификатор пользователя, срок действия и т. д.).
3. **Кодирование заголовка и полезной нагрузки:** Оба JSON-объекта кодируются в формат Base64Url.
4. **Создание подписи:** Заголовок и полезная нагрузка объединяются с секретным ключом и хешируются указанным алгоритмом из заголовка.
5. **Создание JWT:** Полученные закодированные строки заголовка и полезной нагрузки, а также подпись объединяются через точки.

Вот пример JWT: `header.payload.signature` (в кодированном виде выглядит как `xxxxx.yyyyy.zzzzz`).


Приватные утверждения (public claims) и Защищенные утверждения (private claims) в контексте JWT (JSON Web Token) не являются официальными терминами или стандартами. Вместо этого, существуют стандартные и зарезервированные утверждения, а также кастомные (пользовательские) утверждения.

1. **Стандартные утверждения (Registered Claims):** Эти утверждения имеют стандартизированные имена и используются для распространенных сценариев. Некоторые из них включают:

- `iss` (Issuer): Указывает, кто выпустил токен.
- `sub` (Subject): Идентификатор субъекта (пользователя).
- `iat` (Issued At): Время, когда токен был выдан.
- `exp` (Expiration Time): Время истечения срока действия токена.

2. **Приватные утверждения (Public Claims):** Это утверждения, которые могут быть определены и использованы в приложениях по соглашению между сторонами. Они не являются стандартными и не имеют фиксированных имен. Примеры таких утверждений могут включать информацию о роли пользователя или другие прикладные данные.

3. **Защищенные утверждения (Private Claims):** Здесь также может быть некоторое недоразумение, так как обычно утверждения JWT могут быть публичными и доступными для всех, кто имеет доступ к токену. Если вы имеете в виду утверждения, которые должны быть скрытыми от сторонних лиц, то в этом случае JWT должен быть подписан с использованием секретного ключа или сертификата, чтобы обеспечить конфиденциальность. Это не скрывает сами утверждения, но делает токен неподдельным и защищенным от изменений.

Важно помнить, что любые данные, включенные в JWT, могут быть прочитаны всеми, кто имеет доступ к токену. Если важно обеспечить конфиденциальность данных, их следует шифровать, а не просто подписывать.

Процесс аутентификации и авторизации с использованием JWT (JSON Web Tokens) в системе, основанной на Spring Security, можно разделить на несколько ключевых этапов. Ниже я подробно описываю каждый шаг этого процесса.

### Шаг 1: Регистрация и Создание Пользователя
1. **Регистрация пользователя**: Пользователь отправляет запрос на регистрацию (`/signup`), включающий в себя имя пользователя, электронную почту и пароль.
2. **Проверка уникальности**: Система проверяет, существует ли уже пользователь с таким же именем пользователя или электронной почтой.
3. **Хеширование пароля**: Если проверки пройдены успешно, пароль пользователя хешируется для безопасного хранения в базе данных.
4. **Сохранение пользователя**: Запись нового пользователя добавляется в базу данных.

### Шаг 2: Аутентификация и Выдача JWT
1. **Вход в систему**: Пользователь отправляет запрос на вход (`/signin`), указывая свое имя пользователя и пароль.
2. **Поиск пользователя**: Система использует `UserDetailsService` для загрузки данных пользователя из базы данных.
3. **Проверка пароля**: Используется `PasswordEncoder` для проверки, соответствует ли предоставленный пароль хешированной версии, хранящейся в базе данных.
4. **Генерация токена**: Если данные верны, система генерирует JWT, который включает в себя информацию о пользователе и срок действия. Токен подписывается секретным ключом.
5. **Отправка токена пользователю**: JWT возвращается пользователю в ответе на запрос.

### Шаг 3: Запросы с JWT для Доступа к Ресурсам
1. **Отправка токена**: При последующих запросах к защищенным ресурсам пользователь должен включать JWT в заголовке `Authorization`.
2. **Фильтрация токена**: `AuthTokenFilter` анализирует каждый запрос, извлекая JWT из заголовка `Authorization`.
3. **Валидация токена**: JWT проверяется на действительность, включая проверку срока действия и подписи.
4. **Извлечение учетных данных**: Если токен действителен, из него извлекается имя пользователя, и снова загружаются полные учетные данные пользователя через `UserDetailsService`.
5. **Установка аутентификации**: В контекст безопасности Spring устанавливается аутентификационный объект, представляющий пользователя.

### Шаг 4: Авторизация
1. **Проверка доступа**: Для каждого запроса проверяется, имеет ли аутентифицированный пользователь права доступа к запрашиваемым ресурсам.
2. **Доступ к ресурсам**: Если пользователь имеет соответствующие права, запрос продолжается и обрабатывается соответствующим контроллером. Если нет — возвращается ошибка доступа.

### Шаг 5: Обработка Исключений
- **Обработка ошибок**: Любые ошибки в процессе, такие как неверный токен или истекший срок его действия, обрабатываются `AuthEntryPointJwt`, который возвращает соответствующие сообщения об ошибке и статусы HTTP.

### Заключение
Этот подход позволяет обеспечить безопасность пользовательских данных и эффективно контролировать доступ к ресурсам приложения. JWT предоставляет механизм, при котором сервер

не должен хранить состояние сессии, что делает такую систему масштабируемой и подходящей для современных веб-приложений.



</details>