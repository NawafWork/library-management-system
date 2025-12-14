# Instructor Setup Guide - Library Management System

This guide provides step-by-step instructions for instructors to run this student project.

## Prerequisites

Before you begin, ensure you have:

1. **Java 17 or higher** - [Download here](https://adoptium.net/)
   ```bash
   java -version  # Should show version 17+
   ```

2. **Apache Maven 3.6+** - [Download here](https://maven.apache.org/download.cgi)
   ```bash
   mvn -version  # Should show Maven version
   ```

3. **MySQL Server 8.0+** or **MariaDB 10.5+** - [Download here](https://dev.mysql.com/downloads/mysql/)
   ```bash
   mysql --version  # Should show MySQL version
   ```

---

## Quick Start (5 Minutes)

### Step 1: Clone the Repository

```bash
git clone https://github.com/NawafWork/library-management-system.git
cd library-management-system
```

### Step 2: Set Up MySQL Database

Login to MySQL as root (or any user with database creation privileges):

```bash
mysql -u root -p
```

Run the schema and sample data scripts:

```sql
source sql/schema.sql;
source sql/sample_data.sql;
exit;
```

**Note:** The `schema.sql` file automatically creates a database called `library_db` with all required tables.

### Step 3: Configure Database Credentials

**Option A: Using Environment Variables (Recommended)**

Set these environment variables in your terminal:

**Linux/macOS:**
```bash
export DB_URL="jdbc:mysql://localhost:3306/library_db"
export DB_USERNAME="root"
export DB_PASSWORD="your_mysql_password"
```

**Windows (Command Prompt):**
```cmd
set DB_URL=jdbc:mysql://localhost:3306/library_db
set DB_USERNAME=root
set DB_PASSWORD=your_mysql_password
```

**Windows (PowerShell):**
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/library_db"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_mysql_password"
```

**Option B: Using Local Properties File**

If you prefer not to use environment variables:

1. Copy the example properties file:
   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application-local.properties
   ```

2. Edit `application-local.properties` and update your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/library_db
   spring.datasource.username=root
   spring.datasource.password=your_actual_password
   ```

3. Run with the local profile:
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

### Step 4: Run the Application

From the project root directory:

```bash
mvn spring-boot:run
```

You should see output like:
```
Started LibraryManagementApplication in X.XXX seconds
```

### Step 5: Access the Application

Open your web browser and navigate to:

```
http://localhost:8080
```

You should see the Library Management System dashboard.

---

## Testing the Application

The application comes with sample data. You can test the following features:

1. **Dashboard** - View statistics and quick navigation
2. **Branches** - View/Add/Edit/Delete library branches
3. **Librarians** - Manage library staff
4. **Books** - Browse and search the book catalog
5. **Members** - Manage library members
6. **Loans** - Create book loans and process returns
7. **Overdue** - View overdue books and fines

### Sample Data

After running `sql/sample_data.sql`, you'll have:
- 3 library branches
- 6 librarians
- 15 books with multiple copies
- 10 library members
- Various active and returned loans

---

## Troubleshooting

### Issue: "Access denied for user 'root'@'localhost'"

**Solution:** Your MySQL password is incorrect. Update it in your environment variables or properties file.

### Issue: "Unknown database 'library_db'"

**Solution:** The database wasn't created. Run the schema script:
```bash
mysql -u root -p < sql/schema.sql
```

### Issue: "Port 8080 is already in use"

**Solution:** Another application is using port 8080. Either:
- Stop the other application, or
- Change the port in `src/main/resources/application.properties`:
  ```properties
  server.port=8081
  ```

### Issue: "Cannot find Maven command"

**Solution:** Maven is not installed or not in your PATH. Install Maven and ensure it's added to your system PATH.

### Issue: "Unsupported class file major version 61"

**Solution:** You're using an older Java version. This project requires Java 17+. Update your Java installation.

---

## Verifying the Database

To verify the database was set up correctly:

```bash
mysql -u root -p
```

```sql
USE library_db;
SHOW TABLES;  -- Should show 6 tables
SELECT COUNT(*) FROM book;  -- Should show 15 books
SELECT COUNT(*) FROM member;  -- Should show 10 members
```

---

## API Testing (Optional)

You can test the REST API endpoints using curl:

```bash
# Get all branches
curl http://localhost:8080/api/branches

# Get all books
curl http://localhost:8080/api/books

# Search books
curl "http://localhost:8080/api/books/search?keyword=Java"

# Get overdue loans
curl http://localhost:8080/api/loans/overdue
```

---

## Stopping the Application

Press `Ctrl+C` in the terminal where the application is running.

---

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
└── pom.xml                      # Maven configuration
```

---

## Database Schema

The system implements the following entity-relationship model:

**Entities:**
1. Branch - Library branches/locations
2. Librarian - Library staff members
3. Book - Book catalog (master records)
4. BookCopy - Physical copies of books at branches
5. Member - Library members/patrons
6. Loan - Book borrowing transactions

**Relationships:**
- Branch 1:N Librarian
- Branch 1:N BookCopy
- Book 1:N BookCopy
- Member 1:N Loan
- BookCopy 1:N Loan
- Librarian 1:N Loan

All tables are in 3NF (Third Normal Form).

---

## Grading Notes

**What's Implemented:**
- ✅ Complete database schema in 3NF
- ✅ 6 entity tables with proper relationships
- ✅ Foreign key constraints with CASCADE/RESTRICT
- ✅ RESTful API for all entities
- ✅ Full CRUD operations
- ✅ Web interface with Thymeleaf templates
- ✅ Search and filter functionality
- ✅ Automatic fine calculation
- ✅ Overdue tracking
- ✅ Sample data for testing

**Technology Stack:**
- Backend: Spring Boot 3.2.0 with Spring Data JPA
- Database: MySQL 8.0+ compatible
- Frontend: HTML5, CSS3, JavaScript, Thymeleaf
- Build Tool: Maven

---

## Support

If you encounter any issues not covered in this guide, please check:
- Java version: `java -version` (must be 17+)
- Maven version: `mvn -version` (must be 3.6+)
- MySQL service is running: `sudo systemctl status mysql` (Linux) or check Services (Windows)
- Database connection settings match your MySQL configuration

---

## Student Information

**Author:** Nawaf Alshahrani
**Course:** CMPSC-363 Database Systems
**Semester:** Fall 2025
