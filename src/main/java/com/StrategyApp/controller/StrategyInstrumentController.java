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

import com.StrategyApp.domain.StrategyInstrument;
import com.StrategyApp.dto.StrategyInstrumentDTO;
import com.StrategyApp.dto.StrategyInstrumentSearchDTO;
import com.StrategyApp.dto.StrategyInstrumentPageDTO;
import com.StrategyApp.service.StrategyInstrumentService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/strategyInstrument")
@RestController
public class StrategyInstrumentController {

	private final static Logger logger = LoggerFactory.getLogger(StrategyInstrumentController.class);

	@Autowired
	StrategyInstrumentService strategyInstrumentService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<StrategyInstrument> getAll() {

		List<StrategyInstrument> strategyInstruments = strategyInstrumentService.findAll();
		
		return strategyInstruments;	
	}

	@GetMapping(value = "/{strategyInstrumentId}")
	@ResponseBody
	public StrategyInstrumentDTO getStrategyInstrument(@PathVariable Integer strategyInstrumentId) {
		
		return (strategyInstrumentService.getStrategyInstrumentDTOById(strategyInstrumentId));
	}

 	@RequestMapping(value = "/addStrategyInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> addStrategyInstrument(@RequestBody StrategyInstrumentDTO strategyInstrumentDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyInstrumentService.addStrategyInstrument(strategyInstrumentDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/strategyInstruments")
	public ResponseEntity<StrategyInstrumentPageDTO> getStrategyInstruments(StrategyInstrumentSearchDTO strategyInstrumentSearchDTO) {
 
		return strategyInstrumentService.getStrategyInstruments(strategyInstrumentSearchDTO);
	}	

	@RequestMapping(value = "/updateStrategyInstrument", method = RequestMethod.POST)
	public ResponseEntity<?> updateStrategyInstrument(@RequestBody StrategyInstrumentDTO strategyInstrumentDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyInstrumentService.updateStrategyInstrument(strategyInstrumentDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
