package com.StrategyApp.dao;

import java.util.List;
import java.util.Date;

import com.StrategyApp.dao.GenericDAO;
import com.StrategyApp.domain.StrategyParameter;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface StrategyParameterDAO extends GenericDAO<StrategyParameter, Integer> {
  
	List<StrategyParameter> findAll();
	


}

