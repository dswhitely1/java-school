package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.User;
import com.lambdaschool.school.model.UserRoles;
import com.lambdaschool.school.repository.RoleRepository;
import com.lambdaschool.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
    @Autowired
    UserRepository userRepo;

    @Autowired
    RoleRepository roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepo.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());

    }

    @Override
    public List<User> findAll(Pageable pageable)
    {
        List<User> list = new ArrayList<>();
        userRepo.findAll(pageable)
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findUserById(long id) throws ResourceNotFoundException
    {
        return userRepo.findById(id)
                       .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public User findUserByUsername(String username) throws ResourceNotFoundException
    {
        User currentUser = userRepo.findByUsername(username);

        if (currentUser != null)
        {
            return currentUser;
        } else
        {
            throw new ResourceNotFoundException(username);
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());
        List<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);
        return userRepo.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id) throws ResourceNotFoundException
    {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        User updateUser = userRepo.findByUsername(authentication.getName());

        if (updateUser != null)
        {
            if (user.getUsername() != null)
            {
                updateUser.setUsername(user.getUsername());
            }
            if (user.getPassword() != null)
            {
                updateUser.setPasswordNoEncrypt(user.getPassword());
            }
            if (user.getUserRoles()
                    .size() > 0)
            {
                roleRepo.deleteUserRolesByUserId(updateUser.getUserId());
                for (UserRoles ur : user.getUserRoles())
                {
                    roleRepo.insertUserRoles(id, ur.getRole()
                                                   .getRoleid());
                }
            }
            return userRepo.save(updateUser);
        } else
        {
            throw new ResourceNotFoundException(id + " Not current user");
        }
    }

    @Transactional
    @Override
    public void delete(long id) throws ResourceNotFoundException
    {
        if (userRepo.findById(id)
                    .isPresent())
        {
            userRepo.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }
}
