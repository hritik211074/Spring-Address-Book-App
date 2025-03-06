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
    public List<AddressBookDTO> getAllAddresses() {
        return addressBookRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get Address Book by ID
    public Optional<AddressBookDTO> getAddressById(Long id) {
        return addressBookRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Create Address Book
    public AddressBookDTO createAddress(AddressBookDTO addressBookDTO) {
        AddressBook addressBook = convertToEntity(addressBookDTO);
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);
        return convertToDTO(savedAddressBook);
    }

    // Update Address Book
    public Optional<AddressBookDTO> updateAddress(Long id, AddressBookDTO addressBookDTO) {
        return addressBookRepository.findById(id).map(existingAddress -> {
            existingAddress.setName(addressBookDTO.getName());
            existingAddress.setPhoneNumber(addressBookDTO.getPhoneNumber());
            existingAddress.setEmail(addressBookDTO.getEmail());
            AddressBook updatedAddress = addressBookRepository.save(existingAddress);
            return convertToDTO(updatedAddress);
        });
    }

    // Delete Address Book by ID
    public boolean deleteAddress(Long id) {
        if (addressBookRepository.existsById(id)) {
            addressBookRepository.deleteById(id);
            return true;
        }
        return false;
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
        addressBook.setName(addressBookDTO.getName());
        addressBook.setPhoneNumber(addressBookDTO.getPhoneNumber());
        addressBook.setEmail(addressBookDTO.getEmail());
        return addressBook;
    }
}
