package com.amponsem.driver_registration_system.services;

import com.amponsem.driver_registration_system.domain.Driver;
import com.amponsem.driver_registration_system.domain.dto.DriverResponse;
import com.amponsem.driver_registration_system.domain.dto.DriverUpdateRequest;
import com.amponsem.driver_registration_system.exception.DuplicateResourceException;
import com.amponsem.driver_registration_system.exception.ResourceNotFoundException;
import com.amponsem.driver_registration_system.repository.DriverRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {

    private final DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver registerDriver(String fullName, String email, String phone,
                                 String truckType, MultipartFile document) throws IOException, IOException {

        // Check for duplicate email
        if (driverRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("A driver with email " + email + " already exists");
        }

        // Check for duplicate phone number
        if (driverRepository.existsByPhone(phone)) {
            throw new DuplicateResourceException("A driver with phone number " + phone + " already exists");
        }

        Driver driver = new Driver();
        driver.setFullName(fullName);
        driver.setEmail(email);
        driver.setPhone(phone);
        driver.setTruckType(truckType);
        driver.setDocumentName(document.getOriginalFilename());
        driver.setDocumentType(document.getContentType());
        driver.setDocumentData(document.getBytes());
        driver.setRegistrationDate(LocalDateTime.now());

        return driverRepository.save(driver);
    }

    public List<DriverResponse> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::convertToDriverResponse)
                .collect(Collectors.toList());
    }

    public DriverResponse getDriverDetails(Long id) {
        Optional<Driver> driverOpt = driverRepository.findById(id);

        if (driverOpt.isPresent()) {
            return convertToDriverResponse(driverOpt.get());
        }

        throw new ResourceNotFoundException("Driver not found with id: " + id);
    }

    /**
     * Update driver information
     */
    public DriverResponse updateDriver(Long id, DriverUpdateRequest updateRequest) throws IOException {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));

        // Check for duplicate email if it's being changed
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(driver.getEmail()) &&
                driverRepository.existsByEmail(updateRequest.getEmail())) {
            throw new DuplicateResourceException("A driver with email " + updateRequest.getEmail() + " already exists");
        }

        // Check for duplicate phone if it's being changed
        if (updateRequest.getPhone() != null && !updateRequest.getPhone().equals(driver.getPhone()) &&
                driverRepository.existsByPhone(updateRequest.getPhone())) {
            throw new DuplicateResourceException("A driver with phone number " + updateRequest.getPhone() + " already exists");
        }

        // Update fields only if they're provided in the request
        if (updateRequest.getFullName() != null) {
            driver.setFullName(updateRequest.getFullName());
        }

        if (updateRequest.getEmail() != null) {
            driver.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getPhone() != null) {
            driver.setPhone(updateRequest.getPhone());
        }

        if (updateRequest.getTruckType() != null) {
            driver.setTruckType(updateRequest.getTruckType());
        }

        // Update document if provided
        if (updateRequest.getDocument() != null && !updateRequest.getDocument().isEmpty()) {
            driver.setDocumentName(updateRequest.getDocument().getOriginalFilename());
            driver.setDocumentType(updateRequest.getDocument().getContentType());
            driver.setDocumentData(updateRequest.getDocument().getBytes());
        }

        Driver updatedDriver = driverRepository.save(driver);
        return convertToDriverResponse(updatedDriver);
    }

    /**
     * Delete a driver
     */
    public void deleteDriver(Long id) {
        if (!driverRepository.existsById(id)) {
            throw new ResourceNotFoundException("Driver not found with id: " + id);
        }
        driverRepository.deleteById(id);
    }

    /**
     * Get document data
     */
    public byte[] getDocument(Long id) {
        Optional<Driver> driverOpt = driverRepository.findById(id);
        if (driverOpt.isEmpty()) {
            throw new ResourceNotFoundException("Driver not found with id: " + id);
        }
        return driverOpt.get().getDocumentData();
    }

    /**
     * Get document type
     */
    public String getDocumentType(Long id) {
        Optional<Driver> driverOpt = driverRepository.findById(id);
        if (driverOpt.isEmpty()) {
            throw new ResourceNotFoundException("Driver not found with id: " + id);
        }
        return driverOpt.get().getDocumentType();
    }

    /**
     * Helper method to convert Driver entity to DriverResponse DTO
     */
    private DriverResponse convertToDriverResponse(Driver driver) {
        // Create download URL for the document
        String documentDownloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/drivers/document/")
                .path(driver.getId().toString())
                .toUriString();

        return new DriverResponse(
                driver.getId(),
                driver.getFullName(),
                driver.getEmail(),
                driver.getPhone(),
                driver.getTruckType(),
                driver.getDocumentName(),
                documentDownloadUrl
        );
    }
}
