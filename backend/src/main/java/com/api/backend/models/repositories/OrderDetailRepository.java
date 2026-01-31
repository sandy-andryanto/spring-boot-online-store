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

import com.api.backend.models.dto.ProductCartDto;
import com.api.backend.models.entities.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	
	
	@Query(value = "SELECT pp.ID,pp.NAME,pp.image,pp.description,pp.details,x.qty,x.price,x.total FROM orders_details x INNER JOIN products_inventories inv ON inv.ID=x.inventory_id INNER JOIN products pp ON pp.ID=inv.product_id WHERE x.order_id= ?1", nativeQuery = true)
	List<ProductCartDto> findByOrder(long order_id);
	
	
	@Query(value = "SELECT * FROM orders_details x WHERE x.order_id = ?1 AND x.inventory_id = ?2 LIMIT 1", nativeQuery = true)
	OrderDetail getOneByOrderInventory(long order_id, long inventory_id);
	
	@Query(value = "SELECT * FROM orders_details x WHERE x.order_id = ?1", nativeQuery = true)
	List<OrderDetail> findByOrderId(long order_id);

}
