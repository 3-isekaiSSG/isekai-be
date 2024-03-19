package com.isekai.ssgserver.product.entity;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.isekai.ssgserver.member.entity.Memeber;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Table(name = "recent_view")
public class RecentView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recent_view_id")
	private long recentViewId;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Memeber member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
}
