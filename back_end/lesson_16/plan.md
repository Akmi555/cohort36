
<h3 style="text-align: center; padding-bottom: 14px">2024-02-02</h3>

# Lesson plan No. 16 08/26/2024

## What we learned and did in the last lesson:
Finished the Shop application:

- changelog v.0.5.0
- table ...
- primary keys

- Cart, CartDto - classes

- registered relationships using annotations
- Cart - Customer - ...
- Cart - Products - ...

- Customer - CustomerDto - classes
-
- CustomerRepository
- method ...

- cart functionality (see the Project presentation)

- CustomerController
- ...
- CustomerServiceImp
- ...

## Topic of today's lesson:
- Exceptions Handling:
* Spring exception handler
* Exception handling process

- Validation:
- Annotations used for validation
- Validation implementation

## Practice
Fulfilling the plan:
- adding the exception_handling layer
- exceptions
- FirstTestException
- SecondTestException
- ThirdTestException
- CommonAdvice
- Response

- adding validation using Spring annotations
- @NotNull
- @NotBlank
- @Pattern

___

# План занятия №16 26.08.2024

## Что мы узнали и сделали за прошлое занятие:
Доделывали приложение Shop: 
  - changelog v.0.5.0
    - таблица cart-product
    - primary keys cart_id-product_id
    
  - Cart, CartDto - классы, описывают корзину с продуктами

  - прописали с помощью аннотаций связи
    - Cart - Customer - @OneToOne
    - Cart - Products - ManyToMany

- Customer - CustomerDto - классы
  
- CustomerRepository
  - метод FindByName(String name)

- функционал корзины (см. презентацию о Проекте)
  - написали его в классе Cart
  
- CustomerController
  - исправляли тип Customer CustomerDto
- CustomerServiceImp
  - исправляли тип Customer CustomerDto

## Тема сегодняшнего занятия:
- Exceptions Handling:
  * Обработчик исключений Spring
  * Процесс обработки исключений

- Validation:
  - Аннотации, используемые для валидации
  - Внедрение валидации

## Практика
Выполняем план:
- добавляем слой exception_handling
  - exceptions
    - FirstTestException
    - SecondTestException
    - ThirdTestException
  - CommonAdvice
  - Response

  - добавляем валидацию с помощью аннотаций Spring
    - @NotNull
    - @NotBlank
    - @Pattern

