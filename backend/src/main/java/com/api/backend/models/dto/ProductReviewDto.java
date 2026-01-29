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

package com.api.backend.models.dto;

import java.util.Date;

public class ProductReviewDto {

	private Long Id;
	private String FirstName;
	private String LastName;
	private String ReviewDescription;
	private double Rating;
	private double Percentage;
	private Date CreatedAt;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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

	public String getReviewDescription() {
		return ReviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		ReviewDescription = reviewDescription;
	}

	public double getRating() {
		return Rating;
	}

	public void setRating(double rating) {
		Rating = rating;
	}

	public double getPercentage() {
		return Percentage;
	}

	public void setPercentage(double percentage) {
		Percentage = percentage;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

}
