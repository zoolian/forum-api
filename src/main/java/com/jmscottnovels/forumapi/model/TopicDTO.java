package com.jmscottnovels.forumapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

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

	private String title;

	private String description;

	private Date createdDate;
	private Date lastPostDate;
	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;

	public TopicDTO(Long id, Long createdById, String createdByFirstName, String createdByLastName, String title, String description, Date createdDate, Date lastPostDate, boolean active) {
		this.id = id;
		this.createdById = createdById;
		this.createdByFirstName = createdByFirstName;
		this.createdByLastName = createdByLastName;
		this.title = title;
		this.description = description;
		this.createdDate = createdDate;
		this.lastPostDate = lastPostDate;
		this.active = active;
//		this.createdBy = new UserDTO(createdById, createdByFirstName, createdByLastName);
//		this.createdBy.setId(createdById);
//		this.createdBy.setFirstName(createdByFirstName);
//		this.createdBy.setLastName(createdByLastName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TopicDTO topicDTO = (TopicDTO) o;
		return active == topicDTO.active && replies == topicDTO.replies && views == topicDTO.views && Objects.equals(id, topicDTO.id) && Objects.equals(createdById, topicDTO.createdById) && Objects.equals(createdByFirstName, topicDTO.createdByFirstName) && Objects.equals(createdByLastName, topicDTO.createdByLastName) && Objects.equals(title, topicDTO.title) && Objects.equals(description, topicDTO.description) && Objects.equals(createdDate, topicDTO.createdDate) && Objects.equals(lastPostDate, topicDTO.lastPostDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createdById, createdByFirstName, createdByLastName, title, description, createdDate, lastPostDate, active, replies, views);
	}
}
