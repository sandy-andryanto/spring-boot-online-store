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

import com.api.backend.models.entities.ProductReview;
import com.api.backend.models.repositories.ProductReviewRepository;
import com.api.backend.models.services.interfaces.IProductReviewService;

@Service
public class ProductReviewService implements IProductReviewService {
	
	@Autowired
	private ProductReviewRepository repo;

	@Override
	public ProductReview saveOrUpdate(ProductReview model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<ProductReview> getAllByProduct(long product_id) {
		// TODO Auto-generated method stub
		return repo.getAllByProduct(product_id);
	}

	@Override
	public ProductReview getTopRating(long product_id) {
		// TODO Auto-generated method stub
		return repo.getTopRating(product_id);
	}

}
