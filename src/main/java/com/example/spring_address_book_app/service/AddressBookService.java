package com.example.spring_address_book_app.service;

import com.example.spring_address_book_app.dto.AddressBookDTO;
import com.example.spring_address_book_app.model.AddressBook;
import com.example.spring_address_book_app.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressBookService {

    @Autowired
    private AddressBookRepository addressBookRepository;

    // Get all Address Books
    public List<AddressBookDTO> getAllAddressBooks() {
        List<AddressBook> addressBooks = addressBookRepository.findAll();
        return addressBooks.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get Address Book by ID
    public AddressBookDTO getAddressBookById(Long id) {
        Optional<AddressBook> addressBook = addressBookRepository.findById(id);
        return addressBook.map(this::convertToDTO).orElse(null);
    }

    // Create Address Book
    public AddressBookDTO createAddressBook(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = convertToEntity(addressBookDTO);
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);
        return convertToDTO(savedAddressBook);
    }

    // Update Address Book
    public AddressBookDTO updateAddressBook(Long id, AddressBookDTO addressBookDTO) {
        Optional<AddressBook> existingAddressBook = addressBookRepository.findById(id);

        if (existingAddressBook.isPresent()) {
            AddressBook addressBook = existingAddressBook.get();
            addressBook.setName(addressBookDTO.getName());
            addressBook.setPhoneNumber(addressBookDTO.getPhoneNumber());
            addressBook.setEmail(addressBookDTO.getEmail());
            AddressBook updatedAddressBook = addressBookRepository.save(addressBook);
            return convertToDTO(updatedAddressBook);
        }

        return null;
    }

    // Delete Address Book by ID
    public void deleteAddressBook(Long id) {
        addressBookRepository.deleteById(id);
    }

    // Convert Entity to DTO
    private AddressBookDTO convertToDTO(AddressBook addressBook) {
        AddressBookDTO addressBookDTO = new AddressBookDTO();
        addressBookDTO.setId(addressBook.getId());
        addressBookDTO.setName(addressBook.getName());
        addressBookDTO.setPhoneNumber(addressBook.getPhoneNumber());
        addressBookDTO.setEmail(addressBook.getEmail());
        return addressBookDTO;
    }

    // Convert DTO to Entity
    private AddressBook convertToEntity(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = new AddressBook();
        addressBook.setId(addressBookDTO.getId());
        addressBook.setName(addressBookDTO.getName());
        addressBook.setPhoneNumber(addressBookDTO.getPhoneNumber());
        addressBook.setEmail(addressBookDTO.getEmail());
        return addressBook;
    }

    public void deleteAddress(Long id) {
        Optional<AddressBook> addressBook = addressBookRepository.findById(id);
        if (addressBook.isPresent()) {
            addressBookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Address book entry not found for ID: " + id);
        }

    }
}
