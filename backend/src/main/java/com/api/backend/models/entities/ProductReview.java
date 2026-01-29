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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
		name = "products_reviews", 
		indexes = {
			@Index(columnList = "Rating"),
			@Index(columnList = "Status"),
			@Index(columnList = "CreatedAt"), 
			@Index(columnList = "UpdatedAt")
		})
public class ProductReview {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
    @JoinColumn(name="product_id", nullable=true)
    @JsonIgnore
    private Product Product;

	@ManyToOne
    @JoinColumn(name="user_id", nullable=true)
    @JsonIgnore
    private User User;
	
	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int Rating;

	@Column(nullable = true, columnDefinition = "text")
	private String Review;

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

	public Product getProduct() {
		return Product;
	}

	public void setProduct(Product product) {
		Product = product;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public int getRating() {
		return Rating;
	}

	public void setRating(int rating) {
		Rating = rating;
	}

	public String getReview() {
		return Review;
	}

	public void setReview(String review) {
		Review = review;
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
	
	

}