package com.jmscottnovels.forumapi.controller;

import com.jmscottnovels.forumapi.exception.ResourceNotFoundException;
import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.model.TopicDTO;
import com.jmscottnovels.forumapi.repo.TopicRepository;
import com.jmscottnovels.forumapi.repo.UserRepository;
import com.jmscottnovels.forumapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
	public List<TopicDTO> getAllTopics (@RequestParam(required = false, defaultValue = "0") Integer recentNum) {
//		QTopic topic = new QTopic("topic");
//		BooleanExpression filterByRecent =  topic.

		if(recentNum != null && recentNum != 0) {
			//return topicService.buildTopicList(recentNum);
			return topicRepository.findAllTopics();
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
	
//	@PostMapping(path = "/topics")
//	public ResponseEntity<Void> createForumTopic (@Validated @RequestBody Topic topic) throws ResourceNotFoundException {
//		topic.setId(null);
//		topic.setCreatedDate(new Date());		// new topic. set create date here ... we don't want to default this in the constructor
//		topic.setLastPostDate(new Date());	// set immediately before db write
//		topic.setActive(true);
//
//		User createdBy = userRepository.findById(topic.getCreatedBy().getId()).orElseThrow(
//				() -> new ResourceNotFoundException("No user with id: " + topic.getCreatedBy().getId()));
//		User lastPostBy = userRepository.findById(topic.getLastPostBy().getId()).orElseThrow(
//				() -> new ResourceNotFoundException("No user with id: " + topic.getCreatedBy().getId()));
//
//		topic.setCreatedBy(createdBy);
//		topic.setLastPostBy(lastPostBy);
//
//		Topic newTopic = topicRepository.save(topic);
//
//		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//			.buildAndExpand(newTopic.getId()).toUri();
//		return ResponseEntity.created(uri).build();
//	}
//
	@PutMapping(path = "/topics/{id}")
	public ResponseEntity<Topic> updateForumTopic (
			@PathVariable Long id,
			@Validated @RequestBody Topic topic) throws ResourceNotFoundException {

		topicRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("No topic with id: " + id));

		topic.setId(id);
		topic.setLastPostDate(new Date());	// set immediately before db write
		final Topic newTopic = topicRepository.save(topic);

		return new ResponseEntity<>(newTopic, HttpStatus.OK);

	}
//
//	@DeleteMapping(path = "/topics/{id}")
//	public ResponseEntity<Topic> deleteForumTopic (@PathVariable Long id) throws ResourceNotFoundException {
//
//		Topic deletedTopic = topicRepository.findById(id).orElseThrow(
//				() -> new ResourceNotFoundException("No topic with id: " + id));
//
//		topicRepository.deleteById(id);
//		return new ResponseEntity<Topic>(deletedTopic, HttpStatus.OK);
//
//	}

}
