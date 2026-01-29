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



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.api.backend.models.entities.Authentication;

public interface AuthenticationRepository extends JpaRepository<Authentication, Long> {
	
	@Query(value = "SELECT * FROM public.authentications WHERE status = 0 AND expired_at > NOW() AND token = ?1", nativeQuery = true)
	Authentication getByToken(String token);
	
	@Query(value = "SELECT * FROM public.authentications WHERE status = 0 AND expired_at > NOW() AND token = ?1 AND credential = ?2", nativeQuery = true)
	Authentication getByTokenCredential(String token, String credential);

}
