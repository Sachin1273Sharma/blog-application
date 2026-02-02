# Blog Application

## Project Overview

The Blog Application is a web application designed to manage blog posts and comments. It features CRUD (Create, Read, Update, Delete) operations, pagination, search, filtering, and sorting functionalities. This project is built using Spring Boot, Spring Data JPA, and Spring Security, with Thymeleaf used for server-side templating in its initial phase.

## Technologies Used

- **Spring Boot**: Core framework for building the application.
- **Spring Data JPA**: For database connectivity and ORM.
- **Spring Security**: For handling authentication and authorization.
- **Thymeleaf**: For server-side templating.

## Project Structure
```

├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── io
│   │   │       └── mountblue
│   │   │           └── blog_application_project
│   │   │               ├── BlogApplicationProjectApplication.java
│   │   │               ├── config
│   │   │               │   └── SecurityConfig.java
│   │   │               ├── controller
│   │   │               │   ├── CommentController.java
│   │   │               │   ├── PostController.java
│   │   │               │   └── UserController.java
│   │   │               ├── entity
│   │   │               │   ├── Comment.java
│   │   │               │   ├── Post.java
│   │   │               │   ├── Tag.java
│   │   │               │   └── User.java
│   │   │               ├── exception
│   │   │               │   └── GlobalExceptionHandler.java
│   │   │               ├── repository
│   │   │               │   ├── CommentRepository.java
│   │   │               │   ├── PostRepository.java
│   │   │               │   ├── TagRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               └── service
│   │   │                   ├── CommentService.java
│   │   │                   ├── PostService.java
│   │   │                   ├── TagService.java
│   │   │                   └── UserService.java
│   │   └── resources
│   │       ├── application.properties
│   │       ├── static
│   │       │   └── css
│   │       │       ├── create-post.css
│   │       │       ├── denied.css
│   │       │       ├── error.css
│   │       │       ├── home.css
│   │       │       ├── post.css
│   │       │       ├── update-comment.css
│   │       │       └── update-post.css
│   │       └── templates
│   │           ├── access-denied.html
│   │           ├── create-post.html
│   │           ├── error.html
│   │           ├── home.html
│   │           ├── login.html
│   │           ├── post.html
│   │           ├── register.html
│   │           ├── update-comment.html
│   │           └── update-post.html
│   └── test
│       └── java
│           └── io
│               └── mountblue
│                   └── blog_application_project
│                       └── BlogApplicationProjectApplicationTests.java

```

## Features

### Part 1: CRUD Operations

- **Read Blog Posts**:
    - Users can view a list of blog posts, including title, excerpt, author, published date, and tags.
- **View Full Post**:
    - Users can read the full content of a blog post, including title, content, author, published date, and tags.
- **Create Post**:
    - Users can create a new blog post with title, content, author, published date, and tags.
- **Update Post**:
    - Users can update an existing blog post.
- **Delete Post**:
    - Users can delete a blog post.
- **Filter Posts**:
    - Users can filter blog posts by author, published date, and tags.
- **Sort Posts**:
    - Users can sort blog posts by published date.
- **Search Posts**:
    - Users can search blog posts using a full-text search on title, content, author, and tags.
- **Pagination**:
    - Users can navigate through pages of blog posts, with each page displaying a maximum of 10 blog posts.

### Part 2: Authentication & Authorization

- **Authentication**:
    - Implement user login and logout functionality.
- **Authorization**:
    - Different access levels for users, including:
        - **Authors**: Can create, update, and delete their own posts and comment on posts.
        - **Admins**: Can manage all posts and comments, regardless of the author.
        - **Non Logged-In Users**: Can view posts and comments but cannot modify them.

### Part 3: Deployment

- **Deployment on Render**:
    - Instructions and configuration for deploying the application to Render.
- **Deployment on Railway**:
    - Instructions and configuration for deploying the application to Railway.

## Database Schema

- **User**
    - `id`
    - `name`
    - `email`
    - `password`

- **Posts**
    - `id`
    - `title`
    - `excerpt`
    - `content`
    - `author`
    - `published_at`
    - `is_published`
    - `created_at`
    - `updated_at`

- **Tags**
    - `id`
    - `name`
    - `created_at`
    - `updated_at`

- **Post_Tags**
    - `post_id`
    - `tag_id`
    - `created_at`
    - `updated_at`

- **Comments**
    - `id`
    - `name`
    - `email`
    - `comment`
    - `post_id`
    - `created_at`
    - `updated_at`

## Steps to Develop

1. **Initial Setup**
    - Design the HTML and CSS for the application.
    - Create and configure the database schema.

2. **Implementation**
    - Replace static HTML & CSS with Thymeleaf templates.
    - Implement CRUD operations using Spring Data JPA.
    - Integrate pagination, filtering, sorting, and search functionalities.

3. **Authentication and Authorization (Part 2)**
    - Implement user authentication and authorization.
    - Restrict and grant access based on user roles.

4. **Deployment (Part 3)**
    - Deploy the application to Railway and Render.

## Running the Application

- Ensure the database is set up and configured.
- Build and run the Spring Boot application.
- Access the application through the endpoints.
---
Thank you for exploring the Blog Application. We hope it serves as a solid foundation for your own projects and helps you build a powerful blogging platform









