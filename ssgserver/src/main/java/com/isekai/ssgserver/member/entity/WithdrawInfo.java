package com.isekai.ssgserver.member.entity;

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
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Setter
@Getter
@Table(name = "withdraw_info")
public class WithdrawInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "withdraw_info_id")
	private Long withdrawInfoId;

	private String uuid;

	private String reason;

	public static WithdrawInfo toWithdrawInfoEntity(WithdrawInfoDto withdrawInfoDto) {
		WithdrawInfo withdrawInfo = new WithdrawInfo();
		withdrawInfo.setWithdrawInfoId(withdrawInfoDto.getWithdrawInfoId());
		withdrawInfo.setUuid(withdrawInfoDto.getUuid());
		withdrawInfo.setReason(withdrawInfoDto.getReason());
		return withdrawInfo;
	}
}
