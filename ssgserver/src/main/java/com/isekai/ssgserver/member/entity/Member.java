package com.isekai.ssgserver.member.entity;

import com.isekai.ssgserver.common.BaseEntity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
@AllArgsConstructor
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Column(unique = true, nullable = false, updatable = false)
	private String uuid;

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

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public void setIsWithdraw(byte b) {
		this.isWithdraw = b;
	}
}
