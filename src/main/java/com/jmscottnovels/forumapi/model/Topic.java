package com.jmscottnovels.forumapi.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "created_by_id")
	@JsonBackReference(value = "topics-created")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "last_post_by_id")
	@JsonBackReference(value = "topic-posts")
	private User lastPostBy;

	private String title;

	private String description;
	
	private Date createdDate;
	private Date lastPostDate;
	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getLastPostBy() {
		return lastPostBy;
	}

	public void setLastPostBy(User lastPostBy) {
		this.lastPostBy = lastPostBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastPostDate() {
		return lastPostDate;
	}

	public void setLastPostDate(Date lastPostDate) {
		this.lastPostDate = lastPostDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getReplies() {		return replies;	}

	public void setReplies(int replies) {		this.replies = replies;	}

	public int getViews() {		return views;	}

	public void setViews(int views) {		this.views = views;	}

	protected Topic() {}

	public Topic(Long id, User createdBy, User lastPostBy, String title, String description, String content, Date createDate, Date lastPostDate,
				 boolean active) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.lastPostBy = lastPostBy;
		this.title = title;
		this.description = description;
		this.createdDate = createDate;
		this.lastPostDate = lastPostDate;
		this.active = active;
	}

	public Topic(User createdBy, String title, String description) {
		this.createdBy = createdBy;
		this.title = title;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "Topic{" +
				"id=" + id +
				", createdBy=" + createdBy.toString() +
				", lastPostBy=" + lastPostBy.toString() +
				", title='" + title + '\'' +
				", description='" + description + '\'' +
				", createdDate=" + createdDate +
				", lastPostDate=" + lastPostDate +
				", active=" + active +
				", replies=" + replies +
				", views=" + views +
				'}';
	}
}
