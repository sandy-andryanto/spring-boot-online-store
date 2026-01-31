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
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders", indexes = { @Index(columnList = "Status"), @Index(columnList = "CreatedAt"),
		@Index(columnList = "UpdatedAt") })
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToMany
	@JoinTable(name = "orders_carts", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> carts = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "payment_id", nullable = true)
	@JsonIgnore
	private Payment Payment;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	@JsonIgnore
	private User User;

	@Column(nullable = false, columnDefinition = "varchar(100)")
	private String InvoiceNumber;

	@ColumnDefault("0")
	@Column(columnDefinition = "int4")
	private int TotalItem;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double Subtotal;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double TotalDiscount;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double TotalTaxes;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double TotalShipment;

	@ColumnDefault("0")
	@Column(nullable = false, columnDefinition = "decimal(18,4)")
	private Double TotalPaid;

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

	public Set<User> getCarts() {
		return carts;
	}

	public void setCarts(Set<User> carts) {
		this.carts = carts;
	}

	public Payment getPayment() {
		return Payment;
	}

	public void setPayment(Payment payment) {
		Payment = payment;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public int getTotalItem() {
		return TotalItem;
	}

	public void setTotalItem(int totalItem) {
		TotalItem = totalItem;
	}

	public Double getSubtotal() {
		return Subtotal;
	}

	public void setSubtotal(Double subtotal) {
		Subtotal = subtotal;
	}

	public Double getTotalDiscount() {
		return TotalDiscount;
	}

	public void setTotalDiscount(Double totalDiscount) {
		TotalDiscount = totalDiscount;
	}

	public Double getTotalTaxes() {
		return TotalTaxes;
	}

	public void setTotalTaxes(Double totalTaxes) {
		TotalTaxes = totalTaxes;
	}

	public Double getTotalShipment() {
		return TotalShipment;
	}

	public void setTotalShipment(Double totalShipment) {
		TotalShipment = totalShipment;
	}

	public Double getTotalPaid() {
		return TotalPaid;
	}

	public void setTotalPaid(Double totalPaid) {
		TotalPaid = totalPaid;
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