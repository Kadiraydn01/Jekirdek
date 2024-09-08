package com.jekirdek.project.Controller;

import com.jekirdek.project.Entity.Customer;
import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Service.CustomerService;
import com.jekirdek.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/costumer")
public class CostumerController {
    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public CostumerController(CustomerService customerService, UserService userService) {
        this.userService = userService;
        this.customerService = customerService;
    }
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }


    @GetMapping("/loggedIn")
    public ResponseEntity<List<Customer>> getLoggedInUserCustomers(Authentication authentication) {
        User loggedInUser = userService.findByUsername(authentication.getName());

        List<Customer> customers = customerService.getCustomersForLoggedInUser(loggedInUser);

        return ResponseEntity.ok(customers);
    }


    @GetMapping("/filter")
    public List<Customer> filterCustomers(@RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) String region) {
        return customerService.filterCustomers(firstName, email, region);
    }
}
