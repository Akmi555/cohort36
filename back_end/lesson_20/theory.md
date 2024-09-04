<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

# Lesson 19

### File Uploading in Spring Boot

In Spring Boot, file uploading and downloading is typically done with Spring MVC using controllers.
For these tasks, you can use the built-in support for handling multipart files.

### MultipartResolver Configuration

Spring Boot automatically configures `MultipartResolver` if the corresponding library is present in the classpath. However, you can configure file upload settings in `application.properties` or `application.yml`:

```properties
spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB
```

### Path

`Path` in Java is an interface that is part of the `java.nio.file` package and is used to represent a path to a file or directory on the file system.
It provides a more modern and flexible approach to working with file paths compared to the `File` class from `java.io`.

`Path` allows you to perform many operations, such as retrieving the file name, parent directory,
checking if a file exists, and reading file attributes without opening the file. Using `Path`, you can create new paths,
concatenate them with other paths, remove redundant elements, and convert relative paths to absolute ones.

`Path` works in conjunction with other classes in `java.nio.file`, such as `Files` and `FileSystem`,
which provide additional methods for working with files and directories. `Path` can also be used to watch
for changes in the file system via `WatchService`. Methods like `resolve` and `normalize` provide convenient means
for working with paths in the context of the file system.

### Resource

`Resource` is an interface in the Spring Framework that is intended to abstract away low-level resources. It provides universal access to various data sources, whether it is a file on disk, a resource in a JAR file, or a file at a network address. `Resource` provides methods for obtaining an `InputStream`, which makes it possible to read the contents of a resource regardless of its actual location. It also allows you to check whether a resource exists,
and get information about it, such as a URL or URI. Spring automatically uses the appropriate `Resource` implementation depending on the URI syntax you use. This interface is used in many parts of Spring,
including loading configuration files and template resources. `Resource` makes it easier to work with resources in your applications by
providing a uniform API for working with a variety of data sources.

### UrlResource

`UrlResource` is a concrete implementation of the `Resource` interface in the Spring Framework, which is designed to access resources by URL. This class wraps `java.net.URL`, providing access to resources that can be accessed through standard protocols,
such as HTTP, HTTPS, FTP, and file systems. `UrlResource` is useful in situations where resources need to be loaded from an external
source or when it is necessary to interact with resources that are accessible through a network protocol.
It supports various operations, including checking the existence of a resource, getting an `InputStream` for reading data, and
getting information about a resource such as its `URI` or `URL`. `UrlResource` is often used in web applications to load
content from the internet or access files on the local file system via a URL.

It can also be used to integrate with other web services, providing a flexible and powerful way to work with external resources.

### MultipartFile

`MultipartFile` is an interface in the Spring Framework used to represent an uploaded file in multipart form.
This interface makes it easy to handle uploaded files in web applications by providing methods to get the file name, contents, size, and content type. `MultipartFile` is used in Spring MVC controllers where files are uploaded by the user via web forms.
`getBytes()` can be used to retrieve the file contents as a byte array, and `getInputStream()` can be used to read the file contents
as a data stream. `MultipartFile` also supports methods to save the file directly to the file system using `transferTo()`.
This interface is widely used in applications that require handling files uploaded by users, such as photo or document sharing services. It provides safe and efficient handling of file data, while managing the complexities associated with multipart file uploads.

### RedirectAttributes

`RedirectAttributes` is a specialized data type in Spring MVC that is used to pass attributes in redirected
requests. This interface allows attributes to persist across a single redirect, which is useful when implementing the Post/Redirect/Get pattern,
ensuring that parameters or error messages are not lost between requests. `RedirectAttributes` is typically used to add attributes that
should be available after the client has been redirected to another URL, such as successful file upload or error messages.

Using the `addFlashAttribute` methods, you can add data that will be automatically removed after it is used in the next
request, preventing duplicate information when the page is refreshed. `RedirectAttributes` is integrated into Spring MVC and works
in conjunction with `RedirectView` to make it easier for developers to manage application state between redirects. This mechanism is especially important in web applications where it is necessary to maintain state without using a session or saving state on the client side.

## 01. Uploading files

*
* If you upload all the users' files, the load on the server increases significantly
* Because all requests for receiving files will go through us
* Also, the files will take up a lot of space and you will soon run out of it

