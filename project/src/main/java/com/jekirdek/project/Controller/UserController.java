package com.jekirdek.project.Controller;

import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
    // Add a new endpoint for logout
    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        userService.logout(request);  // Oturumu sonlandır
        return ResponseEntity.ok("User logged out successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        userService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("User logged in successfully");
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
