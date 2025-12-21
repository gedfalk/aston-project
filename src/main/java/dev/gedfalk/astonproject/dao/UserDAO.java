package dev.gedfalk.astonproject.dao;

import dev.gedfalk.astonproject.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    // Create
    User save(User user);

    // Read
    Optional<User> findById(Integer id);

    // Update
    User update(Integer id, User user);

    // Delete
    void delete(Integer id);

    Boolean existByEmail(String email);
    List<User> findAll();
}
