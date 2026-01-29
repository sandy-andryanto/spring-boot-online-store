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

package com.api.backend.models.schema;

public class CreateCartSchema {

	private long colourId;
	private double price;
	private int qty;
	private long sizeId;
	private double total;

	public long getColourId() {
		return colourId;
	}

	public void setColourId(long colourId) {
		this.colourId = colourId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public long getSizeId() {
		return sizeId;
	}

	public void setSizeId(long sizeId) {
		this.sizeId = sizeId;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

}
