package com.StrategyApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseStrategyVersionDTO {

	private Integer ownerId;

	private Integer strategyVersionId;

	private Integer strategyVersionStatus;
	
	private Integer nextOrPrevious;
}

