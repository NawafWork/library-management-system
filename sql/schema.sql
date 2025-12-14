-- Library Management System Database Schema
-- CMPSC-363 Database Project
-- =============================================

-- Drop database if exists and create new
DROP DATABASE IF EXISTS library_db;
CREATE DATABASE library_db DEFAULT CHARACTER SET=utf8mb4;
USE library_db;

-- =============================================
-- Table: branch
-- Description: Library branches/locations
-- Keys: branch_id (PK), branch_name (UNIQUE)
-- Functional Dependencies: branch_id -> branch_name, address, phone
-- Normal Form: 3NF (no transitive dependencies)
-- =============================================
DROP TABLE IF EXISTS branch;
CREATE TABLE branch (
    branch_id INT NOT NULL AUTO_INCREMENT,
    branch_name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    PRIMARY KEY (branch_id),
    UNIQUE KEY uk_branch_name (branch_name)
);

-- =============================================
-- Table: librarian
-- Description: Library staff members
-- Keys: librarian_id (PK), email (UNIQUE)
-- Foreign Keys: branch_id -> branch(branch_id)
-- Functional Dependencies: librarian_id -> librarian_name, email, position, branch_id
-- Normal Form: 3NF
-- =============================================
DROP TABLE IF EXISTS librarian;
CREATE TABLE librarian (
    librarian_id INT NOT NULL AUTO_INCREMENT,
    librarian_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    position VARCHAR(50),
    branch_id INT NOT NULL,
    PRIMARY KEY (librarian_id),
    UNIQUE KEY uk_librarian_email (email),
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

-- =============================================
-- Table: book
-- Description: Book catalog (master records)
-- Keys: book_id (PK), isbn (UNIQUE)
-- Functional Dependencies: book_id -> isbn, title, author, publisher, publication_year, genre
-- Normal Form: 3NF
-- =============================================
DROP TABLE IF EXISTS book;
CREATE TABLE book (
    book_id INT NOT NULL AUTO_INCREMENT,
    isbn VARCHAR(20) NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    publication_year INT,
    genre VARCHAR(50),
    PRIMARY KEY (book_id),
    UNIQUE KEY uk_book_isbn (isbn)
);

-- =============================================
-- Table: book_copy
-- Description: Physical copies of books at branches
-- Keys: copy_id (PK)
-- Foreign Keys: book_id -> book(book_id), branch_id -> branch(branch_id)
-- Functional Dependencies: copy_id -> copy_number, status, shelf_location, book_id, branch_id
-- Normal Form: 3NF
-- Relationship: Many-to-Many between Book and Branch (resolved via this table)
-- =============================================
DROP TABLE IF EXISTS book_copy;
CREATE TABLE book_copy (
    copy_id INT NOT NULL AUTO_INCREMENT,
    copy_number INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Available',
    shelf_location VARCHAR(20),
    book_id INT NOT NULL,
    branch_id INT NOT NULL,
    PRIMARY KEY (copy_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (branch_id) REFERENCES branch(branch_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CHECK (status IN ('Available', 'Borrowed', 'Reserved', 'Damaged'))
);

-- =============================================
-- Table: member
-- Description: Library members/patrons
-- Keys: member_id (PK), email (UNIQUE)
-- Functional Dependencies: member_id -> member_name, email, phone, address, membership_date, membership_type
-- Normal Form: 3NF
-- =============================================
DROP TABLE IF EXISTS member;
CREATE TABLE member (
    member_id INT NOT NULL AUTO_INCREMENT,
    member_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    membership_date DATE NOT NULL,
    membership_type VARCHAR(20) DEFAULT 'Regular',
    PRIMARY KEY (member_id),
    UNIQUE KEY uk_member_email (email),
    CHECK (membership_type IN ('Regular', 'Premium', 'Student'))
);

-- =============================================
-- Table: loan
-- Description: Book borrowing transactions
-- Keys: loan_id (PK)
-- Foreign Keys: member_id -> member(member_id), 
--               copy_id -> book_copy(copy_id),
--               librarian_id -> librarian(librarian_id)
-- Functional Dependencies: loan_id -> loan_date, due_date, return_date, fine_amount, status, member_id, copy_id, librarian_id
-- Normal Form: 3NF
-- Relationship: Links Member, BookCopy, and Librarian (Many-to-Many resolved)
-- =============================================
DROP TABLE IF EXISTS loan;
CREATE TABLE loan (
    loan_id INT NOT NULL AUTO_INCREMENT,
    loan_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE,
    fine_amount DECIMAL(10,2) DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'Active',
    member_id INT NOT NULL,
    copy_id INT NOT NULL,
    librarian_id INT NOT NULL,
    PRIMARY KEY (loan_id),
    FOREIGN KEY (member_id) REFERENCES member(member_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (copy_id) REFERENCES book_copy(copy_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (librarian_id) REFERENCES librarian(librarian_id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    CHECK (status IN ('Active', 'Returned', 'Overdue'))
);

-- Create indexes for better query performance
CREATE INDEX idx_book_author ON book(author);
CREATE INDEX idx_book_genre ON book(genre);
CREATE INDEX idx_loan_status ON loan(status);
CREATE INDEX idx_loan_due_date ON loan(due_date);
CREATE INDEX idx_book_copy_status ON book_copy(status);
