-- Library Management System Sample Data
-- CMPSC-363 Database Project
-- Minimum 10 records per table
-- =============================================

USE library_db;

-- =============================================
-- Insert Branch Data (10 records)
-- =============================================
INSERT INTO branch (branch_name, address, phone) VALUES
('Downtown Central Library', '100 Main Street, Boston, MA 02108', '617-555-0100'),
('Beacon Hill Branch', '25 Beacon Street, Boston, MA 02108', '617-555-0101'),
('Back Bay Library', '200 Boylston Street, Boston, MA 02116', '617-555-0102'),
('South End Community Library', '450 Tremont Street, Boston, MA 02116', '617-555-0103'),
('Jamaica Plain Branch', '12 Sedgwick Street, Boston, MA 02130', '617-555-0104'),
('Roxbury Branch Library', '149 Dudley Street, Boston, MA 02119', '617-555-0105'),
('Charlestown Library', '179 Main Street, Boston, MA 02129', '617-555-0106'),
('East Boston Branch', '365 Bremen Street, Boston, MA 02128', '617-555-0107'),
('Brighton Library', '40 Academy Hill Road, Boston, MA 02135', '617-555-0108'),
('Dorchester Branch', '1520 Dorchester Ave, Boston, MA 02122', '617-555-0109');

SELECT * FROM branch;

-- =============================================
-- Insert Librarian Data (10 records)
-- =============================================
INSERT INTO librarian (librarian_name, email, position, branch_id) VALUES
('Sarah Johnson', 'sjohnson@library.org', 'Head Librarian', 1),
('Michael Chen', 'mchen@library.org', 'Senior Librarian', 1),
('Emily Rodriguez', 'erodriguez@library.org', 'Librarian', 2),
('David Kim', 'dkim@library.org', 'Head Librarian', 3),
('Jennifer Williams', 'jwilliams@library.org', 'Senior Librarian', 4),
('Robert Brown', 'rbrown@library.org', 'Librarian', 5),
('Lisa Martinez', 'lmartinez@library.org', 'Assistant', 6),
('James Taylor', 'jtaylor@library.org', 'Head Librarian', 7),
('Amanda White', 'awhite@library.org', 'Librarian', 8),
('Christopher Lee', 'clee@library.org', 'Senior Librarian', 9),
('Michelle Davis', 'mdavis@library.org', 'Librarian', 10),
('Kevin Anderson', 'kanderson@library.org', 'Assistant', 1);

SELECT * FROM librarian;

-- =============================================
-- Insert Book Data (15 records)
-- =============================================
INSERT INTO book (isbn, title, author, publisher, publication_year, genre) VALUES
('978-0-06-112008-4', 'To Kill a Mockingbird', 'Harper Lee', 'Harper Perennial', 1960, 'Fiction'),
('978-0-452-28423-4', '1984', 'George Orwell', 'Signet Classics', 1949, 'Science Fiction'),
('978-0-7432-7356-5', 'The Great Gatsby', 'F. Scott Fitzgerald', 'Scribner', 1925, 'Fiction'),
('978-0-316-76948-0', 'The Catcher in the Rye', 'J.D. Salinger', 'Little, Brown', 1951, 'Fiction'),
('978-0-14-028329-7', 'The Hobbit', 'J.R.R. Tolkien', 'Houghton Mifflin', 1937, 'Fantasy'),
('978-0-553-21311-7', 'Pride and Prejudice', 'Jane Austen', 'Bantam Classics', 1813, 'Romance'),
('978-0-06-093546-7', 'To Kill a Mockingbird Study Guide', 'SparkNotes', 'SparkNotes', 2002, 'Non-Fiction'),
('978-0-385-33348-1', 'Brave New World', 'Aldous Huxley', 'Harper Perennial', 1932, 'Science Fiction'),
('978-0-14-028334-1', 'The Lord of the Rings', 'J.R.R. Tolkien', 'Houghton Mifflin', 1954, 'Fantasy'),
('978-0-06-112241-5', 'Fahrenheit 451', 'Ray Bradbury', 'Simon & Schuster', 1953, 'Science Fiction'),
('978-0-14-118776-1', 'Crime and Punishment', 'Fyodor Dostoevsky', 'Penguin Classics', 1866, 'Fiction'),
('978-0-679-72020-1', 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 'Harper Perennial', 1967, 'Fiction'),
('978-0-06-093004-2', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008, 'Technology'),
('978-0-201-63361-0', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 1994, 'Technology'),
('978-0-13-468599-1', 'The Pragmatic Programmer', 'David Thomas', 'Addison-Wesley', 2019, 'Technology');

