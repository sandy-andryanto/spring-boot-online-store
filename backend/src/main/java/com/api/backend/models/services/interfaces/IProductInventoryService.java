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

package com.api.backend.models.services.interfaces;

import java.util.List;

import com.api.backend.models.entities.ProductInventory;

public interface IProductInventoryService {
	
	ProductInventory saveOrUpdate(ProductInventory model);

	List<ProductInventory> getAvailable(long product_id);
	
	ProductInventory getOneByProduct(long product_id, long size_id, long colour_id);
	
	ProductInventory getOneById(long id);
}
