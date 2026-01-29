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

package com.api.backend.data;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.api.backend.config.EncoderConfig;
import com.api.backend.helpers.Helper;
import com.api.backend.models.entities.Authentication;
import com.api.backend.models.entities.Brand;
import com.api.backend.models.entities.Category;
import com.api.backend.models.entities.Colour;
import com.api.backend.models.entities.Payment;
import com.api.backend.models.entities.Product;
import com.api.backend.models.entities.ProductImage;
import com.api.backend.models.entities.ProductInventory;
import com.api.backend.models.entities.ProductReview;
import com.api.backend.models.entities.Setting;
import com.api.backend.models.entities.Size;
import com.api.backend.models.entities.User;
import com.api.backend.models.services.implementations.UserService;
import com.api.backend.models.services.implementations.AuthenticationService;
import com.api.backend.models.services.implementations.ProductService;
import com.api.backend.models.services.implementations.SizeService;
import com.api.backend.models.services.implementations.PaymentService;
import com.api.backend.models.services.implementations.ColourService;
import com.api.backend.models.services.implementations.BrandService;
import com.api.backend.models.services.implementations.CategoryService;
import com.api.backend.models.services.implementations.SettingService;
import com.api.backend.models.services.implementations.ProductReviewService;
import com.api.backend.models.services.implementations.ProductImageService;
import com.api.backend.models.services.implementations.ProductInventoryService;
import com.github.javafaker.Faker;

@Component
public class Seeder {

	@Autowired
	private UserService UserService;

	@Autowired
	private AuthenticationService AuthenticationService;

	@Autowired
	private ProductService ProductService;

	@Autowired
	private ProductReviewService ProductReviewService;

	@Autowired
	private ProductImageService ProductImageService;

	@Autowired
	private ProductInventoryService ProductInventoryService;

	@Autowired
	private SizeService SizeService;

	@Autowired
	private PaymentService PaymentService;

	@Autowired
	private ColourService ColourService;

	@Autowired
	private BrandService BrandService;

	@Autowired
	private CategoryService CategoryService;

	@Autowired
	private SettingService SettingService;

	@EventListener
	public void run(ContextRefreshedEvent event) throws ParseException {
		this.CreateUser();
		this.CreateSetting();
		this.CreateCategories();
		this.CreateBrands();
		this.CreateColours();
		this.CreatePayment();
		this.CreateSize();
		this.CreateProduct();
	}

	private void CreateProduct() throws ParseException {
		long TotalRows = ProductService.TotalRows();
		if (TotalRows == 0) {

			List<String> images = Arrays.asList(
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product01.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product02.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product03.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product04.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product05.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product06.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product07.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product08.png",
					"https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product09.png");

			List<Size> sizes = SizeService.findAll();
			List<Colour> colours = ColourService.findAll();

			for (int i = 1; i <= 9; i++) {
				int randomIndex = ThreadLocalRandom.current().nextInt(images.size());
				String image = images.get(randomIndex);
				Brand brand = BrandService.findOneByRandom();
				List<Category> Categories = CategoryService.findAllRandom(3);
				List<User> Reviewes = UserService.findAllRandom(5);
				double price = ThreadLocalRandom.current().nextDouble(100.0, 999.0);
				Faker faker = new Faker();

				Product p = new Product();
				p.setImage(image);
				p.setBrand(brand);
				p.setSku("P00" + i);
				p.setName("Product " + i);
				p.setTotalOrder(ThreadLocalRandom.current().nextInt(100, 1000));
				p.setTotalRating(ThreadLocalRandom.current().nextInt(100, 1000));
				p.setPrice(price);
				p.setDescription(faker.lorem().paragraph());
				p.setDetails(faker.lorem().paragraph());
				p.setStatus(1);
				p.setPublishedDate(Helper.DateNow());
				p.setCreatedAt(Helper.DateNow());
				p.setUpdatedAt(Helper.DateNow());
				p.setCategories(Set.copyOf(Categories));
				ProductService.saveOrUpdate(p);

				for (User reviewer : Reviewes) {
					ProductReview pr = new ProductReview();
					pr.setProduct(p);
					pr.setUser(reviewer);
					pr.setRating(ThreadLocalRandom.current().nextInt(100, 999));
					pr.setReview(faker.lorem().paragraph());
					pr.setStatus(1);
					pr.setCreatedAt(Helper.DateNow());
					pr.setUpdatedAt(Helper.DateNow());
					ProductReviewService.saveOrUpdate(pr);
				}

				for (int j = 1; j <= 3; j++) {
					int randomIndexSub = ThreadLocalRandom.current().nextInt(images.size());
					String imageSub = images.get(randomIndexSub);
					ProductImage pi = new ProductImage();
					pi.setProduct(p);
					pi.setPath(imageSub);
					pi.setSort(j);
					pi.setStatus(1);
					pi.setCreatedAt(Helper.DateNow());
					pi.setUpdatedAt(Helper.DateNow());
					ProductImageService.saveOrUpdate(pi);
				}

				for (Size size : sizes) {
					for (Colour colour : colours) {
						ProductInventory inv = new ProductInventory();
						inv.setProduct(p);
						inv.setColour(colour);
						inv.setSize(size);
						inv.setStock(ThreadLocalRandom.current().nextInt(100, 999));
						inv.setStatus(1);
						inv.setCreatedAt(Helper.DateNow());
						inv.setUpdatedAt(Helper.DateNow());
						ProductInventoryService.saveOrUpdate(inv);
					}
				}

			}

		}
	}

