# FitRack Authentication and Authorization

This project demonstrates the implementation of **JWT Authentication and Authorization** for the FitRack application. It secures the API endpoints and manages role-based access control (RBAC) for users.



## Features

### 1. **Authentication**
- **Registration**: Admin-only endpoint for registering users.
- **Login**: All users can log in to obtain a JSON Web Token (JWT) for authenticated API access.

### 2. **Authorization**
- RBAC ensures secure access to resources based on user roles:
  - **Admin**: Full control over user and event management.
  - **User**: Access limited to participation and tracking.

### 3. **JWT Integration**
- Tokens include role-based claims for fine-grained access control.
- Automatic token validation and role extraction during requests.



## Technology Stack

- **Java & Spring Boot**: Backend framework.
- **Jakarta Servlet API**: Request and response handling.
- **JWT**: Secure authentication and token management.
- **Spring Security**: Role-based access and session control.
- **BCrypt**: Password encryption for secure storage.
- **MySQL (via JPA)**: User persistence.



## Endpoints

### **AuthenticationController**
| Endpoint                  | Method | Role           | Description                                  |
|---------------------------|--------|----------------|----------------------------------------------|
| `/api/admin_only/register`| POST   | Admin          | Register a new user.                        |
| `/api/login`              | POST   | All Users      | Authenticate and receive a JWT.             |



## Security Configuration

### **SecurityConfig**:
- **Filters**:
  - Custom `JwtAuthenticationFilter` validates incoming JWTs and extracts user roles.
- **Session Management**:
  - Stateless, ensuring no server-side session storage.
- **CSRF**: Disabled for APIs.
- **Password Encoding**:
  - `BCryptPasswordEncoder` ensures password hashing.



## JWT Implementation

### **JwtService**
- **Generate Token**:
  - Includes `roles` and `userId` in claims for RBAC.
  - Tokens are valid for **24 hours**.
- **Extract Email**:
  - Derives the subject (user email) from the token.
- **Token Validation**:
  - Validates the token's signature, expiry, and user details.




