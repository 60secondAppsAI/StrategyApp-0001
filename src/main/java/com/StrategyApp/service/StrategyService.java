package com.StrategyApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.StrategyApp.domain.Strategy;
import com.StrategyApp.dto.StrategyDTO;
import com.StrategyApp.dto.StrategySearchDTO;
import com.StrategyApp.dto.StrategyPageDTO;
import com.StrategyApp.dto.StrategyConvertCriteriaDTO;
import com.StrategyApp.service.GenericService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface StrategyService extends GenericService<Strategy, Integer> {

	List<Strategy> findAll();

	ResultDTO addStrategy(StrategyDTO strategyDTO, RequestDTO requestDTO);

	ResultDTO updateStrategy(StrategyDTO strategyDTO, RequestDTO requestDTO);

    Page<Strategy> getAllStrategys(Pageable pageable);

    Page<Strategy> getAllStrategys(Specification<Strategy> spec, Pageable pageable);

	ResponseEntity<StrategyPageDTO> getStrategys(StrategySearchDTO strategySearchDTO);
	
	List<StrategyDTO> convertStrategysToStrategyDTOs(List<Strategy> strategys, StrategyConvertCriteriaDTO convertCriteria);

	StrategyDTO getStrategyDTOById(Integer strategyId);



	
}
