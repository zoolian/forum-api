package com.jmscottnovels.forumapi.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
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

//	@ManyToOne
//	@JoinColumn(name = "last_post_by_id")
//	@JsonBackReference(value = "topic-posts")
	@OneToOne
	@JoinColumn(name = "last_post_by_id")
	private User lastPostBy;

	@OneToMany
	private List<Post> posts;

	private String title;

	private String description;
	
	private Date createdDate;
	private Date lastPostDate;
	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;


	protected Topic() {}

	public Topic(Long id, User createdBy, User lastPostBy, String title, String description, Date createDate, Date lastPostDate,
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
