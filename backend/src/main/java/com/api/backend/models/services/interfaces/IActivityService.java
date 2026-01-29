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

package com.api.backend.models.services.interfaces;

import java.util.HashMap;
import java.util.List;

import com.api.backend.models.dto.ActivityListDto;
import com.api.backend.models.entities.User;

public interface IActivityService {
	
	long TotalAll(long user_id);
	
	void saveActivity(User user, String Subject, String Event, String Description);
	
	HashMap<String, Object> getByUser(long user_id, int page, int limit, String order, String sort, String search);

}
