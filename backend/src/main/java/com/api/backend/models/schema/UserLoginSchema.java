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

package com.api.backend.models.schema;

import java.io.Serializable;

public class UserLoginSchema {
	
	private static final long serialVersionUID = 5926468583005150707L;

	private String Email;
	private String Password;

	public UserLoginSchema() {}

	public UserLoginSchema(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
