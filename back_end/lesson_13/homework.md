

<details style="margin-top: 16px">
  <summary style="cursor: pointer; color: blue;"><b>English</b></summary>

1. Reproduce all the code written in class in your project.

2. Debug all the code written in detail, testing the application according to the following plan:

- Get all the products without authorization - it should work.

- Get one product by ID without authorization - it should not work, because the content is protected.

- Get one product using basic authorization and an incorrect password - it should not work for obvious reasons.

- Get one product using basic authorization and the correct password - it should not work, because it is disabled.

- Get access and update tokens for the admin and user, save them somewhere, for example, to a file.

- Get a product with an invalid token, for example, you can delete or add one character - it should not work, because the token should not be validated.

- Get a product with a valid user token - it should work.

Change the token lifetime to 2 minutes.
- Receive a product with a user token when it has expired - should not work.
- Receive a new access token using the refresh token.
- Receive the product again with a fresh token - should work again.
- Save the product in the DB using the user token - should not work, since this action is only allowed to the admin.
- Save the product in the DB using the admin token - should work.

When testing authorization via Postman, use the Authorization tab, in the Auth Type field, select Bearer Token.

</details>

<hr>

<details style="margin-top: 16px">
  <summary style="cursor: pointer; color: blue;"><b>На русском</b></summary>

1. В своём проекте воспроизвести весь код, который писали на занятии.

2. Подробно продебажить весь написанный код, протестировав приложение по следующему плану:

- Получаем все продукты без авторизации - должно работать.

- Получаем один продукт по идентификатору без авторизации - не должно работать, т.к. контент защищён. 

- Получаем один продукт, используя базовую авторизацию и неверный пароль - не должно работать по понятным причинам. 

- Получаем один продукт, используя базовую авторизацию и верный пароль - не должно работать, т.к. она отключена. 

- Получаем токены доступа и обновления для админа и юзера, сохраняем их куда-то, например, в файл. 

- Получаем продукт с невалидным токеном, например, можно удалить или добавить один символ - не должно работать, т.к. не должна проходить валидация токена. 
- Получаем продукт с валидным токеном юзера - должно работать. 

Изменяем время жизни токена на 2 минуты.
- Получаем продукт с токеном юзера, когда он истёк - не должно работать.
- Получаем новый токен доступа по токену обновления.
- Снова получаем продукт со свежим токеном - снова должно работать.
- Сохраняем продукт в БД, используя токен юзера - не должно работать, т.к. это действие разрешено только админу.
- Сохраняем продукт в БД, используя токен админа - должно работать.

При тестировании авторизации через Постман использовать вкладку Authorization, в поле Auth Type выбирать Bearer Token.

</details>


