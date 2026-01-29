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

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.backend.models.dto.DetailSummaryDto;
import com.api.backend.models.dto.JsonResponseDto;
import com.api.backend.models.dto.ProductDisplayDto;
import com.api.backend.models.entities.Product;
import com.api.backend.models.services.interfaces.IBrandService;
import com.api.backend.models.services.interfaces.ICategoryService;
import com.api.backend.models.services.interfaces.IProductService;
import java.util.List;

@RestController
public class ShopController extends BaseController {
	
	@Autowired
	private ICategoryService CategoryService;
	
	@Autowired
	private IBrandService BrandService;
	
	@Autowired
	private IProductService ProductService;
	
	@RequestMapping(value = "/api/shop/filter", method = RequestMethod.GET)
	public ResponseEntity<?> Filter() {
		
		HashMap<String, Object> payload = new HashMap<>();
		Product getMaxPrice = ProductService.getMaxPrice();
		Product getMinPrice = ProductService.getMinPrice();
		List<DetailSummaryDto> Brands = BrandService.getDetailSummary();
		List<DetailSummaryDto> Categories = CategoryService.getDetailSummary();
		List<ProductDisplayDto> topSelling = ProductService.getTopSellings(3);
		
		payload.put("categories", Categories);
		payload.put("brands", Brands);
		payload.put("tops", topSelling);
		payload.put("maxPrice", getMinPrice.getPrice());
		payload.put("minPrice", getMaxPrice.getPrice());
		
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/shop/list", method = RequestMethod.GET)
	public ResponseEntity<?> List(
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "9") int limit, 
			@RequestParam(defaultValue = "id") String orderby,
			@RequestParam(defaultValue = "desc") String orderdir,
			@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "") String category,
			@RequestParam(defaultValue = "") String brand,
			@RequestParam(defaultValue = "") String maxPrice,
			@RequestParam(defaultValue = "") String minPrice
			) {
		HashMap<String, Object> payload = ProductService.getPageProduct(page, limit, orderby, orderdir, category, brand, search, maxPrice, minPrice);
		return new ResponseEntity<Object>(new JsonResponseDto(true, "ok", payload), HttpStatus.OK);
	}

}
