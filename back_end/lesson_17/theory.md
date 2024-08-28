<details>
  <summary style="cursor: pointer;"><b>English</b></summary>

Deploying a Spring Boot application directly from GitHub to Digital Ocean without using Droplets is possible with the DigitalOcean App Platform. This platform allows you to deploy applications directly from your GitHub repository, automatically managing infrastructure, scaling, and monitoring. Here's how you can do it:

### Step 1: Prepare your Spring Boot application
1. **Make sure your application is running locally.** Check that all dependencies are specified correctly in `pom.xml` or `build.gradle`.
2. **Create a `Dockerfile` for your application.**

Example file:
```txt
FROM docker.io/maven:3.9.6-eclipse-temurin-21-alpine AS build
COPY . /home/src WORKDIR /home/src RUN mvn clean package -DskipTests FROM openjdk:21-slim EXPOSE 8080 RUN mkdir /app COPY --from=build /home/src/target/*.jar /app/project.jar ENTRYPOINT ["java", "-jar", "/app/project.jar"] ``` **Add. Place the `Dockerfile` at the root of your project. Make sure your Java version matches the one specified in the file.**

4. **Upload your project to GitHub if it is not there already.**

### Step 2: Create a new application on the DigitalOcean App Platform
1. **Log in to your Digital Ocean account and navigate to the "Apps" section of the dashboard.**
2. **Click "Create App".**
3. **Select the source of your code:**
- Link your GitHub account to Digital Ocean if you have not already done so.
- Select the repository you want to use.
- Select the branch you want to automatically deploy to.

4. **Configure your application:**
- DigitalOcean will automatically detect that your project is based on Spring Boot and suggest deployment options accordingly.
- You can configure environment variables, resources (like adding a database), and other options through the web interface.

5. **Start the deployment process.**
DigitalOcean will build and deploy your application, and then provide a URL where your application will be accessible.

### Step 3: Monitor and manage your application
- **Monitoring:** App Platform provides monitoring tools such as logs, metrics, and alerts.
- **Updates:** You can set up automatic deployments when pushing to a selected branch of your GitHub repository.

This method allows you to quickly and with minimal effort deploy a Spring Boot application, making it ideal for simple applications and projects that require frequent updates without the need for deep server infrastructure setup.

</details>

<hr>

<details style="padding-top: 18px">
  <summary style="cursor: pointer;"><b>На русском</b></summary>

# Lesson 17

Развертывание приложения Spring Boot напрямую из GitHub на Digital Ocean без использования Droplets можно выполнить с помощью платформы DigitalOcean App Platform. Эта платформа позволяет развертывать приложения непосредственно из вашего репозитория на GitHub, автоматически управляя инфраструктурой, масштабированием и мониторингом. Вот как вы можете это сделать:

### Шаг 1: Подготовка вашего приложения Spring Boot
1. **Убедитесь, что ваше приложение работает локально.** Проверьте, что все зависимости указаны корректно в `pom.xml` или `build.gradle`.
2. **Создайте файл `Dockerfile` для вашего приложения.**

   Пример файла:
   ```txt
   FROM docker.io/maven:3.9.6-eclipse-temurin-21-alpine AS build
   COPY . /home/src
   WORKDIR /home/src
   RUN mvn clean package -DskipTests

   FROM openjdk:21-slim
   EXPOSE 8080

   RUN mkdir /app
   COPY --from=build /home/src/target/*.jar /app/project.jar

   ENTRYPOINT ["java", "-jar", "/app/project.jar"]
   ```
3. **Добавьте `Dockerfile` в корень вашего проекта. Убедитесь что ваша версия Java совпадает с указанной в файле.**

4. **Загрузите ваш проект на GitHub, если он еще не там.**

### Шаг 2: Создание нового приложения на DigitalOcean App Platform
1. **Войдите в свой аккаунт на Digital Ocean и перейдите в раздел "Apps" в панели управления.**
2. **Нажмите "Create App".**
3. **Выберите источник вашего кода:**
    - Свяжите ваш аккаунт GitHub с Digital Ocean, если это еще не сделано.
    - Выберите репозиторий, который вы хотите использовать.
    - Выберите ветку, которую вы хотите автоматически развертывать.

4. **Настройте ваше приложение:**
    - DigitalOcean автоматически определит, что ваш проект основан на Spring Boot, и предложит соответствующие параметры развертывания.
    - Вы можете настроить переменные окружения, ресурсы (например, добавить базу данных) и другие параметры через веб-интерфейс.

5. **Запустите процесс развертывания.**
   DigitalOcean проведет сборку и развертывание вашего приложения, после чего выдаст URL, по которому будет доступно ваше приложение.

### Шаг 3: Мониторинг и управление приложением
- **Мониторинг:** App Platform предоставляет инструменты для мониторинга, такие как логи, метрики и алерты.
- **Обновления:** Вы можете настроить автоматические развертывания при пуше в выбранную ветку вашего GitHub репозитория.

Этот метод позволяет быстро и с минимальными усилиями развернуть приложение Spring Boot, делая его идеальным для простых приложений и проектов, требующих частых обновлений без необходимости глубокой настройки серверной инфраструктуры.


</details>