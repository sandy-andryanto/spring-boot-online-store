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

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.OrderBilling;
import com.api.backend.models.entities.Setting;
import com.api.backend.models.repositories.OrderBillingRepository;
import com.api.backend.models.services.interfaces.IOrderBillingService;


@Service
public class OrderBillingService implements IOrderBillingService {
	
	@Autowired
	private OrderBillingRepository repo;

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public OrderBilling saveOrUpdate(OrderBilling model) {
		// TODO Auto-generated method stub
		repo.save(model);
		return model;
	}

	@Override
	public HashMap<String, Object> getAll(long order_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		List<OrderBilling> models = repo.findByOrder(order_id);
		for(OrderBilling row: models) {
			payload.put(row.getName(), row.getDescription());
		}
		return payload;
	}

	@Override
	public List<OrderBilling> getByOrder(long id) {
		// TODO Auto-generated method stub
		return repo.findByOrder(id);
	}

}
