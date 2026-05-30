# Inquiry Manager System

## Overview
Inquiry Manager System is a Java-based Client-Server application for managing customer inquiries. The system supports inquiry creation, representative management, automatic inquiry assignment, inquiry status tracking, and statistics generation.

## Features
- Create and cancel inquiries
- Track inquiry status by inquiry code
- Automatic assignment of inquiries to representatives
- Representative login/logout management
- Archive completed inquiries
- Monthly inquiry statistics
- Representative specialization by inquiry type
- Persistent file-based storage

## Technologies
- Java
- Socket Programming
- Multithreading
- Object Serialization
- ConcurrentLinkedQueue
- AtomicInteger

## Project Structure

```text
Client/
Server/
├── communication
├── data
├── repository
└── service
```

## Running the Project

### Start Server
Run the server application:

```bash
InquiryManagerServer
```

### Start Client
Run the client application:

```bash
InquiryManagerClient
```

## Architecture
The project follows a layered architecture:
- Communication Layer
- Service Layer
- Repository Layer
- Data Layer

The system uses the Repository pattern for data access and a Singleton-based InquiryManager for business logic management.

## Authors
Software Engineering Academic Project
