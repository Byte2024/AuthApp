# AuthApp

## Project Name

## Overview
AuthApp is a robust authentication application designed to streamline user management and authentication processes. The project provides secure authentication mechanisms, user role management, and integrates with JWT (JSON Web Tokens) for token-based authentication. Key features include:

User registration and authentication
JWT-based token generation and validation
Role-based access control
Secure password storage

## Prerequisites

Before building or running the project, ensure you have the following installed:

Java 8 or higher
Maven 3.6.0 or higher

## Dependencies

This project relies on the following dependencies:

io.jsonwebtoken:jjwt-api:0.12.3 - API for JWT creation and parsing
io.jsonwebtoken:jjwt-impl:0.12.3 - Implementation of JWT API
io.jsonwebtoken:jjwt-jackson:0.12.3 - Jackson integration for JSON processing
org.testng:testng:7.6.1 - Testing framework (JUnit excluded)

## Building the Project

To build the project, follow these steps:

Open your terminal or command prompt.
Navigate to the project directory.
Run the following command to clean and install the project:
sh
Copy code
mvn clean install

## Setting Up the Git Repository

If you're setting up a local repository and want to push your project to GitHub, use the following commands:

1. Initialize the repository (if not already initialized):
cd existing_repo
git init

2. Add the remote repository:
git remote add origin https://github.com/Byte2024/AuthApp.git

3. Create and switch to the main branch:
git branch -M main

4. Push the local repository to GitHub:
git push -uf origin main
