package com.StrategyApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StrategyParameterSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer strategyParameterId;
	
	private Integer strategyId;
	
	private String parameterName;
	
	private String parameterValue;
	
	private String dataType;
	
}
