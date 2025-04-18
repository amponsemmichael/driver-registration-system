package com.amponsem.driver_registration_system.repository;

import com.amponsem.driver_registration_system.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    // Check if a driver exists with the given email
    boolean existsByEmail(String email);

    // Check if a driver exists with the given phone number
    boolean existsByPhone(String phone);
}
