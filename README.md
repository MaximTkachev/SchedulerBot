# SchedulerBot
Scheduler bot - это Telegram бот для студентов Высшаей IT-школы ТГУ, который, используя публичное API TSU InTime, предоставляет доступ к расписанию занятий через Telegram. 
# Инструкция по запуску
1. Создайте своего бота, используя [официальный Telegram бот](https://t.me/BotFather)
2. Вставьте данные бота в [файл с переменными окружения](variables.env)
3. Соберите проект
```shell
mvn clean install
```
4. Запустите проект
```shell
docker-compose --env-file ./variables.env up
```

# Использованные технологии
* Java 11
* Spring boot 2.7
* Postgres
* Hibernate ORM
* Redis