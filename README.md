# UnikForma Project

## Overview

UnikForma is a web application designed for managing learners, classes, and related operations. It provides a RESTful API for handling CRUD operations and ensures seamless interaction between different components of the system.

## Features

- **Learner Management**: Create, read, update, and delete learner records.
- **Class Management**: Manage classes, including adding, updating, and deleting class details.
- **Search Functionality**: Retrieve learners and classes based on various criteria.
- **Pagination**: Efficiently handle large data sets with pagination support.

## Tech Stack

- **Backend**: Java 8, Spring Framework (without Spring Boot)
- **Database**: PostgreSQL
- **ORM**: Hibernate JPA
- **API Documentation**: Swagger
- **Testing**: JUnit, Mockito
- **Build Tool**: Maven

## Getting Started

### Prerequisites

- Java 8 JDK
- Maven
- PostgreSQL
- IDE (e.g., IntelliJ IDEA)

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/unikForma.git
   cd unikForma


2. **Set Up PostgreSQL Database**

Create a new database for the project.
Update the database connection settings in application.properties.

3. **Build the Project**
   ```bash
    mvn clean install
    
4. **Run the Application**

- Use your IDE to run the main application class or run it using Maven:
   ```bash
    mvn spring-boot:run

## API Endpoints

### Learner Endpoints
- **Create a Learner**
    - `POST /learners`
    - Request Body: `LearnerDTO`
    - Response: `LearnerDTO`

- **Get All Learners**
    - `GET /learners`
    - Response: `List<LearnerDTO>`

- **Get Learner by ID**
    - `GET /learners/{id}`
    - Response: `LearnerDTO`

- **Update a Learner**
    - `PUT /learners/{id}`
    - Request Body: `LearnerDTO`
    - Response: `LearnerDTO`

- **Delete a Learner**
    - `DELETE /learners/{id}`
    - Response: `204 No Content`

### Class Endpoints
- **Create a Class**
    - `POST /classes`
    - Request Body: `ClasseDTO`
    - Response: `ClasseDTO`

- **Get All Classes**
    - `GET /classes?page={page}&size={size}`
    - Response: `Page<ClasseDTO>`

- **Get Class by ID**
    - `GET /classes/{id}`
    - Response: `ClasseDTO`

- **Update a Class**
    - `PUT /classes/{id}`
    - Request Body: `ClasseDTO`
    - Response: `ClasseDTO`

- **Delete a Class**
    - `DELETE /classes/{id}`
    - Response: `204 No Content`

- **Search Classes by Name**
    - `GET /classes/search?name={name}`
    - Response: `List<ClasseDTO>`

### Instructor Endpoints
- **Create an Instructor**
    - `POST /instructors`
    - Request Body: `InstructorDTO`
    - Response: `InstructorDTO`

- **Get All Instructors**
    - `GET /instructors`
    - Response: `List<InstructorDTO>`

- **Get Instructor by ID**
    - `GET /instructors/{id}`
    - Response: `InstructorDTO`

- **Update an Instructor**
    - `PUT /instructors/{id}`
    - Request Body: `InstructorDTO`
    - Response: `InstructorDTO`

- **Delete an Instructor**
    - `DELETE /instructors/{id}`
    - Response: `204 No Content`

### Course Endpoints
- **Create a Course**
    - `POST /courses`
    - Request Body: `CourseDTO`
    - Response: `CourseDTO`

- **Get All Courses**
    - `GET /courses?page={page}&size={size}`
    - Response: `Page<CourseDTO>`

- **Get Course by ID**
    - `GET /courses/{id}`
    - Response: `CourseDTO`

- **Update a Course**
    - `PUT /courses/{id}`
    - Request Body: `CourseDTO`
    - Response: `CourseDTO`

- **Delete a Course**
    - `DELETE /courses/{id}`
    - Response: `204 No Content`

- **Search Courses by Name**
    - `GET /courses/search?name={name}`
    - Response: `List<CourseDTO>`

## Testing
To run the tests, use the following command:

      mvn test
     
## API Documentation
API documentation is available via Swagger. To access it, visit:

    http://localhost:8080/swagger-ui.html
    
## Contributing
We welcome contributions to this project! To contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature/YourFeature).
3. Make your changes.
4. Commit your changes (git commit -m 'Add some feature').
5. Push to the branch (git push origin feature/YourFeature).
6. Create a new Pull Request.


## Contact
For any inquiries, please reach out to douaesebti33@gmail.com.

Nom: [Sebti Douae]
GitHub: https://github.com/Douaesb
