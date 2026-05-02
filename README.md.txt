# Team Task Manager

A full-stack web application for managing projects, assigning tasks, tracking progress, and collaborating with team members using role-based access control.

## Project Overview

Team Task Manager helps organizations and teams efficiently manage their workflow by allowing users to:

- Create projects
- Add team members
- Assign tasks
- Update task status
- Track overdue tasks
- Monitor dashboard analytics
- Manage user roles (Admin / Member)

This application is built using Spring Boot, MySQL, HTML, CSS, and JavaScript.

---

## Features

### Authentication & Authorization
- User Registration
- User Login
- JWT Authentication
- Role-based access (ADMIN / MEMBER)
- Secure API access
- Logout functionality

### Project Management
- Create project
- View all projects
- Project details
- Track member count
- Track task count
- Deadline management

### Team Management
- Add members to project
- View project members
- Role assignment
- Member tracking

### Task Management
- Create task
- Assign task to user
- Priority levels:
  - LOW
  - MEDIUM
  - HIGH
- Status tracking:
  - PENDING
  - IN_PROGRESS
  - COMPLETED
- Due date tracking
- Overdue status detection
- Task update functionality

### Dashboard
- Total projects
- Total tasks
- Pending tasks
- In-progress tasks
- Completed tasks
- Overdue tasks

### Profile
- View logged-in user information
- Role display
- Session management

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- Maven

### Database
- MySQL

### Frontend
- HTML5
- CSS3
- JavaScript (Vanilla JS)

### Tools
- Postman
- Git
- GitHub
- Railway (Deployment)

---

## Project Structure

```bash
TeamTaskManager/
│
├── backend/
│   ├── src/main/java/com/divyanshu/teamtaskmanager/
│   │   ├── config/
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── security/
│   │   └── service/
│   │
│   └── src/main/resources/
│       └── application.properties
│
└── frontend/
    ├── index.html
    ├── login.html
    ├── register.html
    ├── dashboard.html
    ├── projects.html
    ├── tasks.html
    ├── members.html
    ├── profile.html
    │
    ├── css/
    ├── js/
    └── assets/
```

---

## Database Design

### User
- id
- name
- email
- password
- role
- active
- createdAt
- updatedAt

### Project
- id
- name
- description
- startDate
- deadline
- createdBy
- active

### Project Member
- id
- project
- user

### Task
- id
- title
- description
- priority
- status
- dueDate
- overdue
- progress
- assignedTo
- project

---

## API Endpoints

### Authentication
```http
POST /api/auth/register
POST /api/auth/login
```

### Dashboard
```http
GET /api/dashboard
```

### Projects
```http
POST /api/projects
GET /api/projects
POST /api/projects/{id}/members
GET /api/projects/{id}/members
```

### Tasks
```http
POST /api/tasks
GET /api/tasks
PATCH /api/tasks/{id}/status
GET /api/tasks/user/{id}
```

---

## Security
- JWT token authentication
- Protected APIs
- Stateless sessions
- Password encryption
- CORS configuration
- Role-based authorization

---

## Installation

### Backend Setup

Clone repository:

```bash
git clone <your-repo-url>
```

Go to backend:

```bash
cd backend
```

Run:

```bash
./mvnw spring-boot:run
```

Backend runs on:

```bash
http://localhost:8080
```

---

### Frontend Setup

Open frontend folder using Live Server.

Runs on:

```bash
http://127.0.0.1:5500
```

---

## Future Enhancements
- Email notifications
- Team chat
- File upload support
- Comments on tasks
- Kanban board
- Calendar integration
- Reports export
- Dark mode

---

## Author

**Divyanshu Kumar**

Full Stack Java Developer

---

## License

This project is developed for educational and assessment purposes.