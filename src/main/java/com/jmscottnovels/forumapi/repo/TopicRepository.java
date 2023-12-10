package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.model.TopicDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

	List<Topic> findByCreatedById(Long id);
	@Query("""
			 select new com.jmscottnovels.forumapi.model.TopicDTO(
			 t.id, t.createdBy.id, t.createdBy.firstName, t.createdBy.lastName, 
			 t.lastPostBy.id, t.lastPostBy.firstName, t.lastPostBy.lastName, 
			 t.title, t.description, t.createdDate, t.lastPostDate, t.active, t.views)
			 from Topic t 
			 order by t.lastPostDate desc
			 """)
	List<TopicDTO> findAllTopics();

	@Query("""
			select new com.jmscottnovels.forumapi.model.TopicDTO(
			t.id, t.createdBy.id, t.createdBy.firstName, t.createdBy.lastName, 
			t.lastPostBy.id, t.lastPostBy.firstName, t.lastPostBy.lastName, 
			t.title, t.description, t.createdDate, t.lastPostDate, t.active, t.views)
			from Topic t
			where t.lastPostDate > cast(:startDate AS TIMESTAMP)
				and t.lastPostDate < cast(:endDate AS TIMESTAMP) 
			order by t.lastPostDate desc
			 """)
	List<TopicDTO> findAllTopics(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("""
			 select new com.jmscottnovels.forumapi.model.TopicDTO(
			 t.id, t.createdBy.id, t.createdBy.firstName, t.createdBy.lastName, 
			 t.lastPostBy.id, t.lastPostBy.firstName, t.lastPostBy.lastName, 
			 t.title, t.description, t.createdDate, t.lastPostDate, t.active, t.views)
			 from Topic t
			 where t.id = ?1 
			 order by t.lastPostDate desc
			 """)
	Optional<TopicDTO> findTopicById(Long id);




//
//	Optional<Topic> findById(Long id);
//
//	void deleteById(Long id);
	
}
