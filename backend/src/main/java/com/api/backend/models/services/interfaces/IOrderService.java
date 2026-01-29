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

package com.api.backend.models.services.interfaces;

import java.util.HashMap;
import java.util.List;

import com.api.backend.models.dto.ProductReviewDto;
import com.api.backend.models.entities.Order;
import com.api.backend.models.schema.CheckoutSchema;

public interface IOrderService {

	Long TotalRows(long user_id);

	Order saveOrUpdate(Order model);

	HashMap<String, Object> getPageList(long user_id, int page, int limit, String order, String sort, String search);
	
	void CancelOrder(int order_id);
	
	void AddWhislist(long product_id, long user_id);
	
	void AddToCart(long user_id, long product_id,  long size_id, long colour_id, int qty);
	
	void AddReview(long user_id, long product_id, int rating, String review);
	
	HashMap<String, Object> getDetail(long order_id);
	
	Order findById(long id);
	
	Order findBySession(long user_id);
	
	HashMap<String, Object> getSession(long user_id);
	
	HashMap<String, Object> getInitiate(long id, long user_id);
	
	List<ProductReviewDto> getProductReview(long id);
	
	HashMap<String, Object> getCheckoutInitiate(long id);
	
	void CheckOutOrder(long user_id, CheckoutSchema model);
}
