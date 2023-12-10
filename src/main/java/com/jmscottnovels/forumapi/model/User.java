package com.jmscottnovels.forumapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.List;

// CONSIDER: Create a separate object and collection for passwords

//@QueryEntity
@Data
@Entity
@Table(name = "user_account")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean enabled;
	private boolean tokenExpired;
	@OneToMany(mappedBy = "createdBy")
	@JsonManagedReference(value = "topics-created")
	private List<Topic> topics;

	@OneToMany(mappedBy = "createdBy")
	@JsonManagedReference(value = "posts-created")
	private List<Post> posts;

	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

}
