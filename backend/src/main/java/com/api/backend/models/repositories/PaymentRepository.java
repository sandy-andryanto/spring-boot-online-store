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

package com.api.backend.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.api.backend.models.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	@Query(value = "SELECT count(*) FROM public.payments x WHERE x.id <> 0", nativeQuery = true)
	long count();

	@Query(value = "SELECT * from public.payments x  ORDER BY x.name ASC", nativeQuery = true)
	List<Payment> getAll();
}
