package com.isekai.ssgserver.member.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "member")
public class Memeber {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private long memberId;

	@Column(name = "account_id", nullable = false)
	private String accountId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "gender", nullable = false)
	private byte gender;

	@Column(name = "is_withdraw", nullable = false)
	private byte isWithdraw;

	@Column(name = "withdraw_at")
	private LocalDateTime withdrawAt;

	@Column(name = "credited_at", nullable = false)
	private LocalDateTime creditedAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updateAt;

	// @PrePersist
	// public void prePersist() {
	// 	this.withdrawAt = LocalDateTime.now();
	// 	this.updateAt = LocalDateTime.now();
	// }
}
