package com.ozzo.productivityapp.opportunity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OpportunityRepository extends CrudRepository<Opportunity, Integer> {
	
    @Query(
    		value = "SELECT * FROM opportunity"
    				+ " WHERE id_user = :userId "
    				+ "AND date between :minDate AND :maxDate ;", nativeQuery = true)
    Iterable<Opportunity> findByUserIdAndDate(int userId, LocalDate minDate, LocalDate maxDate);

}
