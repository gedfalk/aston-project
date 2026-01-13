package dev.gedfalk.astonproject;

import dev.gedfalk.astonproject.console.ConsoleInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
//        ConsoleInterface console = new ConsoleInterface();
//        console.run();
        SpringApplication.run(Main.class, args);
    }
}
