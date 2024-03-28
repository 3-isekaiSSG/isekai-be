package com.isekai.ssgserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.member.entity.WithdrawInfo;

public interface WithdrawRepository extends JpaRepository<WithdrawInfo, Long> {

}
