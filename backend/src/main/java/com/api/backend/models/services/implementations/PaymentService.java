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

import com.api.backend.models.entities.Payment;
import com.api.backend.models.repositories.PaymentRepository;
import com.api.backend.models.services.interfaces.IPaymentService;

@Service
public class PaymentService implements IPaymentService {
	
	@Autowired
	private PaymentRepository repo;

	@Override
	public Payment saveOrUpdate(Payment model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public List<Payment> getAll() {
		// TODO Auto-generated method stub
		return repo.getAll();
	}

	@Override
	public Payment getOneById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

}
