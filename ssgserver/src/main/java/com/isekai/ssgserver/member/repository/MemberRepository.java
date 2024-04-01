package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	@Query("SELECT m FROM Member m WHERE m.uuid = :uuid")
	Optional<Member> findByUuid(@Param("uuid") String uuid);

	@Query("SELECT m FROM Member m WHERE m.phone = :phoneNum")
	Member findByPhone(@Param("phoneNum") String phoneNum);
}
