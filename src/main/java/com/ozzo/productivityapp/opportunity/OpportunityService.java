package com.ozzo.productivityapp.opportunity;

import org.hibernate.property.access.spi.PropertyAccessException;
import org.springframework.dao.DataAccessException;

public interface OpportunityService {
	
	int create(OpportunityDTO request);
	
	Iterable<Opportunity> getOpportunities(GetOpportunitiesRequest request);
	
	void deleteOpportunity(int id);

	void update(OpportunityDTO request) throws DataAccessException, PropertyAccessException;

	Opportunity getOpportunity(int id);

}
