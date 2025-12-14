package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "branch")
public class Branch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;
    
    @Column(name = "branch_name", nullable = false, unique = true, length = 100)
    private String branchName;
    
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    // One-to-Many: A branch has many librarians
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Librarian> librarians;
    
    // One-to-Many: A branch has many book copies
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<BookCopy> bookCopies;
    
    // Constructors
    public Branch() {}
    
    public Branch(String branchName, String address, String phone) {
        this.branchName = branchName;
        this.address = address;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
    
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public List<Librarian> getLibrarians() { return librarians; }
    public void setLibrarians(List<Librarian> librarians) { this.librarians = librarians; }
    
    public List<BookCopy> getBookCopies() { return bookCopies; }
    public void setBookCopies(List<BookCopy> bookCopies) { this.bookCopies = bookCopies; }
}
