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

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Table(name = "activities", indexes = { @Index(columnList = "UserId"), @Index(columnList = "Subject"), @Index(columnList = "Event"), @Index(columnList = "Status"),
		@Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })

public class Activity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=true)
    @JsonIgnore
    private User User;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Event;
	
	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Subject;

	@Column(nullable = false, columnDefinition = "text")
	private String Description;
	
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

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
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

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}
	
	

}
