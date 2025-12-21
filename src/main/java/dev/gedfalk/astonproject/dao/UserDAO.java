package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    // Create
    User save(User user);

    // Read
//    Optional<User> findById(Integer id);
//    List<User> findAll();

    // Update
//    User update(User user);

    // Delete
//    void delete(Integer id);

    Boolean existByEmail(String email);
}
