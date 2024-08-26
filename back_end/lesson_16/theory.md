<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

# Lesson 16

### 1. Handling Spring Boot REST API Exceptions

In practice, there is often a need for centralized exception handling within a controller or even the entire application.
Initially, before Spring 3.2, the main methods for handling exceptions in an application were HandlerExceptionResolver and the @ExceptionHandler annotation.
Starting with version 3.2, the @ControllerAdvice annotation appeared, which eliminated the limitations of previous solutions. And in Spring 5, a new ResponseStatusException class was added, which is very convenient for handling basic errors for the REST API.

**Handling exceptions at the controller level - @ExceptionHandler**

Using the @ExceptionHandler annotation, you can handle exceptions at the level of an individual controller. To do this, simply declare a method that will contain all the logic for handling the required exception, and annotate it.

The main disadvantage of @ExceptionHandler is that it is defined for each controller separately, and not globally for the entire application. This limitation can be circumvented if @ExceptionHandler is defined in the base class from which all controllers in the application will inherit, but this approach is not always possible, especially if we have an old application with a lot of legacy.

**HandlerExceptionResolver**
HandlerExceptionResolver is a common interface for exception handlers in Spring. All exceptions thrown in the application will be handled by one of the HandlerExceptionResolver subclasses. You can either create your own implementation of this interface or use existing implementations that Spring provides us out of the box.

The main disadvantage is that only the status code is returned, and in practice for a REST API, one code is often not enough. It is advisable to also return to the client the response body with a description of what happened. This problem can be solved using ModelAndView, but it is not necessary, since there is a better way.

**ResponseStatusExceptionResolver**

ResponseStatusExceptionResolver — allows you to customize the response code for any exception using the @ResponseStatus annotation.

One of the disadvantages of this approach is that, as in the previous case, there is no response body. But if you only want to return a status code, @ResponseStatus is quite handy.

**Exception Handling with @ControllerAdvice**

Since Spring 3.2, you can handle exceptions globally and centrally using classes annotated with @ControllerAdvice.

Any class annotated with @ControllerAdvice is a global exception handler that is highly configurable.

### Data Validation

Validating data when writing to the database is an important step to ensure that the data is correct and complies with certain rules or constraints.
Spring provides powerful tools for validating data at the application level before it is saved to the database.
Here are the main aspects of data validation and the annotations that are often used for this purpose in Spring Data:

### 1. JSR 303/JSR 380 (Bean Validation)

These are specifications for data validation that Spring supports through integration with Hibernate Validator or other Bean Validation implementations. These annotations can be applied directly to entity fields or DTOs (Data Transfer Objects).

#### The main validation annotations are:

- `@NotNull`: The field must not be `null`.
- `@NotEmpty`: The field must not be empty. Applies to collections, strings, and arrays.
- `@NotBlank`: The field must contain at least one non-whitespace character. Most often used for strings.
- `@Size(min = x, max = y)`: Constrains the size or length of an element (such as a string or collection).
- `@Min(value = x)`: Value must be at least `x`.
- `@Max(value = x)`: Value must be at least `x`.
- `@Email`: Field must contain a valid email address.
- `@Positive`: Value must be positive.
- `@Negative`: Value must be negative.

### 2. Validation at the Spring MVC Level

If you are using Spring MVC, you can enable validation for controller method parameters by using the `@Valid` or `@Validated` annotation before the object that needs to be validated.
This will ensure that the object is checked against the constraints specified by the Bean Validation annotations before executing the business logic.

### 3. Handling Validation Errors

After applying validation annotations, it is also important to be able to handle validation errors gracefully.
In Spring MVC, for example, you can use the `BindingResult` class to handle validation errors, which can then be passed to the view to inform the user.

### 4. Custom Validation

You can create your own validation annotations and associated validators for more specific or complex validation rules that are not covered by the standard set of annotations.

Using these validation mechanisms, you can significantly improve the quality of the data in your application by preventing incorrect or unwanted data from being stored.

</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

# Lesson 16

## Коды, которые возвращает сервер на http-запросы:
HTTP-статусные коды — это стандартизированные коды, которые серверы используют для информирования клиентов о результате их запроса. Вот основные категории HTTP-статусных кодов и примеры кодов из каждой категории:

**1xx: Информационные**
100 Continue: клиент может продолжать отправку запроса.
101 Switching Protocols: сервер переключает протоколы согласно запросу клиента.

**2xx: Успешные**
**_200 OK: успешный запрос._**
201 Created: запрос успешно обработан и в результате был создан новый ресурс.
204 No Content: запрос успешен, но клиенту не нужно покидать текущую страницу.

**3xx: Перенаправления**
301 Moved Permanently: запрошенный ресурс окончательно перемещен в новое местоположение.
302 Found: запрошенный ресурс временно перемещен на другой URI.
304 Not Modified: ресурс не был изменен с момента последнего кеширования.

**4xx: Ошибки клиента**
**_400 Bad Request:_** сервер не понимает запрос из-за неверного синтаксиса.
**_401 Unauthorized:_** для доступа к ресурсу необходима аутентификация.
**_403 Forbidden:_** сервер понял запрос, но отказывается его выполнять.
**_404 Not Found:_** запрошенный ресурс не найден.
429 Too Many Requests: клиент отправил слишком много запросов за короткое время.

**5xx: Ошибки сервера**
**_500 Internal Server Error:_** сервер столкнулся с непредвиденной ситуацией, которая не позволила ему выполнить запрос.
**_501 Not Implemented:_** сервер не поддерживает функционал, необходимый для выполнения запроса.
**_503 Service Unavailable_**: сервер временно недоступен, обычно из-за перегрузки или технического обслуживания.
504 Gateway Timeout: сервер в роли шлюза не получил вовремя ответа.

