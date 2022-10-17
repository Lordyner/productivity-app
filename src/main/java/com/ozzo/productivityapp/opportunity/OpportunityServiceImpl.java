package com.ozzo.productivityapp.opportunity;

import java.time.LocalDate;
import java.util.Optional;

import org.hibernate.property.access.spi.PropertyAccessException;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ozzo.productivityapp.mapper.DTOToOpportunityMapper;

@Service
public class OpportunityServiceImpl implements OpportunityService {

	@Autowired
	private OpportunityRepository opportunityRepository;
	
	private DTOToOpportunityMapper mapper = Mappers.getMapper(DTOToOpportunityMapper.class);
	
	@Override
	public int create(OpportunityDTO DTO) {
		
		Opportunity opportunity = mapper.DTOToOpportunity(DTO);
		Opportunity response = opportunityRepository.save(opportunity);
		return response.getIdOpportunity();
	}

	@Override
	public Iterable<Opportunity> getOpportunities(GetOpportunitiesRequest request) {
		
		if(request.isForToday()) {
			request.setMinDate(LocalDate.now());
			request.setMaxDate(LocalDate.now());
		}
		
		return opportunityRepository
			.findByUserIdAndDate(request.getUserId(), request.getMinDate(), request.getMaxDate());
	}
	
	@Override
	public void deleteOpportunity(int id) throws EmptyResultDataAccessException {
		opportunityRepository.deleteById(id);
	
	}
	
	@Override
	public void update(OpportunityDTO DTO) throws DataAccessException, PropertyAccessException {
		
		
		Optional<Opportunity> fetchedOpportunity = opportunityRepository.findById(DTO.getIdOpportunity());
		
		if(fetchedOpportunity.isEmpty()) {
			throw new PropertyAccessException("This opportunity doesn't exist");
		}
		
		Opportunity opportunity = mapper.DTOToOpportunity(DTO);
		opportunityRepository.save(opportunity);
	}

	@Override
	public Opportunity getOpportunity(int id) {
		
		Optional<Opportunity> fetchedOpportunity = opportunityRepository.findById(id);
		if(fetchedOpportunity.isEmpty()) {
			throw new EmptyResultDataAccessException(id);
		}
		return fetchedOpportunity.get();
	
	}
	
	

}
