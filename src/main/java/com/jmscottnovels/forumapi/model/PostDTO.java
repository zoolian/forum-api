package com.jmscottnovels.forumapi.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
public class PostDTO {

	private Long id;

	private Long createdById;

	private String createdByFirstName;

	private String createdByLastName;


	private String title;

	private String content;

	private String createdDate;
	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;


	public PostDTO(Long id, Long createdById, String createdByFirstName, String createdByLastName,
                   String title, String content, Date createdDate, boolean active, int views) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M-dd-yy h:mm a");

		this.id = id;
		this.createdById = createdById;
		this.createdByFirstName = createdByFirstName;
		this.createdByLastName = createdByLastName;
		this.title = title;
		this.content = content;
		this.createdDate = createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dateTimeFormatter);
		this.active = active;
		this.views = views;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PostDTO postDTO = (PostDTO) o;
		return active == postDTO.active && replies == postDTO.replies && views == postDTO.views && Objects.equals(id, postDTO.id) && Objects.equals(createdById, postDTO.createdById) && Objects.equals(createdByFirstName, postDTO.createdByFirstName) && Objects.equals(createdByLastName, postDTO.createdByLastName) && Objects.equals(title, postDTO.title) && Objects.equals(content, postDTO.content) && Objects.equals(createdDate, postDTO.createdDate);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, createdById, createdByFirstName, createdByLastName, title, content, createdDate, active, replies, views);
	}
}
