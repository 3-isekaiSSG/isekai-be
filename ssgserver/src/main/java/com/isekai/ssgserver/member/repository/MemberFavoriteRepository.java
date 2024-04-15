package com.isekai.ssgserver.member.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.member.entity.Favorite;

@Repository
public interface MemberFavoriteRepository extends JpaRepository<Favorite, Long> {
	Long countByDivisionAndUuid(byte division, String uuid);

	@Query("SELECT COUNT(f.favoriteId) FROM Favorite f WHERE (f.division = :division1 OR f.division = :division2) AND f.uuid = :uuid")
	Long countByDivisionEqualsOrDivisionEqualsAndUuid(@Param("division1") byte division1,
		@Param("division2") byte division2, @Param("uuid") String uuid);

	boolean existsByUuidAndDivisionAndIdentifier(String uuid, byte division, Long identifier);

	Optional<Favorite> findByFavoriteIdAndUuid(Long favoriteId, String uuid);

	@Query("SELECT f.favoriteId, f.division, f.identifier FROM Favorite f WHERE f.uuid = :uuid AND (f.division = 2 OR f.division = 3)")
	Page<Object[]> findByUuidCategory(@Param("uuid") String uuid, Pageable pageable);

	@Query("SELECT f.favoriteId, f.division, f.identifier FROM Favorite f WHERE f.uuid = :uuid AND f.division = 4")
	Page<Object[]> findByUuidSellrer(@Param("uuid") String uuid, Pageable pageable);

	@Query("SELECT f.favoriteId, f.division, f.identifier FROM Favorite f WHERE f.uuid = :uuid AND (f.division = 0 OR f.division = 1)")
	Page<Object[]> findByUuidProduct(@Param("uuid") String uuid, Pageable pageable);

	@Query("SELECT f FROM Favorite f WHERE f.uuid = :uuid AND f.identifier = :identifier AND f.division = :division")
	Optional<Favorite> findByUuidAndIdentifierAndDivision(@Param("uuid") String uuid,
		@Param("identifier") String identifier, @Param("division") byte division);
}
