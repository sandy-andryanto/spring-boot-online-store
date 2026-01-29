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

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;

public class DetailOrderDto {

	private Long Id;
	private String InvoiceNumber;
	private String PaymentName;
	private Double Subtotal;
	private Double TotalDiscount;
	private Double TotalTaxes;
	private Double TotalShipment;
	private Double TotalPaid;
	private int Status;
	private Date CreatedAt;
	private Date UpdatedAt;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public String getPaymentName() {
		return PaymentName;
	}

	public void setPaymentName(String paymentName) {
		PaymentName = paymentName;
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
