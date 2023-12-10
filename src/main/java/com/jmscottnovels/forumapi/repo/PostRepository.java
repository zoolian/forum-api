package com.jmscottnovels.forumapi.repo;

import com.jmscottnovels.forumapi.model.Post;
import com.jmscottnovels.forumapi.model.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findByCreatedById(Long id);
	@Query("""
			 select new com.jmscottnovels.forumapi.model.PostDTO(
			 p.id, p.createdBy.id, p.createdBy.firstName, p.createdBy.lastName, 
			 p.title, p.content, p.createdDate, p.lastPostDate, p.active, p.views)
			 from Post p 
			 order by p.lastPostDate desc
			 """)
	List<PostDTO> findAllPosts();

	@Query("""
			select new com.jmscottnovels.forumapi.model.PostDTO(
			p.id, p.createdBy.id, p.createdBy.firstName, p.createdBy.lastName, 
			p.title, p.content, p.createdDate, p.lastPostDate, p.active, p.views)
			from Post p
			where p.lastPostDate > cast(:startDate AS TIMESTAMP)
				and p.lastPostDate < cast(:endDate AS TIMESTAMP) 
			order by p.lastPostDate desc
			 """)
	List<PostDTO> findAllPosts(@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query("""
			 select new com.jmscottnovels.forumapi.model.PostDTO(
			 p.id, p.createdBy.id, p.createdBy.firstName, p.createdBy.lastName, 
			 p.title, p.content, p.createdDate, p.lastPostDate, p.active, p.views)
			 from Post p
			 where p.id = ?1 
			 order by p.lastPostDate desc
			 """)
	Optional<PostDTO> findPostById(Long id);

	
}
