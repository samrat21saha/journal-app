<div align="center">

# 🌙 Aura Journal

**A secure, full-stack journaling application built for reflection and privacy.**

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-47A248?style=flat-square&logo=mongodb)](https://www.mongodb.com/)
[![HTML5](https://img.shields.io/badge/HTML5-Frontend-E34F26?style=flat-square&logo=html5&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/HTML)
[![Tailwind](https://img.shields.io/badge/Tailwind-CSS-38B2AC?style=flat-square&logo=tailwindcss&logoColor=white)](https://tailwindcss.com/)
[![JWT](https://img.shields.io/badge/Auth-JWT-000000?style=flat-square&logo=jsonwebtokens)](https://jwt.io/)
[![License](https://img.shields.io/badge/License-MIT-blue?style=flat-square)](#license)

[Live Demo](#) · [Report Bug](#) · [Request Feature](#)

</div>

---

## 📖 About The Project

**Aura Journal** is a full-stack personal journaling application designed to give users a private, secure space to record their thoughts. Beyond core journaling functionality, the project was built to demonstrate production-grade backend engineering practices — stateless authentication, secure account recovery, role-based authorization, and resilient error handling.

This project was built as a hands-on deep dive into secure API design, evolving through two major architectural passes: migrating from link-based password resets to a time-bound OTP flow, and replacing HTTP Basic Auth with full JWT-based stateless authentication.

---

## 📸 Screenshots

<div align="center">

Home Page
<img width="1795" height="826" alt="Home page" src="https://github.com/user-attachments/assets/de4eeefe-9dd4-4b37-bdc2-679920614a17" />
Click on the Profile icon
<img width="1918" height="861" alt="Click on Profile icon" src="https://github.com/user-attachments/assets/ffc9999f-0c5b-47a2-b753-eb49821d232a" />
Signup and Login Button appears
<img width="1767" height="816" alt="Signup login button appears" src="https://github.com/user-attachments/assets/4ab2d373-e765-4d03-8472-51bd6ee930ff" />

Signup Page
<img width="1416" height="687" alt="Signup" src="https://github.com/user-attachments/assets/402e6c34-2654-4a5e-8372-ccf07c416bab" />
Login Page
<img width="1543" height="752" alt="Login" src="https://github.com/user-attachments/assets/40e6c1a9-87f5-4056-b06f-04df6f106b2d" />
Forgot Password Page
<img width="1447" height="730" alt="Forgot password" src="https://github.com/user-attachments/assets/87168f5b-b630-49f8-95d1-8a2aa66f9206" />
Password Reset Mail
<img width="1892" height="853" alt="Password reset mail" src="https://github.com/user-attachments/assets/4fabd9d6-e803-49a2-906c-575dfe68017e" />
Reset Password Page
<img width="1461" height="818" alt="Reset password page" src="https://github.com/user-attachments/assets/e0d9e2ce-ee53-48fd-bceb-974e26aef3ba" />
Journal Entry Page
<img width="1358" height="857" alt="Journal Entry page" src="https://github.com/user-attachments/assets/6089d5db-7999-4802-916e-c27274622036" />
Save Entry
<img width="1317" height="848" alt="Save Entry" src="https://github.com/user-attachments/assets/b2d33ccc-aebd-4897-a649-1bd1ae67c423" />
Edit and Delete Feature
https://github.com/user-attachments/assets/5f507a6f-59ca-49cf-8837-451b5b29ae15


</div>

---

## ✨ Features

- 🔐 **Stateless JWT Authentication** — Custom filter chain and token service for secure, scalable, session-less login
- 📧 **OTP-Based Password Recovery** — Time-bound 6-digit codes delivered via SMTP, replacing insecure reset links
- 👥 **Role-Based Access Control** — Distinct `USER` and `ADMIN` permission levels
- 📝 **Full Journal CRUD** — Create, read, update, and delete personal journal entries
- 🛡️ **Centralized Exception Handling** — Consistent, predictable API error responses
- 📊 **Structured Logging** — SLF4J logging throughout the API layer for full request/failure traceability
- 💻 **Responsive Frontend** — Clean UI built with HTML, CSS, Tailwind, and vanilla JavaScript, served as static resources from Spring Boot

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
| **HTML5** | Markup / structure |
| **CSS3** | Base styling |
| **Tailwind CSS** | Utility-first styling |
| **Vanilla JavaScript** | Client-side logic & API calls |

### Tooling
- **IntelliJ IDEA** — Primary IDE
- **MongoDB Compass** — Local database inspection
- **Postman / cURL** — API testing

---

## 🏗️ Architecture Overview

```
┌─────────────┐        HTTPS/JWT        ┌──────────────────┐        ┌─────────────┐
│ HTML + CSS +│  ────────────────────>  │   Spring Boot    │  ───>  │   MongoDB   │
│ JS Frontend │  <────────────────────  │   REST API       │  <───  │   Database  │
└─────────────┘                         └──────────────────┘        └─────────────┘
                                                  │
                                                  ▼
                                          ┌──────────────────┐
                                          │  SMTP (Email)    │
                                          │  OTP Delivery    │
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

3. **Run the application**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
   The frontend (`index.html`, static JS/CSS) is served directly by Spring Boot, so both frontend and backend run together at `http://localhost:8080` — no separate frontend server needed.

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

## 🌐 Deployment

Since the frontend is served as static resources directly from the Spring Boot app (not a separate build), the entire application deploys as a **single service** — no need to host frontend and backend separately.

**Suggested free-tier stack:**
- **App hosting:** [Render](https://render.com) (free web service) — deploy the Spring Boot JAR, which serves both the API and the static frontend
- **Database:** [MongoDB Atlas](https://www.mongodb.com/atlas) (free 512MB cluster)

> ⚠️ Render's free tier spins down after 15 minutes of inactivity, so the first request after idle time may take 30–60 seconds to respond.

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
