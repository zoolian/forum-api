package com.jmscottnovels.forumapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "post")
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "created_by_id")
	@JsonBackReference(value = "posts-created")
	private User createdBy;

	private String title;

	private String content;

	private Date createdDate;
	private Date lastPostDate;

	@OneToOne
	@JoinColumn(name = "parent_post_id")
	private Post parentPost;

	private boolean active = true;

	@Transient
	private int replies = 0;

	private int views = 0;


	public Post(Long id, User createdBy, String title, String content, Date createDate, Date lastPostDate,
                boolean active) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.title = title;
		this.content = content;
		this.createdDate = createDate;
		this.lastPostDate = lastPostDate;
		this.active = active;
	}

	public Post(User createdBy, String title, String content) {
		this.createdBy = createdBy;
		this.title = title;
		this.content = content;
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
		Post other = (Post) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}

	@Override
	public String toString() {
		return "Topic{" +
				"id=" + id +
				", title='" + title + '\'' +
				", content='" + content + '\'' +
				", createdDate=" + createdDate +
				", lastPostDate=" + lastPostDate +
				", active=" + active +
				", replies=" + replies +
				", views=" + views +
				'}';
	}
}
