package com.addressbook.service;

import com.addressbook.entity.Address;
import com.addressbook.repository.AddressRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);
    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address save(Address address) {
        Address saved = addressRepository.save(address);
        logger.info("Address saved with id={}", saved.getAddressId());
        return saved;
    }

    public Optional<Address> findById(int id) {
        Optional<Address> result = addressRepository.findById(id);
        if (result.isEmpty())
            logger.warn("Address lookup failed, id={} not found", id);
        return result;
    }

    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    public List<Address> findByCity(String city) {
        return addressRepository.findByCityIgnoreCase(city);
    }

    public List<Address> findByContactName(String name) {
        return addressRepository.findByContactNameContainingIgnoreCase(name);
    }

    public Optional<Address> update(int id, Address updatedAddress) {
        return addressRepository.findById(id).map(existing -> {
            existing.setContactName(updatedAddress.getContactName());
            existing.setStreet(updatedAddress.getStreet());
            existing.setCity(updatedAddress.getCity());
            existing.setState(updatedAddress.getState());
            existing.setZipCode(updatedAddress.getZipCode());
            Address saved = addressRepository.save(existing);
            logger.info("Address updated with id={}", id);
            return saved;
        });
    }

    public boolean delete(int id) {
        if (!addressRepository.existsById(id)) {
            logger.warn("Address delete failed, id={} not found", id);
            return false;
        }
        addressRepository.deleteById(id);
        logger.info("Address deleted with id={}", id);
        return true;
    }
}
