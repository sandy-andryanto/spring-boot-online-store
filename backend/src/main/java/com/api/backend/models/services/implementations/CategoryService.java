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

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.entities.Category;
import com.api.backend.models.repositories.CategoryRepository;
import com.api.backend.models.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	
	@Autowired
	private CategoryRepository repo;

	@Override
	public Category saveOrUpdate(Category model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<Category> findAllRandom(int limit) {
		// TODO Auto-generated method stub
		return repo.findAllRandom(limit);
	}

	@Override
	public List<Category> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public List<Category> findAllDisplayed(int limit) {
		// TODO Auto-generated method stub
		return repo.findAllDisplayed(limit);
	}

	@Override
	public List<DetailSummaryDto> getDetailSummary() {
		// TODO Auto-generated method stub
		return repo.getDetailSummary();
	}

}
