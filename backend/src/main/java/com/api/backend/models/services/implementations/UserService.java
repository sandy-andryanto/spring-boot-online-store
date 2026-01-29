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

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.config.EncoderConfig;
import com.api.backend.helpers.Helper;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.UserRepository;
import com.api.backend.models.schema.UserChangePasswordSchema;
import com.api.backend.models.schema.UserChangeProfileSchema;
import com.api.backend.models.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepository repo;

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public User saveOrUpdate(User model) {
		// TODO Auto-generated method stub
		 repo.save(model);
		 return model;
	}

	@Override
	public List<User> findAllRandom(int limit) {
		// TODO Auto-generated method stub
		return repo.findAllRandom(limit);
	}

	@Override
	public User findByEmail(String email, long id) {
		// TODO Auto-generated method stub
		return repo.findByEmail(email, id);
	}

	@Override
	public User findByPhone(String phone, long id) {
		// TODO Auto-generated method stub
		return repo.findByPhone(phone, id);
	}

	@Override
	public User ChangeProfile(User User, UserChangeProfileSchema model) {
		// TODO Auto-generated method stub
		User.setFirstName(model.getFirstName());
		User.setLastName(model.getLastName());
		User.setEmail(model.getEmail());
		User.setPhone(model.getPhone());
		User.setGender(model.getGender());
		User.setCountry(model.getCountry());
		User.setCity(model.getCity());
		User.setAddress(model.getAddress());
		User.setZipCode(model.getZipCode());
		return saveOrUpdate(User);
	}

	@Override
	public User ChangePassword(User User, UserChangePasswordSchema model) {
		// TODO Auto-generated method stub
		try {
			EncoderConfig enc = new EncoderConfig();
			String password = enc.passwordEncoder().encode(model.getNewPassword());
			User.setPassword(password);
			User.setUpdatedAt(Helper.DateNow());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveOrUpdate(User);
	}

	@Override
	public UserDetailDto detail(User user) {
		// TODO Auto-generated method stub
		UserDetailDto model = new UserDetailDto();
		model.setId(user.getId());
		model.setFirstName(user.getFirstName());
		model.setLastName(user.getLastName());
		model.setEmail(user.getEmail());
		model.setPhone(user.getPhone());
		model.setImage(user.getImage());
		model.setGender(user.getGender());
		model.setCountry(user.getCountry());
		model.setCity(user.getCity());
		model.setAddress(user.getAddress());
		model.setZipCode(user.getZipCode());
		model.setCreatedAt(user.getCreatedAt());
		model.setUpdatedAt(user.getUpdatedAt());
		return model;
	}

	@Override
	public void UploadImage(User User, String Image) {
		// TODO Auto-generated method stub
		User.setImage(Image);
		try {
			User.setUpdatedAt(Helper.DateNow());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveOrUpdate(User);
	}

	@Override
	public User getOneById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

}