SELECT * FROM book;

-- =============================================
-- Insert Book Copy Data (20 records)
-- Demonstrates Many-to-Many between Book and Branch
-- =============================================
INSERT INTO book_copy (copy_number, status, shelf_location, book_id, branch_id) VALUES
(1, 'Available', 'A-101', 1, 1),
(2, 'Borrowed', 'A-101', 1, 1),
(1, 'Available', 'A-102', 1, 2),
(1, 'Available', 'B-201', 2, 1),
(2, 'Available', 'B-201', 2, 3),
(1, 'Borrowed', 'B-202', 3, 1),
(1, 'Available', 'C-301', 4, 2),
(2, 'Reserved', 'C-301', 4, 4),
(1, 'Available', 'D-401', 5, 1),
(2, 'Available', 'D-401', 5, 5),
(1, 'Available', 'E-501', 6, 3),
(1, 'Damaged', 'E-502', 6, 6),
(1, 'Available', 'F-601', 7, 1),
(1, 'Available', 'G-701', 8, 2),
(2, 'Borrowed', 'G-701', 8, 4),
(1, 'Available', 'H-801', 9, 1),
(1, 'Available', 'I-901', 10, 3),
(1, 'Available', 'J-1001', 11, 5),
(1, 'Available', 'K-1101', 13, 1),
(2, 'Available', 'K-1101', 13, 7);

SELECT * FROM book_copy;

-- =============================================
-- Insert Member Data (12 records)
-- =============================================
INSERT INTO member (member_name, email, phone, address, membership_date, membership_type) VALUES
('Alice Thompson', 'athompson@email.com', '617-555-1001', '123 Oak Street, Boston, MA 02108', '2023-01-15', 'Regular'),
('Bob Martinez', 'bmartinez@email.com', '617-555-1002', '456 Pine Avenue, Boston, MA 02116', '2023-02-20', 'Premium'),
('Carol Wilson', 'cwilson@email.com', '617-555-1003', '789 Maple Road, Boston, MA 02130', '2023-03-10', 'Student'),
('Daniel Garcia', 'dgarcia@email.com', '617-555-1004', '321 Elm Street, Boston, MA 02119', '2023-04-05', 'Regular'),
('Eva Nguyen', 'enguyen@email.com', '617-555-1005', '654 Cedar Lane, Boston, MA 02129', '2023-05-12', 'Premium'),
('Frank O''Brien', 'fobrien@email.com', '617-555-1006', '987 Birch Court, Boston, MA 02128', '2023-06-18', 'Student'),
('Grace Kim', 'gkim@email.com', '617-555-1007', '147 Willow Way, Boston, MA 02135', '2023-07-22', 'Regular'),
('Henry Smith', 'hsmith@email.com', '617-555-1008', '258 Spruce Drive, Boston, MA 02122', '2023-08-30', 'Premium'),
('Isabella Johnson', 'ijohnson@email.com', '617-555-1009', '369 Ash Boulevard, Boston, MA 02108', '2023-09-14', 'Student'),
('Jack Brown', 'jbrown@email.com', '617-555-1010', '741 Cherry Street, Boston, MA 02116', '2023-10-25', 'Regular'),
('Katie Lee', 'klee@email.com', '617-555-1011', '852 Poplar Avenue, Boston, MA 02130', '2023-11-08', 'Premium'),
('Liam Davis', 'ldavis@email.com', '617-555-1012', '963 Walnut Road, Boston, MA 02119', '2023-12-01', 'Student');

