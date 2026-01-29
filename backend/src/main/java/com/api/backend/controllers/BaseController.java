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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.api.backend.models.services.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;

public class BaseController {
	
	@Autowired
	private IUserService UserService;

	protected com.api.backend.models.entities.User AuthUser() {
		try {
			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				return null;
			}
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			return UserService.findByEmail(auth.getName(), 0);
		} catch (Exception e) {
			return null;
		}
	}
	
	protected String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0];
        }
        return ip;
    }

}
