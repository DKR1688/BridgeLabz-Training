package com.addressbook.repository;

import com.addressbook.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCityIgnoreCase(String city);

    List<Address> findByContactNameContainingIgnoreCase(String namePart);
}
