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
import com.StrategyApp.dao.StrategyInstrumentDAO;
import com.StrategyApp.domain.StrategyInstrument;
import com.StrategyApp.dto.StrategyInstrumentDTO;
import com.StrategyApp.dto.StrategyInstrumentSearchDTO;
import com.StrategyApp.dto.StrategyInstrumentPageDTO;
import com.StrategyApp.dto.StrategyInstrumentConvertCriteriaDTO;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;
import com.StrategyApp.service.StrategyInstrumentService;
import com.StrategyApp.util.ControllerUtils;


@Service
public class StrategyInstrumentServiceImpl extends GenericServiceImpl<StrategyInstrument, Integer> implements StrategyInstrumentService {

    private final static Logger logger = LoggerFactory.getLogger(StrategyInstrumentServiceImpl.class);

	@Autowired
	StrategyInstrumentDAO strategyInstrumentDao;

	

	@Override
	public GenericDAO<StrategyInstrument, Integer> getDAO() {
		return (GenericDAO<StrategyInstrument, Integer>) strategyInstrumentDao;
	}
	
	public List<StrategyInstrument> findAll () {
		List<StrategyInstrument> strategyInstruments = strategyInstrumentDao.findAll();
		
		return strategyInstruments;	
		
	}

	public ResultDTO addStrategyInstrument(StrategyInstrumentDTO strategyInstrumentDTO, RequestDTO requestDTO) {

		StrategyInstrument strategyInstrument = new StrategyInstrument();

		strategyInstrument.setStrategyInstrumentId(strategyInstrumentDTO.getStrategyInstrumentId());

		strategyInstrument.setStrategyId(strategyInstrumentDTO.getStrategyId());

		strategyInstrument.setInstrumentId(strategyInstrumentDTO.getInstrumentId());

		strategyInstrument.setActive(strategyInstrumentDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		strategyInstrument = strategyInstrumentDao.save(strategyInstrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<StrategyInstrument> getAllStrategyInstruments(Pageable pageable) {
		return strategyInstrumentDao.findAll(pageable);
	}

	public Page<StrategyInstrument> getAllStrategyInstruments(Specification<StrategyInstrument> spec, Pageable pageable) {
		return strategyInstrumentDao.findAll(spec, pageable);
	}

	public ResponseEntity<StrategyInstrumentPageDTO> getStrategyInstruments(StrategyInstrumentSearchDTO strategyInstrumentSearchDTO) {
	
			Integer strategyInstrumentId = strategyInstrumentSearchDTO.getStrategyInstrumentId(); 
 			Integer strategyId = strategyInstrumentSearchDTO.getStrategyId(); 
 			Integer instrumentId = strategyInstrumentSearchDTO.getInstrumentId(); 
  			String sortBy = strategyInstrumentSearchDTO.getSortBy();
			String sortOrder = strategyInstrumentSearchDTO.getSortOrder();
			String searchQuery = strategyInstrumentSearchDTO.getSearchQuery();
			Integer page = strategyInstrumentSearchDTO.getPage();
			Integer size = strategyInstrumentSearchDTO.getSize();

	        Specification<StrategyInstrument> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, strategyInstrumentId, "strategyInstrumentId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, strategyId, "strategyId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, instrumentId, "instrumentId"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

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

		Page<StrategyInstrument> strategyInstruments = this.getAllStrategyInstruments(spec, pageable);
		
		//System.out.println(String.valueOf(strategyInstruments.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(strategyInstruments.getTotalPages()));
		
		List<StrategyInstrument> strategyInstrumentsList = strategyInstruments.getContent();
		
		StrategyInstrumentConvertCriteriaDTO convertCriteria = new StrategyInstrumentConvertCriteriaDTO();
		List<StrategyInstrumentDTO> strategyInstrumentDTOs = this.convertStrategyInstrumentsToStrategyInstrumentDTOs(strategyInstrumentsList,convertCriteria);
		
		StrategyInstrumentPageDTO strategyInstrumentPageDTO = new StrategyInstrumentPageDTO();
		strategyInstrumentPageDTO.setStrategyInstruments(strategyInstrumentDTOs);
		strategyInstrumentPageDTO.setTotalElements(strategyInstruments.getTotalElements());
		return ResponseEntity.ok(strategyInstrumentPageDTO);
	}

	public List<StrategyInstrumentDTO> convertStrategyInstrumentsToStrategyInstrumentDTOs(List<StrategyInstrument> strategyInstruments, StrategyInstrumentConvertCriteriaDTO convertCriteria) {
		
		List<StrategyInstrumentDTO> strategyInstrumentDTOs = new ArrayList<StrategyInstrumentDTO>();
		
		for (StrategyInstrument strategyInstrument : strategyInstruments) {
			strategyInstrumentDTOs.add(convertStrategyInstrumentToStrategyInstrumentDTO(strategyInstrument,convertCriteria));
		}
		
		return strategyInstrumentDTOs;

	}
	
	public StrategyInstrumentDTO convertStrategyInstrumentToStrategyInstrumentDTO(StrategyInstrument strategyInstrument, StrategyInstrumentConvertCriteriaDTO convertCriteria) {
		
		StrategyInstrumentDTO strategyInstrumentDTO = new StrategyInstrumentDTO();

		strategyInstrumentDTO.setStrategyInstrumentId(strategyInstrument.getStrategyInstrumentId());

		strategyInstrumentDTO.setStrategyId(strategyInstrument.getStrategyId());

		strategyInstrumentDTO.setInstrumentId(strategyInstrument.getInstrumentId());

		strategyInstrumentDTO.setActive(strategyInstrument.getActive());
		
		return strategyInstrumentDTO;
	}

	public ResultDTO updateStrategyInstrument(StrategyInstrumentDTO strategyInstrumentDTO, RequestDTO requestDTO) {
		
		StrategyInstrument strategyInstrument = strategyInstrumentDao.getById(strategyInstrumentDTO.getStrategyInstrumentId());
		
		strategyInstrument.setStrategyInstrumentId(ControllerUtils.setValue(strategyInstrument.getStrategyInstrumentId(), strategyInstrumentDTO.getStrategyInstrumentId()));
		
		strategyInstrument.setStrategyId(ControllerUtils.setValue(strategyInstrument.getStrategyId(), strategyInstrumentDTO.getStrategyId()));
		
		strategyInstrument.setInstrumentId(ControllerUtils.setValue(strategyInstrument.getInstrumentId(), strategyInstrumentDTO.getInstrumentId()));
		
		strategyInstrument.setActive(ControllerUtils.setValue(strategyInstrument.getActive(), strategyInstrumentDTO.getActive()));

        strategyInstrument = strategyInstrumentDao.save(strategyInstrument);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public StrategyInstrumentDTO getStrategyInstrumentDTOById(Integer strategyInstrumentId) {
	
		StrategyInstrument strategyInstrument = strategyInstrumentDao.getById(strategyInstrumentId);
		
		StrategyInstrumentConvertCriteriaDTO convertCriteria = new StrategyInstrumentConvertCriteriaDTO();
		return(this.convertStrategyInstrumentToStrategyInstrumentDTO(strategyInstrument,convertCriteria));
	}

}
