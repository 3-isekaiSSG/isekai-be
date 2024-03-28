package com.isekai.ssgserver.member.entity;

import com.isekai.ssgserver.common.BaseEntity;
import com.isekai.ssgserver.member.dto.WithdrawInfoDto;

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
@Table(name = "withdraw_info")
public class WithdrawInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "withdraw_info_id")
	private Long withdrawInfoId;

	private String uuid;

	private String reason;

	public static WithdrawInfo toWithdrawInfoEntity(WithdrawInfoDto withdrawInfoDto) {
		return WithdrawInfo.builder()
			.withdrawInfoId(withdrawInfoDto.getWithdrawInfoId())
			.uuid(withdrawInfoDto.getUuid())
			.reason(withdrawInfoDto.getReason())
			.build();
	}

}
