package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.MemberSocial;

@Repository
public interface SocialRepository extends JpaRepository<MemberSocial, Long> {
	Optional<MemberSocial> findByMemberSocialCode(String socialCode);
}
