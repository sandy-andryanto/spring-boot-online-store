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

package com.api.backend.models.services.interfaces;

import java.util.List;

import com.api.backend.models.entities.Payment;

public interface IPaymentService {
	
	Long TotalRows();
	
	Payment saveOrUpdate(Payment model);
	
	List<Payment> getAll();
	
	Payment getOneById(long id);

}
