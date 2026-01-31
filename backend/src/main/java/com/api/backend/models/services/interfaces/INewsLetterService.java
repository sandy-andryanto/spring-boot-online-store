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

import com.api.backend.models.entities.Authentication;
import com.api.backend.models.entities.NewsLetter;

public interface INewsLetterService {
	
	NewsLetter saveOrUpdate(NewsLetter model);

}
