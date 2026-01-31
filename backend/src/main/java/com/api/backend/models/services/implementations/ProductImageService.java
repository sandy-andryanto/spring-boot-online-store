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

import com.api.backend.models.entities.ProductImage;
import com.api.backend.models.repositories.ProductImageRepository;
import com.api.backend.models.services.interfaces.IProductImageService;

@Service
public class ProductImageService implements IProductImageService {
	
	@Autowired
	private ProductImageRepository repo;

	@Override
	public ProductImage saveOrUpdate(ProductImage model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<ProductImage> getAllByProduct(long product_id) {
		// TODO Auto-generated method stub
		return repo.getAllByProduct(product_id);
	}

}
