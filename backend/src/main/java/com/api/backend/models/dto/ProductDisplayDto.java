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

package com.api.backend.models.dto;

import java.util.List;

public class ProductDisplayDto {

	private Long Id;
	private String Image;
	private String Name;
	private String Description;
	private String Details;
	private Double Price;
	private Double PriceOld;
	private String CategoryName;
	private List<String> Categories;
	private Boolean IsNewest;
	private Boolean IsDiscount;
	private Double TotalRating;

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

	public String getDetails() {
		return Details;
	}

	public void setDetails(String details) {
		Details = details;
	}

	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double price) {
		Price = price;
	}

	public Double getPriceOld() {
		return PriceOld;
	}

	public void setPriceOld(Double priceOld) {
		PriceOld = priceOld;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public List<String> getCategories() {
		return Categories;
	}

	public void setCategories(List<String> categories) {
		Categories = categories;
	}

	public Boolean getIsNewest() {
		return IsNewest;
	}

	public void setIsNewest(Boolean isNewest) {
		IsNewest = isNewest;
	}

	public Boolean getIsDiscount() {
		return IsDiscount;
	}

	public void setIsDiscount(Boolean isDiscount) {
		IsDiscount = isDiscount;
	}

	public Double getTotalRating() {
		return TotalRating;
	}

	public void setTotalRating(Double totalRating) {
		TotalRating = totalRating;
	}

}
