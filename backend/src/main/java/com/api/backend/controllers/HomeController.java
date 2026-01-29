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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.helpers.Helper;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.entities.Category;
import com.api.backend.models.entities.NewsLetter;
import com.api.backend.models.services.interfaces.ICategoryService;
import com.api.backend.models.services.interfaces.INewsLetterService;
import com.api.backend.models.services.interfaces.IProductService;
import com.api.backend.models.services.interfaces.ISettingService;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HomeController extends BaseController {

	@Autowired
	private INewsLetterService NewsLetterService;

	@Autowired
	private ISettingService SettingService;

	@Autowired
	private ICategoryService CategoryService;

	@Autowired
	private IProductService ProductService;

	@RequestMapping(value = "/api/home/ping", method = RequestMethod.GET)
	public ResponseEntity<?> Ping() {
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/home/newsletter", method = RequestMethod.POST)
	public ResponseEntity<?> Newsletter(@RequestBody Map<String, Object> body, HttpServletRequest request)
			throws ParseException {

		String email = (String) body.get("email");

		if (email == null || email.trim().isBlank()) {
			return new ResponseEntity<Object>(new JsonResponseDto(false, "The field 'email' can not be empty!", null),
					HttpStatus.BAD_REQUEST);
		}

		String IpAddress = this.getClientIp(request);

		NewsLetter model = new NewsLetter();
		model.setEmail(email);
		model.setIpAddress(IpAddress);
		model.setCreatedAt(Helper.DateNow());
		model.setUpdatedAt(Helper.DateNow());
		model.setStatus(1);
		NewsLetterService.saveOrUpdate(model);

		return new ResponseEntity<Object>(new JsonResponseDto(true, "Message sent successfully!", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/home/component", method = RequestMethod.GET)
	public ResponseEntity<?> Component() {

		HashMap<String, Object> payload = new HashMap<>();
		HashMap<String, Object> settings = SettingService.getAll();
		List<Category> categories = CategoryService.findAll();

		payload.put("settings", settings);
		payload.put("categories", categories);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/home/page", method = RequestMethod.GET)
	public ResponseEntity<?> Page() {
		HashMap<String, Object> payload = ProductService.getPageDispalyed();
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

}