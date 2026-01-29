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

package com.api.backend.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.backend.config.JwtTokenUtil;
import com.api.backend.config.JwtUserDetailsService;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.JwtResponseDto;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.FormUploadFileSchema;
import com.api.backend.models.schema.UserChangePasswordSchema;
import com.api.backend.models.schema.UserChangeProfileSchema;
import com.api.backend.models.services.interfaces.IActivityService;
import com.api.backend.models.services.interfaces.IUserService;
import com.api.backend.services.FileStorageService;

@RestController
public class ProfileController extends BaseController {

	@Autowired
	private IUserService UserService;

	@Autowired
	private IActivityService ActivityService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private FileStorageService fileStorageService;

	@RequestMapping(value = "/api/profile/detail", method = RequestMethod.GET)
	public ResponseEntity<?> Detail() {
		User AuthUser = this.AuthUser();
		UserDetailDto UserDetail = UserService.detail(AuthUser);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/profile/token", method = RequestMethod.POST)
	public ResponseEntity<?> Token() {
		User AuthUser = this.AuthUser();
		final UserDetails userDetails = userDetailsService.loadUserByUsername(AuthUser.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponseDto(token));
	}

	@RequestMapping(value = "/api/profile/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Update(@RequestBody UserChangeProfileSchema model) {

		User AuthUser = this.AuthUser();
		User CheckEmail = UserService.findByEmail(model.getEmail(), AuthUser.getId());
		User CheckPhone = UserService.findByPhone(model.getPhone(), AuthUser.getId());

		if (model.getEmail() == null || model.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (model.getPhone() == null || model.getPhone().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'phone' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (CheckEmail != null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The e-mail address has already been taken.!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (CheckPhone != null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The phone number has already been taken.!", null),
					HttpStatus.BAD_REQUEST);
		}

		User UpdateUser = UserService.ChangeProfile(AuthUser, model);
		UserDetailDto UserDetail = UserService.detail(UpdateUser);

		// Save Activity
		ActivityService.saveActivity(UpdateUser, "Change Current Profile", "Update Profile",
				"Edit user profile account");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", UserDetail), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/profile/password", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Password(@RequestBody UserChangePasswordSchema model) {

		User AuthUser = this.AuthUser();

		if (model.getCurrentPassword() == null || model.getCurrentPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'currentPassword' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (model.getNewPassword() == null || model.getNewPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'newPassword' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (model.getPasswordConfirm() == null || model.getPasswordConfirm().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (model.getNewPassword().length() < 8) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password must be at least 8 characters.!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (!model.getNewPassword().equalsIgnoreCase(model.getPasswordConfirm())) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password confirmation does not match.!", null),
					HttpStatus.BAD_REQUEST);
		}

		boolean matches = passwordEncoder.matches(model.getCurrentPassword(), AuthUser.getPassword());

		if (!matches) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The current password does not match!", null),
					HttpStatus.BAD_REQUEST);
		}

		User UpdateUser = UserService.ChangePassword(AuthUser, model);
		UserDetailDto UserDetail = UserService.detail(UpdateUser);

		// Save Activity
		ActivityService.saveActivity(UpdateUser, "Reset Account Password", "Reset Password", "Reset account password");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "You current password has been changed.", UserDetail), HttpStatus.OK);

	}

	@RequestMapping(value = "/api/profile/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Upload(@RequestParam("file") MultipartFile file) {

		User user = this.AuthUser();
		String fileName = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/page/file/").path(fileName)
				.toUriString();
		FormUploadFileSchema result = new FormUploadFileSchema(fileName, fileDownloadUri, file.getContentType(),
				file.getSize());

		if (result.getFileDownloadUri() != null) {
			UserService.UploadImage(user, fileName);
		}

		// Save Activity
		ActivityService.saveActivity(user, "Change Current Image", "Upload Image", "Upload new user profile image");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "Upload new user profile image success", result), HttpStatus.OK);

	}

	@RequestMapping(value = "/api/profile/activity", method = RequestMethod.GET)
	public ResponseEntity<?> ListActivity(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir, @RequestParam(defaultValue = "") String search) {
		User user = this.AuthUser();
		HashMap<String, Object> payload = ActivityService.getByUser(user.getId(), page, limit, orderby, orderdir,
				search);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

}
