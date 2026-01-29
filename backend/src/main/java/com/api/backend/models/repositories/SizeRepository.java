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
import com.api.backend.models.entities.Size;

public interface SizeRepository extends JpaRepository<Size, Long> {

	@Query(value = "SELECT count(*) FROM public.sizes x WHERE x.id <> 0", nativeQuery = true)
	long count();

	@Query(value = "SELECT * from public.sizes x  ORDER BY x.name ASC", nativeQuery = true)
	List<Size> getAll();

}
