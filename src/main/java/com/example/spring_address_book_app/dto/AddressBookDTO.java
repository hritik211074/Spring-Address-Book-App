package com.example.spring_address_book_app.dto;

import lombok.Data;

@Data
public class AddressBookDTO {
    private Long id;
    private String name;
    private String phoneNumber;
    private String email;
}