	private void CreateSize() throws ParseException {
		long TotalRows = SizeService.TotalRows();
		if (TotalRows == 0) {
			List<String> items = Arrays.asList("11 to 12 Inches", "13 to 14 Inches", "15 to 16 Inches",
					"17 to 18 Inches");

			for (int i = 0; i < items.size(); i++) {
				Faker faker = new Faker();
				Size model = new Size();
				model.setName(items.get(i));
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				model.setDescription(faker.lorem().paragraph());
				model.setStatus(1);
				SizeService.saveOrUpdate(model);
			}
		}
	}

	private void CreatePayment() throws ParseException {
		long TotalRows = PaymentService.TotalRows();
		if (TotalRows == 0) {
			List<String> items = Arrays.asList("Direct Bank Transfer", "Cheque Payment", "Paypal System");
			for (int i = 0; i < items.size(); i++) {
				Faker faker = new Faker();
				Payment model = new Payment();
				model.setName(items.get(i));
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				model.setDescription(faker.lorem().paragraph());
				model.setStatus(1);
				PaymentService.saveOrUpdate(model);
			}
		}
	}

	private void CreateColours() throws ParseException {
		long TotalRows = ColourService.TotalRows();
		if (TotalRows == 0) {
			Map<String, String> colors = new LinkedHashMap<>();
			colors.put("#FF0000", "Red");
			colors.put("#0000FF", "Blue");
			colors.put("#FFFF00", "Yellow");
			colors.put("#000000", "Black");
			colors.put("#FFFFFF", "White");
			colors.put("#666", "Dark Gray");
			colors.put("#AAA", "Light Gray");

			for (Map.Entry<String, String> entry : colors.entrySet()) {
				Faker faker = new Faker();
				Colour model = new Colour();
				model.setCode(entry.getKey().toString());
				model.setName(entry.getValue().toString());
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				model.setDescription(faker.lorem().paragraph());
				model.setStatus(1);
				ColourService.saveOrUpdate(model);
			}
		}
	}

	private void CreateBrands() throws ParseException {
		long TotalRows = BrandService.TotalRows();
		if (TotalRows == 0) {
			List<String> items = Arrays.asList("Samsung", "LG", "Sony", "Apple", "Microsoft");
			for (int i = 0; i < items.size(); i++) {
				Faker faker = new Faker();
				Brand model = new Brand();
				model.setName(items.get(i));
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				model.setDescription(faker.lorem().paragraph());
				model.setStatus(1);
				BrandService.saveOrUpdate(model);
			}
		}
	}

