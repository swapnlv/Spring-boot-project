package com.smart.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name="USER")
public class User {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int U_id;
	@NotBlank(message="Name Field cannot be empty")
	@Size(min=2, max=50)
	private String name;
	@Column(unique=true)
	@Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	@NotBlank(message="Email fielld cannot be empty ")
	private String email;
	private String  password;
	private boolean enabled;
	private String imageURL;
	private String role;
	@Column(length=1000)
	private String about;
	
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="user")
	private List<Contact> contacts=new ArrayList<>();
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getU_id() {
		return U_id;
	}
	public void setU_id(int u_id) {
		U_id = u_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public List<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}
	@Override
	public String toString() {
		return "User [U_id=" + U_id + ", name=" + name + ", email=" + email + ", password=" + password + ", enabled="
				+ enabled + ", imageURL=" + imageURL + ", about=" + about + ", contacts=" + contacts + "]";
	}
	
	
	
} 
