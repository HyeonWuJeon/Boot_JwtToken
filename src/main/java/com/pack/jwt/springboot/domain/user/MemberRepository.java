package com.pack.jwt.springboot.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m FROM Member m where m.email =  :email ORDER BY m.id DESC")
    Member findEmailCheck(String email);

    @Query("SELECT m FROM Member m WHERE m.role ='USER' or m.role= 'GOOGLE' ORDER BY m.id DESC ")
    List<Member> findAllDesc();

    Optional<Member> findByEmail(String email);

}
