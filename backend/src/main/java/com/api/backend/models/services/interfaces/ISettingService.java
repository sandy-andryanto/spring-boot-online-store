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

import java.util.HashMap;

import com.api.backend.models.entities.Setting;

public interface ISettingService {
	
	Long TotalRows();

	Setting saveOrUpdate(Setting model);
	
	HashMap<String, Object> getAll();
	
	String getValue(String key);
}
