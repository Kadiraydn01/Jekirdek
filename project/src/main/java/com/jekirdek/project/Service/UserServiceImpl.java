package com.jekirdek.project.Service;

import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {
        logger.info("Registering user with username: {}", user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        logger.info("User registered with username: {}", savedUser.getUsername());
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user with username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    @Override
    public User findUserById(int id) {
        logger.info("Finding user with ID: {}", id);
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User login(String username, String password) {
        logger.info("User login attempt with username: {}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Şifre kontrolü
        if (!encoder.matches(password, user.getPassword())) {
            logger.warn("Invalid credentials for username: {}", username);
            throw new RuntimeException("Invalid credentials");
        }

        logger.info("User logged in with username: {}", username);
        return user;
    }

    @Override
    public void logout(HttpServletRequest request) {
        logger.info("User logout request");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            logger.info("Session invalidated");
        }

        SecurityContextHolder.clearContext();
        logger.info("Security context cleared");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean passwordMatches(String rawPassword, String encodedPassword) {
        logger.info("Password matching attempt");
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        logger.info("Password match result: {}", matches);
        return matches;
    }

    @Override
    public User findUserByRole(String role) {
        logger.info("Finding user with role: {}", role);
        return userRepository.findByRole(role).orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        logger.info("Finding user with username: {}", username);
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            logger.warn("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        return userOptional.get();
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }
}
