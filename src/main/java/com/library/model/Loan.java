package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;
    
    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;
    
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "return_date")
    private LocalDate returnDate;
    
    @Column(name = "fine_amount")
    private Double fineAmount;
    
    @Column(name = "status", nullable = false, length = 20)
    private String status; // Active, Returned, Overdue
    
    // Many-to-One: Many loans by one member
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    
    // Many-to-One: Many loans of one book copy
    @ManyToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private BookCopy bookCopy;
    
    // Many-to-One: Many loans processed by one librarian
    @ManyToOne
    @JoinColumn(name = "librarian_id", nullable = false)
    private Librarian librarian;
    
    // Constructors
    public Loan() {}
    
    public Loan(LocalDate loanDate, LocalDate dueDate, String status, 
                Member member, BookCopy bookCopy, Librarian librarian) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = status;
        this.member = member;
        this.bookCopy = bookCopy;
        this.librarian = librarian;
        this.fineAmount = 0.0;
    }
    
    // Getters and Setters
    public Long getLoanId() { return loanId; }
    public void setLoanId(Long loanId) { this.loanId = loanId; }
    
    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    
    public Double getFineAmount() { return fineAmount; }
    public void setFineAmount(Double fineAmount) { this.fineAmount = fineAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Member getMember() { return member; }
    public void setMember(Member member) { this.member = member; }
    
    public BookCopy getBookCopy() { return bookCopy; }
    public void setBookCopy(BookCopy bookCopy) { this.bookCopy = bookCopy; }
    
    public Librarian getLibrarian() { return librarian; }
    public void setLibrarian(Librarian librarian) { this.librarian = librarian; }
}
