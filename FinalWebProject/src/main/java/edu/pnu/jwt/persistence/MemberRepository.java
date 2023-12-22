package edu.pnu.jwt.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.jwt.domain.Member;



public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByLoginId(String loginId);
	

}
