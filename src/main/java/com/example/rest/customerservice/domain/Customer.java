package com.example.rest.customerservice.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "customer")
@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "dateOfBirth", nullable = true)
    private LocalDate dateofBirth;

    @Column(name = "phoneNumber")
    private String phoneNumber;

}
