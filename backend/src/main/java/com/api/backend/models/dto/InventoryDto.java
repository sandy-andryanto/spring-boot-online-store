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

public class InventoryDto {

	private Long Id;
	private Long ProductId;
	private Long SizeId;
	private Long ColourId;
	private int Stock;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getProductId() {
		return ProductId;
	}

	public void setProductId(Long productId) {
		ProductId = productId;
	}

	public Long getSizeId() {
		return SizeId;
	}

	public void setSizeId(Long sizeId) {
		SizeId = sizeId;
	}

	public Long getColourId() {
		return ColourId;
	}

	public void setColourId(Long colourId) {
		ColourId = colourId;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

}
