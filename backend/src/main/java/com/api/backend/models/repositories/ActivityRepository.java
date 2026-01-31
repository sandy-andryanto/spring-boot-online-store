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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	
	@Query(value = "SELECT count(*) FROM public.activities x WHERE x.user_id = ?1", nativeQuery = true)
	long TotalAll(long user_id);

}
