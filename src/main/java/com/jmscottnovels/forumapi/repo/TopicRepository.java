package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.model.TopicDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByCreatedById(Long id);
	@Query("""
			 select new com.jmscottnovels.forumapi.model.TopicDTO(t.id as id, t.createdBy.id,
			 t.createdBy.firstName as createdByFirstName, t.createdBy.lastName as createdByLastName, t.title as title,
			 t.description as description, t.createdDate, t.lastPostDate, t.active)
			 from Topic t
			 """)
	List<TopicDTO> findAllTopics();


//
//	Optional<Topic> findById(Long id);
//
//	void deleteById(Long id);
	
}
