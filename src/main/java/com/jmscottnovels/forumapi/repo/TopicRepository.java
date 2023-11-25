package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.model.TopicDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByCreatedById(Long id);
	@Query("""
			 select new com.jmscottnovels.forumapi.model.TopicDTO(
			 t.id, t.createdBy.id, t.createdBy.firstName, t.createdBy.lastName, t.title,
			 t.description, t.createdDate, t.lastPostDate, t.active, t.views)
			 from Topic t
			 """)
	List<TopicDTO> findAllTopics();

	@Query("""
			 select new com.jmscottnovels.forumapi.model.TopicDTO(
			 t.id, t.createdBy.id, t.createdBy.firstName, t.createdBy.lastName, t.title,
			 t.description, t.createdDate, t.lastPostDate, t.active, t.views)
			 from Topic t
			 where t.id = ?1
			 """)
	Optional<TopicDTO> findTopicById(Long id);


//
//	Optional<Topic> findById(Long id);
//
//	void deleteById(Long id);
	
}
