package dev.gedfalk.astonproject.console;

import dev.gedfalk.astonproject.dao.UserDAO;
import dev.gedfalk.astonproject.dao.UserDAOHibernate;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner;
    private final UserDAO userDAO;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAOHibernate();
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
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        while (true) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
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
                    HibernateUtil.disconnect();
                    return;
                default:
                    System.out.println("Некорректный ввод.\nВведите цифру от 1 до 6");
            }
        }
    }

    public void createUser() {
        System.out.println("Введите User в формате 'name;email;age':");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println("___Строка не может быть пустой!___");
            return;
        }

        String[] chunks = input.split(";");
        if (chunks.length != 3) {
            System.out.println("___Неверный формат данных!___");
            return;
        }

        String name = chunks[0].trim();
        String email = chunks[1].trim();
        Integer age;
        try {
            age = Integer.parseInt(chunks[2].trim());
        } catch (NumberFormatException e) {
            System.out.println("___age должно быть числом!___");
            return;
        }

        User user = User.builder()
                .name(name)
                .email(email)
                .age(age)
                .createdAt(LocalDateTime.now())
                .build();

        if (userDAO.existByEmail(email)) {
            System.out.println("___User с таким мылом уже существует___");
            return;
        }

        try {
            userDAO.save(user);
            System.out.println("___User создан успешно___");
        } catch (Exception e) {
            System.out.println("___ERRR___не удалось создать User___");
        }
    }
}
