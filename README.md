# ⚡ TaskFlow — Team Task Manager

A full-stack team task management application built with Spring Boot and vanilla HTML/CSS/JavaScript.

---

## 🚀 Live Demo

- **Frontend:** [Netlify](https://your-netlify-url.netlify.app)
- **Backend API:** [Railway](https://taskmanager-production-11c9.up.railway.app/api)

---

## 🛠 Tech Stack

**Backend**
- Java 17
- Spring Boot 3.4.1
- Spring Security + JWT Authentication
- Spring Data JPA + Hibernate
- PostgreSQL
- Maven

**Frontend**
- HTML5 / CSS3 / JavaScript (Vanilla — no frameworks)
- Deployed on Netlify

**Deployment**
- Backend → Railway
- Database → Railway PostgreSQL
- Frontend → Netlify

---

## 📁 Project Structure

```
taskmanager/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/taskmanager/taskmanager/
│   │   │   │   ├── config/          # Security config, CORS
│   │   │   │   ├── controller/      # REST API controllers
│   │   │   │   ├── dto/             # Request / Response objects
│   │   │   │   ├── entity/          # JPA entities
│   │   │   │   ├── exception/       # Global exception handler
│   │   │   │   ├── repository/      # JPA repositories
│   │   │   │   ├── security/        # JWT filter, user details
│   │   │   │   └── service/         # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   └── pom.xml
├── frontend/
│   └── index.html                   # Entire frontend in one file
├── README.md
└── .gitignore
```

---

## ⚙️ API Endpoints

### Auth
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/signup` | No | Register new user |
| POST | `/api/auth/login` | No | Login and get JWT token |

### Projects
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/projects` | Yes | Get all my projects |
| POST | `/api/projects` | Yes | Create a project |
| GET | `/api/projects/{id}` | Yes | Get project by ID |
| PUT | `/api/projects/{id}` | Yes (Admin) | Update project |
| DELETE | `/api/projects/{id}` | Yes (Owner) | Delete project |
| POST | `/api/projects/{id}/members` | Yes (Admin) | Add member |
| DELETE | `/api/projects/{id}/members/{userId}` | Yes (Admin) | Remove member |

### Tasks
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/tasks` | Yes | Get all my tasks |
| GET | `/api/tasks/my` | Yes | Get tasks assigned to me |
| GET | `/api/tasks/project/{id}` | Yes | Get tasks in a project |
| POST | `/api/tasks` | Yes | Create a task |
| PUT | `/api/tasks/{id}` | Yes | Update a task |
| PATCH | `/api/tasks/{id}/status?status=DONE` | Yes | Update task status only |
| DELETE | `/api/tasks/{id}` | Yes | Delete a task |

### Dashboard
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | `/api/dashboard` | Yes | Get dashboard stats |

---

## 🔐 Authentication

All protected routes require a Bearer token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

Get your token from the `/api/auth/login` response.

---

## 🏃 Running Locally

### Prerequisites
- Java 17+
- Maven 3.9+
- PostgreSQL 13+

### 1. Clone the repository

```bash
git clone https://github.com/Aryan150704/taskmanager.git
cd taskmanager
```

### 2. Create the database

```sql
CREATE DATABASE taskmanager;
```

### 3. Configure environment variables

Create `backend/src/main/resources/application.properties` with:

```properties
server.port=${PORT:8080}

spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

jwt.secret=your_jwt_secret_key_minimum_32_characters
jwt.expiration=86400000
```

### 4. Run the backend

```bash
cd backend
mvn spring-boot:run
```

Backend starts at `http://localhost:8080`

### 5. Run the frontend

Open `frontend/index.html` directly in your browser. Make sure line 1351 points to your local backend:

```javascript
const API_BASE = 'http://localhost:8080/api';
```

---

## ☁️ Deployment

### Backend → Railway

1. Push code to GitHub
2. Create new Railway service → connect GitHub repo
3. Set **Root Directory** to `backend`
4. Set **Custom Start Command** to:
   ```
   java -jar target/taskmanager-0.0.1-SNAPSHOT.jar
   ```
5. Add these environment variables in Railway:

| Variable | Value |
|----------|-------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://host:port/railway` |
| `SPRING_DATASOURCE_USERNAME` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | your db password |
| `JWT_SECRET` | any long random string |
| `JWT_EXPIRATION` | `86400000` |
| `PORT` | `8080` |

### Frontend → Netlify

1. Update `API_BASE` in `frontend/index.html` to your Railway URL
2. Drag and drop the `frontend` folder onto [netlify.com/drop](https://netlify.com/drop)
3. Or connect your GitHub repo and set publish directory to `frontend`

---

## ✨ Features

- **Authentication** — JWT-based signup and login with session persistence
- **Projects** — Create, edit, delete projects with role-based access (Owner / Admin / Member)
- **Tasks** — Full CRUD with status tracking (Todo → In Progress → Done)
- **Dashboard** — Live stats: total projects, tasks, completed count, overdue count
- **Overdue Detection** — Tasks past their due date are automatically flagged
- **Member Management** — Add/remove project members by email with role assignment
- **Search & Filter** — Filter tasks by status, project, or keyword in real time
- **Responsive UI** — Works on mobile with slide-in sidebar navigation
- **Keyboard Shortcuts** — `Ctrl+K` to create task, `Escape` to close modals

---

## 🔒 Security

- Passwords hashed with BCrypt
- JWT tokens expire after 24 hours (configurable via `JWT_EXPIRATION`)
- All endpoints except `/api/auth/**` require a valid JWT
- CORS configured to allow all origins (restrict in production as needed)
- Project data is isolated — users can only access projects they are members of

---

## 👤 Author

**Aryan Bhanwala**
- GitHub: [@Aryan150704](https://github.com/Aryan150704)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
