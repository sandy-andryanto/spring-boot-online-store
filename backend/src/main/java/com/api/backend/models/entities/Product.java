/**
 * This file is part of the Sandy Andryanto Online Store Website.
 *
 * @author     Sandy Andryanto <sandy.andryanto.blade@gmail.com>
 * @copyright  2025
 *
 * For the full copyright and license information,
 * please view the LICENSE.md file that was distributed
 * with this source code.
 */

package com.api.backend.models.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products", indexes = { @Index(columnList = "Image"), @Index(columnList = "Sku"),
		@Index(columnList = "Name"), @Index(columnList = "Price"), @Index(columnList = "TotalOrder"),
		@Index(columnList = "TotalRating"), @Index(columnList = "PublishedDate"), @Index(columnList = "Status"),
		@Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToMany
	@JoinTable(name = "products_categories", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "products_wishlists", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> wishlists = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = true)
	@JsonIgnore
	private Brand Brand;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Image;

	@Column(nullable = false, columnDefinition = "varchar(64)")
	private String Sku;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Name;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double Price;

	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int TotalOrder;

	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int TotalRating;

	@Column(nullable = true)
	private Date PublishedDate;

	@Column(nullable = true, columnDefinition = "text")
	private String Description;

	@Column(nullable = true, columnDefinition = "text")
	private String Details;

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

	public Brand getBrand() {
		return Brand;
	}

	public void setBrand(Brand brand) {
		Brand = brand;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getSku() {
		return Sku;
	}

	public void setSku(String sku) {
		Sku = sku;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}

	public int getTotalOrder() {
		return TotalOrder;
	}

	public void setTotalOrder(int totalOrder) {
		TotalOrder = totalOrder;
	}

	public int getTotalRating() {
		return TotalRating;
	}

	public void setTotalRating(int totalRating) {
		TotalRating = totalRating;
	}

	public Date getPublishedDate() {
		return PublishedDate;
	}

	public void setPublishedDate(Date publishedDate) {
		PublishedDate = publishedDate;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<User> getWishlists() {
		return wishlists;
	}

	public void setWishlists(Set<User> wishlists) {
		this.wishlists = wishlists;
	}

	

}