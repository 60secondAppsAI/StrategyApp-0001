package com.StrategyApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseStrategyInstrumentDTO {

	private Integer ownerId;

	private Integer strategyInstrumentId;

	private Integer strategyInstrumentStatus;
	
	private Integer nextOrPrevious;
}

