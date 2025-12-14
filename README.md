# Library Management System

## CMPSC-363 Database Systems Project

A full-stack web application for managing library operations built with Java Spring Boot and MySQL.

## Features

- **Dashboard**: Overview with statistics and quick navigation
- **Branch Management**: CRUD operations for library branches
- **Librarian Management**: Staff management with branch assignments
- **Book Catalog**: Book management with search functionality
- **Member Registration**: Member management with different membership types
- **Loan Management**: Book borrowing and returns with automatic fine calculation
- **Overdue Tracking**: Monitor and manage overdue books

## Technology Stack

- **Backend**: Java Spring Boot 3.x with Spring Data JPA
- **Database**: MySQL (MariaDB compatible)
- **Frontend**: HTML5, CSS3, JavaScript with Thymeleaf templates
- **API**: RESTful web services

## Entity Sets

1. **Branch** - Library branches/locations
2. **Librarian** - Library staff members
3. **Book** - Book catalog (master records)
4. **BookCopy** - Physical copies of books at branches
5. **Member** - Library members/patrons
6. **Loan** - Book borrowing transactions

## Relationships

- Branch 1:N Librarian (A branch has many librarians)
- Branch 1:N BookCopy (A branch has many book copies)
- Book 1:N BookCopy (A book has many copies)
- Member 1:N Loan (A member can have many loans)
- BookCopy 1:N Loan (A copy can have many loan records)
- Librarian 1:N Loan (A librarian processes many loans)

## Database Setup

1. Install MySQL Server
2. Run the schema script:
```sql
source sql/schema.sql
```
3. Load sample data:
```sql
source sql/sample_data.sql
```

## Configuration

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

### Using Maven
```bash
mvn spring-boot:run
```

### Using IDE
Run `LibraryManagementApplication.java`

### Access
Open browser: http://localhost:8080

## API Endpoints

### Branches
- `GET /api/branches` - Get all branches
- `GET /api/branches/{id}` - Get branch by ID
- `POST /api/branches` - Create new branch
- `PUT /api/branches/{id}` - Update branch
- `DELETE /api/branches/{id}` - Delete branch

### Books
- `GET /api/books` - Get all books
- `GET /api/books/search?keyword={q}` - Search books
- `GET /api/books/genre/{genre}` - Get books by genre
- `POST /api/books` - Create new book
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book

### Members
- `GET /api/members` - Get all members
- `GET /api/members/search?name={name}` - Search members
- `POST /api/members` - Create new member
- `PUT /api/members/{id}` - Update member
- `DELETE /api/members/{id}` - Delete member

### Loans
- `GET /api/loans` - Get all loans
- `GET /api/loans/active` - Get active loans
- `GET /api/loans/overdue` - Get overdue loans
- `POST /api/loans` - Create new loan
- `PUT /api/loans/{id}/return` - Return a book
- `DELETE /api/loans/{id}` - Delete loan

## Project Structure

```
library-management-system/
├── src/
│   └── main/
│       ├── java/com/library/
│       │   ├── controller/       # REST Controllers
│       │   ├── model/            # JPA Entities
│       │   ├── repository/       # JPA Repositories
│       │   └── LibraryManagementApplication.java
│       └── resources/
│           ├── static/css/       # Stylesheets
│           ├── templates/        # Thymeleaf templates
│           └── application.properties
├── sql/
│   ├── schema.sql               # Database schema
│   └── sample_data.sql          # Sample data
├── pom.xml                      # Maven configuration
└── README.md
```

## Screenshots

The application includes a modern web interface with:
- Dashboard with statistics and quick navigation
- Responsive tables for all entities
- Modal forms for adding/editing records
- Real-time search functionality
- Status badges and visual indicators

## Author

Nawaf Alshahrani  
CMPSC-363 Database Systems  
Fall 2025
