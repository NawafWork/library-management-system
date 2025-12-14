package com.library.repository;

import com.library.model.Librarian;
import com.library.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    
    Optional<Librarian> findByEmail(String email);
    
    List<Librarian> findByBranch(Branch branch);
    
    List<Librarian> findByPosition(String position);
    
    @Query("SELECT l FROM Librarian l WHERE l.librarianName LIKE CONCAT('%', :name, '%')")
    List<Librarian> searchByName(String name);
    
    @Query("SELECT l FROM Librarian l JOIN l.branch b WHERE b.branchName = :branchName")
    List<Librarian> findByBranchName(String branchName);
}
