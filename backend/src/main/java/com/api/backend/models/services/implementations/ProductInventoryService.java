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

package com.api.backend.models.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.ProductInventory;
import com.api.backend.models.repositories.ProductInventoryRepository;
import com.api.backend.models.services.interfaces.IProductInventoryService;

@Service
public class ProductInventoryService implements IProductInventoryService {
	
	@Autowired
	private ProductInventoryRepository repo;

	@Override
	public ProductInventory saveOrUpdate(ProductInventory model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<ProductInventory> getAvailable(long product_id) {
		// TODO Auto-generated method stub
		return repo.getAvailable(product_id);
	}

	@Override
	public ProductInventory getOneByProduct(long product_id, long size_id, long colour_id) {
		// TODO Auto-generated method stub
		return repo.getOneByProduct(product_id, size_id, colour_id);
	}

	@Override
	public ProductInventory getOneById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

}
