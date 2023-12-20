
package edu.pnu.jwt.domain;

import java.sql.Timestamp;

import org.antlr.v4.runtime.misc.NotNull;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(length = 20, nullable = false)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String loginPassword;

    @Column(length = 10, nullable = false)
    private String depart;

    @Column(length = 5, nullable = false)
    private String rank_a;

    @Column(length = 5, nullable = false)
    private String manager;

    @Column(nullable = false)
    private Integer contact;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ManageType manageType;

    @Column(columnDefinition = "varchar(10) default 'enable'", nullable = false)
    private String userStat;

    @Column(nullable = false,
    		columnDefinition = "timestamp default current_timestamp")
    private Timestamp userCreateDate;

    @Column(nullable = false)
    
    private Integer manageArea;
}