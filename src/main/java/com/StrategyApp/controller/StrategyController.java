package com.StrategyApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.StrategyApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.StrategyApp.domain.Strategy;
import com.StrategyApp.dto.StrategyDTO;
import com.StrategyApp.dto.StrategySearchDTO;
import com.StrategyApp.dto.StrategyPageDTO;
import com.StrategyApp.service.StrategyService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/strategy")
@RestController
public class StrategyController {

	private final static Logger logger = LoggerFactory.getLogger(StrategyController.class);

	@Autowired
	StrategyService strategyService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<Strategy> getAll() {

		List<Strategy> strategys = strategyService.findAll();
		
		return strategys;	
	}

	@GetMapping(value = "/{strategyId}")
	@ResponseBody
	public StrategyDTO getStrategy(@PathVariable Integer strategyId) {
		
		return (strategyService.getStrategyDTOById(strategyId));
	}

 	@RequestMapping(value = "/addStrategy", method = RequestMethod.POST)
	public ResponseEntity<?> addStrategy(@RequestBody StrategyDTO strategyDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyService.addStrategy(strategyDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/strategys")
	public ResponseEntity<StrategyPageDTO> getStrategys(StrategySearchDTO strategySearchDTO) {
 
		return strategyService.getStrategys(strategySearchDTO);
	}	

	@RequestMapping(value = "/updateStrategy", method = RequestMethod.POST)
	public ResponseEntity<?> updateStrategy(@RequestBody StrategyDTO strategyDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyService.updateStrategy(strategyDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
