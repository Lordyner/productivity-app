package com.ozzo.productivityapp.role;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	Optional<Role> findByName(RoleEnum name);

}
