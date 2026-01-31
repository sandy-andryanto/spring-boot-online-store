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

package com.api.backend.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.ProductDisplayDto;
import com.api.backend.models.dto.ProductReviewDto;
import com.api.backend.models.entities.User;
import com.api.backend.models.schema.CheckoutSchema;
import com.api.backend.models.schema.CreateCartSchema;
import com.api.backend.models.schema.ReviewSchema;
import com.api.backend.models.schema.UserChangeProfileSchema;
import com.api.backend.models.services.interfaces.IActivityService;
import com.api.backend.models.services.interfaces.IOrderService;
import com.api.backend.models.services.interfaces.IProductService;

@RestController
public class OrderController extends BaseController {

	@Autowired
	private IOrderService OrderService;

	@Autowired
	private IProductService ProductService;

	@Autowired
	private IActivityService ActivityService;

	@RequestMapping(value = "/api/order/list", method = RequestMethod.GET)
	public ResponseEntity<?> List(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir, @RequestParam(defaultValue = "") String search) {

		User AuthUser = this.AuthUser();
		long user_id = AuthUser.getId();
		HashMap<String, Object> payload = OrderService.getPageList(user_id, page, limit, orderby, orderdir, search);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/detail/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Detail(@PathVariable long id) throws ParseException {
		HashMap<String, Object> payload = OrderService.getDetail(id);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/cancel/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> Cancel(@PathVariable int id) throws ParseException {
		User AuthUser = this.AuthUser();
		OrderService.CancelOrder(id);
		ActivityService.saveActivity(AuthUser, "Cancel Order", "Canceling Current Order",
				"Your has been canceling current order.");
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/wishlist/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> AddWhislist(@PathVariable int id) throws ParseException {
		User AuthUser = this.AuthUser();
		OrderService.AddWhislist(id, AuthUser.getId());
		ActivityService.saveActivity(AuthUser, "Add Wishlist", "Add Product To Wishlist",
				"Your has been added product to your wishlist.");
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/session", method = RequestMethod.GET)
	public ResponseEntity<?> GetSession() throws ParseException {
		User AuthUser = this.AuthUser();
		HashMap<String, Object> payload = OrderService.getSession(AuthUser.getId());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/create/review/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> CreateReview(@PathVariable(required = false) long id, @RequestBody ReviewSchema model)
			throws ParseException {
		User AuthUser = this.AuthUser();
		ProductDisplayDto product = ProductService.getById(id);
		OrderService.AddReview(AuthUser.getId(), id, model.getRating(), model.getReview());
		ActivityService.saveActivity(AuthUser, "Create new review", "Add review to " + product.getName(),
				"Your has been added new review to " + product.getName());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/list/review/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> ListReview(@PathVariable(required = false) long id) throws ParseException {
		List<ProductReviewDto> reiviews = OrderService.getProductReview(id);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", reiviews), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/create/cart/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> CreateCart(@PathVariable(required = false) long id, @RequestBody CreateCartSchema model)
			throws ParseException {
		User AuthUser = this.AuthUser();
		OrderService.AddToCart(AuthUser.getId(), id, model.getSizeId(), model.getColourId(), model.getQty());
		ActivityService.saveActivity(AuthUser, "Add Cart", "Add Product To Cart",
				"Your has been added product to cart.");
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/cart/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> CartInitiate(@PathVariable(required = false) long id) throws ParseException {
		User AuthUser = this.AuthUser();
		HashMap<String, Object> payload = OrderService.getInitiate(id, AuthUser.getId());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/checkout/initial", method = RequestMethod.GET)
	public ResponseEntity<?> CheckoutInitial() throws ParseException {
		User AuthUser = this.AuthUser();
		HashMap<String, Object> payload = OrderService.getCheckoutInitiate(AuthUser.getId());
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/order/checkout/submit", method = RequestMethod.POST)
	public ResponseEntity<?> CheckoutSubmit(@RequestBody CheckoutSchema model) throws ParseException {
		User AuthUser = this.AuthUser();
		OrderService.CheckOutOrder(AuthUser.getId(), model);
		ActivityService.saveActivity(AuthUser, "Checkout Order", "Completed Checkout Current Order",
				"Your order has been finished.");
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", null), HttpStatus.OK);
	}

}
