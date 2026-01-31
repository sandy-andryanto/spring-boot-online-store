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

package com.api.backend.models.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.dto.ProductCartDto;
import com.api.backend.models.entities.OrderDetail;
import com.api.backend.models.repositories.OrderDetailRepository;
import com.api.backend.models.services.interfaces.IOrderDetailService;

@Service
public class OrderDetailService implements IOrderDetailService {
	
	@Autowired
	private OrderDetailRepository repo;

	@Override
	public List<ProductCartDto> findByOrder(long order_id) {
		// TODO Auto-generated method stub
		return repo.findByOrder(order_id);
	}

	@Override
	public OrderDetail getOneByOrderInventory(long order_id, long inventory_id) {
		// TODO Auto-generated method stub
		return repo.getOneByOrderInventory(order_id, inventory_id);
	}

	@Override
	public OrderDetail saveOrUpdate(OrderDetail model) {
		// TODO Auto-generated method stub
		repo.save(model);
		return model;
	}

	@Override
	public List<OrderDetail> findByOrderId(long order_id) {
		// TODO Auto-generated method stub
		return repo.findByOrderId(order_id);
	}

}
