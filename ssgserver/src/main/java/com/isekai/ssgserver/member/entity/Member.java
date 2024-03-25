package com.isekai.ssgserver.member.entity;

import java.time.LocalDateTime;

import com.isekai.ssgserver.member.dto.WithdrawMemberDto;

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
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Setter
@Table(name = "member")
public class Member {

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

	@Column(name = "withdraw_at")
	private LocalDateTime withdrawAt;

	@Column(name = "credited_at", nullable = false)
	private LocalDateTime creditedAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updateAt;

	public static Member toWithdrawMember(WithdrawMemberDto withdrawMemberDto) {
		LocalDateTime currentTime = LocalDateTime.now();

		Member member = new Member();
		member.setMemberId(withdrawMemberDto.getMemberId());
		member.setUuid(withdrawMemberDto.getUuid());
		member.setAccountId(withdrawMemberDto.getAccountId());
		member.setName(withdrawMemberDto.getName());
		member.setPassword(withdrawMemberDto.getPassword());
		member.setEmail(withdrawMemberDto.getEmail());
		member.setPhone(withdrawMemberDto.getPhone());
		member.setAddress(withdrawMemberDto.getAddress());
		member.setGender(withdrawMemberDto.getGender());
		member.setIsWithdraw((byte)1);
		member.setWithdrawAt(currentTime);
		member.setCreditedAt(withdrawMemberDto.getCreatedAt());
		member.setUpdateAt(currentTime);
		return member;
	}
}
