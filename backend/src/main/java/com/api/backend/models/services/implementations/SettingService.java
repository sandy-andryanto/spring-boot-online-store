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

package com.api.backend.models.services.implementations;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.entities.Setting;
import com.api.backend.models.repositories.SettingRepository;
import com.api.backend.models.services.interfaces.ISettingService;

@Service
public class SettingService implements ISettingService {
	
	@Autowired
	private SettingRepository repo;

	@Override
	public Setting saveOrUpdate(Setting model) {
		// TODO Auto-generated method stub
		 repo.save(model);
	     return model;
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public HashMap<String, Object> getAll() {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		List<Setting> settings = repo.findAll();
		
		for(Setting row: settings) {
			payload.put(row.getKeyName(), row.getKeyValue());
		}
		
		return payload;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		Setting model = repo.findByKey(key);
		return model != null ? model.getKeyValue() : key;
	}

}
