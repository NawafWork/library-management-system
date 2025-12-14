package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "librarian")
public class Librarian {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "librarian_id")
    private Long librarianId;
    
    @Column(name = "librarian_name", nullable = false, length = 100)
    private String librarianName;
    
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;
    
    @Column(name = "position", length = 50)
    private String position;
    
    // Many-to-One: Many librarians work at one branch
    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
    
    // One-to-Many: A librarian processes many loans
    @OneToMany(mappedBy = "librarian")
    @JsonIgnore
    private List<Loan> processedLoans;
    
    // Constructors
    public Librarian() {}
    
    public Librarian(String librarianName, String email, String position, Branch branch) {
        this.librarianName = librarianName;
        this.email = email;
        this.position = position;
        this.branch = branch;
    }
    
    // Getters and Setters
    public Long getLibrarianId() { return librarianId; }
    public void setLibrarianId(Long librarianId) { this.librarianId = librarianId; }
    
    public String getLibrarianName() { return librarianName; }
    public void setLibrarianName(String librarianName) { this.librarianName = librarianName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    
    public Branch getBranch() { return branch; }
    public void setBranch(Branch branch) { this.branch = branch; }
    
    public List<Loan> getProcessedLoans() { return processedLoans; }
    public void setProcessedLoans(List<Loan> processedLoans) { this.processedLoans = processedLoans; }
}
