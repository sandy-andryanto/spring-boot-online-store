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

package com.api.backend.models.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.entities.OrderBilling;

public interface OrderBillingRepository extends JpaRepository<OrderBilling, Long> {

	@Query(value = "SELECT * FROM orders_billings x WHERE x.order_id = ?1 ORDER BY x.id ASC", nativeQuery = true)
	List<OrderBilling> findByOrder(long order_id);

}
