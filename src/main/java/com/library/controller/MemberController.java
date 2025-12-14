package com.library.controller;

import com.library.model.Member;
import com.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private MemberRepository memberRepository;
    
    // GET all members
    @GetMapping
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    // GET member by ID
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST create new member
    @PostMapping
    public Member createMember(@RequestBody Member member) {
        return memberRepository.save(member);
    }
    
    // PUT update member
    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member memberDetails) {
        return memberRepository.findById(id)
            .map(member -> {
                member.setMemberName(memberDetails.getMemberName());
                member.setEmail(memberDetails.getEmail());
                member.setPhone(memberDetails.getPhone());
                member.setAddress(memberDetails.getAddress());
                member.setMembershipDate(memberDetails.getMembershipDate());
                member.setMembershipType(memberDetails.getMembershipType());
                return ResponseEntity.ok(memberRepository.save(member));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE member
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMember(@PathVariable Long id) {
        return memberRepository.findById(id)
            .map(member -> {
                memberRepository.delete(member);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // SEARCH members by name
    @GetMapping("/search")
    public List<Member> searchMembers(@RequestParam String name) {
        return memberRepository.searchByName(name);
    }
    
    // GET members by membership type
    @GetMapping("/type/{type}")
    public List<Member> getMembersByType(@PathVariable String type) {
        return memberRepository.findByMembershipType(type);
    }
    
    // GET active loan count for member
    @GetMapping("/{id}/active-loans")
    public ResponseEntity<Long> getActiveLoanCount(@PathVariable Long id) {
        Long count = memberRepository.countActiveLoans(id);
        return ResponseEntity.ok(count);
    }
    
    // GET members with overdue books
    @GetMapping("/overdue")
    public List<Member> getMembersWithOverdueBooks() {
        return memberRepository.findMembersWithOverdueBooks();
    }
}
