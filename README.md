## Aston
Консольное Java-приложение, которое реализует базовые CRUD-операции без использования Spring`а. 
Технологии, фреймворки, паттерны: 
 - Docker
 - Maven
 - PostgreSQL
 - Hibernate
 - DAO
 - Junit/Mockito/TestContainers
---

### 🚀 Быстрый старт
```shell
git clone https://github.com/gedfalk/aston-project.git
cd aston-project

# Linux
sudo service docker restart
docker-compose up -d
mvn clean compile
mvn exec:java -Dexec.mainClass="dev.gedfalk.astonproject.Main"

# Windows
# запускаем Docker Desktop
docker-compose up -d
mvn clean compile
mvn exec:java "-Dexec.mainClass=dev.gedfalk.astonproject.Main"
```
---

### 🧪 Тестирование
Юнит-тесты для Service-слоя написаны с использованием Mockito.
Интеграционные тесты для DAO-слоя написаны с использованием Testcontainers.

Позитивные кейсы, негативные кейсы, непараметризированные, параметризированные (через @ValueSource, через @CsvSource).
```shell
git checkout feature/tests

# решение для Linux - при проблемах с ryuk-контейнером
export TESTCONTAINERS_RYUK_DISABLED=true

mvn clean test
```

---

### 🔧 Docker/Linux
В связи с тем, что Docker на Linux не прокидывает localhost автоматически (в отличие от Windows Docker Desktop) - решить проблему запуска можно раскомментировав следующую строку в конфиге докера:
```yaml
### docker-compose.yml

network_mode: host
```