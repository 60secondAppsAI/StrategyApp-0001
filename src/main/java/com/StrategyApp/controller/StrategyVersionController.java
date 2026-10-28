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

import com.StrategyApp.domain.StrategyVersion;
import com.StrategyApp.dto.StrategyVersionDTO;
import com.StrategyApp.dto.StrategyVersionSearchDTO;
import com.StrategyApp.dto.StrategyVersionPageDTO;
import com.StrategyApp.service.StrategyVersionService;
import com.StrategyApp.dto.common.RequestDTO;
import com.StrategyApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/strategyVersion")
@RestController
public class StrategyVersionController {

	private final static Logger logger = LoggerFactory.getLogger(StrategyVersionController.class);

	@Autowired
	StrategyVersionService strategyVersionService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<StrategyVersion> getAll() {

		List<StrategyVersion> strategyVersions = strategyVersionService.findAll();
		
		return strategyVersions;	
	}

	@GetMapping(value = "/{strategyVersionId}")
	@ResponseBody
	public StrategyVersionDTO getStrategyVersion(@PathVariable Integer strategyVersionId) {
		
		return (strategyVersionService.getStrategyVersionDTOById(strategyVersionId));
	}

 	@RequestMapping(value = "/addStrategyVersion", method = RequestMethod.POST)
	public ResponseEntity<?> addStrategyVersion(@RequestBody StrategyVersionDTO strategyVersionDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyVersionService.addStrategyVersion(strategyVersionDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/strategyVersions")
	public ResponseEntity<StrategyVersionPageDTO> getStrategyVersions(StrategyVersionSearchDTO strategyVersionSearchDTO) {
 
		return strategyVersionService.getStrategyVersions(strategyVersionSearchDTO);
	}	

	@RequestMapping(value = "/updateStrategyVersion", method = RequestMethod.POST)
	public ResponseEntity<?> updateStrategyVersion(@RequestBody StrategyVersionDTO strategyVersionDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = strategyVersionService.updateStrategyVersion(strategyVersionDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
