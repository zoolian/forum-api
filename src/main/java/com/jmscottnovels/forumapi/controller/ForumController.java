package com.jmscottnovels.forumapi.controller;

import com.jmscottnovels.forumapi.exception.ResourceNotFoundException;
import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.model.TopicDTO;
import com.jmscottnovels.forumapi.model.User;
import com.jmscottnovels.forumapi.repo.TopicRepository;
import com.jmscottnovels.forumapi.repo.UserRepository;
import com.jmscottnovels.forumapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
public class ForumController {
	
	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private TopicService topicService;

	@Autowired
	private UserRepository userRepository;

	@GetMapping(path = "/")
	public ResponseEntity<String> getHealth () {

		return ResponseEntity.ok().body("OK");
	}

	@GetMapping(path = "/topics/createdBy/{id}")
	public List<Topic> getTopicsByCreatedBy (@PathVariable Long id) throws ResourceNotFoundException {
		userRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No user with id: " + id));
		return topicRepository.findByCreatedById(id);
	}
	
	@GetMapping(path = "/topics")
	@PreAuthorize("hasAuthority('SCOPE_forum')")
	public List<TopicDTO> getAllTopics (
			@RequestParam(required = false, defaultValue = "2020-01-01")  Optional<String> optionalStartDate
			, @RequestParam(required = false) Optional<String> optionalEndDate) {

		String endDate = optionalEndDate.orElse(new Date().toString());

		if(optionalStartDate.isPresent()) {
			//return topicService.buildTopicList(recentNum);
			return topicRepository.findAllTopics(optionalStartDate.get(), endDate);
		}
		//return topicService.buildTopicList(0);
		return topicRepository.findAllTopics();
	}
	
	@GetMapping(path = "/topics/{id}")
	public ResponseEntity<TopicDTO> getTopic (@PathVariable Long id) throws ResourceNotFoundException {
		
		TopicDTO topic = topicRepository.findTopicById(id).orElseThrow(
				() -> new ResourceNotFoundException("No topic with id: " + id));
		
		return ResponseEntity.ok().body(topic);
	}
	
	@PostMapping(path = "/topics")
	public ResponseEntity<String> createForumTopic (@Validated @RequestBody Topic topic) throws ResourceNotFoundException {

		User createdBy = userRepository.findById(topic.getCreatedBy().getId()).orElseThrow(
				() -> new ResourceNotFoundException("No user with id: " + topic.getCreatedBy().getId()));
		User lastPostBy = userRepository.findById(topic.getLastPostBy().getId()).orElseThrow(
				() -> new ResourceNotFoundException("No user with id: " + topic.getLastPostBy().getId()));

		topic.setCreatedBy(createdBy);
		topic.setLastPostBy(lastPostBy);

		topic.setId(null);
		topic.setCreatedDate(new Date());		// new topic. set create date here ... we don't want to default this in the constructor
		topic.setLastPostDate(new Date());	// set immediately before db write
		topic.setActive(true);

		Topic newTopic = topicRepository.save(topic);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(newTopic.getId()).toUri();

		return ResponseEntity.created(uri).header("NewID",newTopic.getId().toString()).build();
	}

	@PutMapping(path = "/topics/{id}")
	public ResponseEntity<Topic> updateForumTopic (
			@PathVariable Long id,
			@Validated @RequestBody Topic topic) throws ResourceNotFoundException {

		Topic oldTopic = topicRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No topic with id: " + id));

		topic.setId(id);
		topic.setLastPostDate(oldTopic.getLastPostDate()); // don't change these
		topic.setCreatedDate(oldTopic.getCreatedDate());
		topic.setCreatedBy(oldTopic.getCreatedBy());
		topic.setLastPostBy(oldTopic.getLastPostBy());

		final Topic newTopic = topicRepository.save(topic);

		return new ResponseEntity<>(newTopic, HttpStatus.OK);

	}

	@DeleteMapping(path = "/topics/{id}")
	public ResponseEntity<Topic> deleteForumTopic (@PathVariable Long id) throws ResourceNotFoundException {

		Topic deletedTopic = topicRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No topic with id: " + id));

		topicRepository.deleteById(id);
		return new ResponseEntity<>(deletedTopic, HttpStatus.OK);

	}

}
