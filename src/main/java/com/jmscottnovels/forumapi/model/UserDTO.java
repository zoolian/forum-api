package com.jmscottnovels.forumapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDTO {

	private Long id;

	private String firstName;
	private String lastName;
	private String email;

	public UserDTO(Long id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}


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

	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return Objects.equals(id, userDTO.id) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(email, userDTO.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, email);
	}
}
