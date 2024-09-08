package com.jekirdek.project.Service;


import com.jekirdek.project.Entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User findUserById(int id);
    User login(String username, String password);
    void logout(HttpServletRequest request);
    User findUserByRole(String role);
    User findByUsername(String username);
    List<User> getAllUsers();
    User registerUser(User user);

}
