package com.example.spring_address_book_app.repository;

import com.example.spring_address_book_app.model.AddressBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressBookRepository extends JpaRepository<AddressBook, Long> {
}
