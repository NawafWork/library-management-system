package com.library.repository;

import com.library.model.BookCopy;
import com.library.model.Book;
import com.library.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    
    List<BookCopy> findByBook(Book book);
    
    List<BookCopy> findByBranch(Branch branch);
    
    List<BookCopy> findByStatus(String status);
    
    List<BookCopy> findByBookAndBranch(Book book, Branch branch);
    
    @Query("SELECT bc FROM BookCopy bc WHERE bc.book.bookId = :bookId AND bc.status = 'Available'")
    List<BookCopy> findAvailableCopiesByBook(Long bookId);
    
    @Query("SELECT bc FROM BookCopy bc WHERE bc.branch.branchId = :branchId AND bc.status = 'Available'")
    List<BookCopy> findAvailableCopiesByBranch(Long branchId);
    
    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE bc.book.bookId = :bookId")
    Long countCopiesByBook(Long bookId);
    
    @Query("SELECT COUNT(bc) FROM BookCopy bc WHERE bc.book.bookId = :bookId AND bc.status = 'Available'")
    Long countAvailableCopiesByBook(Long bookId);
}
