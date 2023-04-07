# Todolist
[![Java CI](https://github.com/PerpetuumEbner/job4j_todo/actions/workflows/maven.yml/badge.svg)](https://github.com/PerpetuumEbner/job4j_todo/actions/workflows/maven.yml)
## Общее описание:

Веб-сервис для управления задачами.

***

## Реализовано:
* Регистрация/Вход
* Добавление задач с выбором категорий, редактирование, удаление
* Список выполненных задач
* Список новых задач, которые не старше суток

***

## Технологии:
[![java](https://img.shields.io/badge/java-17-red)](https://www.java.com/)
[![maven](https://img.shields.io/badge/apache--maven-3.8.3-blue)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.4-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgresSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.1-green)](https://www.thymeleaf.org/)
[![Hibernate](https://img.shields.io/badge/Hibernate-5.6.9-yellowgreen)](https://hibernate.org/)

***

## Запуск проекта:
* maven install
* java -jar target/job4j_todo-1.0.jar
* Перейти по ссылке http://localhost:8080/items

***

## Структура сайта:

### Главная страница. Список всех задач.

![1](images/1.jpg)
### Проверка доступа. Только авторизованный пользователь может подробно смотреть и добавлять задачи.

![2](images/2.jpg)

### Регистрация. Добавление нового пользователя.

![3](images/3.jpg)

### Страница добавления новой задачи с возможностью выбора нескольких категорий.

![4](images/4.jpg)

### Подробное описания задачи с указанием автора, с возможностью редактирования, удаления и отметить как выполненное.

![5](images/5.jpg)

### Страница редактирования задания.

![6](images/6.jpg)

### Страница добавления и удаления категорий.

![7](images/7.jpg)

### Список выполненных задач.

![8](images/8.jpg)

### Список новых задач, которые не старше суток.

![9](images/8.jpg)