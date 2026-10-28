package com.StrategyApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseStrategyDTO {

	private Integer ownerId;

	private Integer strategyId;

	private Integer strategyStatus;
	
	private Integer nextOrPrevious;
}

