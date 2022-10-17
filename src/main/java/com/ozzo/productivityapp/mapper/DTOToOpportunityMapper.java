package com.ozzo.productivityapp.mapper;

import org.mapstruct.Mapper;

import com.ozzo.productivityapp.opportunity.Opportunity;
import com.ozzo.productivityapp.opportunity.OpportunityDTO;

@Mapper
public interface DTOToOpportunityMapper {
	
	Opportunity DTOToOpportunity(OpportunityDTO source);
    OpportunityDTO OpportunityToDTO(Opportunity destination);

}
