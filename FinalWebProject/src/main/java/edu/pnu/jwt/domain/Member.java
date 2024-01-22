
package edu.pnu.jwt.domain;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
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
@Table(name="user")
public class Member {
	//DB에 저장되지않는 임시항목 2개
	@Transient
	private String managementArea1;
	@Transient
	private String managementArea2;
	
	
	
	@Id 
    private Integer userId;

    @Column(length = 20, nullable = false,unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String loginPassword;

    @Column(length = 10, nullable = false)
    private String depart;

    @Column(length = 5, nullable = false)
    private String rank_a;

    @Column(length = 5, nullable = false)
    private String manager;

    @Column
    @Min(-1)
    private Integer contact;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ManageType manageType;

    @Column(columnDefinition = "varchar(10) default 'enable'", nullable = false)
    @Builder.Default
    private String userStat="on";

    @Column(nullable = false,
    		columnDefinition = "timestamp default current_timestamp")
    @Builder.Default
    private Timestamp userCreateDate=new Timestamp(System.currentTimeMillis()); 

    @Column(nullable = false)
    private Integer manageArea;
    
    @Column(nullable = false,columnDefinition = "varchar(10)")
    private String admnsType;
}