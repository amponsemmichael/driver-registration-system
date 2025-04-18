package com.amponsem.driver_registration_system.controller;

import com.amponsem.driver_registration_system.domain.Driver;
import com.amponsem.driver_registration_system.domain.dto.DriverResponse;
import com.amponsem.driver_registration_system.domain.dto.DriverUpdateRequest;
import com.amponsem.driver_registration_system.services.DriverService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*") // For development - restrict in production
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /**
     * Create a new driver
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("truckType") String truckType,
            @RequestParam("document") MultipartFile document) {

        try {
            Driver driver = driverService.registerDriver(fullName, email, phone, truckType, document);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverService.getDriverDetails(driver.getId()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process document upload: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    /**
     * Get all drivers
     */
    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAllDrivers() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    /**
     * Get a specific driver by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getDriverDetails(@PathVariable Long id) {
        try {
            DriverResponse driver = driverService.getDriverDetails(id);
            return ResponseEntity.ok(driver);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /**
     * Update a driver
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDriver(
            @PathVariable Long id,
            @RequestParam(value = "fullName", required = false) String fullName,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "truckType", required = false) String truckType,
            @RequestParam(value = "document", required = false) MultipartFile document) {

        try {
            DriverUpdateRequest updateRequest = new DriverUpdateRequest();
            updateRequest.setFullName(fullName);
            updateRequest.setEmail(email);
            updateRequest.setPhone(phone);
            updateRequest.setTruckType(truckType);
            updateRequest.setDocument(document);

            DriverResponse updatedDriver = driverService.updateDriver(id, updateRequest);
            return ResponseEntity.ok(updatedDriver);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process document upload: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    /**
     * Delete a driver
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        try {
            driverService.deleteDriver(id);
            return ResponseEntity.ok().body("Driver with ID " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    /**
     * Get driver document
     */
    @GetMapping("/document/{id}")
    public ResponseEntity<?> getDocument(@PathVariable Long id) {
        try {
            byte[] document = driverService.getDocument(id);
            String documentType = driverService.getDocumentType(id);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(documentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document\"")
                    .body(document);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}