## Aston
Консольное Java-приложение, которое реализует базовые CRUD- операции без использования Spring`а. 
Технологии, фреймворки, паттерны: 
 - Docker
 - Maven
 - PostgreSQL
 - Hibernate
 - DAO
---

### 🚀 Быстрый старт
```shell
git clone https://github.com/gedfalk/aston-project.git
cd aston-project
git checkout feature/hibernate

docker-compose up -d

mvn clean compile
mvn exec:java -Dexec.mainClass="dev.gedfalk.astonproject.Main"
```
---

### 🔧 Linux
При отсутствии установленного Docker Desktop и проблеме, когда приложение на хосте (localhost) не может подключиться к localhost:5432 внутри контейнера - можно раскомментировать `network_mode: host`