package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByCreatedById(Long id);
//
//	Optional<Topic> findById(Long id);
//
//	void deleteById(Long id);
	
}
