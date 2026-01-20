## Aston
RESTful Spring Boot приложение с базовыми CRUD-операциями.

Технологии, фреймворки, паттерны:
 - Docker
 - Maven
 - PostgreSQL
 - Spring Boot
 - MockMvc
 - Kafka
---

### 🚀 Быстрый старт
```shell
git clone https://github.com/gedfalk/aston-project.git
cd aston-project
git checkout feature/spring

# Linux
sudo service docker restart
docker-compose up -d
mvn clean compile
mvn spring-boot:run

# Windows
# запускаем Docker Desktop
docker-compose up -d
mvn clean compile
mvn spring-boot:run
```
---

### 📡 API Endpoints
    GET /api/users - получить всех пользователей
    GET /api/users/{id} - получить пользователя по ID
    POST /api/users - создать нового пользователя
    PUT /api/users/{id} - обновить пользователя
    DELETE /api/users/{id} - удалить пользователя
---

### 🔧 Kafka
Помимо PostgreSQL докер автоматически поднимает образы Kafka и Kafka-ui. 

При создании и удалении пользователя система автоматически отправит события в топик `userEvents` с пометкой `USER_CREATED`/`USER_DELETED`. 

Для мониторинга открыть в браузере `http://localhost:8081` 

---

### 🧪 Тестирование
API-тесты написаны с ипользованием встроенного MockMvc
```shell
mvn clean test
```
---

### 🔧 Docker/Linux
В связи с тем, что Docker на Linux не прокидывает localhost автоматически (в отличие от Windows Docker Desktop) - решить проблему запуска можно раскомментировав следующую строку в конфиге докера:
```yaml
### docker-compose.yml

network_mode: host
```