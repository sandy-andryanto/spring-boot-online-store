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

package com.api.backend.models.services.interfaces;

import java.util.HashMap;
import java.util.List;

import com.api.backend.models.dto.ProductDisplayDto;
import com.api.backend.models.entities.Product;

public interface IProductService {

	Long TotalRows();

	Product saveOrUpdate(Product model);

	Product getTopRating();

	List<Product> getDispalyed(String column, int limit);

	HashMap<String, Object> getPageDispalyed();

	Product getMaxPrice();

	Product getMinPrice();
	
	List<ProductDisplayDto> getTopSellings(int limit);
	
	HashMap<String, Object> getPageProduct(int page, int limit, String order, String sort, String categories, String brand, String search, String maxPrice, String minPrice);
	
	ProductDisplayDto getById(long id);

	List<ProductDisplayDto> getRecomended(long product_id);
	
	Product getOneById(long id);
}
