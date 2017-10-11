package iom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import iom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername (String username);
}
