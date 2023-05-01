package com.jmscottnovels.forumapi.repo;

import java.util.Optional;

import com.jmscottnovels.forumapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);
	Optional<Role> findById(Long id);

	void deleteById(Long id);
}
