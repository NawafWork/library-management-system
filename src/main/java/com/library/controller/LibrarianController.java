package com.library.controller;

import com.library.model.Librarian;
import com.library.model.Branch;
import com.library.repository.LibrarianRepository;
import com.library.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/librarians")
@CrossOrigin(origins = "*")
public class LibrarianController {
    
    @Autowired
    private LibrarianRepository librarianRepository;
    
    @Autowired
    private BranchRepository branchRepository;
    
    // GET all librarians
    @GetMapping
    public List<Librarian> getAllLibrarians() {
        return librarianRepository.findAll();
    }
    
    // GET librarian by ID
    @GetMapping("/{id}")
    public ResponseEntity<Librarian> getLibrarianById(@PathVariable Long id) {
        return librarianRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new librarian
    @PostMapping
    public ResponseEntity<Librarian> createLibrarian(@RequestBody Librarian librarian) {
        if (librarian.getBranch() != null && librarian.getBranch().getBranchId() != null) {
            Branch branch = branchRepository.findById(librarian.getBranch().getBranchId()).orElse(null);
            if (branch == null) {
                return ResponseEntity.badRequest().build();
            }
            librarian.setBranch(branch);
        }
        return ResponseEntity.ok(librarianRepository.save(librarian));
    }
    
    // PUT update librarian
    @PutMapping("/{id}")
    public ResponseEntity<Librarian> updateLibrarian(@PathVariable Long id, @RequestBody Librarian librarianDetails) {
        return librarianRepository.findById(id)
            .map(librarian -> {
                librarian.setLibrarianName(librarianDetails.getLibrarianName());
                librarian.setEmail(librarianDetails.getEmail());
                librarian.setPosition(librarianDetails.getPosition());
                if (librarianDetails.getBranch() != null && librarianDetails.getBranch().getBranchId() != null) {
                    Branch branch = branchRepository.findById(librarianDetails.getBranch().getBranchId()).orElse(null);
                    if (branch != null) {
                        librarian.setBranch(branch);
                    }
                }
                return ResponseEntity.ok(librarianRepository.save(librarian));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE librarian
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibrarian(@PathVariable Long id) {
        return librarianRepository.findById(id)
            .map(librarian -> {
                librarianRepository.delete(librarian);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET librarians by branch
    @GetMapping("/branch/{branchId}")
    public List<Librarian> getLibrariansByBranch(@PathVariable Long branchId) {
        Branch branch = branchRepository.findById(branchId).orElse(null);
        if (branch == null) return List.of();
        return librarianRepository.findByBranch(branch);
    }
    
    // SEARCH librarians by name
    @GetMapping("/search")
    public List<Librarian> searchLibrarians(@RequestParam String name) {
        return librarianRepository.searchByName(name);
    }
}
