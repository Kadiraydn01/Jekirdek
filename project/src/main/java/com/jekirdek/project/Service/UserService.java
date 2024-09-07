package com.jekirdek.project.Service;


import com.jekirdek.project.Entity.User;

public interface UserService {
    User findUserByUsername(int id);
    User findUserByRole(String role);

}