* It is advisable to transfer this to third-party solutions, such as - `Dropbox`, `AWS S3`, `Digital Ocean Spaces`

* These are cloud storages with fast content delivery capabilities (unlimited memory and high speed)
* The advantage of a third-party solution is very high content delivery speed without loading your server

## 02. Typical file upload to local storage

```
@PostMapping("/api/files")
public StandardResponseDto upload(@RequestParam("file") MultipartFile file) {
String originalFileName = file.getOriginalFilename(); // get the original file name

String extension;

if (originalFileName != null) {
extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // get the file extension
} else {
throw new IllegalArgumentException("null original file name");
}

String uuid = UUID.randomUUID().toString(); // generate a random string in UUID format
String newFileName = uuid + "." + extension; // create a new file name that consists of a random string and an extension

try (InputStream inputStream = file.getInputStream()) { // open a stream for reading from the file being loaded
Files.copy(inputStream, Path.of("C:\\Users\\marse\\Desktop\\OnlineShop\\static\\" + newFileName)); // transfer data from the stream for reading
// to our disk
} catch (IOException e) {
throw new IllegalStateException(e);
}

return StandardResponseDto.builder()
.message(newFileName)
.build();
}
```
## 03. Connecting S3/DigitalOcean

```xml
<dependency>
<groupId>com.amazonaws</groupId>
<artifactId>aws-java-sdk-s3</artifactId>
<version>1.12.572</version>
</dependency>
```

* `sdk3-s3` - significantly simplifies working with the service

Moving file storage from a local environment to an external cloud storage, such as Digital Ocean Spaces, provides a number of advantages, even if the files are initially uploaded through your server. Here are some of the key benefits:

1. **Scalability**: Cloud storage is easily scalable. You don’t have to worry about having enough disk space on your server, as you can easily increase your cloud storage based on your needs.

2. **Reliability and Availability**: Cloud storage offers high availability and reliability of data. Your files will be hosted in data centers that guarantee their safety even if your physical server fails.

3. **Security**: Cloud service providers usually offer advanced security features, such as encryption of data during transmission and storage, as well as various options for access control and auditing.

4. **Cost savings**: Storing data in the cloud can be more cost-effective compared to managing your own storage infrastructure. You only pay for what you use, eliminating the maintenance and depreciation costs of physical servers.

5. **Management and Maintenance**: Cloud providers take the burden of managing and maintaining storage off your hands, allowing you to focus on growing your application.

6. **Global Access**: Files hosted in the cloud can be easily accessed from anywhere in the world, improving performance for users located far from your main server.

7. **Backup and Restore**: Many cloud services offer built-in backup and restore solutions, making your application more resilient to disasters.


</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

# Lesson 19

### Загрузка файлов в Spring Boot

В Spring Boot загрузка и выгрузка файлов обычно выполняются с помощью Spring MVC, используя контроллеры.
Для этих задач можно использовать встроенную поддержку обработки многокомпонентных (multipart) файлов.

#### Конфигурация MultipartResolver

Spring Boot автоматически настраивает `MultipartResolver`, если в classpath присутствует соответствующая библиотека. Однако, вы можете настроить параметры загрузки файлов в `application.properties` или `application.yml`:

```properties
spring.servlet.multipart.max-file-size=2MB
spring.servlet.multipart.max-request-size=2MB
```


### Path

`Path` в Java представляет собой интерфейс, который является частью пакета `java.nio.file` и используется для представления пути к файлу или директории в файловой системе.
Он предоставляет более современный и гибкий подход к работе с файловыми путями по сравнению с классом `File` из `java.io`.
`Path` позволяет выполнять множество операций, таких как извлечение имени файла, родительской директории,
проверка существования файла и чтение атрибутов файла без открытия файла. Используя `Path`, можно создавать новые пути,
объединять их с другими путями, удалять избыточные элементы и преобразовывать относительные пути в абсолютные.
`Path` работает в тесной связке с другими классами в `java.nio.file`, такими как `Files` и `FileSystem`,
которые предоставляют дополнительные методы для работы с файлами и директориями. С помощью `Path` можно также наблюдать
за изменениями в файловой системе через `WatchService`. Методы вроде `resolve` и `normalize` обеспечивают удобные средства
для работы с путями в контексте файловой системы.

### Resource

