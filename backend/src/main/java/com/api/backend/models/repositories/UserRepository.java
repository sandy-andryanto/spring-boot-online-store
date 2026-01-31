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
import com.api.backend.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query(value = "SELECT count(*) FROM public.users x WHERE x.id <> 0", nativeQuery = true)
	long count();
	
	@Query(value = "SELECT * FROM public.users x WHERE x.email = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	User findByEmail(String email, long id);
    
    @Query(value = "SELECT * FROM public.users x WHERE x.phone = ?1 AND x.id <> ?2 LIMIT 1", nativeQuery = true)
	User findByPhone(String phone, long id);
    
    @Query(value = "SELECT * from public.users x  ORDER BY RANDOM() LIMIT ?1", nativeQuery = true)
	List<User> findAllRandom(int limit);
    
}