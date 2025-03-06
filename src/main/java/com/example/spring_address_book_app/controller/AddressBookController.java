package com.example.spring_address_book_app.controller;

import com.example.spring_address_book_app.dto.AddressBookDTO;
import com.example.spring_address_book_app.service.AddressBookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressBookController {

    private final AddressBookService addressBookService;

    // Constructor Injection (Recommended)
    public AddressBookController(AddressBookService addressBookService) {
        this.addressBookService = addressBookService;
    }

    // Get all address book entries
    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllAddresses() {
        List<AddressBookDTO> addresses = addressBookService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    // Get address book entry by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getAddressById(@PathVariable("id") Long id) {
        return addressBookService.getAddressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new address book entry
    @PostMapping
    public ResponseEntity<AddressBookDTO> createAddress(@Valid @RequestBody AddressBookDTO addressBookDTO) {
        AddressBookDTO createdAddressBook = addressBookService.createAddress(addressBookDTO);
        return ResponseEntity.created(URI.create("/api/addresses/" + createdAddressBook.getId()))
                .body(createdAddressBook);
    }

    // Update an existing address book entry
    @PutMapping("/{id}")
    public ResponseEntity<AddressBookDTO> updateAddress(@PathVariable("id") Long id, @Valid @RequestBody AddressBookDTO addressBookDTO) {
        Optional<AddressBookDTO> updatedAddress = addressBookService.updateAddress(id, addressBookDTO);
        return updatedAddress.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an address book entry
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") Long id) {
        addressBookService.deleteAddress(id);
    }
}
