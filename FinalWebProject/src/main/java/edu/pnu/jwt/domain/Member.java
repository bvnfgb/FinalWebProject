package edu.pnu.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
	private Integer userId;
	private String  loginId;
	private String  loginPassword;
	private String  manageArea;
	private String  depart;
	private String  rankA;
	private String  manager;
	private Integer contact;
	private String  manageType;
	private String  userStat;
	private String  userCreateDate;
	
}