`Resource` — это интерфейс в Spring Framework, предназначенный для абстрактной работы с низкоуровневыми ресурсами.
Он позволяет обеспечить универсальный доступ к различным источникам данных, будь то файл на диске, ресурс в JAR-файле,
или файл по сетевому адресу. `Resource` предоставляет методы для получения `InputStream`, что делает возможным чтение
содержимого ресурса независимо от его фактического местоположения. Он также позволяет проверять, существует ли ресурс,
и получать информацию о нём, такую как URL или URI. Spring автоматически использует соответствующую реализацию `Resource`
в зависимости от синтаксиса URI, который вы используете. Этот интерфейс находит применение во многих частях Spring,
включая загрузку конфигурационных файлов и ресурсов шаблонов. `Resource` облегчает работу с ресурсами в приложениях,
предоставляя единый API для работы с разнообразными источниками данных.

### UrlResource

`UrlResource` — это конкретная реализация интерфейса `Resource` в Spring Framework, которая предназначена для доступа к ресурсам по URL.
Этот класс оборачивает `java.net.URL`, предоставляя доступ к ресурсам, которые могут быть доступны через стандартные протоколы,
такие как HTTP, HTTPS, FTP, и файловые системы. `UrlResource` полезен в ситуациях, когда ресурсы должны быть загружены из внешнего
источника или когда необходимо взаимодействовать с ресурсами, которые доступны через сетевой протокол.
Он поддерживает различные операции, включая проверку существования ресурса, получение `InputStream` для чтения данных и
получение информации о ресурсе, такой как его `URI` или `URL`. `UrlResource` часто используется в веб-приложениях для загрузки
контента из интернета или доступа к файлам в локальной файловой системе через URL

. Также он может использоваться для интеграции с другими веб-сервисами, предоставляя гибкий и мощный способ работы с внешними ресурсами.

### MultipartFile

`MultipartFile` — это интерфейс в Spring Framework, используемый для представления загруженного файла в многокомпонентной (multipart) форме.
Этот интерфейс облегчает обработку загруженных файлов в веб-приложениях, предоставляя методы для получения имени файла, его содержимого,
размера и типа контента. `MultipartFile` применяется в контроллерах Spring MVC, где файлы загружаются пользователем через веб-формы.
С помощью `getBytes()` можно извлечь содержимое файла в виде массива байтов, а `getInputStream()` позволяет прочитать содержимое файла
как поток данных. `MultipartFile` также поддерживает методы для сохранения файла напрямую в файловую систему с использованием `transferTo()`.
Этот интерфейс широко используется в приложениях, где требуется обработка файлов, загруженных пользователями, например, в сервисах обмена
фотографиями или документами. Он обеспечивает безопасную и эффективную обработку файловых данных, управляя сложностями, связанными с
многокомпонентной загрузкой файлов.

### RedirectAttributes

`RedirectAttributes` — это специализированный тип данных в Spring MVC, который используется для передачи атрибутов в перенаправленных
запросах. Этот интерфейс позволяет сохранять атрибуты в течение одного редиректа, что полезно при реализации шаблона Post/Redirect/Get,
обеспечивая, чтобы параметры или сообщения об ошибках не терялись между запросами. `RedirectAttributes` обычно используется для добавления атрибутов,
которые должны быть доступны после перенаправления клиента на другой URL, например, сообщения о успешной загрузке файла или ошибке.
С помощью методов `addFlashAttribute` можно добавить данные, которые будут автоматически удалены после того, как они будут использованы в
следующем запросе, что предотвращает дублирование информации при обновлении страницы. `RedirectAttributes` интегрирован в Spring MVC и работает
в сочетании с `RedirectView`, облегчая разработчикам управление состоянием приложения между перенаправлениями.
Этот механизм особенно важен в веб-приложениях, где необходимо поддерживать состояние без использования сессии или сохранения состояния на стороне клиента.

## 01. Загрузка файлов

*
* Если загружать все файлы пользователей, то сильно возрастает нагрузка на сервер
    * Потому что все запросы на получение файлов будут проходить через нас
    * Также, файлы будут занимать очень много места и вам скоро его не хватит

* Целесообразно это переложить на сторонние решения, по типу - `Dropbox`, `AWS S3`, `Digital Ocean Spaces`

* Это облачные хранилища с возможностями быстрой доставки контента (безграничная память и высокая скорость)
* Преимущество стороннего решения в очень высокой скорости доставки контента без нагрузки вашего сервера

