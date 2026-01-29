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
import com.api.backend.models.dto.ProductWishlistsDto;
import com.api.backend.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query(value = "SELECT count(*) FROM public.orders x WHERE x.user_id = ?1", nativeQuery = true)
	long count(long user_id);
	
	@Query(value = "SELECT * FROM public.orders x WHERE x.id = ?1 LIMIT 1", nativeQuery = true)
	Order findById(long id);
	
	@Query(value = "SELECT * FROM public.orders x WHERE x.user_id = ?1 AND x.status = 0 LIMIT 1", nativeQuery = true)
	Order findBySession(long user_id);
	
	@Query(value = "SELECT pp.ID,pp.NAME,pp.image,pp.description,pp.details,pp.price FROM products_wishlists x INNER JOIN products pp ON pp.ID=x.product_id WHERE x.user_id = ?1", nativeQuery = true)
	List<ProductWishlistsDto> findByWishlistsByUser(long user_id);

}
