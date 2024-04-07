package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Favorite;

@Repository
public interface MemberFavoriteRepository extends JpaRepository<Favorite, Long> {
	Long countByDivision(byte division);

	Long countByDivisionEqualsOrDivisionEquals(byte division1, byte division2);

	boolean existsByUuidAndDivisionAndIdentifier(String uuid, byte division, Long identifier);

	Optional<Favorite> findByFavoriteIdAndUuid(Long favoriteId, String uuid);
}
