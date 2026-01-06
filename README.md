# Todolist API

A study project developed during an introductory Java with Spring Boot course from [Rocketseat](https://app.rocketseat.com.br/). This project allows users to register and manage their tasks with title, description, priority, and schedule.

## 🚀 Live Demo

The application is deployed and available at:
[https://todolist-wimmer.onrender.com](https://todolist-wimmer.onrender.com)

## 🛠️ Technologies

- **Java 17**
- **Spring Boot 3.5.9**
- **Spring Data JPA**
- **H2 Database** (In-memory)
- **Lombok**
- **BCrypt** (for password hashing)
- **Maven**

## 🏗️ Project Structure

```text
src/main/java/com/carloswimmer/todolist/
├── user/          # User management (Model, Controller, Repository)
├── task/          # Task management (Model, Controller, Repository)
├── filter/        # Authentication filters
├── errors/        # Global exception handling
├── dto/           # Data Transfer Objects for API responses
└── utils/         # Helper classes (e.g., ObjectMerger)
```

## ⚙️ How to Run Locally

### Prerequisites

- JDK 17 or higher
- Maven 3.x

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/todolist.git
   cd todolist
   ```

2. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```
   The server will start at `http://localhost:8080`.

## 🗄️ Database (H2)

The project uses an H2 in-memory database for development.

- **Console URL:** `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:todolist`
- **Username:** `admin`
- **Password:** `admin`

## 📡 API Endpoints

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/users/` | Register a new user |

**Body:**
```json
{
  "username": "johndoe",
  "name": "John Doe",
  "password": "secretpassword"
}
```

### Tasks

All task endpoints require **Basic Authentication** (use the username and password registered).

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/tasks/` | Create a new task |
| GET    | `/tasks/` | List all tasks for the authenticated user |
| PUT    | `/tasks/{id}` | Update an existing task |

**Task Creation Body:**
```json
{
  "title": "Learn Spring Boot",
  "description": "Study JPA and Security",
  "priority": "High",
  "startAt": "2026-01-10T09:00:00",
  "endAt": "2026-01-10T11:00:00"
}
```

## ☁️ Deployment

This project is configured for deployment on [Render](https://render.com). It includes a `Dockerfile` for containerized deployment.

To deploy your own version:
1. Connect your GitHub repository to Render.
2. Select "Web Service".
3. Render will automatically detect the `Dockerfile` and build the application.

## 📋 Future Improvements

Detailed plans for architectural enhancements, new features, and technical debt are documented in the [TODOS.md](TODOS.md) file.

---
Developed by [Carlos Wimmer](https://github.com/carloswimmer).

