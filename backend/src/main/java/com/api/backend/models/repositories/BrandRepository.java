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

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.entities.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
	
	@Query(value = "SELECT count(*) FROM public.brands x WHERE x.id <> 0", nativeQuery = true)
	long count();

	@Query(value = "SELECT * from public.brands x  ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	List<Brand> findAllRandom(int limit);
	
	@Query(value = "SELECT * from public.brands x  ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Brand findOneByRandom();
	
	@Query(value = "SELECT x.id, x.NAME,COUNT(*) total FROM brands x INNER JOIN products pr ON pr.brand_id=x.ID GROUP BY x.id, x.NAME ORDER BY x.NAME", nativeQuery = true)
	List<DetailSummaryDto> getDetailSummary();
}
