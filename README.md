# Inquiry Manager System

A Java-based client-server system for managing customer inquiries with automatic assignment, multithreading support, and persistent file storage.

---

## Overview

Inquiry Manager System is a client-server Java application that simulates a customer support workflow.

Customers submit inquiries which are automatically queued and assigned to available representatives. The assignment process supports workload balancing and representative specialization.

The system demonstrates core backend concepts such as concurrency, socket communication, layered architecture, and file-based persistence.

---

## Features

- Create and cancel customer inquiries
- Automatic assignment of inquiries to available representatives
- Representative specialization by inquiry type (REQUEST / QUESTION / COMPLAIN)
- Real-time inquiry status tracking
- Representative login/logout system
- Inquiry archiving after completion
- Monthly inquiry statistics
- Thread-safe processing with concurrent data structures

---

## Architecture

The system is built using a layered architecture:

- **Communication Layer** – Handles client-server socket communication
- **Service Layer** – Contains business logic and system orchestration (`InquiryManager`)
- **Repository Layer** – Manages file-based data persistence
- **Data Layer** – Defines domain models (Inquiry, Representative, etc.)

The system uses a centralized `InquiryManager` (Singleton) to maintain global state and coordination.

---

## Tech Stack

- Java
- Socket Programming (Client-Server)
- Multithreading
- Object Serialization
- Concurrent Data Structures (ConcurrentLinkedQueue, AtomicInteger)
- File-based storage

---

## Project Structure
InquiryManagerSystem/
├── Client/
└── Server/
├── communication/
├── data/
├── repository/
└── service/


---

## How to Run

### Start Server

java InquiryManagerServer


### Start Client

java InquiryManagerClient


> Important: The server must be running before starting any client instance.

---

## System Flow

1. Client creates a new inquiry  
2. Server receives and validates the request  
3. Inquiry is added to the processing queue  
4. System assigns inquiry to an available representative  
5. Inquiry status is updated during handling  
6. Completed inquiries are archived  
7. Monthly statistics can be retrieved  

---

## Design Highlights

- Thread-safe concurrent processing
- Automatic load balancing between representatives
- Separation of concerns via layered architecture
- Scalable design for future extensions

---

## Future Improvements

- Database integration (e.g., PostgreSQL)
- REST API layer
- Authentication and authorization system
- Admin dashboard UI
- Logging and monitoring system

---

## Author

Academic Software Engineering Project
