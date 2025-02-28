package com.example.spring_address_book_app.controller;

import com.example.spring_address_book_app.model.AddressBook;
import com.example.spring_address_book_app.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    // Get all address book entries
    @GetMapping
    public List<AddressBook> getAllAddresses() {
        return addressBookService.getAllAddresses();
    }

    // Get address book entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getAddressById(@PathVariable("id") Long id) {
        Optional<AddressBook> addressBook = addressBookService.getAddressById(id);
        if (addressBook.isPresent()) {
            return ResponseEntity.ok(addressBook.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a new address book entry
    @PostMapping
    public ResponseEntity<AddressBook> createAddress(@RequestBody AddressBook addressBook) {
        AddressBook createdAddressBook = addressBookService.createAddress(addressBook);
        return ResponseEntity.ok(createdAddressBook);
    }

    // Update an existing address book entry
    @PutMapping("/{id}")
    public ResponseEntity<AddressBook> updateAddress(@PathVariable("id") Long id, @RequestBody AddressBook updatedAddressBook) {
        Optional<AddressBook> existingAddressBook = addressBookService.getAddressById(id);
        if (existingAddressBook.isPresent()) {
            updatedAddressBook.setId(id);
            AddressBook updated = addressBookService.updateAddress(updatedAddressBook);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an address book entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id) {
        Optional<AddressBook> existingAddressBook = addressBookService.getAddressById(id);
        if (existingAddressBook.isPresent()) {
            addressBookService.deleteAddress(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
