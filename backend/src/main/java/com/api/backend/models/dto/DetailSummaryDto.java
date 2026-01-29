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

public interface DetailSummaryDto {
	
	@Value(value = "#{target.id}")
	public long getId();

	@Value(value = "#{target.name}")
	public String getName();

	@Value(value = "#{target.total}")
	public Long getTotal();

}
