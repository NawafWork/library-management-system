package com.library.controller;

import com.library.model.Branch;
import com.library.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
@CrossOrigin(origins = "*")
public class BranchController {
    
    @Autowired
    private BranchRepository branchRepository;
    
    // GET all branches
    @GetMapping
    public List<Branch> getAllBranches() {
        return branchRepository.findAllOrderByName();
    }
    
    // GET branch by ID
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return branchRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new branch
    @PostMapping
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepository.save(branch);
    }
    
    // PUT update branch
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branchDetails) {
        return branchRepository.findById(id)
            .map(branch -> {
                branch.setBranchName(branchDetails.getBranchName());
                branch.setAddress(branchDetails.getAddress());
                branch.setPhone(branchDetails.getPhone());
                return ResponseEntity.ok(branchRepository.save(branch));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE branch
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBranch(@PathVariable Long id) {
        return branchRepository.findById(id)
            .map(branch -> {
                branchRepository.delete(branch);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET librarian count by branch
    @GetMapping("/{id}/librarian-count")
    public ResponseEntity<Long> getLibrarianCount(@PathVariable Long id) {
        Long count = branchRepository.countLibrariansByBranch(id);
        return ResponseEntity.ok(count);
    }
}
