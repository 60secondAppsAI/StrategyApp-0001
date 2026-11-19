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
import com.StrategyApp.dao.StrategyDAO;
import com.StrategyApp.domain.Strategy;
import com.StrategyApp.dto.StrategyDTO;
import com.StrategyApp.dto.StrategySearchDTO;
import com.StrategyApp.dto.StrategyPageDTO;
import com.StrategyApp.dto.StrategyConvertCriteriaDTO;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import com.StrategyApp.service.StrategyService;
import com.StrategyApp.util.ControllerUtils;


@Service
public class StrategyServiceImpl extends GenericServiceImpl<Strategy, Integer> implements StrategyService {

    private final static Logger logger = LoggerFactory.getLogger(StrategyServiceImpl.class);

	@Autowired
	StrategyDAO strategyDao;

	

	@Override
	public GenericDAO<Strategy, Integer> getDAO() {
		return (GenericDAO<Strategy, Integer>) strategyDao;
	}
	
	public List<Strategy> findAll () {
		List<Strategy> strategys = strategyDao.findAll();
		
		return strategys;	
		
	}

	public ResultDTO addStrategy(StrategyDTO strategyDTO, RequestDTO requestDTO) {

		Strategy strategy = new Strategy();

		strategy.setStrategyId(strategyDTO.getStrategyId());

		strategy.setName(strategyDTO.getName());

		strategy.setDescription(strategyDTO.getDescription());

		strategy.setStrategyType(strategyDTO.getStrategyType());

		strategy.setStatus(strategyDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		strategy = strategyDao.save(strategy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Strategy> getAllStrategys(Pageable pageable) {
		return strategyDao.findAll(pageable);
	}

	public Page<Strategy> getAllStrategys(Specification<Strategy> spec, Pageable pageable) {
		return strategyDao.findAll(spec, pageable);
	}

	public ResponseEntity<StrategyPageDTO> getStrategys(StrategySearchDTO strategySearchDTO) {
	
			Integer strategyId = strategySearchDTO.getStrategyId(); 
 			String name = strategySearchDTO.getName(); 
 			String description = strategySearchDTO.getDescription(); 
 			String strategyType = strategySearchDTO.getStrategyType(); 
 			String status = strategySearchDTO.getStatus(); 
 			String sortBy = strategySearchDTO.getSortBy();
			String sortOrder = strategySearchDTO.getSortOrder();
			String searchQuery = strategySearchDTO.getSearchQuery();
			Integer page = strategySearchDTO.getPage();
			Integer size = strategySearchDTO.getSize();

	        Specification<Strategy> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyType, "strategyType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("strategyType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<Strategy> strategys = this.getAllStrategys(spec, pageable);
		
		//System.out.println(String.valueOf(strategys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(strategys.getTotalPages()));
		
		List<Strategy> strategysList = strategys.getContent();
		
		StrategyConvertCriteriaDTO convertCriteria = new StrategyConvertCriteriaDTO();
		List<StrategyDTO> strategyDTOs = this.convertStrategysToStrategyDTOs(strategysList,convertCriteria);
		
		StrategyPageDTO strategyPageDTO = new StrategyPageDTO();
		strategyPageDTO.setStrategys(strategyDTOs);
		strategyPageDTO.setTotalElements(strategys.getTotalElements());
		return ResponseEntity.ok(strategyPageDTO);
	}

	public List<StrategyDTO> convertStrategysToStrategyDTOs(List<Strategy> strategys, StrategyConvertCriteriaDTO convertCriteria) {
		
		List<StrategyDTO> strategyDTOs = new ArrayList<StrategyDTO>();
		
		for (Strategy strategy : strategys) {
			strategyDTOs.add(convertStrategyToStrategyDTO(strategy,convertCriteria));
		}
		
		return strategyDTOs;

	}
	
	public StrategyDTO convertStrategyToStrategyDTO(Strategy strategy, StrategyConvertCriteriaDTO convertCriteria) {
		
		StrategyDTO strategyDTO = new StrategyDTO();

		strategyDTO.setStrategyId(strategy.getStrategyId());

		strategyDTO.setName(strategy.getName());

		strategyDTO.setDescription(strategy.getDescription());

		strategyDTO.setStrategyType(strategy.getStrategyType());

		strategyDTO.setStatus(strategy.getStatus());
		
		return strategyDTO;
	}

	public ResultDTO updateStrategy(StrategyDTO strategyDTO, RequestDTO requestDTO) {
		
		Strategy strategy = strategyDao.getById(strategyDTO.getStrategyId());
		
		strategy.setStrategyId(ControllerUtils.setValue(strategy.getStrategyId(), strategyDTO.getStrategyId()));
		
		strategy.setName(ControllerUtils.setValue(strategy.getName(), strategyDTO.getName()));
		
		strategy.setDescription(ControllerUtils.setValue(strategy.getDescription(), strategyDTO.getDescription()));
		
		strategy.setStrategyType(ControllerUtils.setValue(strategy.getStrategyType(), strategyDTO.getStrategyType()));
		
		strategy.setStatus(ControllerUtils.setValue(strategy.getStatus(), strategyDTO.getStatus()));

        strategy = strategyDao.save(strategy);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public StrategyDTO getStrategyDTOById(Integer strategyId) {
	
		Strategy strategy = strategyDao.getById(strategyId);
		
		StrategyConvertCriteriaDTO convertCriteria = new StrategyConvertCriteriaDTO();
		return(this.convertStrategyToStrategyDTO(strategy,convertCriteria));
	}

}
