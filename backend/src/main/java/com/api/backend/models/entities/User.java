/**
 * This file is part of the Sandy Andryanto Online Store Website.
 *
 * @author     Sandy Andryanto <sandy.andryanto.official@gmail.com>
 * @copyright  2025
 *
 * For the full copyright and license information,
 * please view the LICENSE.md file that was distributed
 * with this source code.
 */

package com.api.backend.models.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = { @Index(columnList = "Email"), @Index(columnList = "Password"),
		@Index(columnList = "Phone"), @Index(columnList = "Image"), @Index(columnList = "FirstName"),
		@Index(columnList = "ZipCode"), @Index(columnList = "LastName"), @Index(columnList = "Gender"),
		@Index(columnList = "Country"), @Index(columnList = "City"), @Index(columnList = "Status"),
		@Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToMany(mappedBy = "carts")
	private Set<Order> carts = new HashSet<>();

	@ManyToMany(mappedBy = "wishlists")
	private Set<Product> wishlists = new HashSet<>();

	@Column(nullable = false, columnDefinition = "varchar(180)", unique = true)
	private String Email;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String Password;

	@Column(nullable = true, columnDefinition = "varchar(64)", unique = true)
	private String Phone;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Image;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String FirstName;

	@Column(nullable = true, columnDefinition = "varchar(191)")
	private String LastName;

	@Column(nullable = true, columnDefinition = "varchar(2)")
	private String Gender;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Country;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String City;

	@Column(nullable = true, columnDefinition = "varchar(100)")
	private String ZipCode;

	@Column(nullable = true, columnDefinition = "text")
	private String Address;

	@ColumnDefault("0")
	@Column(columnDefinition = "int2")
	private int Status;

	@Column(nullable = true)
	private Date CreatedAt;

	@Column(nullable = true)
	private Date UpdatedAt;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public Date getUpdatedAt() {
		return UpdatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		UpdatedAt = updatedAt;
	}

	public Set<Product> getWishlists() {
		return wishlists;
	}

	public void setWishlists(Set<Product> wishlists) {
		this.wishlists = wishlists;
	}

	public Set<Order> getCarts() {
		return carts;
	}

	public void setCarts(Set<Order> carts) {
		this.carts = carts;
	}

	public String getZipCode() {
		return ZipCode;
	}

	public void setZipCode(String zipCode) {
		ZipCode = zipCode;
	}

}
