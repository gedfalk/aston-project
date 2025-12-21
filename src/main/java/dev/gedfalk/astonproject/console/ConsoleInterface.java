package dev.gedfalk.astonproject.console;

import dev.gedfalk.astonproject.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void printMenu() {
        System.out.println();
        System.out.println("Выберите из нижеперечисленного (цифрой от 1 до 6):");
        System.out.println("1. create");
        System.out.println("2. read");
        System.out.println("3. update");
        System.out.println("4. delete");
        System.out.println("5. Вывести весь список");
        System.out.println("6. Выход");
    }

    public void run() {
        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // TODO: временная заглушка. Удалить!!!
                    createUser();
                    break;
                case "2":
                    // TODO: readUser
                    break;
                case "3":
                    // TODO: updateUser
                    break;
                case "4":
                    // TODO: deleteUser
                    break;
                case "5":
                    // TODO: вывести всех
                    break;
                case "6":
                    System.out.println("Завершение программы...");
                    return;
                default:
                    System.out.println("Некорректный ввод.\nВведите цифру от 1 до 6");
            }
        }
    }

    // TODO: временная заглушка. Удалить!!!
    public void createUser() {
        User user = User.builder()
                .name("Eugene")
                .email("bla-bla@bla.com")
                .age(30)
                .createdAt(LocalDateTime.now())
                .build();

        try {
            SessionFactory sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            System.out.println("noooooooooo");
        }


    }
}
