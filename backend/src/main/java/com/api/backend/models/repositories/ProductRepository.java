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
import com.api.backend.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "SELECT count(*) FROM public.products x WHERE x.id <> 0", nativeQuery = true)
	long count();
	
	@Query(value = "SELECT * from public.products x WHERE x.published_date <= NOW() AND x.status = 1 ORDER BY total_rating DESC LIMIT 1", nativeQuery = true)
	Product getTopRating();
	
	@Query(value = "SELECT * from public.products x WHERE x.published_date <= NOW() AND x.status = 1 ORDER BY ?1 DESC LIMIT ?2", nativeQuery = true)
	List<Product> getDispalyed(String column, int limit);
	
	@Query(value = "SELECT * from public.products x WHERE x.published_date <= NOW() AND x.status = 1 ORDER BY price DESC LIMIT 1", nativeQuery = true)
	Product getMaxPrice();
	
	@Query(value = "SELECT * from public.products x WHERE x.published_date <= NOW() AND x.status = 1 ORDER BY price ASC LIMIT 1", nativeQuery = true)
	Product getMinPrice();
	
	@Query(value = "SELECT * from public.products x WHERE x.published_date <= NOW() AND x.status = 1 AND x.id != ?1 ORDER BY x.total_rating DESC LIMIT ?2", nativeQuery = true)
	List<Product> getRecomended(long product_id, int limit);

}
