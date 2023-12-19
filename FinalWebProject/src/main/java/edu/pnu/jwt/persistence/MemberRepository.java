package edu.pnu.jwt.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.jwt.domain.Member;



public interface MemberRepository extends JpaRepository<Member, String> {

}
