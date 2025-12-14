package com.library.repository;

import com.library.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    
    Optional<Member> findByEmail(String email);
    
    List<Member> findByMembershipType(String membershipType);
    
    @Query("SELECT m FROM Member m WHERE m.memberName LIKE CONCAT('%', :name, '%')")
    List<Member> searchByName(String name);
    
    @Query("SELECT m FROM Member m WHERE m.membershipDate >= :date")
    List<Member> findMembersJoinedAfter(LocalDate date);
    
    @Query("SELECT COUNT(l) FROM Loan l WHERE l.member.memberId = :memberId AND l.status = 'Active'")
    Long countActiveLoans(Long memberId);
    
    @Query("SELECT m FROM Member m JOIN m.loans l WHERE l.status = 'Overdue' GROUP BY m")
    List<Member> findMembersWithOverdueBooks();
}
