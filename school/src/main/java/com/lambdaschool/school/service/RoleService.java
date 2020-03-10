package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Role;

import java.util.List;

public interface RoleService
{
    List<Role> findAll();

    Role findRoleById(long id);

    Role save(Role role);

    Role findRoleByName(String name);

    void delete(long id);
}
