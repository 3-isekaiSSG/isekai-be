package com.isekai.ssgserver.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Favorite;

@Repository
public interface MemberFavoriteRepository extends JpaRepository<Favorite, Long> {
	byte countByDivision(byte division);

	@Query("SELECT COUNT(f) FROM Favorite f WHERE f.division = :division1 OR f.division = :division2")
	Long countByDivisionIn(@Param("division1") byte division1, @Param("division2") byte division2);

	Long countByDivisionEqualsOrDivisionEquals(byte division1, byte division2);

	boolean existsByUuidAndDivisionAndIdentifier(String uuid, byte division, Long identifier);
}
