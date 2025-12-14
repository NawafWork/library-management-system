package com.library.repository;

import com.library.model.Loan;
import com.library.model.Member;
import com.library.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    List<Loan> findByMember(Member member);
    
    List<Loan> findByBookCopy(BookCopy bookCopy);
    
    List<Loan> findByStatus(String status);
    
    @Query("SELECT l FROM Loan l WHERE l.dueDate < :today AND l.status = 'Active'")
    List<Loan> findOverdueLoans(LocalDate today);
    
    @Query("SELECT l FROM Loan l WHERE l.member.memberId = :memberId AND l.status = 'Active'")
    List<Loan> findActiveLoansByMember(Long memberId);
    
    @Query("SELECT l FROM Loan l WHERE l.loanDate BETWEEN :startDate AND :endDate")
    List<Loan> findLoansByDateRange(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT SUM(l.fineAmount) FROM Loan l WHERE l.member.memberId = :memberId")
    Double getTotalFinesByMember(Long memberId);
    
    @Query("SELECT l FROM Loan l WHERE l.bookCopy.book.bookId = :bookId")
    List<Loan> findLoansByBook(Long bookId);
    
    @Query("SELECT l.bookCopy.book.title, COUNT(l) as loanCount FROM Loan l GROUP BY l.bookCopy.book ORDER BY loanCount DESC")
    List<Object[]> getMostBorrowedBooks();
}
