package com.StrategyApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.StrategyApp.domain.StrategyVersion;
import com.StrategyApp.dto.StrategyVersionDTO;
import com.StrategyApp.dto.StrategyVersionSearchDTO;
import com.StrategyApp.dto.StrategyVersionPageDTO;
import com.StrategyApp.dto.StrategyVersionConvertCriteriaDTO;
import com.StrategyApp.service.GenericService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface StrategyVersionService extends GenericService<StrategyVersion, Integer> {

	List<StrategyVersion> findAll();

	ResultDTO addStrategyVersion(StrategyVersionDTO strategyVersionDTO, RequestDTO requestDTO);

	ResultDTO updateStrategyVersion(StrategyVersionDTO strategyVersionDTO, RequestDTO requestDTO);

    Page<StrategyVersion> getAllStrategyVersions(Pageable pageable);

    Page<StrategyVersion> getAllStrategyVersions(Specification<StrategyVersion> spec, Pageable pageable);

	ResponseEntity<StrategyVersionPageDTO> getStrategyVersions(StrategyVersionSearchDTO strategyVersionSearchDTO);
	
	List<StrategyVersionDTO> convertStrategyVersionsToStrategyVersionDTOs(List<StrategyVersion> strategyVersions, StrategyVersionConvertCriteriaDTO convertCriteria);

	StrategyVersionDTO getStrategyVersionDTOById(Integer strategyVersionId);



	
}
