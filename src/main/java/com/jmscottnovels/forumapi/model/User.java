package com.jmscottnovels.forumapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

// CONSIDER: Create a separate object and collection for passwords

//@QueryEntity
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
	private List<Topic> topicsCreated;

	@OneToMany(mappedBy = "lastPostBy")
	@JsonManagedReference(value = "topic-posts")
	private List<Topic> topicPosts;

	@ManyToMany
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
					name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public List<Topic> getTopicsCreated() {
		return topicsCreated;
	}

	public void setTopicsCreated(List<Topic> topicsCreated) {
		this.topicsCreated = topicsCreated;
	}

	public List<Topic> getTopicPosts() {
		return topicPosts;
	}

	public void setTopicPosts(List<Topic> topicPosts) {
		this.topicPosts = topicPosts;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", tokenExpired=" + tokenExpired +
				", roles=" + roles +
				'}';
	}
}
