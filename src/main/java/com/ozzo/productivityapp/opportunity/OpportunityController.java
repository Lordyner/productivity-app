package com.ozzo.productivityapp.opportunity;

import java.net.URI;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;


@Controller
@RequestMapping(path="/opportunities")
public class OpportunityController {
	
	@Autowired 
	private OpportunityServiceImpl opportunityService;
	
	Logger logger = LoggerFactory.getLogger(OpportunityController.class);
	
	
	
	@GetMapping(path="/opportunity/{id}")
	public ResponseEntity<?> getOpportunity(@PathVariable int id) {
		Opportunity opportunityFetched = opportunityService.getOpportunity(id);
		return ResponseEntity.ok(opportunityFetched);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<?> getOpportunities(@RequestParam Integer userId, @RequestParam String dateToLookFor, @RequestParam boolean isForToday) {
		LocalDate date = LocalDate.of(Integer.parseInt(dateToLookFor.substring(0,4)),
				Integer.parseInt(dateToLookFor.substring(5,7)),
				Integer.parseInt(dateToLookFor.substring(8,10)));
		GetOpportunitiesRequest request = new GetOpportunitiesRequest(userId, date, date, isForToday);
		Iterable<Opportunity> opportunityList = opportunityService.getOpportunities(request);
		opportunityList.forEach(opportunity -> logger.info("Opportunity : "+opportunity.getTitle()));
		return ResponseEntity.ok(opportunityList);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<?> createOpportunity(@RequestBody OpportunityDTO request) {
		int opportunityId;
		try {
			opportunityId = opportunityService.create(request);
		} catch(PropertyValueException e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error in the data sent", e);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "", e);
		} 	
		
		return ResponseEntity.created(URI.create("/opportunities/"+opportunityId)).build();
	}
	
	@PatchMapping(path="/opportunity")
	public @ResponseBody ResponseEntity<?> updateOpportunity(@RequestBody OpportunityDTO request) {
		
		opportunityService.update(request);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping
	(path="/opportunity/{id}")
	public ResponseEntity<?> deleteOpportunity(@PathVariable int id) {
		opportunityService.deleteOpportunity(id);	
		return ResponseEntity.noContent().build();	
	}
	
}
