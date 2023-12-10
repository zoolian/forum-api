package com.jmscottnovels.forumapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
public class TopicDTO {

	private Long id;

//	private UserDTO createdBy;
//
//	private UserDTO lastPostBy;
	private Long createdById;

	private String createdByFirstName;

	private String createdByLastName;

	private Long lastPostById;

	private String lastPostByFirstName;

	private String lastPostByLastName;

	private String title;

	private String description;

	private String createdDate;
	private String lastPostDate;
	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;


	public TopicDTO(Long id, Long createdById, String createdByFirstName, String createdByLastName,
					Long lastPostById, String lastPostByFirstName, String lastPostByLastName,
					String title, String description, Date createdDate, Date lastPostDate, boolean active, int views) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M-dd-yy h:mm a");

		this.id = id;
		this.createdById = createdById;
		this.createdByFirstName = createdByFirstName;
		this.createdByLastName = createdByLastName;
		this.lastPostById = lastPostById;
		this.lastPostByFirstName = lastPostByFirstName;
		this.lastPostByLastName = lastPostByLastName;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateTimeFormatter);
		this.lastPostDate = lastPostDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateTimeFormatter);
		this.active = active;
		this.views = views;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TopicDTO topicDTO = (TopicDTO) o;
		return active == topicDTO.active && replies == topicDTO.replies && views == topicDTO.views && Objects.equals(id, topicDTO.id) && Objects.equals(createdById, topicDTO.createdById) && Objects.equals(createdByFirstName, topicDTO.createdByFirstName) && Objects.equals(createdByLastName, topicDTO.createdByLastName) && Objects.equals(lastPostById, topicDTO.lastPostById) && Objects.equals(lastPostByFirstName, topicDTO.lastPostByFirstName) && Objects.equals(lastPostByLastName, topicDTO.lastPostByLastName) && Objects.equals(title, topicDTO.title) && Objects.equals(description, topicDTO.description) && Objects.equals(createdDate, topicDTO.createdDate) && Objects.equals(lastPostDate, topicDTO.lastPostDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createdById, createdByFirstName, createdByLastName, lastPostById, lastPostByFirstName, lastPostByLastName, title, description, createdDate, lastPostDate, active, replies, views);
	}
}
