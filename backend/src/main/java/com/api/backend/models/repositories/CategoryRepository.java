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

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query(value = "SELECT count(*) FROM public.categories x WHERE x.id <> 0", nativeQuery = true)
	long count();
	
	@Query(value = "SELECT * from public.categories x  ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	List<Category> findAllRandom(int limit);
	
	@Query(value = "SELECT * from public.categories x  WHERE x.status = 1 AND x.displayed = 1 ORDER BY x.name LIMIT ?1", nativeQuery = true)
	List<Category> findAllDisplayed(int limit);
	
	@Query(value = "SELECT x.id, x.NAME,COUNT(*) total FROM categories x INNER JOIN products_categories pc ON pc.category_id=x.ID GROUP BY x.id, x.NAME ORDER BY x.NAME", nativeQuery = true)
	List<DetailSummaryDto> getDetailSummary();
	
}
