package edu.pnu.jwt.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.jwt.domain.Member;
import java.util.List;




public interface MemberRepository extends JpaRepository<Member, Integer> {

	Optional<Member> findByLoginId(String loginId);
	void deleteByLoginId(String loginId);

}
