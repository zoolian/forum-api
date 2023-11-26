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
			where t.lastPostDate > cast(:startDate AS TIMESTAMP)
				and t.lastPostDate < cast(:endDate AS TIMESTAMP)
			 """)
	List<TopicDTO> findAllTopics(@Param("startDate") String startDate, @Param("endDate") String endDate);

	// no need to top, go back to jpql new!!!!!
//	@Query(nativeQuery = true, value = """
//			select t.id, t.title, t.description, t.created_date as createdDate, t.last_post_date lastPostDate, t.active, t.views,
//				u.id as createdById,
//				u.first_name as createdByFirstName,
//				u.last_name as createdByLastName
//			from topic t
//			join user_account u
//				on t.created_by_id = u.id
//			where t.last_post_date > cast(:startDate AS TIMESTAMP)
//				and t.last_post_date < cast(:endDate AS TIMESTAMP)
//			"""
//			)
//	List<TopicDTO> findAllTopics(@Param("startDate") String startDate, @Param("endDate") String endDate);

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
