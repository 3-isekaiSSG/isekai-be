package com.isekai.ssgserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
