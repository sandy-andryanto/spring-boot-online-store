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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.services.FileStorageService;

import java.io.FileInputStream;
import java.io.InputStream;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/page/file/{filename:.+}", method = RequestMethod.GET)
	public void streamImage(@PathVariable String filename, HttpServletResponse response) {
		try {

			Path path = fileStorageService.getFileStorageLocation();

			File file = new File(path + "/" + filename);

			if (!file.exists()) {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			String contentType = Files.probeContentType(file.toPath());
			response.setContentType(contentType != null ? contentType : "application/octet-stream");

			InputStream inputStream = new FileInputStream(file);
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}