package com.isekai.ssgserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Favorite;

@Repository
public interface MemberFavoriteRepository extends JpaRepository<Favorite, Long> {
}
