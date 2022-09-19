package com.prj.travelRecord.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TR_SPOT_CODE")
public class TravelCode {

	@Id
	@Column(name="rnum")
	private int rnum;
	@Column(name="spot_code")
	private String spotCode;
	@Column(name="spot_name")
	private String SpotName;
	@Override
	public String toString() {
		return "TravelCode [spotCode=" + spotCode + ", SpotName=" + SpotName + ", rnum=" + rnum + "]";
	}		
	
}
