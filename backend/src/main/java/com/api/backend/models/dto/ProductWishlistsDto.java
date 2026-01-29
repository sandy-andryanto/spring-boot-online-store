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

package com.api.backend.models.dto;

import org.springframework.beans.factory.annotation.Value;

public interface ProductWishlistsDto {

	@Value(value = "#{target.id}")
	public Long getId();

	@Value(value = "#{target.image}")
	public String getImage();

	@Value(value = "#{target.name}")
	public String getName();

	@Value(value = "#{target.description}")
	public String getDescription();

	@Value(value = "#{target.details}")
	public String getDetails();

	@Value(value = "#{target.price}")
	public double getPrice();
}
