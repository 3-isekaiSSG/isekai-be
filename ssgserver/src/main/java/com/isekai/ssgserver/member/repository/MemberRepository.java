package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isekai.ssgserver.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	@Query("SELECT m FROM Member m WHERE m.uuid = :uuid")
	Optional<Member> findByUuid(@Param("uuid") String uuid);
}
