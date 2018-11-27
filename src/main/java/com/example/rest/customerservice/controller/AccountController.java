package com.example.rest.customerservice.controller;

import com.example.rest.customerservice.domain.Account;
import com.example.rest.customerservice.domain.Customer;
import com.example.rest.customerservice.exception.ResourceNotFoundException;
import com.example.rest.customerservice.repository.AccountRepository;
import com.example.rest.customerservice.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    public AccountController(CustomerRepository customerRepository, AccountRepository accountRepository){
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @PostMapping(value = "/customers/{id}/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account save(@PathVariable long id, @RequestBody Account account){

        return customerRepository.findById(id).map(customer -> {
            account.setCustomer(customer);

            return accountRepository.save(account);
        }).orElseThrow(() -> new ResourceNotFoundException("customer is null"));
    }

    @GetMapping(value = "/customers/{id}/accounts")
    public Page<Account> all(@PathVariable long id, Pageable pageable){

        return accountRepository.findByCustomerId(id, pageable);
    }
    @DeleteMapping(value = "/customers/{customerId}/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable long customerId, @PathVariable long accountId){
        if(!customerRepository.existsById(customerId)){
            throw new ResourceNotFoundException("customer is null");
        }

        return accountRepository.findById(accountId).map(account -> {
            accountRepository.delete(account);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("account is null"));
    }

    @PutMapping(value = "/customers/{customerId}/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable long customerId,
                                                 @PathVariable long accountId,
                                                 @RequestBody Account newAccount){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("customer is null"));

        return accountRepository.findById(accountId).map(account -> {

            newAccount.setCustomer(customer);
            accountRepository.save(newAccount);

            return ResponseEntity.ok(newAccount);
        }).orElseThrow(() -> new ResourceNotFoundException("account is null"));

    }
}
