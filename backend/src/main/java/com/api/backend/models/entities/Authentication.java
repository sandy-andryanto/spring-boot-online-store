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

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "authentications", indexes = { @Index(columnList = "UserId"), @Index(columnList = "AuthType"), @Index(columnList = "Credential"), @Index(columnList = "Status"),
		@Index(columnList = "Token"),@Index(columnList = "ExpiredAt"),
		@Index(columnList = "CreatedAt"), @Index(columnList = "UpdatedAt") })
public class Authentication {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@ManyToOne
    @JoinColumn(name="user_id", nullable=true)
    @JsonIgnore
    private User User;
	
	@Column(nullable = false, columnDefinition = "varchar(150)")
	private String AuthType;
	
	@Column(nullable = false, columnDefinition = "varchar(180)")
	private String Credential;
	
	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String Token;
	
	@Column(nullable = true)
	private Date ExpiredAt;
	
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

	public String getAuthType() {
		return AuthType;
	}

	public void setAuthType(String authType) {
		AuthType = authType;
	}

	public String getCredential() {
		return Credential;
	}

	public void setCredential(String credential) {
		Credential = credential;
	}

	public String getToken() {
		return Token;
	}

	public void setToken(String token) {
		Token = token;
	}

	public Date getExpiredAt() {
		return ExpiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		ExpiredAt = expiredAt;
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
