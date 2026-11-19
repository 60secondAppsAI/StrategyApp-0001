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
import com.StrategyApp.dao.StrategyParameterDAO;
import com.StrategyApp.domain.StrategyParameter;
import com.StrategyApp.dto.StrategyParameterDTO;
import com.StrategyApp.dto.StrategyParameterSearchDTO;
import com.StrategyApp.dto.StrategyParameterPageDTO;
import com.StrategyApp.dto.StrategyParameterConvertCriteriaDTO;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import com.StrategyApp.service.StrategyParameterService;
import com.StrategyApp.util.ControllerUtils;


@Service
public class StrategyParameterServiceImpl extends GenericServiceImpl<StrategyParameter, Integer> implements StrategyParameterService {

    private final static Logger logger = LoggerFactory.getLogger(StrategyParameterServiceImpl.class);

	@Autowired
	StrategyParameterDAO strategyParameterDao;

	

	@Override
	public GenericDAO<StrategyParameter, Integer> getDAO() {
		return (GenericDAO<StrategyParameter, Integer>) strategyParameterDao;
	}
	
	public List<StrategyParameter> findAll () {
		List<StrategyParameter> strategyParameters = strategyParameterDao.findAll();
		
		return strategyParameters;	
		
	}

	public ResultDTO addStrategyParameter(StrategyParameterDTO strategyParameterDTO, RequestDTO requestDTO) {

		StrategyParameter strategyParameter = new StrategyParameter();

		strategyParameter.setStrategyParameterId(strategyParameterDTO.getStrategyParameterId());

		strategyParameter.setStrategyId(strategyParameterDTO.getStrategyId());

		strategyParameter.setParameterName(strategyParameterDTO.getParameterName());

		strategyParameter.setParameterValue(strategyParameterDTO.getParameterValue());

		strategyParameter.setDataType(strategyParameterDTO.getDataType());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		strategyParameter = strategyParameterDao.save(strategyParameter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<StrategyParameter> getAllStrategyParameters(Pageable pageable) {
		return strategyParameterDao.findAll(pageable);
	}

	public Page<StrategyParameter> getAllStrategyParameters(Specification<StrategyParameter> spec, Pageable pageable) {
		return strategyParameterDao.findAll(spec, pageable);
	}

	public ResponseEntity<StrategyParameterPageDTO> getStrategyParameters(StrategyParameterSearchDTO strategyParameterSearchDTO) {
	
			Integer strategyParameterId = strategyParameterSearchDTO.getStrategyParameterId(); 
 			Integer strategyId = strategyParameterSearchDTO.getStrategyId(); 
 			String parameterName = strategyParameterSearchDTO.getParameterName(); 
 			String parameterValue = strategyParameterSearchDTO.getParameterValue(); 
 			String dataType = strategyParameterSearchDTO.getDataType(); 
 			String sortBy = strategyParameterSearchDTO.getSortBy();
			String sortOrder = strategyParameterSearchDTO.getSortOrder();
			String searchQuery = strategyParameterSearchDTO.getSearchQuery();
			Integer page = strategyParameterSearchDTO.getPage();
			Integer size = strategyParameterSearchDTO.getSize();

	        Specification<StrategyParameter> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, strategyParameterId, "strategyParameterId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, parameterName, "parameterName"); 
			
			spec = ControllerUtils.andIfNecessary(spec, parameterValue, "parameterValue"); 
			
			spec = ControllerUtils.andIfNecessary(spec, dataType, "dataType"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("parameterName")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("parameterValue")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("dataType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<StrategyParameter> strategyParameters = this.getAllStrategyParameters(spec, pageable);
		
		//System.out.println(String.valueOf(strategyParameters.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(strategyParameters.getTotalPages()));
		
		List<StrategyParameter> strategyParametersList = strategyParameters.getContent();
		
		StrategyParameterConvertCriteriaDTO convertCriteria = new StrategyParameterConvertCriteriaDTO();
		List<StrategyParameterDTO> strategyParameterDTOs = this.convertStrategyParametersToStrategyParameterDTOs(strategyParametersList,convertCriteria);
		
		StrategyParameterPageDTO strategyParameterPageDTO = new StrategyParameterPageDTO();
		strategyParameterPageDTO.setStrategyParameters(strategyParameterDTOs);
		strategyParameterPageDTO.setTotalElements(strategyParameters.getTotalElements());
		return ResponseEntity.ok(strategyParameterPageDTO);
	}

	public List<StrategyParameterDTO> convertStrategyParametersToStrategyParameterDTOs(List<StrategyParameter> strategyParameters, StrategyParameterConvertCriteriaDTO convertCriteria) {
		
		List<StrategyParameterDTO> strategyParameterDTOs = new ArrayList<StrategyParameterDTO>();
		
		for (StrategyParameter strategyParameter : strategyParameters) {
			strategyParameterDTOs.add(convertStrategyParameterToStrategyParameterDTO(strategyParameter,convertCriteria));
		}
		
		return strategyParameterDTOs;

	}
	
	public StrategyParameterDTO convertStrategyParameterToStrategyParameterDTO(StrategyParameter strategyParameter, StrategyParameterConvertCriteriaDTO convertCriteria) {
		
		StrategyParameterDTO strategyParameterDTO = new StrategyParameterDTO();

		strategyParameterDTO.setStrategyParameterId(strategyParameter.getStrategyParameterId());

		strategyParameterDTO.setStrategyId(strategyParameter.getStrategyId());

		strategyParameterDTO.setParameterName(strategyParameter.getParameterName());

		strategyParameterDTO.setParameterValue(strategyParameter.getParameterValue());

		strategyParameterDTO.setDataType(strategyParameter.getDataType());
		
		return strategyParameterDTO;
	}

	public ResultDTO updateStrategyParameter(StrategyParameterDTO strategyParameterDTO, RequestDTO requestDTO) {
		
		StrategyParameter strategyParameter = strategyParameterDao.getById(strategyParameterDTO.getStrategyParameterId());
		
		strategyParameter.setStrategyParameterId(ControllerUtils.setValue(strategyParameter.getStrategyParameterId(), strategyParameterDTO.getStrategyParameterId()));
		
		strategyParameter.setStrategyId(ControllerUtils.setValue(strategyParameter.getStrategyId(), strategyParameterDTO.getStrategyId()));
		
		strategyParameter.setParameterName(ControllerUtils.setValue(strategyParameter.getParameterName(), strategyParameterDTO.getParameterName()));
		
		strategyParameter.setParameterValue(ControllerUtils.setValue(strategyParameter.getParameterValue(), strategyParameterDTO.getParameterValue()));
		
		strategyParameter.setDataType(ControllerUtils.setValue(strategyParameter.getDataType(), strategyParameterDTO.getDataType()));

        strategyParameter = strategyParameterDao.save(strategyParameter);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public StrategyParameterDTO getStrategyParameterDTOById(Integer strategyParameterId) {
	
		StrategyParameter strategyParameter = strategyParameterDao.getById(strategyParameterId);
		
		StrategyParameterConvertCriteriaDTO convertCriteria = new StrategyParameterConvertCriteriaDTO();
		return(this.convertStrategyParameterToStrategyParameterDTO(strategyParameter,convertCriteria));
	}

}
