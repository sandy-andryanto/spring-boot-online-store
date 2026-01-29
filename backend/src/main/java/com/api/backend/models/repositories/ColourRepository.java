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

import com.api.backend.models.entities.Colour;

public interface ColourRepository extends JpaRepository<Colour, Long> {
	
	@Query(value = "SELECT count(*) FROM public.colours x WHERE x.id <> 0", nativeQuery = true)
	long count();
	
	@Query(value = "SELECT * from public.colours x  ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	List<Colour> findAllRandom(int limit);
	
	@Query(value = "SELECT * from public.colours x  ORDER BY x.name ASC", nativeQuery = true)
	List<Colour> getAll();

}
