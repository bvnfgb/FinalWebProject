package edu.pnu.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
public class AwningLocationOnly {
	
	//
	@Column
	private Integer managementArea;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal latitude;
	
	@Column(nullable = false,columnDefinition = "decimal(10,7)")
	private BigDecimal longitude;
	
	@Column(length = 50,nullable = false,unique = true)
	private String installationLocationMemo;
	
	@Column(length = 20,nullable = false)
	private String managementNumber;
	
	@Column
	@Id
	
	private Integer awningId;
	
	@Column(unique = true
			,columnDefinition = "varchar(20) not null")
	
	private String deviceId;
	
	@Column(nullable = false,unique = true)
	private Integer controlId;
	
	@Column(columnDefinition = "varchar(10) not null default 'auto'")
	@Builder.Default
	private String statusOperationMode="auto";
}
