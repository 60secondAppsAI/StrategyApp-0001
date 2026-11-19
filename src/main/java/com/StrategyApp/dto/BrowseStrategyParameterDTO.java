package com.StrategyApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseStrategyParameterDTO {

	private Integer ownerId;

	private Integer strategyParameterId;

	private Integer strategyParameterStatus;
	
	private Integer nextOrPrevious;
}