## 02. Типовая загрузка файлов в локальное хранилище

```
    @PostMapping("/api/files")
    public StandardResponseDto upload(@RequestParam("file") MultipartFile file) {
        String originalFileName = file.getOriginalFilename(); // получаем имя файла оригинальное

        String extension;

        if (originalFileName != null) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1); // забираем расширение файла
        } else {
            throw new IllegalArgumentException("null original file name");
        }

        String uuid = UUID.randomUUID().toString(); // генерируем случайную строку в формате UUID
        String newFileName = uuid + "." + extension; // создаем новое имя файла, которое состоит из случайной строки и расширения

        try (InputStream inputStream = file.getInputStream()) { // открываем у загружаемого файла поток для чтения
            Files.copy(inputStream, Path.of("C:\\Users\\marse\\Desktop\\OnlineShop\\static\\" + newFileName)); // перекидываем данные из потока для чтения
            // к нам на диск
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return StandardResponseDto.builder()
                .message(newFileName)
                .build();
    }
```
## 03. Подключение S3/DigitalOcean

```xml
<dependency>
  <groupId>com.amazonaws</groupId>
  <artifactId>aws-java-sdk-s3</artifactId>
  <version>1.12.572</version>
</dependency>
```

* `sdk3-s3` - значительно упрощает работу с сервисом


Перенос хранения файлов из локальной среды на внешнее облачное хранилище, такое как Digital Ocean Spaces, предоставляет ряд преимуществ, даже если файлы изначально загружаются через ваш сервер. Вот некоторые из ключевых преимуществ:

1. **Масштабируемость**: Облачные хранилища легко масштабируются. Вам не придется беспокоиться о достаточности дискового пространства на вашем сервере, так как можно легко увеличить объем хранения в облаке в зависимости от нужд.

2. **Надежность и доступность**: Облачные хранилища обеспечивают высокую степень доступности и надежности данных. Ваши файлы будут размещены в дата-центрах, которые гарантируют их сохранность даже в случае сбоя вашего физического сервера.

3. **Безопасность**: Провайдеры облачных услуг обычно предлагают расширенные возможности безопасности, такие как шифрование данных во время передачи и хранения, а также различные опции для управления доступом и аудита.

4. **Снижение затрат**: Хранение данных в облаке может быть более экономичным по сравнению с управлением собственной инфраструктурой хранения. Вы платите только за то, что используете, и избавляетесь от затрат на обслуживание и амортизацию физических серверов.

5. **Управление и обслуживание**: Облачные провайдеры берут на себя всю тяжесть управления и поддержки хранилища, что позволяет вам сконцентрироваться на развитии вашего приложения.

6. **Глобальный доступ**: Файлы, размещенные в облаке, могут быть легко доступны из любой точки мира, что улучшает производительность для пользователей, расположенных далеко от вашего основного сервера.

7. **Бэкап и восстановление**: Многие облачные сервисы предлагают встроенные решения для резервного копирования и восстановления, что повышает устойчивость вашего приложения к потере данных.

Чтобы интегрировать хранение в облаке Digital Ocean с вашим Spring Boot приложением, вы можете использовать библиотеки и SDK, предоставляемые Digital Ocean, или сторонние библиотеки, поддерживающие S3-совместимые API (поскольку Digital Ocean Spaces совместим с API S3).
Это позволит вашему приложению работать с облачным хранилищем так же, как с любым другим S3-совместимым хранилищем.


## Переменные окружения в Spring Boot

Это параметры конфигурации, которые управляют поведением приложения в различных средах (разработка, тестирование, продакшн).
Они представляют собой внешний способ передачи конфигурационных данных в приложение, что позволяет избежать хардкода значений непосредственно в коде.

### Как использовать переменные окружения в Spring Boot

1. **Определение переменных окружения:**
   Переменные окружения могут быть определены в операционной системе или в конфигурационных файлах CI/CD. Например, в Unix-подобных системах вы можете задать переменную окружения в терминале:
   ```bash
   export DATABASE_URL="jdbc:mysql://localhost:3306/mydb"
   ```

2. **Доступ к переменным окружения в приложении:**
   В Spring Boot для доступа к переменным окружения можно использовать аннотацию `@Value`. Например:
   ```java
   @Component
   public class MyComponent {
       @Value("${DATABASE_URL}")
       private String databaseUrl;

       // использование databaseUrl в коде
   }
   ```

