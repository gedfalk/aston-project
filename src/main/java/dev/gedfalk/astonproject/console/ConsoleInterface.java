package dev.gedfalk.astonproject.console;

import dev.gedfalk.astonproject.dao.UserDAO;
import dev.gedfalk.astonproject.dao.UserDAOHibernate;
import dev.gedfalk.astonproject.entity.User;
import dev.gedfalk.astonproject.service.UserService;
import dev.gedfalk.astonproject.utils.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
public class ConsoleInterface {
    private final Scanner scanner;
    private final UserDAO userDAO;
    private final UserService userService;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
        this.userDAO = new UserDAOHibernate();
        this.userService = new UserService(userDAO);
    }

    // Объект для валидации-хранения input-строки типа 'name;email;age'
    public record UserData(String name, String email, Integer age) {}

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
                    findById();
                    break;
                case "3":
                    updateUser();
                    break;
                case "4":
                    deleteById();
                    break;
                case "5":
                    listAll();
                    break;
                case "6":
                    log.info("Завершение программы...");
                    HibernateUtil.disconnect();
                    return;
                default:
                    log.warn("Некорректный ввод.\nВведите цифру от 1 до 6");
            }
        }
    }

    private Optional<UserData> validatedUserInput(String input) {
        if (input.isEmpty()) {
            log.warn("___Строка не может быть пустой!___");
            return Optional.empty();
        }

        String[] chunks = input.split(";");
        if (chunks.length != 3) {
            log.warn("___Неверный формат данных!___");
            return Optional.empty();
        }

        String name = chunks[0].trim();
        String email = chunks[1].trim();
        Integer age;
        try {
            age = Integer.parseInt(chunks[2].trim());
        } catch (NumberFormatException e) {
            log.warn("___age должно быть числом!___");
            return Optional.empty();
        }

        return Optional.of(new UserData(name, email, age));
    }

    public void createUser() {
        log.info("Введите User в формате 'name;email;age':");

        String input = scanner.nextLine().trim();
        Optional<UserData> userOpt = validatedUserInput(input);

        if (userOpt.isPresent()) {
            UserData userData = userOpt.get();
            String name = userData.name();
            String email = userData.email();
            Integer age = userData.age();

            try {
                User user = userService.createUser(name, email, age);
                log.info("___User создан успешно___");
            } catch (Exception e) {
                log.warn("___Не удалось создать User___\n{}", e.getMessage());
            }

        } else {
            return;
        }
    }

    // TODO: ?? заменить userDAO на service-слой ??
    public void findById() {
        Optional<Integer> idOpt = chooseId();

        if (idOpt.isPresent()) {
            Integer id = idOpt.get();
            Optional<User> userOpt = userDAO.findById(id);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                log.info("\nНайден пользователь:");
                log.info("{}", user);
            } else {
                log.warn("___Пользователь не найден___");
            }
        }
    }

    // TODO: ?? заменить userDAO на service-слой ??
    public void deleteById() {
        Optional<Integer> idOpt = chooseId();

        if (idOpt.isPresent()) {
            Integer id = idOpt.get();
            Optional<User> userOpt = userDAO.findById(id);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                userDAO.delete(id);
                log.info("\nПользователь с данным id удалён из базы");
            } else {
                log.info("___Пользователь не найден___");
            }
        }
    }

    public void updateUser() {
        Optional<Integer> idOpt = chooseId();

        if (idOpt.isPresent()) {
            Integer id = idOpt.get();
            User oldUser = userService.findById(id);

            log.info("Введите Нового User в формате 'name;email;age':");
            String input = scanner.nextLine().trim();
            Optional<UserData> modifiedUserOpt = validatedUserInput(input);

            if (modifiedUserOpt.isPresent()) {
                UserData userData = modifiedUserOpt.get();
                String name = userData.name();
                String email = userData.email();
                Integer age = userData.age();

                try {
                    userService.updateUser(id, name, email, age);
                    log.info("___User успешно обновлён___");
                } catch (Exception e) {
                    log.error("___не удалось обновить User___", e);
                }
            } else {
                return;
            }
        }
    }

    private Optional<Integer> chooseId() {
        log.info("\nВведите Id:");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            log.info("___Нужно было ввести число!___");
            return Optional.empty();
        }

        Integer id;
        try {
            id = Integer.parseInt(input);
            return Optional.of(id);
        } catch (NumberFormatException e) {
            log.error("___id должно быть числом!___", e);
            return Optional.empty();
        }
    }

    // TODO: ?? заменить userDAO на service-слой ??
    public void listAll() {
        log.info("\n___База данных___");
        try {
            List<User> users = userDAO.findAll();

            if (users.isEmpty()) {
                log.info("___(таблица пуста)___");
                return;
            }

            for (User user : users) {
                log.info("{}", user);
            }

        } catch (Exception e) {
            log.error("___Ошибка чтения___", e);
        }
    }
}
