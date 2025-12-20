package dev.gedfalk.astonproject;

import dev.gedfalk.astonproject.entity.User;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hell");

        User user = User.builder()
                .name("Eugene")
                .email("bla-bla@bla.com")
                .age(30)
                .created_at(LocalDateTime.now())
                .build();

        System.out.println(user);
        System.out.println(user.getCreated_at());
    }
}
