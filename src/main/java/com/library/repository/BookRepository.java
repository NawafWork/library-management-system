package com.library.repository;

import com.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByAuthor(String author);
    
    List<Book> findByGenre(String genre);
    
    List<Book> findByPublicationYear(Integer year);
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :keyword, '%') OR b.author LIKE CONCAT('%', :keyword, '%')")
    List<Book> searchBooks(String keyword);
    
    @Query("SELECT DISTINCT b.genre FROM Book b WHERE b.genre IS NOT NULL ORDER BY b.genre")
    List<String> findAllGenres();
    
    @Query("SELECT DISTINCT b.author FROM Book b ORDER BY b.author")
    List<String> findAllAuthors();
    
    @Query("SELECT b FROM Book b WHERE b.publicationYear BETWEEN :startYear AND :endYear")
    List<Book> findByYearRange(Integer startYear, Integer endYear);
}
