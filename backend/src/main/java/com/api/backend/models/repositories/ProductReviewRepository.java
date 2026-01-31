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
import com.api.backend.models.entities.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
	
	@Query(value = "SELECT * from public.products_reviews x WHERE x.product_id = ?1 ORDER BY x.id DESC", nativeQuery = true)
	List<ProductReview> getAllByProduct(long product_id);
	
	
	@Query(value = "SELECT * from public.products_reviews x WHERE x.product_id = ?1 ORDER BY x.rating DESC LIMIT 1", nativeQuery = true)
	ProductReview getTopRating(long product_id);

}
