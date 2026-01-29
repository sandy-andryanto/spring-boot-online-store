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

package com.api.backend.models.schema;

public class UserChangePasswordSchema {
	
	private String CurrentPassword;
	private String NewPassword;
	private String PasswordConfirm;
	
	public String getCurrentPassword() {
		return CurrentPassword;
	}
	
	public void setCurrentPassword(String currentPassword) {
		CurrentPassword = currentPassword;
	}
	
	public String getNewPassword() {
		return NewPassword;
	}
	
	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}
	
	public String getPasswordConfirm() {
		return PasswordConfirm;
	}
	
	public void setPasswordConfirm(String passwordConfirm) {
		PasswordConfirm = passwordConfirm;
	}

	

}
