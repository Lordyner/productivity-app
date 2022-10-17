package com.ozzo.productivityapp.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	Optional<User> findByEmail(String email);

	Optional<User> findByUserName(String userName);
	
	@Transactional
	@Modifying
	@Query(value="update users u set u.refresh_token = :refreshToken where id_user = :userId ;", nativeQuery = true)
	void updateRefreshToken(String refreshToken, String userId) throws DataAccessException;
	
	Optional<User> findByRefreshToken(String refreshToken);


}