3. **Использование `application.properties` или `application.yml`:**
   Вы можете задать значения по умолчанию в этих файлах, которые будут перезаписаны значениями из переменных окружения при необходимости:
   ```properties
   # application.properties
   DATABASE_URL=jdbc:h2:mem:testdb
   ```

   ```yaml
   # application.yml
   DATABASE_URL: jdbc:h2:mem:testdb
   ```

### Нюансы использования переменных окружения в Spring Boot

1. **Приоритеты конфигурации:**
   Spring Boot использует строгую иерархию для определения приоритета конфигурационных данных. Переменные окружения имеют один из самых высоких приоритетов, что позволяет им перезаписывать значения, заданные в `application.properties` или `application.yml`.

2. **Безопасность:**
   Старайтесь не хранить чувствительные данные, такие как пароли или секреты API, прямо в переменных окружения. Используйте для этого защищенные хранилища, например, Spring Cloud Config или HashiCorp Vault.

3. **Профили Spring:**
   Вы можете определить различные профили для разных сред, используя `spring.profiles.active` и определяя переменные окружения для каждой среды соответственно. Это позволяет гибко управлять конфигурацией для различных условий развертывания.

4. **Передача сложных структур данных:**
   Если вам нужно передать сложные структуры данных через переменные окружения, подумайте о форматировании их как строк, возможно, в JSON-формате, который затем можно разобрать в приложении.

Использование переменных окружения улучшает безопасность и упрощает управление конфигурациями приложения, делая его более гибким и адаптируемым к изменениям среды.

При запуске приложения на Spring Boot переменные окружения можно задать несколькими способами в зависимости от среды и способа запуска. Вот несколько основных методов:

### 1. В командной строке
Вы можете установить переменные окружения прямо перед запуском приложения в командной строке. Это работает на любой операционной системе.

**На UNIX-подобных системах (Linux, macOS):**
```bash
export DATABASE_URL="jdbc:mysql://localhost:3306/mydb"
java -jar myapp.jar
```

**На Windows:**
```cmd
set DATABASE_URL=jdbc:mysql://localhost:3306/mydb
java -jar myapp.jar
```

### 2. При запуске через Maven или Gradle
Если вы используете Maven или Gradle для запуска вашего приложения во время разработки, вы можете указать переменные окружения в конфигурации плагина.

**Для Maven:**
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <environmentVariables>
            <DATABASE_URL>jdbc:mysql://localhost:3306/mydb</DATABASE_URL>
        </environmentVariables>
    </configuration>
</plugin>
```
Затем запускаете:
```bash
mvn spring-boot:run
```

**Для Gradle:**
```groovy
bootRun {
    environment "DATABASE_URL", "jdbc:mysql://localhost:3306/mydb"
}
```
Затем запускаете:
```bash
./gradlew bootRun
```

### 3. В файле конфигурации IDE
Если вы используете IDE, такие как IntelliJ IDEA или Eclipse, вы можете установить переменные окружения в настройках конфигурации запуска вашего приложения.

**В IntelliJ IDEA:**
- Откройте конфигурацию запуска (Run/Debug Configurations).
- Найдите раздел "Environment variables" и установите нужные переменные.

**В Eclipse:**
- Откройте конфигурацию запуска (Run Configurations).
- Во вкладке "Environment" добавьте необходимые переменные окружения.

### 4. В Docker или Kubernetes
При запуске приложений в контейнерах (например, с использованием Docker или Kubernetes) вы также можете задать переменные окружения через конфигурацию контейнера.

**Docker:**
```dockerfile
FROM openjdk:11
COPY target/myapp.jar myapp.jar
ENV DATABASE_URL jdbc:mysql://localhost:3306/mydb
ENTRYPOINT ["java", "-jar", "myapp.jar"]
```

**Kubernetes:**
```yaml
apiVersion: v1
kind: Pod
metadata:
  name: myapp
spec:
  containers:
  - name: myapp
    image: myapp:latest
    env:
    - name: DATABASE_URL
      value: "jdbc:mysql://localhost:3306/mydb"
```

Эти методы позволяют гибко управлять конфигурацией приложения, адаптируя её под разные среды и условия запуска.


</details>