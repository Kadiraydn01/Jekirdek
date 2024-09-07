package com.jekirdek.project.Service;

import com.jekirdek.project.Entity.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Optional<Customer> getCustomerById(int id);
    Customer updateCustomer(int id, Customer customer);
    void deleteCustomer(int id);
    List<Customer> getAllCustomers();
    List<Customer> filterCustomers(String firstName, String email, String region);
}
