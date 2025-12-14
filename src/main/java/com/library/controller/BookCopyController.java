package com.library.controller;

import com.library.model.BookCopy;
import com.library.model.Book;
import com.library.model.Branch;
import com.library.repository.BookCopyRepository;
import com.library.repository.BookRepository;
import com.library.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/copies")
@CrossOrigin(origins = "*")
public class BookCopyController {
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BranchRepository branchRepository;
    
    // GET all copies
    @GetMapping
    public List<BookCopy> getAllCopies() {
        return bookCopyRepository.findAll();
    }
    
    // GET copy by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookCopy> getCopyById(@PathVariable Long id) {
        return bookCopyRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new copy
    @PostMapping
    public ResponseEntity<BookCopy> createCopy(@RequestBody BookCopy bookCopy) {
        if (bookCopy.getBook() != null && bookCopy.getBook().getBookId() != null) {
            Book book = bookRepository.findById(bookCopy.getBook().getBookId()).orElse(null);
            if (book == null) return ResponseEntity.badRequest().build();
            bookCopy.setBook(book);
        }
        if (bookCopy.getBranch() != null && bookCopy.getBranch().getBranchId() != null) {
            Branch branch = branchRepository.findById(bookCopy.getBranch().getBranchId()).orElse(null);
            if (branch == null) return ResponseEntity.badRequest().build();
            bookCopy.setBranch(branch);
        }
        return ResponseEntity.ok(bookCopyRepository.save(bookCopy));
    }
    
    // PUT update copy
    @PutMapping("/{id}")
    public ResponseEntity<BookCopy> updateCopy(@PathVariable Long id, @RequestBody BookCopy copyDetails) {
        return bookCopyRepository.findById(id)
            .map(copy -> {
                copy.setCopyNumber(copyDetails.getCopyNumber());
                copy.setStatus(copyDetails.getStatus());
                copy.setShelfLocation(copyDetails.getShelfLocation());
                if (copyDetails.getBook() != null && copyDetails.getBook().getBookId() != null) {
                    Book book = bookRepository.findById(copyDetails.getBook().getBookId()).orElse(null);
                    if (book != null) copy.setBook(book);
                }
                if (copyDetails.getBranch() != null && copyDetails.getBranch().getBranchId() != null) {
                    Branch branch = branchRepository.findById(copyDetails.getBranch().getBranchId()).orElse(null);
                    if (branch != null) copy.setBranch(branch);
                }
                return ResponseEntity.ok(bookCopyRepository.save(copy));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE copy
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCopy(@PathVariable Long id) {
        return bookCopyRepository.findById(id)
            .map(copy -> {
                bookCopyRepository.delete(copy);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET copies by book
    @GetMapping("/book/{bookId}")
    public List<BookCopy> getCopiesByBook(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book == null) return List.of();
        return bookCopyRepository.findByBook(book);
    }
    
    // GET copies by branch
    @GetMapping("/branch/{branchId}")
    public List<BookCopy> getCopiesByBranch(@PathVariable Long branchId) {
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (branch == null) return List.of();
        return bookCopyRepository.findByBranch(branch);
    }
    
    // GET available copies
    @GetMapping("/available")
    public List<BookCopy> getAvailableCopies() {
        return bookCopyRepository.findByStatus("Available");
    }
    
    // GET available copies by book
    @GetMapping("/available/book/{bookId}")
    public List<BookCopy> getAvailableCopiesByBook(@PathVariable Long bookId) {
        return bookCopyRepository.findAvailableCopiesByBook(bookId);
    }
}
