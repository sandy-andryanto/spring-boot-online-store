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

import com.api.backend.models.entities.Colour;
import com.api.backend.models.entities.Size;
import com.api.backend.models.repositories.ColourRepository;
import com.api.backend.models.services.interfaces.IColourService;

@Service
public class ColourService implements IColourService {
	
	@Autowired
	private ColourRepository repo;

	@Override
	public Colour saveOrUpdate(Colour model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public List<Colour> findAllRandom(int limit) {
		// TODO Auto-generated method stub
		return repo.findAllRandom(limit);
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public List<Colour> findAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Colour getOneById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	

}
