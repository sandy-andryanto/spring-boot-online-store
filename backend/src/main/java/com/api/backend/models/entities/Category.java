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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories", indexes = { @Index(columnList = "Image"), @Index(columnList = "Name"),
		@Index(columnList = "Status"), @Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Image;

	@Column(nullable = true, columnDefinition = "varchar(255)")
	private String Name;

	@Column(nullable = true, columnDefinition = "text")
	private String Description;

	@ColumnDefault("0")
	@Column(columnDefinition = "int2")
	private int Displayed;

	@ColumnDefault("0")
	@Column(columnDefinition = "int2")
	private int Status;

	@Column(nullable = true)
	private Date CreatedAt;

	@Column(nullable = true)
	private Date UpdatedAt;

	@ManyToMany(mappedBy = "categories")
	private Set<Product> products = new HashSet<>();

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getDisplayed() {
		return Displayed;
	}

	public void setDisplayed(int displayed) {
		Displayed = displayed;
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
