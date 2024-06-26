package com.isekai.ssgserver.deliveryAddress.entity;

import com.isekai.ssgserver.common.BaseEntity;

import com.isekai.ssgserver.deliveryAddress.dto.DeliveryAddressCreateDto;
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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
@SQLDelete(sql = "UPDATE delivery_address SET is_deleted = true WHERE delivery_address_id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "delivery_address")
public class DeliveryAddress extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_address_id")
	private Long deliveryAddressId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "cellphone", nullable = false)
	private String cellphone;

	@Column(name = "telephone")
	private String telephone;

	@Column(name = "zipcode", nullable = false)
	private String zipcode;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "is_default", nullable = false)
	private boolean isDefault;

	@Column(name = "is_deleted", nullable = false)
	private boolean isDeleted;

	@Column(name = "order_history", nullable = false)
	private boolean orderHistory;

	public void changeIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Builder
	public DeliveryAddress(Long memberId, String nickname, String name, String cellphone, String telephone,
		String zipcode,
		String address, boolean isDefault, boolean isDeleted, boolean orderHistory) {
		this.memberId = memberId;
		this.nickname = nickname;
		this.name = name;
		this.cellphone = cellphone;
		this.telephone = telephone;
		this.zipcode = zipcode;
		this.address = address;
		this.isDefault = isDefault;
		this.isDeleted = isDeleted;
		this.orderHistory = orderHistory;
	}

	public void update(DeliveryAddressCreateDto deliveryAddressCreateDto) {
		this.nickname = deliveryAddressCreateDto.getNickname();
		this.name = deliveryAddressCreateDto.getName();
		this.cellphone = deliveryAddressCreateDto.getCellphone();
		this.telephone = deliveryAddressCreateDto.getTelephone();
		this.zipcode = deliveryAddressCreateDto.getZipcode();
		this.address = deliveryAddressCreateDto.getAddress();
	}
}
