
# Project: Secure Spring Application
This project contains a comprehensive, secure 
Spring Boot application implementing robust user 
management with advanced security features and 
role-based access control.

This Spring Boot application provides a secure 
foundation for user management with a focus on
security best practices, including JWT 
authentication and role-based authorization.

## Table of Contents
- [Overview](#overview)
  - [General Features](#general-features)
  - [Tech Stack](#tech-stack)
  - [Project Structure](#project-structure-srcmain)
- [Getting Started](#project-setup)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#application-properties)

## Overview
### General Features
- User management (CRUD operations)
- Login management
- Role-based access control (RBAC)
- Secure password handling
- Token-based authentication
- Audit logging

### Tech Stack
#### Core
- Java 21
- Gradle
- Spring Boot

#### Security
- JWT (JSON Web Tokens)
- BCrypt password encoding
- Spring Security

#### Database
- Spring Data JPA

#### Logging & Monitoring
- SLF4J
- Logback

### Project Structure (`~/src/main/`)
```
 main/
   ├── java/
   │   ├── com/cognizant/SecureSpringApplication/
   │   ├── config/ # Security & application configuration
   │   ├── controllers/ # REST controllers 
   │   ├── dto/ # Data transfer objects
   │   ├── filters/ # Security filters
   │   ├── models/ # Domain models
   │   ├── repositories/ # Data access layer
   │   ├── security/ # JWT utilities & security
   │   └── services/ # Business logic & user services
   └── resources/
             └── application.properties
``` 

### API Endpoints
#### User Management (`/api/users`)
Available endpoints:
* `GET`: Get all users.
  * **Authorized role:** `admin`
* `GET /{username}`: Get user by username.
    * **Authorized role:** `own-user OR admin`, 
* `PUT /{username}`: Update user by username.
    * **Authorized role:** `own-user`
* `DELETE /{username}`: Remove user by username.
    * **Authorized role:** `own-user OR admin`,

#### Login Management (`/login`)
Available endpoints:
* `GET`: Get login page content.
    * **Authorized role:** `ALL`
* `POST`: Log into account.
    * **Authorized role:** `ALL`,

#### Signup Management (`/signup`)
Available endpoints:
* `GET`: Get signup page content.
    * **Authorized role:** `ALL`
* `POST`: Create account.
    * **Authorized role:** `ALL`,

<br>
  
Here are a few sample requests
(assume every request is properly 
authenticated/authorized):

**Get All Users**\
Request:
```
 GET http://localhost:8080/api/users
```
Response:
```json
[
  {
    "username": "john_doe", 
    "password": "$2a$10$jHpu1JETm0PIkS...", 
    "role": "user"
  },
  {
    "username": "greg567",
    "password": "$2a$10$lc8KVzw90v7H5o...",
    "role": "user"
  },
  ...
]
```
<br>

**Get User by Username**\
Request:
```
 GET http://localhost:8080/api/users/john_doe
```
Response:
```json
{
  "username": "john_doe",
  "password": "$2a$10$jHpu1JETm0PIkSCcfFwFVObMnU1g26Stq3nPMLzBNZh/RkihOplDC",
  "role": "user"
}
```
<br>

**Update User by Username**\
Request:
```
PUT http://localhost:8080/api/users/john_doe?newUsername=john_smith&password=newSecurePass123
``` 
Response:
```
200 OK
``` 
<br>

**Delete User by Username**\
Request:
```
DELETE http://localhost:8080/api/users/john_doe
``` 
Response:
```
User john_doe deleted.
```
<br>

**Sign Up**\
Request:
```
POST http://localhost:8080/signup
```
```json
//Request body:
{
  "username": "john.doe",
  "password": "Pa$$word123",
  "role": "user"
}
```
Response:
```
200 OK
```
<br>

**Log In**\
Request:
```
POST http://localhost:8080/login
```
```json
//Request body:
{
  "username": "user123", 
  "password": "securePassword123"
}
```
Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## Project Setup
### Prerequisites
- JDK 21 or higher
- Your preferred IDE (IntelliJ IDEA recommended)
- Git
- MySQL

### Installation
#### 1. Clone the repository:
```bash
git clone https://github.com/adrian-cr/csa-project-secure-spring-application.git
cd csa-project-secure-spring-application
```

#### 2. Run the application:
Run the application's main class `SecureSpringApplication`.

### Application Properties
```properties
# Server Configuration
server.port=8080 server.servlet.context-path=/api
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/secure_app_db
spring.datasource.username={DB_USERNAME}
spring.datasource.password={DB_PASSWORD}
# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
``` 


