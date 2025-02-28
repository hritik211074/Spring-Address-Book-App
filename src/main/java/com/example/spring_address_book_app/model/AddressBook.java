package com.example.spring_address_book_app.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Table(name = "address_book")
@Entity
@Data
@ToString
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates the ID
    private Long id;

    @Column(nullable = false, length = 100)  // Sets constraints on the column
    private String name;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(nullable = true, length = 100)
    private String email;
}
