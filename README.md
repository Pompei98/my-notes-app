# My Notes App

Notes management application made with React/Vite (frontend) and Java Spring (backend).  
JWT token is implemented for security.

## Project Structure

- **frontend/**: React/Vite project
- **backend/**: Spring Boot project

## Requirements

- Node.js (frontend)
- Java 17+ and Maven (backend)
- PostgreSQL (database)

## Configuration

### Frontend

1. Copy the file `.env.example` and rename it to `.env`:
    ```bash
    cp frontend/.env.example frontend/.env
    ```

2. Replace the value of `VITE_API_URL` with your backend endpoint.

3. Install dependencies and start the frontend:
    ```bash
    cd frontend
    npm install
    npm run dev
    ```

### Backend

1. Rename `application-example.properties` to `application.properties`:
    ```bash
    cp backend/application-example.properties backend/src/main/resources/application.properties
    ```

2. Create the database using the `seed.sql` script in backend/db.
    you can open the file using DBeaver or any SQL client.

3. Edit `application.properties` to set your database credentials and other variables.

4. Start the backend:
    ```bash
    cd backend
    mvn spring-boot:run
    ```