package com.StrategyApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class StrategyVersionDTO {

	private Integer strategyVersionId;

	private Integer strategyId;

	private String versionNumber;

	private Date createdAt;

	private String status;






}
