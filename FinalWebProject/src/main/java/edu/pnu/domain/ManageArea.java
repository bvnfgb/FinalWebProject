package edu.pnu.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;
@Entity
@Getter
@ToString
public class ManageArea {
	@Column(nullable = false, updatable = false)
    private String city;

    @Column(updatable = false)
    private String city2;
    
    @Id
    @Column(nullable = false, updatable = false)
    private Integer cityId;
}
