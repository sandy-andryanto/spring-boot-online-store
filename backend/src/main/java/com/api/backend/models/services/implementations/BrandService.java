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

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.entities.Brand;
import com.api.backend.models.repositories.BrandRepository;
import com.api.backend.models.services.interfaces.IBrandService;

@Service
public class BrandService implements IBrandService {
	
	@Autowired
	private BrandRepository repo;

	@Override
	public Brand saveOrUpdate(Brand model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<Brand> findAllRandom(int limit) {
		// TODO Auto-generated method stub
		return repo.findAllRandom(limit);
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public Brand findOneByRandom() {
		// TODO Auto-generated method stub
		return repo.findOneByRandom();
	}

	@Override
	public List<DetailSummaryDto> getDetailSummary() {
		// TODO Auto-generated method stub
		return repo.getDetailSummary();
	}

}
