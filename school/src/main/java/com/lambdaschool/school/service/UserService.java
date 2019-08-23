package com.lambdaschool.school.service;

import com.lambdaschool.school.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService
{
    List<User> findAll(Pageable pageable);

    User findUserById(long id);

    User findUserByUsername(String username);

    User save(User user);

    User update(User user, long id);

    void delete(long id);
}
