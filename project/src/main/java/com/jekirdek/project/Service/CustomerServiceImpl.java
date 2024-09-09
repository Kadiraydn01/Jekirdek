package com.jekirdek.project.Service;

import com.jekirdek.project.Entity.Customer;
import com.jekirdek.project.Entity.User;
import com.jekirdek.project.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById((long) id);
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

            return customerRepository.save(updatedCustomer);
        }
        return null;
    }

    public List<Customer> getCustomersForLoggedInUser(User loggedInUser) {
        return customerRepository.findByUserId(loggedInUser.getId());
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.deleteById((long) id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> filterCustomers(String firstName, String email, String region) {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .filter(c -> (firstName == null || c.getFirstName().equalsIgnoreCase(firstName)) &&
                        (email == null || c.getEmail().equalsIgnoreCase(email)) &&
                        (region == null || c.getRegion().equalsIgnoreCase(region)))
                .collect(Collectors.toList());
    }
}
