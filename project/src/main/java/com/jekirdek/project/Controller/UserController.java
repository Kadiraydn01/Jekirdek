package com.jekirdek.project.Controller;

import com.jekirdek.project.Config.JwtTokenProvider;
import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register") //create user done
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
    // Add a new endpoint for logout
    @GetMapping("/logout") //logout user done
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.ok("User logged out successfully");
    }
    @PostMapping("/login") //login user done
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        int userId = userService.findByUsername(user.getUsername()).getId();

        if (userDetails != null && userService.passwordMatches(user.getPassword(), userDetails.getPassword())) {
            String token = jwtTokenProvider.generateToken(userDetails.getUsername(), userId);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    @GetMapping("/all") //get all users done
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
