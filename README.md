# 💬 Group Chat Application

The project is deployed on an Amazon EC2 instance and can be accessed at:

http://13.60.19.69:8070/

## 🎯 Aim
This project aims to explore and learn key concepts in modern web application development, including:

- Java Spring framework and its project structure
- Real-time communication using WebSockets
- Containerization techniques
- MongoDB for data persistence
- Building a group chat application as a practical implementation of these concepts

Through the development of this real-time group chat application, the project serves as a hands-on learning experience for understanding the integration and application of these technologies in a full-stack environment.

## ✨ Features
- 🚀 Real-time messaging using WebSocket
- 🚪 User join and leave notifications
- 💾 Message persistence using MongoDB
- 🐳 Dockerized application for easy deployment
- 📜 Fetching of stored messages when a user joins the chat

## 🛠️ Tech Stack
- ☕ Java Spring Boot
- 🔌 WebSocket (STOMP)
- 🍃 MongoDB
- 🐳 Docker

## 🏗️ Setup

### Prerequisites
- Java JDK 11 or higher
- Maven
- Docker and Docker Compose

### Configuration Files

#### 📄 application.properties
Create an `application.properties` file in the `src/main/resources` directory with the following content:

```properties
server.port=8061
spring.data.mongodb.uri=mongodb+srv://[username]:[password]@[your-cluster-url]/Group_Chat?retryWrites=true&w=majority
```

Replace `[username]`, `[password]`, and `[your-cluster-url]` with your actual MongoDB credentials and cluster URL.

#### 🐳 Dockerfile
Create a `Dockerfile` in the root directory of your project with the following content:

```dockerfile
FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/your-application-name.jar app.jar

EXPOSE 8061

ENTRYPOINT ["java", "-jar", "app.jar"]
```

Replace `your-application-name.jar` with the actual name of your JAR file.

#### 🐳 docker-compose.yml
Create a `docker-compose.yml` file in the root directory of your project with the following content:

```yaml
version: '3.8'
services:
  java-app:
    build: .
    container_name: group_chat_app
    ports:
      - "8061:8061"  # Map the container's port 8061 to the host's port 8061
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb+srv://jaydoshi:JOhGSGe3bnv51Bmo@cluster0.99n0tun.mongodb.net/Group_Chat?retryWrites=true&w=majority  # MongoDB URI for local MongoDB (if used)
    depends_on:
      - mongo-db  # Ensures MongoDB starts before the app
    networks:
      - chat-network

  mongo-db:
    image: mongo:latest
    container_name: mongodb
    environment:
      - MONGO_INITDB_ROOT_USERNAME=root
      - MONGO_INITDB_ROOT_PASSWORD=example
      - MONGO_INITDB_DATABASE=Group_Chat
    ports:
      - "27017:27017"  # Expose MongoDB
    volumes:
      - mongo-data:/data/db
    networks:
      - chat-network

networks:
  chat-network:
    driver: bridge

volumes:
  mongo-data:
    driver: local
```

### 🚀 Steps to Run

1. Clone the repository:
   ```
   git clone [your-repo-url]
   cd [your-repo-name]
   ```

2. Build the application:
   ```
   mvn clean package
   ```

3. Run using Docker Compose:
   ```
   docker-compose up --build
   ```

4. Access the application at `http://localhost:8061`

## 📁 Project Structure

```
GROUP_CHAT
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── jd
│   │   │           └── websocket
│   │   │               ├── ChatApplication.java
│   │   │               └── chatc
│   │   │                   ├── ChatController.java
│   │   │                   ├── ChatMessage.java
│   │   │                   ├── ChatMessageEntity.java
│   │   │                   ├── ChatMessageRepository.java
│   │   │                   └── MessageType.java
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│   └── test
│       └── java
│           └── com
│               └── jd
│                   └── chat
│                       └── ChatApplicationTests.java
├── .gitignore
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## 🖥️ Usage

1. Open the application in a web browser.
2. Enter your username to join the chat.
3. Start sending and receiving messages in real-time.
4. When you join, you'll see previous messages stored in the database.

## 🔒 Security Note

Please ensure to keep your MongoDB credentials secure and do not expose them in public repositories. The `application.properties` file and `docker-compose.yml` file containing sensitive information should be added to your `.gitignore` file.

## 🤝 Contributing

Feel To Contribute in this Project.
