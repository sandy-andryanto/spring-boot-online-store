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

package com.api.backend.models.services.interfaces;

import java.util.List;

import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.UserChangePasswordSchema;
import com.api.backend.models.schema.UserChangeProfileSchema;

public interface IUserService {
	Long TotalRows();

	User saveOrUpdate(User model);

	List<User> findAllRandom(int limit);

	User findByEmail(String email, long id);

	User findByPhone(String phone, long id);

	User ChangeProfile(User User, UserChangeProfileSchema model);

	User ChangePassword(User User, UserChangePasswordSchema model);

	UserDetailDto detail(User user);

	void UploadImage(User User, String Image);
	
	User getOneById(long id);
}
