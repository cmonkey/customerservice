package com.example.rest.customerservice.repository;

import com.example.rest.customerservice.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account, Long> {

    Page<Account> findByCustomerId(long customerId, Pageable pageable);
}
