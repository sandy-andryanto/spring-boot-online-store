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

package com.api.backend.controllers;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.config.EncoderConfig;
import com.api.backend.config.JwtTokenUtil;
import com.api.backend.config.JwtUserDetailsService;
import com.api.backend.helpers.Helper;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.JwtResponseDto;
import com.api.backend.models.entities.Authentication;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.UserForgotPasswordSchema;
import com.api.backend.models.schema.UserLoginSchema;
import com.api.backend.models.schema.UserRegisterSchema;
import com.api.backend.models.schema.UserResetPasswordSchema;
import com.api.backend.models.services.interfaces.IActivityService;
import com.api.backend.models.services.interfaces.IAuthenticationService;
import com.api.backend.models.services.interfaces.IUserService;

@RestController
public class AuthController extends BaseController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IAuthenticationService AuthenticationService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IActivityService ActivityService;

	@RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> Login(@RequestBody UserLoginSchema authenticationRequest) {
		try {

			String username = authenticationRequest.getEmail();
			String password = authenticationRequest.getPassword();

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
			final User user = UserService.findByEmail(username, 0);

			if (user.getStatus() == 0) {
				return new ResponseEntity<Object>(new JsonResponseDto(false,
						"You need to confirm your account. We have sent you an activation code, please check your email.!",
						null), HttpStatus.BAD_REQUEST);
			} else {
				final String token = jwtTokenUtil.generateToken(userDetails);
				return ResponseEntity.ok(new JwtResponseDto(token));
			}

		} catch (Exception e) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "These credentials do not match our records.", null),
					HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/api/auth/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> Register(@RequestBody UserRegisterSchema form) throws ParseException {

		if (form.getName() == null || form.getName().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'name' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword() == null || form.getPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'password' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

			if (form.getPasswordConfirm() == null || form.getPasswordConfirm().trim().isBlank()) {
				return new ResponseEntity<Object>(
						new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null),
						HttpStatus.BAD_REQUEST);
			}

		if (form.getPassword().length() < 8) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password must be at least 8 characters.!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (!form.getPassword().equalsIgnoreCase(form.getPasswordConfirm())) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password confirmation does not match.!", null),
					HttpStatus.BAD_REQUEST);
		}

		User UserByEmail = UserService.findByEmail(form.getEmail(), 0);

		if (UserByEmail != null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The email address has already been taken.!", null),
					HttpStatus.BAD_REQUEST);
		}

		EncoderConfig enc = new EncoderConfig();
		String password = enc.passwordEncoder().encode(form.getPassword());
		String token = UUID.randomUUID().toString();

		User NewUser = new User();

		String[] names = form.getEmail().split(" ");

		if (names.length > 1) {
			String[] subArray = Arrays.copyOfRange(names, 1, names.length);
			NewUser.setFirstName(names[0]);
			NewUser.setLastName(String.join(" ", subArray));
		} else {
			NewUser.setFirstName(names[0]);
		}

		NewUser.setPassword(password);
		NewUser.setEmail(form.getEmail());
		NewUser.setStatus(0);
		NewUser.setCreatedAt(Helper.DateNow());
		NewUser.setUpdatedAt(Helper.DateNow());
		UserService.saveOrUpdate(NewUser);

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(30);
		Date expiredDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		Authentication auth = new Authentication();
		auth.setStatus(0);
		auth.setUser(NewUser);
		auth.setAuthType("email-confirm");
		auth.setCredential(form.getEmail());
		auth.setToken(token);
		auth.setCreatedAt(Helper.DateNow());
		auth.setUpdatedAt(Helper.DateNow());
		auth.setExpiredAt(expiredDate);
		AuthenticationService.saveOrUpdate(auth);

		// Save Activity
		ActivityService.saveActivity(NewUser, "Sign Up", "User Register", "Register new user account");

		return new ResponseEntity<Object>(new JsonResponseDto(true,
				"You need to confirm your account. We have sent you an activation code, please check your email.!",
				token), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/auth/confirm/{token}", method = RequestMethod.GET)
	public ResponseEntity<?> Confirm(@PathVariable String token) throws ParseException {

		Authentication verification = AuthenticationService.getByToken(token);

		if (verification == null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "We can't find a user with that  token is invalid.!", null),
					HttpStatus.BAD_REQUEST);
		}

		verification.setStatus(2);
		verification.setExpiredAt(Helper.DateNow());
		AuthenticationService.saveOrUpdate(verification);

		User user = UserService.findByEmail(verification.getCredential(), 0);

		if (user == null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "We can't find a user with that e-mail address.!", null),
					HttpStatus.BAD_REQUEST);
		}

		user.setStatus(1);
		UserService.saveOrUpdate(user);

		ActivityService.saveActivity(user, "User Verification", "Email Verification",
				"Confirm new member registration account");

		return new ResponseEntity<Object>(
				new JsonResponseDto(true, "Your e-mail is verified. You can now login.", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/auth/email/forgot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> ForgotPassword(@RequestBody UserForgotPasswordSchema form) throws ParseException {

		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		User user = UserService.findByEmail(form.getEmail(), 0);

		if (user == null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "We can't find a user with that e-mail address.", null),
					HttpStatus.BAD_REQUEST);
		}

		LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(30);
		Date expiredDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

		String token = UUID.randomUUID().toString();
		Authentication auth = new Authentication();
		auth.setStatus(0);
		auth.setUser(user);
		auth.setAuthType("reset-password");
		auth.setCredential(form.getEmail());
		auth.setToken(token);
		auth.setCreatedAt(Helper.DateNow());
		auth.setUpdatedAt(Helper.DateNow());
		auth.setExpiredAt(expiredDate);
		AuthenticationService.saveOrUpdate(auth);

		// Save Activity
		ActivityService.saveActivity(user, "Request Reset Password", "Forgot Password", "Request reset password link");

		return new ResponseEntity<Object>(
				new JsonResponseDto(true, "We have e-mailed your password reset link!", token), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/auth/email/reset/{token}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> reset(@PathVariable String token, @RequestBody UserResetPasswordSchema form)
			throws ParseException {

		if (form.getEmail() == null || form.getEmail().trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		User user = UserService.findByEmail(form.getEmail(), 0);

		if (user == null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "We can't find a user with that e-mail address.", null),
					HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword() == null || form.getPassword().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'password' can not be empty!", null), HttpStatus.BAD_REQUEST);
		}

		if (form.getPasswordConfirm() == null || form.getPasswordConfirm().trim().isBlank()) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The field 'passwordConfirm' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (form.getPassword().length() < 8) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password must be at least 8 characters.!", null),
					HttpStatus.BAD_REQUEST);
		}

		if (!form.getPassword().equalsIgnoreCase(form.getPasswordConfirm())) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "The password confirmation does not match.!", null),
					HttpStatus.BAD_REQUEST);
		}

		Authentication verification = AuthenticationService.getByTokenCredential(token, form.getEmail());

		if (verification == null) {
			return new ResponseEntity<Object>(
					new JsonResponseDto(false, "We can't find a user with that  token is invalid.!", null),
					HttpStatus.BAD_REQUEST);
		}

		verification.setStatus(2);
		verification.setExpiredAt(Helper.DateNow());
		AuthenticationService.saveOrUpdate(verification);

		EncoderConfig enc = new EncoderConfig();
		String password = enc.passwordEncoder().encode(form.getPassword());

		user.setStatus(1);
		user.setPassword(password);
		user.setUpdatedAt(Helper.DateNow());
		UserService.saveOrUpdate(user);

		// Save Activity
		ActivityService.saveActivity(user, "Reset Account Password", "Reset Password", "Reset account password");

		return new ResponseEntity<Object>(new JsonResponseDto(true, "Your password has been reset!", user),
				HttpStatus.OK);
	}

}
