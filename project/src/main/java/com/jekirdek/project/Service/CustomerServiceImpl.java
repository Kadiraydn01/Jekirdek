package com.jekirdek.project.Service;

import com.jekirdek.project.Entity.Customer;
import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Created new customer with ID: {}", savedCustomer.getId());
        return savedCustomer;
    }

    @Override
    public Customer findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElse(null);
        if (customer != null) {
            logger.info("Found customer with email: {}", email);
        } else {
            logger.warn("No customer found with email: {}", email);
        }
        return customer;
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        Optional<Customer> customer = customerRepository.findById((long) id);
        if (customer.isPresent()) {
            logger.info("Found customer with ID: {}", id);
        } else {
            logger.warn("No customer found with ID: {}", id);
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(int id, Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById((long) id);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            if (customer.getFirstName() != null) {
                updatedCustomer.setFirstName(customer.getFirstName());
            }
            if (customer.getLastName() != null) {
                updatedCustomer.setLastName(customer.getLastName());
            }
            if (customer.getEmail() != null) {
                updatedCustomer.setEmail(customer.getEmail());
            }
            if (customer.getRegion() != null) {
                updatedCustomer.setRegion(customer.getRegion());
            }
            if (customer.getRegistrationDate() != null) {
                updatedCustomer.setRegistrationDate(customer.getRegistrationDate());
            }
            Customer savedCustomer = customerRepository.save(updatedCustomer);
            logger.info("Updated customer with ID: {}", id);
            return savedCustomer;
        }
        logger.warn("No customer found to update with ID: {}", id);
        return null;
    }

    public List<Customer> getCustomersForLoggedInUser(User loggedInUser) {
        List<Customer> customers = customerRepository.findByUserId(loggedInUser.getId());
        logger.info("Retrieved {} customers for user ID: {}", customers.size(), loggedInUser.getId());
        return customers;
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById((long) id);
        logger.info("Deleted customer with ID: {}", id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        logger.info("Retrieved all customers. Total count: {}", customers.size());
        return customers;
    }

    @Override
    public List<Customer> filterCustomers(String firstName, String email, String region) {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> filteredCustomers = customers.stream()
                .filter(c -> (firstName == null || firstName.isEmpty() ||
                        c.getFirstName().toLowerCase().contains(firstName.toLowerCase())) &&
                        (email == null || email.isEmpty() ||
                                c.getEmail().toLowerCase().contains(email.toLowerCase())) &&
                        (region == null || region.isEmpty() ||
                                c.getRegion().toLowerCase().contains(region.toLowerCase())))
                .collect(Collectors.toList());
        logger.info("Filtered customers based on criteria - FirstName: {}, Email: {}, Region: {}. Total count: {}",
                firstName, email, region, filteredCustomers.size());
        return filteredCustomers;
    }
}
