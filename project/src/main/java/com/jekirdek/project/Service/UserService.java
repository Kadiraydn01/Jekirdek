package com.jekirdek.project.Service;


import com.jekirdek.project.Entity.Role;
import com.jekirdek.project.Entity.User;

import java.util.Optional;

public interface UserService {
    User findUserById(int id);
    User findUserByRole(Role role);
    User findByUsername(String username);

}
