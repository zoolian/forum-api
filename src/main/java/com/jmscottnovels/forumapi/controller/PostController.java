package com.jmscottnovels.forumapi.controller;

import com.jmscottnovels.forumapi.exception.ResourceNotFoundException;
import com.jmscottnovels.forumapi.model.Post;
import com.jmscottnovels.forumapi.model.PostDTO;
import com.jmscottnovels.forumapi.model.User;
import com.jmscottnovels.forumapi.repo.PostRepository;
import com.jmscottnovels.forumapi.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = {"http://localhost:3002", "http://localhost:3000", "https://security.jmscottnovels.com", "https://forumapi.jmscottnovels.com"}, allowCredentials = "true")
public class PostController {
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;


	@GetMapping(path = "/posts/createdBy/{id}")
	public List<Post> getPostsByCreatedBy (@PathVariable Long id) throws ResourceNotFoundException {
		userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No user with id: " + id));
		return postRepository.findByCreatedById(id);
	}
	
	@GetMapping(path = "/posts")
	@PreAuthorize("hasAuthority('SCOPE_forum')")
	public List<PostDTO> getAllPosts (
			@RequestParam(required = false, defaultValue = "2020-01-01")  Optional<String> optionalStartDate
			, @RequestParam(required = false) Optional<String> optionalEndDate) {

		String endDate = optionalEndDate.orElse(new Date().toString());

		if(optionalStartDate.isPresent()) {
			//return postService.buildPostList(recentNum);
			return postRepository.findAllPosts(optionalStartDate.get(), endDate);
		}
		//return postService.buildPostList(0);
		return postRepository.findAllPosts();
	}
	
	@GetMapping(path = "/posts/{id}")
	public ResponseEntity<PostDTO> getPost (@PathVariable Long id) throws ResourceNotFoundException {
		
		PostDTO post = postRepository.findPostById(id).orElseThrow(
				() -> new ResourceNotFoundException("No post with id: " + id));
		
		return ResponseEntity.ok().body(post);
	}

	// TODO: make sure and back-update topic with last post user, for both created and update
	@PostMapping(path = "/posts")
	public ResponseEntity<String> createForumPost (@Validated @RequestBody Post post) throws ResourceNotFoundException {

		User createdBy = userRepository.findById(post.getCreatedBy().getId()).orElseThrow(
				() -> new ResourceNotFoundException("No user with id: " + post.getCreatedBy().getId()));

		post.setCreatedBy(createdBy);

		post.setId(null);
		post.setCreatedDate(new Date());		// new post. set create date here ... we don't want to default this in the constructor
		post.setActive(true);

		Post newPost = postRepository.save(post);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newPost.getId()).toUri();

		return ResponseEntity.created(uri).header("NewID",newPost.getId().toString()).build();
	}

	@PutMapping(path = "/posts/{id}")
	public ResponseEntity<Post> updateForumPost (
			@PathVariable Long id,
			@Validated @RequestBody Post post) throws ResourceNotFoundException {

		Post oldPost = postRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No post with id: " + id));

		post.setId(id);
		post.setLastPostDate(oldPost.getLastPostDate()); // don't change these
		post.setCreatedDate(oldPost.getCreatedDate());
		post.setCreatedBy(oldPost.getCreatedBy());

		final Post newPost = postRepository.save(post);

		return new ResponseEntity<>(newPost, HttpStatus.OK);

	}

	@DeleteMapping(path = "/posts/{id}")
	public ResponseEntity<Post> deleteForumPost (@PathVariable Long id) throws ResourceNotFoundException {

		Post deletedPost = postRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No post with id: " + id));

		postRepository.deleteById(id);
		return new ResponseEntity<>(deletedPost, HttpStatus.OK);

	}

}
