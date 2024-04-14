package com.isekai.ssgserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.WithdrawInfo;

@Repository
public interface WithdrawRepository extends JpaRepository<WithdrawInfo, Long> {

}
