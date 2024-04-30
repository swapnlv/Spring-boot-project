package com.smart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="Contacts")
public class Contact {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int C_id;
	@NotBlank(message="Name Field cannot be empty")
	@Size(min=2, max=50)
	private String name;
	private String nickName;
	private String work;
	@Column(unique=true)
	@Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	@NotBlank(message="Email fielld cannot be empty ")
	private String email;
	@NotBlank(message="Phone number field cannot be empty ")
	private String phone;
	private String image;
	
	@Column(length=50000)
	@NotBlank(message="Description required ")
	private String description;

	@ManyToOne
	private User user;
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int c_id, String name, String nickName, String work, String email, String phone, String image,
			String description) {
		super();
		C_id = c_id;
		this.name = name;
		this.nickName = nickName;
		this.work = work;
		this.email = email;
		this.phone = phone;
		this.image = image;
		this.description = description;
	}

	public int getC_id() {
		return C_id;
	}

	public void setC_id(int c_id) {
		C_id = c_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	
}
