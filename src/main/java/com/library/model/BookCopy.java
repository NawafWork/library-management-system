package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_copy")
public class BookCopy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "copy_id")
    private Long copyId;
    
    @Column(name = "copy_number", nullable = false)
    private Integer copyNumber;
    
    @Column(name = "status", nullable = false, length = 20)
    private String status; // Available, Borrowed, Reserved, Damaged
    
    @Column(name = "shelf_location", length = 20)
    private String shelfLocation;
    
    // Many-to-One: Many copies belong to one book
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    
    // Many-to-One: Many copies are at one branch
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    
    // One-to-Many: A copy can have many loan records
    @OneToMany(mappedBy = "bookCopy")
    @JsonIgnore
    private List<Loan> loans;
    
    // Constructors
    public BookCopy() {}
    
    public BookCopy(Integer copyNumber, String status, String shelfLocation, Book book, Branch branch) {
        this.copyNumber = copyNumber;
        this.status = status;
        this.shelfLocation = shelfLocation;
        this.book = book;
        this.branch = branch;
    }
    
    // Getters and Setters
    public Long getCopyId() { return copyId; }
    public void setCopyId(Long copyId) { this.copyId = copyId; }
    
    public Integer getCopyNumber() { return copyNumber; }
    public void setCopyNumber(Integer copyNumber) { this.copyNumber = copyNumber; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getShelfLocation() { return shelfLocation; }
    public void setShelfLocation(String shelfLocation) { this.shelfLocation = shelfLocation; }
    
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
    
    public List<Loan> getLoans() { return loans; }
    public void setLoans(List<Loan> loans) { this.loans = loans; }
}
