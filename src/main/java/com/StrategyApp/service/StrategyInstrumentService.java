package com.StrategyApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.StrategyApp.domain.StrategyInstrument;
import com.StrategyApp.dto.StrategyInstrumentDTO;
import com.StrategyApp.dto.StrategyInstrumentSearchDTO;
import com.StrategyApp.dto.StrategyInstrumentPageDTO;
import com.StrategyApp.dto.StrategyInstrumentConvertCriteriaDTO;
import com.StrategyApp.service.GenericService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface StrategyInstrumentService extends GenericService<StrategyInstrument, Integer> {

	List<StrategyInstrument> findAll();

	ResultDTO addStrategyInstrument(StrategyInstrumentDTO strategyInstrumentDTO, RequestDTO requestDTO);

	ResultDTO updateStrategyInstrument(StrategyInstrumentDTO strategyInstrumentDTO, RequestDTO requestDTO);

    Page<StrategyInstrument> getAllStrategyInstruments(Pageable pageable);

    Page<StrategyInstrument> getAllStrategyInstruments(Specification<StrategyInstrument> spec, Pageable pageable);

	ResponseEntity<StrategyInstrumentPageDTO> getStrategyInstruments(StrategyInstrumentSearchDTO strategyInstrumentSearchDTO);
	
	List<StrategyInstrumentDTO> convertStrategyInstrumentsToStrategyInstrumentDTOs(List<StrategyInstrument> strategyInstruments, StrategyInstrumentConvertCriteriaDTO convertCriteria);

	StrategyInstrumentDTO getStrategyInstrumentDTOById(Integer strategyInstrumentId);



	
}
