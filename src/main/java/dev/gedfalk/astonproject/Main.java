package dev.gedfalk.astonproject;

import dev.gedfalk.astonproject.console.ConsoleInterface;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hell");
        ConsoleInterface console = new ConsoleInterface();
        console.run();
    }
}
