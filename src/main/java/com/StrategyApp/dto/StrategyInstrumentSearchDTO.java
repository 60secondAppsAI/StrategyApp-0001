package com.StrategyApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StrategyInstrumentSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer strategyInstrumentId;
	
	private Integer strategyId;
	
	private Integer instrumentId;
	
	private Boolean active;
	
}
