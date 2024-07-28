package com.codewithprojects.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithprojects.entities.User;
import com.codewithprojects.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findFirstByEmail(String username);

	User findByUserRole(UserRole admin);

}
