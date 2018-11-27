package com.example.rest.customerservice.controller;

import com.example.rest.customerservice.domain.Customer;
import com.example.rest.customerservice.exception.ResourceNotFoundException;
import com.example.rest.customerservice.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {
    private CustomerRepository customerRepository;

    public void CustomerController(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @PostMapping(value = "/customers")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer){

        return customerRepository.save(customer);
    }

    @GetMapping(value = "/customers")
    public Page<Customer> all(Pageable pageable){

        return customerRepository.findAll(pageable);
    }

    @GetMapping(value = "/custoemrs/{id}")
    public Customer findById(@PathVariable long id){
        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("customer is null"));
    }

    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        return customerRepository.findById(id).map(customer -> {
            customerRepository.delete(customer);
            return ResponseEntity.ok().build();
        }
        ).orElseThrow(() -> new ResourceNotFoundException("customer is null"));

    }

    @PutMapping(value = "/custoemrs/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id, @RequestBody Customer newCustomer){
        return customerRepository.findById(id).map(customer ->  {
            customer.setName(newCustomer.getName());
            customer.setDateofBirth(newCustomer.getDateofBirth());
            customer.setPhoneNumber(newCustomer.getPhoneNumber());

            customerRepository.save(customer);

            return ResponseEntity.ok(customer);
        }).orElseThrow(() -> new ResourceNotFoundException("customer is null"));
    }
}
