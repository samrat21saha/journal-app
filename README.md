<div align="center">
# 🌙 Aura Journal
 
**A secure, full-stack journaling application built for reflection and privacy.**
 
[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-47A248?style=flat-square&logo=mongodb)](https://www.mongodb.com/)
[![React](https://img.shields.io/badge/React-Frontend-61DAFB?style=flat-square&logo=react)](https://react.dev/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](#license)
 
[Live Demo](#) · [Report Bug](#) · [Request Feature](#)
 
</div>
---
 
## 📖 About The Project
 
**Aura Journal** is a full-stack personal journaling application designed to give users a private, secure space to record their thoughts. Beyond core journaling functionality, the project was built to demonstrate production-grade backend engineering practices — stateless authentication, secure account recovery, role-based authorization, and resilient error handling.
 
This project was built as a hands-on deep dive into secure API design, evolving through two major architectural passes: migrating from link-based password resets to a time-bound OTP flow, and replacing HTTP Basic Auth with full JWT-based stateless authentication.
 
---
 
## ✨ Features
 
- 🔐 **Stateless JWT Authentication** — Custom filter chain and token service for secure, scalable, session-less login
- 📧 **OTP-Based Password Recovery** — Time-bound 6-digit codes delivered via SMTP, replacing insecure reset links
- 👥 **Role-Based Access Control** — Distinct `USER` and `ADMIN` permission levels
- 📝 **Full Journal CRUD** — Create, read, update, and delete personal journal entries
- 🛡️ **Centralized Exception Handling** — Consistent, predictable API error responses
- 📊 **Structured Logging** — SLF4J logging throughout the API layer for full request/failure traceability
- 💻 **Responsive React Frontend** — Clean, modern UI built with the assistance of Claude Code
---
 
## 🛠️ Tech Stack
 
### Backend
| Technology | Purpose |
|---|---|
| **Java 17** | Core language |
| **Spring Boot 3.3** | Application framework |
| **Spring Security** | Authentication & authorization |
| **JWT (JJWT)** | Stateless token-based auth |
| **MongoDB** | NoSQL document database |
| **SLF4J** | Logging abstraction |
| **Spring Mail (SMTP)** | OTP email delivery |
| **Maven** | Dependency management & build |
 
### Frontend
| Technology | Purpose |
|---|---|
| **React** | UI framework |
| **Tailwind CSS** | Styling |
 
### Tooling
- **IntelliJ IDEA** — Primary IDE
- **MongoDB Compass** — Local database inspection
- **Postman / cURL** — API testing
---
 
## 🏗️ Architecture Overview
 
```
┌─────────────┐        HTTPS/JWT        ┌──────────────────┐        ┌─────────────┐
│   React     │  ────────────────────>  │   Spring Boot     │  ───>  │   MongoDB   │
│  Frontend   │  <────────────────────  │   REST API        │  <───  │   Database  │
└─────────────┘                         └──────────────────┘        └─────────────┘
                                                  │
                                                  ▼
                                          ┌──────────────────┐
                                          │  SMTP (Email)     │
                                          │  OTP Delivery     │
                                          └──────────────────┘
```
 
**Auth flow:** Client authenticates → `AuthController` issues a JWT → `JwtAuthenticationFilter` validates the Bearer token on every protected request → `SecurityConfig` enforces role-based route access.
 
---
 
## 🚀 Getting Started
 
### Prerequisites
 
Make sure you have the following installed:
 
- **JDK 17** (Spring Boot 3.3 targets Java 17 — newer JDKs may cause compatibility issues)
- **Maven** 3.8+
- **MongoDB** (local instance or a free [MongoDB Atlas](https://www.mongodb.com/atlas) cluster)
- **Node.js** 18+ and npm (for the React frontend)
- An **SMTP-enabled email account** (e.g., Gmail with an App Password) for OTP delivery
### Installation
 
1. **Clone the repository**
```bash
   git clone https://github.com/<your-username>/aura-journal.git
   cd aura-journal
```
 
2. **Configure environment variables**
   Create a `.env` file (or configure `application.properties`) in the backend root with:
```env
   MONGODB_URI=mongodb://localhost:27017/aurajournal
   JWT_SECRET=your_256_bit_minimum_secret_key
   JWT_EXPIRATION=3600000
   SMTP_HOST=smtp.gmail.com
   SMTP_PORT=587
   SMTP_USERNAME=your_email@gmail.com
   SMTP_PASSWORD=your_app_password
```
   > ⚠️ Never commit real secrets. Use `.env.example` as a template and add `.env` to `.gitignore`.
 
3. **Run the backend**
```bash
   mvn clean install
   mvn spring-boot:run
```
   The API will start on `http://localhost:8080`.
 
4. **Run the frontend**
```bash
   cd frontend
   npm install
   npm run dev
```
   The app will be available at `http://localhost:5173` (or your configured port).
 
---
 
## 📡 API Overview
 
| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| `POST` | `/auth/signup` | Register a new user | ❌ |
| `POST` | `/auth/login` | Authenticate and receive JWT | ❌ |
| `POST` | `/auth/forgot-password` | Request a password reset OTP | ❌ |
| `POST` | `/auth/reset-password` | Verify OTP and reset password | ❌ |
| `GET` | `/api/entries` | Fetch all journal entries for the user | ✅ |
| `POST` | `/api/entries` | Create a new journal entry | ✅ |
| `PUT` | `/api/entries/{id}` | Update a journal entry | ✅ |
| `DELETE` | `/api/entries/{id}` | Delete a journal entry | ✅ |
 
> 📌 Update this table to reflect your actual controller endpoints and route names.
 
---
 
## 🔒 Security Notes
 
- Passwords are hashed before storage (never stored in plaintext)
- JWT signing keys must be **256-bit minimum** (HMAC-SHA requirement)
- OTPs are single-use and expire after a configurable window
- Role checks are enforced at the endpoint level via Spring Security
---
 
## 🗺️ Roadmap
 
- [ ] Rate-limiting on OTP requests to prevent brute-force attempts
- [ ] Refresh token support
- [ ] Journal entry tagging & search
- [ ] Docker Compose setup for one-command local spin-up
- [ ] CI/CD pipeline via GitHub Actions
---
 
## 🤝 Contributing
 
Contributions are welcome. Please open an issue first to discuss what you'd like to change.
 
1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
---
 
## 📄 License
 
Distributed under the MIT License. See `LICENSE` for more information.
 
---
 
## 👤 Author
 
**Samrat**
🔗 [GitHub](#) · [LinkedIn](#) · [Portfolio](#)
 
---
 
<div align="center">
Made with care, coffee, and a lot of debugging ☕
</div>
 
