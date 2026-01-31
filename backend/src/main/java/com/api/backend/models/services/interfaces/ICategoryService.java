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

package com.api.backend.models.services.interfaces;

import java.util.List;

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.entities.Category;

public interface ICategoryService {

	Long TotalRows();

	Category saveOrUpdate(Category model);

	List<Category> findAllRandom(int limit);

	List<Category> findAll();

	List<Category> findAllDisplayed(int limit);

	List<DetailSummaryDto> getDetailSummary();

}
