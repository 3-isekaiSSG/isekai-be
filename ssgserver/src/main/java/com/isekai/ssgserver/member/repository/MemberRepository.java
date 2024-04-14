package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByUuid(String uuid);

	Optional<Member> findByAccountId(String accountId);

	Optional<Member> findByPhone(String phone);

	Optional<Member> findByUuidAndIsWithdraw(String uuid, byte isWithdraw);

	boolean existsByUuidAndIsWithdraw(String uuid, byte isWithdraw);
}
