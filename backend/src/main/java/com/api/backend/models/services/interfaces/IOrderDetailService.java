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

import java.util.List;

import com.api.backend.models.dto.ProductCartDto;
import com.api.backend.models.entities.OrderDetail;

public interface IOrderDetailService {
	
	List<ProductCartDto> findByOrder(long order_id);

	OrderDetail getOneByOrderInventory(long order_id, long inventory_id);
	
	OrderDetail saveOrUpdate(OrderDetail model);
	
	List<OrderDetail> findByOrderId(long order_id);
}
