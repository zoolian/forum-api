package com.jmscottnovels.forumapi.repo;

import java.util.List;
import java.util.Optional;

import com.jmscottnovels.forumapi.model.Role;
import com.jmscottnovels.forumapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//	@Query("{ 'name' : ?0 }")
//	List<User> findUsersByName(String name);
//
//	@Query("{ 'age' : { $gt: ?0, $lt: ?1 } }")
//	List<User> findUsersByAgeBetween(int ageGT, int ageLT);

//    @Query("{ 'name' : { $regex: ?0 } }")
//    List<User> findUsersByRegexpName(String regexp);

	List<Role> getRolesById(String userId);

    User findByEmail(String email);

//	List<User> findByNameLikeOrderByAgeAsc(String name);
//
//    List<User> findByAgeBetween(int ageGT, int ageLT);
//
//    List<User> findByNameStartingWith(String regexp);
//
//    List<User> findByNameEndingWith(String regexp);
}
