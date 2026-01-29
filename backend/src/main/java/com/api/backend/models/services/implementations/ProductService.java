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

package com.api.backend.models.services.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.models.dto.ProductDisplayDto;
import com.api.backend.models.entities.Category;
import com.api.backend.models.entities.Product;
import com.api.backend.models.repositories.ProductRepository;
import com.api.backend.models.services.interfaces.ICategoryService;
import com.api.backend.models.services.interfaces.IProductService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private ICategoryService CategoryService;

	@Autowired
	private EntityManager entityManager;

	@Override
	public Product saveOrUpdate(Product model) {
		// TODO Auto-generated method stub
		repo.save(model);
		return model;
	}

	@Override
	public Long TotalRows() {
		// TODO Auto-generated method stub
		return repo.count();
	}

	@Override
	public Product getTopRating() {
		// TODO Auto-generated method stub
		return repo.getTopRating();
	}

	@Override
	public List<Product> getDispalyed(String column, int limit) {
		// TODO Auto-generated method stub
		return repo.getDispalyed(column, limit);
	}

	@Override
	public HashMap<String, Object> getPageDispalyed() {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();

		List<Category> categories = CategoryService.findAllDisplayed(3);
		List<ProductDisplayDto> newest = new ArrayList<>();
		List<ProductDisplayDto> bestSellers = new ArrayList<>();
		List<ProductDisplayDto> topSellings = new ArrayList<>();

		List<Product> getNewest = repo.getDispalyed("x.id", 4);
		List<Product> getBesetSellers = repo.getDispalyed("x.total_rating", 3);
		List<Product> getTopSellings = repo.getDispalyed("x.total_order", 6);

		bestSellers = generateResult(getBesetSellers);
		topSellings = generateResult(getTopSellings);
		newest = generateResult(getNewest);

		payload.put("categories", categories);
		payload.put("bestSellers", bestSellers);
		payload.put("topSellings", topSellings);
		payload.put("newest", newest);
		return payload;
	}

	private List<ProductDisplayDto> generateResult(List<Product> data) {
		Product topRating = repo.getTopRating();
		List<ProductDisplayDto> dataResult = new ArrayList<>();
		for (Product p : data) {
			int getRandom = ThreadLocalRandom.current().nextInt(1, 2);
			Double price = p.getPrice();
			Double priceOld = price + (price * 0.05);
			Double TotalRating = ((((double) p.getTotalRating() / (double) topRating.getTotalRating()) * 100) / 20);
			Set<Category> getCategories = p.getCategories();
			List<Category> ProductCategories = new ArrayList<>(getCategories);
			List<String> categoryNames = ProductCategories.stream().map(Category::getName).collect(Collectors.toList());
			ProductDisplayDto d = new ProductDisplayDto();
			d.setId(p.getId());
			d.setImage(p.getImage());
			d.setName(p.getName());
			d.setDescription(p.getDescription());
			d.setDetails(p.getDetails());
			d.setPrice((double) Math.round(p.getPrice()));
			d.setPriceOld((double) Math.round(priceOld));
			d.setCategoryName(ProductCategories.get(0).getName());
			d.setCategories(categoryNames);
			d.setIsDiscount(getRandom == 1);
			d.setIsNewest(getRandom == 2);
			d.setTotalRating(Math.floor(TotalRating));
			dataResult.add(d);
		}
		return dataResult;
	}

	@Override
	public Product getMaxPrice() {
		// TODO Auto-generated method stub
		return repo.getMaxPrice();
	}

	@Override
	public Product getMinPrice() {
		// TODO Auto-generated method stub
		return repo.getMinPrice();
	}

	@Override
	public List<ProductDisplayDto> getTopSellings(int limit) {
		// TODO Auto-generated method stub
		List<ProductDisplayDto> topSellings = new ArrayList<>();
		List<Product> getTopSellings = repo.getDispalyed("x.total_order", limit);
		topSellings = generateResult(getTopSellings);
		return topSellings;
	}

	@Override
	public HashMap<String, Object> getPageProduct(int page, int limit, String order, String sort, String categories,
			String brand, String search, String maxPrice, String minPrice) {
		// TODO Auto-generated method stub

		List<ProductDisplayDto> getDisplayed = new ArrayList<>();
		HashMap<String, Object> payload = new HashMap<>();
		Long totalAll = this.TotalRows();
		Long totalFiltered = this.TotalRows();
		int offset = ((page - 1) * limit);

		String Additional = " x.id IS NOT NULL";

		if (!brand.isBlank()) {
			Additional += " AND x.brand_id IN (" + brand + ")";
		}

		if (!categories.isBlank()) {
			Additional += " AND x.id IN (SELECT p.product_id FROM products_categories p WHERE p.category_id IN ("
					+ categories + "))";
		}
		
		if (!maxPrice.isBlank() && !minPrice.isBlank()) {
			Additional += " AND x.price BETWEEN "+minPrice+" AND "+maxPrice+" ";
		}

		String base = """
				SELECT * FROM public.products x
				WHERE x.published_date <= NOW()
				AND x.status = 1
				AND (
				    LOWER(CAST(x.sku as varchar)) LIKE :search
				    OR LOWER(CAST(x.name as varchar)) LIKE :search
				)
				AND """ + Additional + """
				    ORDER BY %s %s
				    LIMIT :limit OFFSET :offset
				""".formatted(order, sort);

		Query query = entityManager.createNativeQuery(base, Product.class);
		query.setParameter("limit", limit);
		query.setParameter("offset", offset);
		query.setParameter("search", "%" + search.toLowerCase() + "%");
		List<Product> getProduct = query.getResultList();
		totalFiltered = (long) getProduct.size();

		getDisplayed = generateResult(getProduct);
		payload.put("list", getDisplayed);
		payload.put("totalAll", totalAll);
		payload.put("totalFiltered", totalFiltered);
		payload.put("limit", limit);
		payload.put("page", page);
		return payload;
	}

	@Override
	public ProductDisplayDto getById(long id) {
		// TODO Auto-generated method stub
		Product topRating = repo.getTopRating();
		Product p = repo.findById(id).orElse(null);
		int getRandom = ThreadLocalRandom.current().nextInt(1, 2);
		Double price = p.getPrice();
		Double priceOld = price + (price * 0.05);
		Double TotalRating = ((((double) p.getTotalRating() / (double) topRating.getTotalRating()) * 100) / 20);
		Set<Category> getCategories = p.getCategories();
		List<Category> ProductCategories = new ArrayList<>(getCategories);
		List<String> categoryNames = ProductCategories.stream().map(Category::getName).collect(Collectors.toList());
		ProductDisplayDto d = new ProductDisplayDto();
		d.setId(p.getId());
		d.setImage(p.getImage());
		d.setName(p.getName());
		d.setDescription(p.getDescription());
		d.setDetails(p.getDetails());
		d.setPrice((double) Math.round(p.getPrice()));
		d.setPriceOld((double) Math.round(priceOld));
		d.setCategoryName(ProductCategories.get(0).getName());
		d.setCategories(categoryNames);
		d.setIsDiscount(getRandom == 1);
		d.setIsNewest(getRandom == 2);
		d.setTotalRating(Math.floor(TotalRating));
		return d;
	}

	@Override
	public List<ProductDisplayDto> getRecomended(long product_id) {
		// TODO Auto-generated method stub
		List<Product> getTopSellings = repo.getRecomended(product_id, 3);
		return generateResult(getTopSellings);
	}

	@Override
	public Product getOneById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}
	
	

}
