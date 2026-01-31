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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.NewsLetter;
import com.api.backend.models.repositories.NewsLetterRepository;
import com.api.backend.models.services.interfaces.INewsLetterService;

@Service
public class NewsLetterService implements INewsLetterService {
	
	@Autowired
	private NewsLetterRepository repo;

	@Override
	public NewsLetter saveOrUpdate(NewsLetter model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

}