	private void CreateCategories() throws ParseException {
		long TotalRows = CategoryService.TotalRows();
		if (TotalRows == 0) {
			Map<String, String> items = new HashMap<>();
			items.put("Laptop", "https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product01.png");
			items.put("Smartphone", "https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product02.png");
			items.put("Camera", "https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product03.png");
			items.put("Accessories", "https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product04.png");
			items.put("Others", "https://5an9y4lf0n50.github.io/demo-images/demo-commerce/product05.png");

			int index = 0;
			for (Map.Entry<String, String> entry : items.entrySet()) {
				Faker faker = new Faker();
				Category model = new Category();
				model.setDisplayed(index < 3 ? 1 : 0);
				model.setImage(entry.getValue());
				model.setName(entry.getKey());
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				model.setDescription(faker.lorem().paragraph());
				model.setStatus(1);
				CategoryService.saveOrUpdate(model);
				index++;
			}
		}
	}

	private void CreateSetting() throws ParseException {
		long TotalRows = SettingService.TotalRows();
		if (TotalRows == 0) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String discountStart = LocalDateTime.now().format(formatter);
			String discountEnd = LocalDateTime.now().plusDays(7).format(formatter);

			Map<String, Object> settings = new LinkedHashMap<>();
			settings.put("about_section",
					"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut.");
			settings.put("com_location", "West Java, Indonesia");
			settings.put("com_phone", "+62-898-921-8470");
			settings.put("com_email", "sandy.andryanto.blade@gmail.com");
			settings.put("com_currency", "USD");
			settings.put("installed", 1);
			settings.put("discount_active", 1);
			settings.put("discount_value", 5);
			settings.put("discount_start", discountStart);
			settings.put("discount_end", discountEnd);
			settings.put("taxes_value", 10);
			settings.put("total_shipment", 50);

			for (Map.Entry<String, Object> entry : settings.entrySet()) {
				Setting model = new Setting();
				model.setKeyName(entry.getKey());
				model.setKeyValue(entry.getValue().toString());
				model.setStatus(1);
				model.setCreatedAt(Helper.DateNow());
				model.setUpdatedAt(Helper.DateNow());
				SettingService.saveOrUpdate(model);
			}

		}
	}

	private void CreateUser() throws ParseException {
		long TotalRows = UserService.TotalRows();
		if (TotalRows == 0) {
			for (int i = 1; i <= 10; i++) {

				UUID randomUUID = UUID.randomUUID();
				Faker faker = new Faker();
				EncoderConfig enc = new EncoderConfig();
				int max = 2;
				int min = 1;
				int genderIndex = min + (int) (Math.random() * ((max - min) + 1));
				String Gender = genderIndex == 1 ? "M" : "F";

				User user = new User();
				user.setEmail(faker.internet().emailAddress());
				user.setPhone(faker.phoneNumber().cellPhone());
				user.setPassword(enc.passwordEncoder().encode("Qwerty123!"));
				user.setFirstName(faker.name().firstName());
				user.setLastName(faker.name().lastName());
				user.setGender(Gender);
				user.setCity(faker.address().city());
				user.setZipCode(faker.address().zipCode());
				user.setCountry(faker.address().country());
				user.setAddress(faker.address().fullAddress());
				user.setCreatedAt(Helper.DateNow());
				user.setUpdatedAt(Helper.DateNow());
				user.setStatus(1);
				UserService.saveOrUpdate(user);

				Authentication auth = new Authentication();
				auth.setUser(user);
				auth.setAuthType("email-confirm");
				auth.setCredential(user.getEmail());
				auth.setToken(randomUUID.toString());
				auth.setStatus(2);
				auth.setCreatedAt(Helper.DateNow());
				auth.setUpdatedAt(Helper.DateNow());
				auth.setExpiredAt(Helper.DateNow());
				AuthenticationService.saveOrUpdate(auth);

			}
		}
	}

}
