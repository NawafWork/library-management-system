package com.library.controller;

import com.library.model.Loan;
import com.library.model.Member;
import com.library.model.BookCopy;
import com.library.model.Librarian;
import com.library.repository.LoanRepository;
import com.library.repository.MemberRepository;
import com.library.repository.BookCopyRepository;
import com.library.repository.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "*")
public class LoanController {
    
    @Autowired
    private LoanRepository loanRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    @Autowired
    private LibrarianRepository librarianRepository;
    
    // GET all loans
    @GetMapping
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    
    // GET loan by ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        return loanRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new loan (borrow a book)
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        // Validate and set member
        if (loan.getMember() != null && loan.getMember().getMemberId() != null) {
            Member member = memberRepository.findById(loan.getMember().getMemberId()).orElse(null);
            if (member == null) return ResponseEntity.badRequest().build();
            loan.setMember(member);
        }
        
        // Validate and set book copy
        if (loan.getBookCopy() != null && loan.getBookCopy().getCopyId() != null) {
            BookCopy copy = bookCopyRepository.findById(loan.getBookCopy().getCopyId()).orElse(null);
            if (copy == null || !copy.getStatus().equals("Available")) {
                return ResponseEntity.badRequest().build();
            }
            loan.setBookCopy(copy);
            // Update book copy status to Borrowed
            copy.setStatus("Borrowed");
            bookCopyRepository.save(copy);
        }
        
        // Validate and set librarian
        if (loan.getLibrarian() != null && loan.getLibrarian().getLibrarianId() != null) {
            Librarian librarian = librarianRepository.findById(loan.getLibrarian().getLibrarianId()).orElse(null);
            if (librarian == null) return ResponseEntity.badRequest().build();
            loan.setLibrarian(librarian);
        }
        
        // Set default values
        if (loan.getLoanDate() == null) loan.setLoanDate(LocalDate.now());
        if (loan.getDueDate() == null) loan.setDueDate(LocalDate.now().plusWeeks(2));
        if (loan.getStatus() == null) loan.setStatus("Active");
        if (loan.getFineAmount() == null) loan.setFineAmount(0.0);
        
        return ResponseEntity.ok(loanRepository.save(loan));
    }
    
    // PUT update loan
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable Long id, @RequestBody Loan loanDetails) {
        return loanRepository.findById(id)
            .map(loan -> {
                loan.setDueDate(loanDetails.getDueDate());
                loan.setReturnDate(loanDetails.getReturnDate());
                loan.setFineAmount(loanDetails.getFineAmount());
                loan.setStatus(loanDetails.getStatus());
                return ResponseEntity.ok(loanRepository.save(loan));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // PUT return a book
    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        return loanRepository.findById(id)
            .map(loan -> {
                loan.setReturnDate(LocalDate.now());
                loan.setStatus("Returned");
                
                // Calculate fine if overdue
                if (loan.getDueDate().isBefore(LocalDate.now())) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
                    loan.setFineAmount(daysOverdue * 0.25); // $0.25 per day
                }
                
                // Update book copy status to Available
                BookCopy copy = loan.getBookCopy();
                copy.setStatus("Available");
                bookCopyRepository.save(copy);
                
                return ResponseEntity.ok(loanRepository.save(loan));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE loan
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable Long id) {
        return loanRepository.findById(id)
            .map(loan -> {
                loanRepository.delete(loan);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET loans by member
    @GetMapping("/member/{memberId}")
    public List<Loan> getLoansByMember(@PathVariable Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) return List.of();
        return loanRepository.findByMember(member);
    }
    
    // GET active loans
    @GetMapping("/active")
    public List<Loan> getActiveLoans() {
        return loanRepository.findByStatus("Active");
    }
    
    // GET overdue loans
    @GetMapping("/overdue")
    public List<Loan> getOverdueLoans() {
        return loanRepository.findOverdueLoans(LocalDate.now());
    }
    
    // GET most borrowed books
    @GetMapping("/popular")
    public List<Object[]> getMostBorrowedBooks() {
        return loanRepository.getMostBorrowedBooks();
    }
    
    // GET total fines by member
    @GetMapping("/fines/member/{memberId}")
    public ResponseEntity<Double> getTotalFines(@PathVariable Long memberId) {
        Double fines = loanRepository.getTotalFinesByMember(memberId);
        return ResponseEntity.ok(fines != null ? fines : 0.0);
    }
}
