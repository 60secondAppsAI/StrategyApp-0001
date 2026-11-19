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

import com.StrategyApp.domain.StrategyParameter;
import com.StrategyApp.dto.StrategyParameterDTO;
import com.StrategyApp.dto.StrategyParameterSearchDTO;
import com.StrategyApp.dto.StrategyParameterPageDTO;
import com.StrategyApp.service.StrategyParameterService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/strategyParameter")
@RestController
public class StrategyParameterController {

	private final static Logger logger = LoggerFactory.getLogger(StrategyParameterController.class);

	@Autowired
	StrategyParameterService strategyParameterService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<StrategyParameter> getAll() {

		List<StrategyParameter> strategyParameters = strategyParameterService.findAll();
		
		return strategyParameters;	
	}

	@GetMapping(value = "/{strategyParameterId}")
	@ResponseBody
	public StrategyParameterDTO getStrategyParameter(@PathVariable Integer strategyParameterId) {
		
		return (strategyParameterService.getStrategyParameterDTOById(strategyParameterId));
	}

 	@RequestMapping(value = "/addStrategyParameter", method = RequestMethod.POST)
	public ResponseEntity<?> addStrategyParameter(@RequestBody StrategyParameterDTO strategyParameterDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyParameterService.addStrategyParameter(strategyParameterDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/strategyParameters")
	public ResponseEntity<StrategyParameterPageDTO> getStrategyParameters(StrategyParameterSearchDTO strategyParameterSearchDTO) {
 
		return strategyParameterService.getStrategyParameters(strategyParameterSearchDTO);
	}	

	@RequestMapping(value = "/updateStrategyParameter", method = RequestMethod.POST)
	public ResponseEntity<?> updateStrategyParameter(@RequestBody StrategyParameterDTO strategyParameterDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyParameterService.updateStrategyParameter(strategyParameterDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
