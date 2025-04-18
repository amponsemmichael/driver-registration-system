
# 🚚 Driver Registration & Dashboard System

A full-stack system for truck driver registration, data management, and document handling. This application offers full CRUD operations, responsive design, and secure validation for accurate and efficient management.

---

## 📋 Table of Contents
- Overview  
- Features  
- Technologies Used  
- Installation  
  - Prerequisites  
  - Backend Setup  
  - Frontend Setup  
- API Endpoints  
- Usage (with Postman)  
- Error Handling  
- Production Deployment  
- License  
- Acknowledgments  

---

## 📌 Overview

The Driver Registration & Dashboard System enables users to register, view, update, and delete truck driver records while managing associated documents. The system supports responsive UI, real-time validation, and a simple, intuitive interface for administrators.

---

## ✨ Features

### 🚛 CRUD Operations
- **Create:** Register drivers with optional document upload
- **Read:** View driver details individually or in a list
- **Update:** Modify driver info and optionally replace documents
- **Delete:** Remove driver records

### 🧠 Data Validation
- Prevents duplicate email and phone number entries
- Enforces required fields through form validation

### 📎 Document Management
- Upload and view/download ID or truck documents (PDF/images)

### 📱 Responsive Design
- Built with a mobile-first approach
- Clean and accessible UI

---

## 🛠 Technologies Used

### Backend
- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- PostgreSQL  
- Global exception handling

### Frontend
- React  
- React Router  
- Axios  
- Custom CSS for responsiveness

---

## ⚙️ Installation

### 🔧 Prerequisites
- Java 17+
- Node.js 16+
- npm or yarn
- Maven or Gradle

---

### ▶️ Backend Setup

```bash
git clone https://github.com/amponsemmichael/driver-registration-system.git
cd driver-registration-system
```

**Run with Maven:**
```bash
./mvnw spring-boot:run
```

> Backend runs at `http://localhost:4300`

---

### 💻 Frontend Setup

```bash
cd frontend
cd driver_registration_dashboard
npm install
npm run dev
```

> Frontend runs at `http://localhost:5173`

---

## 🔌 API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/drivers/register` | Register a new driver |
| GET    | `/api/drivers/{id}`     | Get driver by ID |
| GET    | `/api/drivers`          | List all drivers |
| PUT    | `/api/drivers/{id}`     | Update driver |
| DELETE | `/api/drivers/{id}`     | Delete driver |
| GET    | `/api/drivers/document/{id}` | Download driver's document |

---

## 🧪 Usage (Testing Backend With Postman Examples)

### ✅ Create a Driver
- **Method:** POST  
- **URL:** `http://localhost:4300/api/drivers/register`  
- **Body (form-data):**
  - fullName: Michael Mouse
  - email: micky.mouse@fright.com
  - phone: 23354356789
  - truckType: Semi-Truck
  - document: (Upload PDF/Image)

### 🔍 Get All Drivers
- **Method:** GET  
- **URL:** `http://localhost:4300/api/drivers`

### 📄 Get Driver by ID
- **Method:** GET  
- **URL:** `http://localhost:4300/api/drivers/1`

### ✏️ Update Driver
- **Method:** PUT  
- **URL:** `http://localhost:4300/api/drivers/1`  
- **Body (form-data):**
  - fullName: Micky Mouse Updated
  - email: micky.mouse@example.com
  - phone: 1234567890
  - truckType: Box Truck
  - document: (Optional file)

### ❌ Delete Driver
- **Method:** DELETE  
- **URL:** `http://localhost:4300/api/drivers/1`

### 📥 Download Document
- **Method:** GET  
- **URL:** `http://localhost:4300/api/drivers/document/1`

---

## ❗ Error Handling

All API responses follow a consistent error structure:

- **404 Not Found:** Driver not found  
- **409 Conflict:** Duplicate email/phone  
- **500 Internal Server Error:** File handling failure  

---

## 📄 License

This project is licensed under the **MIT License**.  
See the LICENSE file for details.

---

## 🙌 Acknowledgments

- Java OpenJDK  
- Spring Boot  
- React + Vite 
- Contributors to open-source libraries used in this project

---
