package com.StrategyApp.dao;

import java.util.List;
import java.util.Date;

import com.StrategyApp.dao.GenericDAO;
import com.StrategyApp.domain.StrategyVersion;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface StrategyVersionDAO extends GenericDAO<StrategyVersion, Integer> {
  
	List<StrategyVersion> findAll();
	


}