SELECT * FROM member;

-- =============================================
-- Insert Loan Data (15 records)
-- Demonstrates relationships between Member, BookCopy, and Librarian
-- =============================================
INSERT INTO loan (loan_date, due_date, return_date, fine_amount, status, member_id, copy_id, librarian_id) VALUES
('2024-01-05', '2024-01-19', '2024-01-18', 0.00, 'Returned', 1, 1, 1),
('2024-01-10', '2024-01-24', '2024-01-26', 0.50, 'Returned', 2, 4, 2),
('2024-01-15', '2024-01-29', NULL, 0.00, 'Active', 3, 2, 1),
('2024-01-20', '2024-02-03', '2024-02-01', 0.00, 'Returned', 4, 5, 3),
('2024-02-01', '2024-02-15', NULL, 0.00, 'Active', 5, 6, 4),
('2024-02-05', '2024-02-19', '2024-02-25', 1.50, 'Returned', 6, 7, 5),
('2024-02-10', '2024-02-24', NULL, 0.00, 'Active', 7, 9, 6),
('2024-02-15', '2024-03-01', '2024-02-28', 0.00, 'Returned', 8, 10, 7),
('2024-03-01', '2024-03-15', NULL, 0.00, 'Active', 9, 11, 8),
('2024-03-05', '2024-03-19', '2024-03-18', 0.00, 'Returned', 10, 13, 9),
('2024-03-10', '2024-03-24', NULL, 0.00, 'Active', 11, 14, 10),
('2024-03-15', '2024-03-29', NULL, 0.00, 'Active', 12, 15, 11),
('2024-03-20', '2024-04-03', '2024-04-10', 1.75, 'Returned', 1, 16, 1),
('2024-04-01', '2024-04-15', NULL, 0.00, 'Active', 2, 17, 2),
('2024-04-05', '2024-04-19', NULL, 0.00, 'Active', 3, 18, 3);

SELECT * FROM loan;

-- =============================================
-- Sample Queries for the Application
-- =============================================

-- Query 1: Find all available books at a specific branch
SELECT b.title, b.author, bc.copy_number, bc.shelf_location, br.branch_name
FROM book b
JOIN book_copy bc ON b.book_id = bc.book_id
JOIN branch br ON bc.branch_id = br.branch_id
WHERE br.branch_name = 'Downtown Central Library' AND bc.status = 'Available';

-- Query 2: Find all overdue loans
SELECT l.loan_id, m.member_name, b.title, l.loan_date, l.due_date
FROM loan l
JOIN member m ON l.member_id = m.member_id
JOIN book_copy bc ON l.copy_id = bc.copy_id
JOIN book b ON bc.book_id = b.book_id
WHERE l.status = 'Active' AND l.due_date < CURDATE();

-- Query 3: Get most borrowed books
SELECT b.title, b.author, COUNT(l.loan_id) as borrow_count
FROM book b
JOIN book_copy bc ON b.book_id = bc.book_id
JOIN loan l ON bc.copy_id = l.copy_id
GROUP BY b.book_id, b.title, b.author
ORDER BY borrow_count DESC
LIMIT 5;

-- Query 4: Find total fines by member
SELECT m.member_name, m.email, SUM(l.fine_amount) as total_fines
FROM member m
JOIN loan l ON m.member_id = l.member_id
GROUP BY m.member_id, m.member_name, m.email
HAVING total_fines > 0;

-- Query 5: Count books by genre
SELECT genre, COUNT(*) as book_count
FROM book
WHERE genre IS NOT NULL
GROUP BY genre
ORDER BY book_count DESC;

-- Query 6: List librarians with their branch and loan count
SELECT lib.librarian_name, lib.position, br.branch_name, COUNT(l.loan_id) as loans_processed
FROM librarian lib
JOIN branch br ON lib.branch_id = br.branch_id
LEFT JOIN loan l ON lib.librarian_id = l.librarian_id
GROUP BY lib.librarian_id, lib.librarian_name, lib.position, br.branch_name
ORDER BY loans_processed DESC;
