package com.StrategyApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.StrategyApp.dao.GenericDAO;
import com.StrategyApp.service.GenericService;
import com.StrategyApp.service.impl.GenericServiceImpl;
import com.StrategyApp.dao.StrategyVersionDAO;
import com.StrategyApp.domain.StrategyVersion;
import com.StrategyApp.dto.StrategyVersionDTO;
import com.StrategyApp.dto.StrategyVersionSearchDTO;
import com.StrategyApp.dto.StrategyVersionPageDTO;
import com.StrategyApp.dto.StrategyVersionConvertCriteriaDTO;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import com.StrategyApp.service.StrategyVersionService;
import com.StrategyApp.util.ControllerUtils;


@Service
public class StrategyVersionServiceImpl extends GenericServiceImpl<StrategyVersion, Integer> implements StrategyVersionService {

    private final static Logger logger = LoggerFactory.getLogger(StrategyVersionServiceImpl.class);

	@Autowired
	StrategyVersionDAO strategyVersionDao;

	

	@Override
	public GenericDAO<StrategyVersion, Integer> getDAO() {
		return (GenericDAO<StrategyVersion, Integer>) strategyVersionDao;
	}
	
	public List<StrategyVersion> findAll () {
		List<StrategyVersion> strategyVersions = strategyVersionDao.findAll();
		
		return strategyVersions;	
		
	}

	public ResultDTO addStrategyVersion(StrategyVersionDTO strategyVersionDTO, RequestDTO requestDTO) {

		StrategyVersion strategyVersion = new StrategyVersion();

		strategyVersion.setStrategyVersionId(strategyVersionDTO.getStrategyVersionId());

		strategyVersion.setStrategyId(strategyVersionDTO.getStrategyId());

		strategyVersion.setVersionNumber(strategyVersionDTO.getVersionNumber());

		strategyVersion.setCreatedAt(strategyVersionDTO.getCreatedAt());

		strategyVersion.setStatus(strategyVersionDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		strategyVersion = strategyVersionDao.save(strategyVersion);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<StrategyVersion> getAllStrategyVersions(Pageable pageable) {
		return strategyVersionDao.findAll(pageable);
	}

	public Page<StrategyVersion> getAllStrategyVersions(Specification<StrategyVersion> spec, Pageable pageable) {
		return strategyVersionDao.findAll(spec, pageable);
	}

	public ResponseEntity<StrategyVersionPageDTO> getStrategyVersions(StrategyVersionSearchDTO strategyVersionSearchDTO) {
	
			Integer strategyVersionId = strategyVersionSearchDTO.getStrategyVersionId(); 
 			Integer strategyId = strategyVersionSearchDTO.getStrategyId(); 
 			String versionNumber = strategyVersionSearchDTO.getVersionNumber(); 
 			Date createdAt = strategyVersionSearchDTO.getCreatedAt();
 			String status = strategyVersionSearchDTO.getStatus(); 
 			String sortBy = strategyVersionSearchDTO.getSortBy();
			String sortOrder = strategyVersionSearchDTO.getSortOrder();
			String searchQuery = strategyVersionSearchDTO.getSearchQuery();
			Integer page = strategyVersionSearchDTO.getPage();
			Integer size = strategyVersionSearchDTO.getSize();

	        Specification<StrategyVersion> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, strategyVersionId, "strategyVersionId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, versionNumber, "versionNumber"); 
			
			spec = ControllerUtils.andIfNecessary(spec, createdAt, "createdAt");
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("versionNumber")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<StrategyVersion> strategyVersions = this.getAllStrategyVersions(spec, pageable);
		
		//System.out.println(String.valueOf(strategyVersions.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(strategyVersions.getTotalPages()));
		
		List<StrategyVersion> strategyVersionsList = strategyVersions.getContent();
		
		StrategyVersionConvertCriteriaDTO convertCriteria = new StrategyVersionConvertCriteriaDTO();
		List<StrategyVersionDTO> strategyVersionDTOs = this.convertStrategyVersionsToStrategyVersionDTOs(strategyVersionsList,convertCriteria);
		
		StrategyVersionPageDTO strategyVersionPageDTO = new StrategyVersionPageDTO();
		strategyVersionPageDTO.setStrategyVersions(strategyVersionDTOs);
		strategyVersionPageDTO.setTotalElements(strategyVersions.getTotalElements());
		return ResponseEntity.ok(strategyVersionPageDTO);
	}

	public List<StrategyVersionDTO> convertStrategyVersionsToStrategyVersionDTOs(List<StrategyVersion> strategyVersions, StrategyVersionConvertCriteriaDTO convertCriteria) {
		
		List<StrategyVersionDTO> strategyVersionDTOs = new ArrayList<StrategyVersionDTO>();
		
		for (StrategyVersion strategyVersion : strategyVersions) {
			strategyVersionDTOs.add(convertStrategyVersionToStrategyVersionDTO(strategyVersion,convertCriteria));
		}
		
		return strategyVersionDTOs;

	}
	
	public StrategyVersionDTO convertStrategyVersionToStrategyVersionDTO(StrategyVersion strategyVersion, StrategyVersionConvertCriteriaDTO convertCriteria) {
		
		StrategyVersionDTO strategyVersionDTO = new StrategyVersionDTO();

		strategyVersionDTO.setStrategyVersionId(strategyVersion.getStrategyVersionId());

		strategyVersionDTO.setStrategyId(strategyVersion.getStrategyId());

		strategyVersionDTO.setVersionNumber(strategyVersion.getVersionNumber());

		strategyVersionDTO.setCreatedAt(strategyVersion.getCreatedAt());

		strategyVersionDTO.setStatus(strategyVersion.getStatus());
		
		return strategyVersionDTO;
	}

	public ResultDTO updateStrategyVersion(StrategyVersionDTO strategyVersionDTO, RequestDTO requestDTO) {
		
		StrategyVersion strategyVersion = strategyVersionDao.getById(strategyVersionDTO.getStrategyVersionId());
		
		strategyVersion.setStrategyVersionId(ControllerUtils.setValue(strategyVersion.getStrategyVersionId(), strategyVersionDTO.getStrategyVersionId()));
		
		strategyVersion.setStrategyId(ControllerUtils.setValue(strategyVersion.getStrategyId(), strategyVersionDTO.getStrategyId()));
		
		strategyVersion.setVersionNumber(ControllerUtils.setValue(strategyVersion.getVersionNumber(), strategyVersionDTO.getVersionNumber()));
		
		strategyVersion.setCreatedAt(ControllerUtils.setValue(strategyVersion.getCreatedAt(), strategyVersionDTO.getCreatedAt()));
		
		strategyVersion.setStatus(ControllerUtils.setValue(strategyVersion.getStatus(), strategyVersionDTO.getStatus()));

        strategyVersion = strategyVersionDao.save(strategyVersion);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public StrategyVersionDTO getStrategyVersionDTOById(Integer strategyVersionId) {
	
		StrategyVersion strategyVersion = strategyVersionDao.getById(strategyVersionId);
		
		StrategyVersionConvertCriteriaDTO convertCriteria = new StrategyVersionConvertCriteriaDTO();
		return(this.convertStrategyVersionToStrategyVersionDTO(strategyVersion,convertCriteria));
	}

}
