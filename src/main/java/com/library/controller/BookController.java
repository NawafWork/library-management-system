package com.library.controller;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    // GET all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // GET book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }
    
    // PUT update book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookRepository.findById(id)
            .map(book -> {
                book.setIsbn(bookDetails.getIsbn());
                book.setTitle(bookDetails.getTitle());
                book.setAuthor(bookDetails.getAuthor());
                book.setPublisher(bookDetails.getPublisher());
                book.setPublicationYear(bookDetails.getPublicationYear());
                book.setGenre(bookDetails.getGenre());
                return ResponseEntity.ok(bookRepository.save(book));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE book
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id)
            .map(book -> {
                bookRepository.delete(book);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // SEARCH books
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String keyword) {
        return bookRepository.searchBooks(keyword);
    }
    
    // GET books by genre
    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return bookRepository.findByGenre(genre);
    }
    
    // GET books by author
    @GetMapping("/author/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.findByAuthor(author);
    }
    
    // GET all genres
    @GetMapping("/genres")
    public List<String> getAllGenres() {
        return bookRepository.findAllGenres();
    }
    
    // GET all authors
    @GetMapping("/authors")
    public List<String> getAllAuthors() {
        return bookRepository.findAllAuthors();
    }
    
    // GET book availability
    @GetMapping("/{id}/availability")
    public ResponseEntity<Map<String, Long>> getBookAvailability(@PathVariable Long id) {
        Map<String, Long> availability = new HashMap<>();
        availability.put("totalCopies", bookCopyRepository.countCopiesByBook(id));
        availability.put("availableCopies", bookCopyRepository.countAvailableCopiesByBook(id));
        return ResponseEntity.ok(availability);
    }
}
