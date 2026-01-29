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

import com.api.backend.helpers.Helper;
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
@Table(name = "orders_billings", indexes = { @Index(columnList = "Status"), @Index(columnList = "CreatedAt"),
		@Index(columnList = "UpdatedAt") })
public class OrderBilling {
	
	public OrderBilling() {}

	public OrderBilling(Order Order, String Name, String Description) {
		try {
			setOrder(Order);
			setName(Name);
			setDescription(Description);
			setStatus(1);
			setCreatedAt(Helper.DateNow());
			setUpdatedAt(Helper.DateNow());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = true)
	@JsonIgnore
	private Order Order;

	@Column(nullable = false, columnDefinition = "varchar(255)")
	private String Name;

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

	public Order getOrder() {
		return Order;
	}

	public void setOrder(Order order) {
		Order = order;
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