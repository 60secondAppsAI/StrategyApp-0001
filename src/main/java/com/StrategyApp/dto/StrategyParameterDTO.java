package com.StrategyApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class StrategyParameterDTO {

	private Integer strategyParameterId;

	private Integer strategyId;

	private String parameterName;

	private String parameterValue;

	private String dataType;






}
