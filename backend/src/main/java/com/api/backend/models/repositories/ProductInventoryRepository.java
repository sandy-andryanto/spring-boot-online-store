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

package com.api.backend.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.entities.ProductInventory;
import com.api.backend.models.entities.Size;

public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
	
	
	@Query(value = "SELECT * from public.products_inventories x WHERE x.stock > 0 AND x.product_id = ?1", nativeQuery = true)
	List<ProductInventory> getAvailable(long product_id);
	
	@Query(value = "SELECT * from public.products_inventories x WHERE x.product_id = ?1 AND x.size_id = ?2 AND x.colour_id = ?3 LIMIT 1", nativeQuery = true)
	ProductInventory getOneByProduct(long product_id, long size_id, long colour_id);

}
