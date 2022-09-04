package com.prj.travelRecord.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class EntityInfo {
	
	@Column(name="register_date")
	private LocalDateTime registerDate;
	@Column(name="register_id")
	private String registerId;
	@Column(name="update_date")
	private LocalDateTime updateDate;
	@Column(name="update_id")
	private String updateId;

}
