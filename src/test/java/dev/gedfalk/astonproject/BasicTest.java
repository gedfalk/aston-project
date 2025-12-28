package dev.gedfalk.astonproject;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class BasicTest {
    @Test
    @DisplayName("Простейший тест. Проверка JUnit")
    void simpleTest() {
        log.info("Тест-тест");
        assertEquals(true, true, "2 + 2 = 5");
    }
}
