package com.jekirdek.project.Controller;

import com.jekirdek.project.Entity.Customer;
import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Service.CustomerService;
import com.jekirdek.project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final UserService userService;

    @Autowired
    public CustomerController(CustomerService customerService, UserService userService) {
        this.userService = userService;
        this.customerService = customerService;
    }
    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@RequestBody Map<String, Object> customerData) {
        try {
            System.out.println("Received Data: " + customerData);

            String firstName = (String) customerData.get("firstName");
            String lastName = (String) customerData.get("lastName");
            String email = (String) customerData.get("email");
            String region = (String) customerData.get("region");
            int userId = (Integer) customerData.get("userId");

            // Customer nesnesini oluştur
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setRegion(region);

            User user = userService.findUserById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User bulunamazsa hata döner
            }

            customer.setUser(user);

            //  Save Customer
            Customer createdCustomer = customerService.createCustomer(customer);

            return ResponseEntity.ok(createdCustomer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}") //get customer by id done
    public Optional<Customer> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}") //update customer by id done
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")//delete customer by id done
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/all") //get all customers done
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }


    @GetMapping("/loggedIn") //get customers for logged in user done
    public ResponseEntity<List<Customer>> getLoggedInUserCustomers(Authentication authentication) {
        User loggedInUser = userService.findByUsername(authentication.getName());

        List<Customer> customers = customerService.getCustomersForLoggedInUser(loggedInUser);

        return ResponseEntity.ok(customers);
    }


    @GetMapping("/filter") //filter customers done
    public List<Customer> filterCustomers(@RequestParam(required = false) String firstName,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) String region) {
        return customerService.filterCustomers(firstName, email, region);
    }
}
