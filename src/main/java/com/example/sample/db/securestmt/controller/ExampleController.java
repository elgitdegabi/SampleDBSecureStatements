/** 
 * SampleDBSecureStatements.
 * Copyright (C) 2017 Gabelopment (gabelopment@gmail.com)
 * 
 * This file is part of SampleDBSecureStatements.
 * 
 * SampleDBSecureStatements is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SampleDBSecureStatements is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SampleDBSecureStatements.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.example.sample.db.securestmt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.sample.db.securestmt.dto.ExampleDTO;
import com.example.sample.db.securestmt.service.ExampleService;

/**
 * Handles the request for the example. 
 * @author Gabriel
 *
 */
@RestController
public class ExampleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleController.class);
	
	@Autowired
	ExampleService exampleService;
	
	/**
	 * Gets all configured names.
	 * @return
	 */
	@RequestMapping(path="/user/info", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserInfo(@RequestParam final String user) {
		LOGGER.info("getUserInfo - start");
		
		ResponseEntity<?> responseEntity = null;
		
		try{
			List<ExampleDTO> users = exampleService.getUserInfo(user);
			responseEntity = ResponseEntity.ok(users);
		} catch (Exception e) {
			responseEntity = ResponseEntity.badRequest().body("ERROR : " + e.getMessage());
		}
		
		LOGGER.info("getUserInfo - end");
		return responseEntity;		
	}	
}
