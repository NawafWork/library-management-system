package com.library.repository;

import com.library.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    
    Optional<Branch> findByBranchName(String branchName);
    
    List<Branch> findByAddressContaining(String address);
    
    @Query("SELECT b FROM Branch b ORDER BY b.branchName")
    List<Branch> findAllOrderByName();
    
    @Query("SELECT COUNT(l) FROM Librarian l WHERE l.branch.branchId = :branchId")
    Long countLibrariansByBranch(Long branchId);
}
