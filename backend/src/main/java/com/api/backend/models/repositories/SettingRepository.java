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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.api.backend.models.entities.Setting;
import com.api.backend.models.entities.User;

public interface SettingRepository extends JpaRepository<Setting, Long> {

	@Query(value = "SELECT count(*) FROM public.settings x WHERE x.id <> 0", nativeQuery = true)
	long count();

	@Query(value = "SELECT * FROM public.settings x WHERE x.key_name = ?1 LIMIT 1", nativeQuery = true)
	Setting findByKey(String key);

}