## Проверяемые и НЕпроверяемые исключения
Проверяемые ошибки (exception)- ~~кем~~ или чем проверяются?

- **проверяемые** -> это те exceptions, которые проверяются компиллятором Java, нам просто не дадут запустить приложение, их лучше использовать при написании собственных приложений 

- **НЕпроверяемые** - это те, которые не проверяются копмпилятором, их лучше использовать, когда пишем приложение с использованием сторонних библиотек или фреймворков

### 1. Обработка исключений Spring Boot REST API

Часто на практике возникает необходимость централизованной обработки исключений в рамках контроллера или даже всего приложения.
Изначально до Spring 3.2 основными способами обработки исключений в приложении были HandlerExceptionResolver и аннотация @ExceptionHandler.
Начиная с версии 3.2 появилась аннотация @ControllerAdvice, в которой устранены ограничения из предыдущих решений. 
А в Spring 5 добавился новый класс ResponseStatusException, который очень удобен для обработки базовых ошибок для REST API.

**Обработка исключений на уровне контроллера — @ExceptionHandler**

С помощью аннотации @ExceptionHandler можно обрабатывать исключения на уровне отдельного контроллера. Для этого достаточно объявить метод, в котором будет содержаться вся логика обработки нужного исключения, и проаннотировать его.

Основной недостаток @ExceptionHandler в том что он определяется для каждого контроллера отдельно, а не глобально для всего приложения. Это ограничение можно обойти если @ExceptionHandler определен в базовом классе, от которого будут наследоваться все контроллеры в приложении, но такой подход не всегда возможен, особенно если перед нами старое приложение с большим количеством легаси.

**HandlerExceptionResolver**
HandlerExceptionResolver является общим интерфейсом для обработчиков исключений в Spring. Все исключений выброшенные в приложении будут обработаны одним из подклассов HandlerExceptionResolver. Можно сделать как свою собственную реализацию данного интерфейса, так и использовать существующие реализации, которые предоставляет нам Spring из коробки.

Основной недостаток заключается в том что возвращается только код статуса, а на практике для REST API одного кода часто не достаточно. 
Желательно вернуть клиенту **_еще и тело ответа с описанием того что произошло_**. Эту проблему можно решить с помощью ModelAndView, но не нужно, так как есть способ лучше.


**ResponseStatusExceptionResolver**

ResponseStatusExceptionResolver — позволяет настроить код ответа для любого исключения с помощью аннотации @ResponseStatus.
Из недостатков такого подхода — как и в предыдущем случае отсутствует тело ответа. Но если нужно вернуть только код статуса, то @ResponseStatus довольно удобная штука.


**Обработка исключений с помощью @ControllerAdvice**

Начиная со Spring 3.2 можно глобально и централизованно обрабатывать исключения с помощью классов с аннотацией @ControllerAdvice.
Любой класс с аннотацией @ControllerAdvice является глобальным обработчиком исключений, который очень гибко настраивается.

### Валидация данных

Валидация данных при записи в базу данных — это важный шаг для обеспечения того, чтобы данные были корректными и соответствовали определённым правилам или ограничениям.
Spring предоставляет мощные инструменты для валидации данных на уровне приложения, прежде чем они будут сохранены в базу данных.
Вот основные аспекты валидации данных и аннотации, которые часто используются в Spring Data для этой цели:

### 1. JSR 303/JSR 380 (Bean Validation)

Это спецификации для валидации данных, которые Spring поддерживает через интеграцию с Hibernate Validator или другими реализациями Bean Validation.
Эти аннотации можно применять непосредственно к полям сущностей или DTO (Data Transfer Objects).

#### Основные аннотации валидации:

- `@NotNull`: Поле не должно быть `null`.
- `@NotEmpty`: Поле не должно быть пустым. Применимо к коллекциям, строкам и массивам.
- `@NotBlank`: Поле должно содержать хотя бы один непробельный символ. Чаще всего используется для строк.
- `@Size(min = x, max = y)`: Ограничивает размер или длину элемента (например, строки или коллекции).
- `@Min(value = x)`: Значение должно быть не меньше `x`.
- `@Max(value = x)`: Значение должно быть не больше `x`.
- `@Email`: Поле должно содержать корректный email-адрес.
- `@Positive`: Значение должно быть положительным.
- `@Negative`: Значение должно быть отрицательным.

### 2. Валидация на уровне Spring MVC

Если вы используете Spring MVC, вы можете включить валидацию для параметров методов контроллера, используя аннотацию `@Valid` или `@Validated` перед объектом, который нужно валидировать.
Это приведёт к тому, что перед выполнением бизнес-логики объект будет проверен на соответствие ограничениям, указанным с помощью аннотаций Bean Validation.

### 3. Обработка ошибок валидации

После применения аннотаций валидации важно также уметь корректно обрабатывать ошибки валидации.
В Spring MVC, например, можно использовать класс `BindingResult` для обработки ошибок валидации, которые могут быть затем переданы во view для информирования пользователя.

### 4. Кастомная валидация

Вы можете создать свои собственные аннотации валидации и связанные с ними валидаторы для более специфичных или сложных валидационных правил, которые не покрываются стандартным набором аннотаций.

Используя эти механизмы валидации, вы можете значительно улучшить качество данных в вашем приложении, предотвращая сохранение некорректной или нежелательной информации в базу данных.


</details>