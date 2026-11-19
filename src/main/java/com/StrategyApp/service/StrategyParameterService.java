package com.StrategyApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.StrategyApp.domain.StrategyParameter;
import com.StrategyApp.dto.StrategyParameterDTO;
import com.StrategyApp.dto.StrategyParameterSearchDTO;
import com.StrategyApp.dto.StrategyParameterPageDTO;
import com.StrategyApp.dto.StrategyParameterConvertCriteriaDTO;
import com.StrategyApp.service.GenericService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface StrategyParameterService extends GenericService<StrategyParameter, Integer> {

	List<StrategyParameter> findAll();

	ResultDTO addStrategyParameter(StrategyParameterDTO strategyParameterDTO, RequestDTO requestDTO);

	ResultDTO updateStrategyParameter(StrategyParameterDTO strategyParameterDTO, RequestDTO requestDTO);

    Page<StrategyParameter> getAllStrategyParameters(Pageable pageable);

    Page<StrategyParameter> getAllStrategyParameters(Specification<StrategyParameter> spec, Pageable pageable);

	ResponseEntity<StrategyParameterPageDTO> getStrategyParameters(StrategyParameterSearchDTO strategyParameterSearchDTO);
	
	List<StrategyParameterDTO> convertStrategyParametersToStrategyParameterDTOs(List<StrategyParameter> strategyParameters, StrategyParameterConvertCriteriaDTO convertCriteria);

	StrategyParameterDTO getStrategyParameterDTOById(Integer strategyParameterId);



	
}
