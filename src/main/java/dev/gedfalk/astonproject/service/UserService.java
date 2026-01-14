package dev.gedfalk.astonproject.service;

import dev.gedfalk.astonproject.entity.User;

import java.time.LocalDateTime;

public class UserService {
//    private final UserDAO userDao;
//
//    public UserService(UserDAO userDao) {
//        this.userDao = userDao;
//    }
//
//    private void validateName(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            throw new IllegalArgumentException("Имя не может быть пустым");
//        }
//        String verifiedName = name.trim();
//        if (verifiedName.length() < 2) {
//            throw new IllegalArgumentException("Имя должно быть больше 2 символов");
//        }
//        if (verifiedName.length() > 30) {
//            throw new IllegalArgumentException("Слииишком длинное имя");
//        }
//    }
//
//    private void validateEmail(String email) {
//        if (email == null || email.trim().isEmpty()) {
//            throw new IllegalArgumentException("Мыло обязательно");
//        }
//        String verifiedEmail = email.trim();
//        if (!verifiedEmail.contains("@") || !verifiedEmail.contains(".")) {
//            throw new IllegalArgumentException("Некорректный формат мыла");
//        }
//        if (verifiedEmail.length() > 50) {
//            throw new IllegalArgumentException("Cлиииишком длинное мыло");
//        }
//    }
//
//    private void validateAge(Integer age) {
//        if (age == null) {
//            throw new IllegalArgumentException("Возраст нужно указать обязательно");
//        }
//        if (age < 0) {
//            throw new IllegalArgumentException("Возраст должен быть больше 0");
//        }
//        if (age > 120) {
//            throw new IllegalArgumentException("Возраст должен быть меньше 120");
//        }
//    }
//
//    public User createUser(String name, String email, Integer age) {
//        validateName(name);
//        validateEmail(email);
//        validateAge(age);
//
//        if (userDao.existByEmail(email.trim().toLowerCase())) {
//            throw new IllegalArgumentException("Пользователь с мылом " + email.trim().toLowerCase() + " уже есть");
//        }
//
//        User user = User.builder()
//                .name(name.trim())
//                .email(email.trim().toLowerCase())
//                .age(age)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return userDao.save(user);
//    }
//
//    public User updateUser(Integer id, String name, String email, Integer age) {
//        validateName(name);
//        validateEmail(email);
//        validateAge(age);
//
//        findById(id);
//
//        User user = User.builder()
//                .name(name.trim())
//                .email(email.trim().toLowerCase())
//                .age(age)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return userDao.update(id, user);
//    }
//
//    public User findById(Integer id) {
//        return userDao.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Пользователь с ID " + id + " не найден"));
//    }
}
