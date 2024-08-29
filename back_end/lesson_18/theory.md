<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

# Lesson 17

### 1. Objects and Dependencies
- **JavaMailSender**: This is a Spring Framework interface that abstracts the functionality of sending emails. This abstraction allows the developer to use different mail protocol implementations without changing the core code.

### 2. Sending Emails
- **SimpleMailMessage**: A class used to create simple email messages. Allows you to specify the sender, recipient, subject, and message body.
- **Sending Message**: Using the `JavaMailSender` object to send a prepared email message.

## Freemarker
This is a Java templating engine that helps developers generate HTML pages, configuration files, and other text formats using templates. This tool is widely used to create web pages in Java applications. Here is a detailed description of how to use Freemarker to create pages in a Java application:

### Freemarker Basics

Freemarker does not process HTML by itself; it creates HTML files based on templates that you define. Freemarker templates are written in its own declarative language and contain static text (which will be part of the final document) and Freemarker directives that control the creation of dynamic content.

### Freemarker Components

1. **Templates**: Freemarker templates (usually with the `.ftl` extension) are text files containing fixed text and special constructs for dynamic data insertion.

2. **Data Model**: The data to be inserted into a template, usually represented as a Map or other Java data structure, which is passed to the template engine during processing.

3. **Configuration**: The Configuration object in Freemarker controls all aspects of the template engine's behavior, including formatting specifications, localization, and other settings.

### Freemarker Example

#### 1. Setup

Before using Freemarker in your project, add the dependency to your `pom.xml` or `build.gradle` file:

```xml
<!-- For Maven -->
<dependency>
<groupId>org.freemarker</groupId>
<artifactId>freemarker</artifactId>
<version>2.3.31</version> <!-- Check for latest version -->
</dependency>
```

```groovy
// For Gradle
implementation 'org.freemarker:freemarker:2.3.31' // Check for latest version
```

#### 2. Create Configuration

```java
import freemarker.template.Configuration;
import freemarker.template.Version;

Configuration cfg = new Configuration(new Version("2.3.31"));
cfg.setDirectoryForTemplateLoading(new File("/path/to/templates"));
cfg.setDefaultEncoding("UTF-8");
cfg.setLocale(Locale.US);
cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
``` #### 3. Preparing the data model ```java Map<String, Object> root = new HashMap<>();
root.put("user", "John Doe");
List<String> messages = Arrays.asList("Your application is up and running.", "Enjoy Freemarker!");
root.put("messages", messages);
```

#### 4. Processing the template

```java
/* Load the template */
Template temp = cfg.getTemplate("test.ftl");

/* Connect the data model to the template and output the results */
try (Writer out = new OutputStreamWriter(System.out)) {
temp.process(root, out);
}
```

#### 5. test.ftl template

```plaintext
<html>
<head>
<title>Welcome</title>
</head>
<body>
<h1>Welcome ${user}!</h1>
<p>Messages:</p>
<ul>
<#list messages as message>
<li>${message}</li>
<#list>
</ul>
</body>
</html>
```

This example demonstrates the basic process of creating an HTML page using dynamic data.
Freemarker is powerful and flexible, supporting complex data manipulation and text formatting.

</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

# Lesson 17

### 1. Объекты и зависимости
- **JavaMailSender**: Это интерфейс Spring Framework, который абстрагирует функционал отправки электронной почты. За счет этой абстракции разработчик может использовать различные реализации почтовых протоколов без изменения основного кода.

### 2. Отправка электронных сообщений
- **SimpleMailMessage**: Класс, используемый для создания простых электронных сообщений. Позволяет указать отправителя, получателя, тему и текст сообщения.
- **Отправка сообщения**: Использование объекта `JavaMailSender` для отправки подготовленного сообщения электронной почты.


## Freemarker
Это шаблонизатор на Java, который помогает разработчикам генерировать HTML-страницы, конфигурационные файлы и другие текстовые форматы, используя шаблоны. Этот инструмент широко применяется для создания веб-страниц в Java-приложениях. Вот подробное описание работы с Freemarker для создания страниц в Java-приложении:

### Основы Freemarker

Freemarker не обрабатывает HTML сам по себе; он создаёт HTML-файлы на основе шаблонов, которые вы определяете. Шаблоны Freemarker написаны в собственном декларативном языке и содержат статический текст (который будет частью конечного документа) и директивы Freemarker, которые управляют созданием динамического контента.

### Компоненты Freemarker

1. **Шаблоны**: Шаблоны Freemarker (обычно с расширением `.ftl`) — это текстовые файлы, содержащие фиксированный текст и специальные конструкции для динамической вставки данных.

2. **Модель данных**: Данные, которые должны быть вставлены в шаблон, обычно представлены в виде карты (Map) или другой структуры данных в Java, которая передаётся в шаблонизатор при обработке.

3. **Конфигурация**: Объект Configuration в Freemarker управляет всеми аспектами поведения шаблонизатора, включая спецификации форматирования, локализацию и другие настройки.

### Пример работы с Freemarker

#### 1. Настройка

Перед использованием Freemarker в вашем проекте добавьте зависимость в ваш `pom.xml` или `build.gradle` файл:

```xml
<!-- Для Maven -->
<dependency>
    <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
    <version>2.3.31</version> <!-- Проверьте последнюю версию -->
</dependency>
```

```groovy
// Для Gradle
implementation 'org.freemarker:freemarker:2.3.31' // Проверьте последнюю версию
```

#### 2. Создание конфигурации

```java
import freemarker.template.Configuration;
import freemarker.template.Version;

Configuration cfg = new Configuration(new Version("2.3.31"));
cfg.setDirectoryForTemplateLoading(new File("/path/to/templates"));
cfg.setDefaultEncoding("UTF-8");
cfg.setLocale(Locale.US);
cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
```

#### 3. Подготовка модели данных

```java
Map<String, Object> root = new HashMap<>();
root.put("user", "John Doe");
List<String> messages = Arrays.asList("Your application is up and running.", "Enjoy Freemarker!");
root.put("messages", messages);
```

#### 4. Обработка шаблона

```java
/* Загрузите шаблон */
Template temp = cfg.getTemplate("test.ftl");

/* Соедините модель данных с шаблоном и выводите результаты */
try (Writer out = new OutputStreamWriter(System.out)) {
    temp.process(root, out);
}
```

#### 5. Шаблон test.ftl

```plaintext
<html>
<head>
  <title>Welcome</title>
</head>
<body>
  <h1>Welcome ${user}!</h1>
  <p>Messages:</p>
  <ul>
  <#list messages as message>
    <li>${message}</li>
  <#list>
  </ul>
</body>
</html>
```

Этот пример демонстрирует базовый процесс создания HTML-страницы с использованием динамических данных.
Freemarker мощный и гибкий, поддерживающий сложные операции с данными и форматированием текста.

</details>