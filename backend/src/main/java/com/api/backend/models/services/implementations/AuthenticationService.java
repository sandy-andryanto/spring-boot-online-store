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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.Authentication;
import com.api.backend.models.repositories.AuthenticationRepository;
import com.api.backend.models.services.interfaces.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {
	
	@Autowired
	private AuthenticationRepository repo;

	@Override
	public Authentication saveOrUpdate(Authentication model) {
		// TODO Auto-generated method stub
		 repo.save(model);
		 return model;
	}

	@Override
	public Authentication getByToken(String token) {
		// TODO Auto-generated method stub
		return repo.getByToken(token);
	}

	@Override
	public Authentication getByTokenCredential(String token, String credential) {
		// TODO Auto-generated method stub
		return repo.getByTokenCredential(token, credential);
	}

}
