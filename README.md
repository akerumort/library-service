<h1 align="center" id="title">library-service</h1>

<p align="center"><img src="https://socialify.git.ci/akerumort/library-service/image?description=1&font=Jost&language=1&name=1&owner=1&pattern=Diagonal%20Stripes&theme=Dark"></p>

LibraryService is a complex service designed for effective management of authors and books. This service allows you to create, update and retrieve detailed information about authors and their associated books. 
The application also includes robust error checking and handling mechanisms to ensure data stability and consistency.

## ⚙ Features

- CRUD operations for managing authors and books
- Integration with PostgreSQL database
- Flyway for database migration
- Swagger/OpenAPI for API documentation
- Exception handling and validation
- Docker support for containerized deployment
- Unit testing

## 🌐 API Endpoints & description

### Authors:
- `GET /authors`: Retrieve all authors. 
- `GET /authors/{id}`: Retrieve a specific author by ID.

- `POST /authors`: Create a new author. If books are specified, they must be valid books in the system.
- `PUT /authors/{id}`: Update an existing author. If bookIds are not specified, the existing list of books remains unchanged. If bookIds are specified, they replace the existing book list.
- `DELETE /authors/{id}`: Delete an author. When an author is deleted, all books have that author deleted.
- `DELETE /authors`: Delete all authors.
- `POST /authors/{id}/add-books`: Add books to an author. Passed bookIds to authors who did not have books specified at creation.

### Books:
- `GET /books`: Retrieve all books.
- `GET /books/{id}`: Retrieve a specific book by ID.

- `POST /books`: Create a new book. The book must be associated with valid authors.
- `PUT /books/{id}`: Update an existing book. If authorIds are not specified, the existing author list remains unchanged. If authorIds are specified, they replace the existing author list.
- `DELETE /books/{id}`: Delete a book. When a book is deleted, all authors associated with that book are retained, but the authors' book information is updated.
- `DELETE /books`: Delete all books.


## 💻 Used technologies

- **Java 17**
- **Spring Boot 3.3.1**
- **Spring Data JPA**
- **Maven**
- **PostgreSQL 16.4**
- **Flyway 10.0.0**
- **MapStruct 1.5.0**
- **SpringDoc OpenAPI 2.5.0**
- **Log4j 2.23.1**
- **Hibernate Validator 8.0.0**
- **JUnit 5**
- **Mockito**
- **Docker & Docker Compose**

## 🐋 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/akerumort/library-service.git
   cd library-service
2. Build and start the containers:
   ```bash
   docker-compose up --build
3. The application will start on http://localhost:8080

## ⌨️ Testing

- Run the tests using Maven:
    ```bash
    mvn test
## 📝 API Documentation

- Available on:
   ```bash
   http://localhost:8080/swagger-ui/index.html

## 🛡️ License
This project is licensed under the MIT License. See the `LICENSE` file for more details.

## ✉️ Contact
For any questions or inquiries, please contact `akerumort404@gmail.com`.
