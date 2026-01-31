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

package com.api.backend.models.services.implementations;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backend.helpers.Helper;
import com.api.backend.models.dto.DetailOrderDto;
import com.api.backend.models.dto.InventoryDto;
import com.api.backend.models.dto.ProductCartDto;
import com.api.backend.models.dto.ProductDisplayDto;
import com.api.backend.models.dto.ProductImageDto;
import com.api.backend.models.dto.ProductReviewDto;
import com.api.backend.models.dto.ProductWishlistsDto;
import com.api.backend.models.dto.UserDetailDto;
import com.api.backend.models.entities.Colour;
import com.api.backend.models.entities.Order;
import com.api.backend.models.entities.OrderBilling;
import com.api.backend.models.entities.OrderDetail;
import com.api.backend.models.entities.Payment;
import com.api.backend.models.entities.Product;
import com.api.backend.models.entities.ProductImage;
import com.api.backend.models.entities.ProductInventory;
import com.api.backend.models.entities.ProductReview;
import com.api.backend.models.entities.Size;
import com.api.backend.models.entities.User;
import com.api.backend.models.repositories.OrderRepository;
import com.api.backend.models.schema.CheckoutSchema;
import com.api.backend.models.schema.ListOrderDto;
import com.api.backend.models.services.interfaces.IColourService;
import com.api.backend.models.services.interfaces.IOrderBillingService;
import com.api.backend.models.services.interfaces.IOrderDetailService;
import com.api.backend.models.services.interfaces.IOrderService;
import com.api.backend.models.services.interfaces.IProductImageService;
import com.api.backend.models.services.interfaces.IProductInventoryService;
import com.api.backend.models.services.interfaces.IProductReviewService;
import com.api.backend.models.services.interfaces.IProductService;
import com.api.backend.models.services.interfaces.ISettingService;
import com.api.backend.models.services.interfaces.ISizeService;
import com.api.backend.models.services.interfaces.IUserService;
import com.api.backend.models.services.interfaces.IPaymentService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository repo;

	@Autowired
	private IOrderDetailService OrderDetailService;

	@Autowired
	private IOrderBillingService OrderBillingService;

	@Autowired
	private ISettingService SettingService;

	@Autowired
	private IProductService ProductService;

	@Autowired
	private IColourService ColourService;

	@Autowired
	private IPaymentService PaymentService;

	@Autowired
	private ISizeService SizeService;

	@Autowired
	private IUserService UserService;

	@Autowired
	private IProductInventoryService ProductInventoryService;

	@Autowired
	private IProductImageService ProductImageService;

	@Autowired
	private IProductReviewService ProductReviewService;

	@Autowired
	private EntityManager entityManager;

	@Override
	public Long TotalRows(long user_id) {
		// TODO Auto-generated method stub
		return repo.count(user_id);
	}

	@Override
	public Order saveOrUpdate(Order model) {
		// TODO Auto-generated method stub
		repo.save(model);
		return model;
	}

	@Override
	public HashMap<String, Object> getPageList(long user_id, int page, int limit, String order, String sort,
			String search) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		Long totalAll = this.TotalRows(user_id);
		Long totalFiltered = this.TotalRows(user_id);
		int offset = ((page - 1) * limit);

		String base = """
				SELECT * FROM public.orders x
				WHERE x.user_id = :user_id
				AND LOWER(CAST(x.invoice_number as varchar)) LIKE :search
				    ORDER BY %s %s
				    LIMIT :limit OFFSET :offset
				""".formatted(order, sort);

		Query query = entityManager.createNativeQuery(base, Order.class);
		query.setParameter("user_id", user_id);
		query.setParameter("limit", limit);
		query.setParameter("offset", offset);
		query.setParameter("search", "%" + search.toLowerCase() + "%");

		List<Order> getOrder = query.getResultList();
		totalFiltered = (long) getOrder.size();

		List<ListOrderDto> resultData = new ArrayList<>();

		for (Order ord : getOrder) {
			ListOrderDto x = new ListOrderDto();
			x.setId(ord.getId());
			x.setCreatedAt(ord.getCreatedAt());
			x.setInvoiceNumber(ord.getInvoiceNumber());
			x.setTotalItem(ord.getTotalItem());
			x.setTotalPaid(ord.getTotalPaid());
			x.setStatus(ord.getStatus());
			resultData.add(x);
		}

		payload.put("list", resultData);
		payload.put("totalAll", totalAll);
		payload.put("totalFiltered", totalFiltered);
		payload.put("limit", limit);
		payload.put("page", page);
		return payload;
	}

	@Override
	@Transactional
	public void CancelOrder(int order_id) {
		// TODO Auto-generated method stub
		entityManager.createNativeQuery("DELETE FROM orders_details WHERE order_id = :id").setParameter("id", order_id)
				.executeUpdate();
		entityManager.createNativeQuery("DELETE FROM orders_carts WHERE order_id = :id").setParameter("id", order_id)
				.executeUpdate();
		entityManager.createNativeQuery("DELETE FROM orders_billings WHERE order_id = :id").setParameter("id", order_id)
				.executeUpdate();
		entityManager.createNativeQuery("DELETE FROM orders WHERE id = :id").setParameter("id", order_id)
				.executeUpdate();
	}

	@Override
	public HashMap<String, Object> getDetail(long order_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();

		Order order = repo.findById(order_id);
		DetailOrderDto resultOrder = new DetailOrderDto();
		resultOrder.setId(order.getId());
		resultOrder.setCreatedAt(order.getCreatedAt());
		resultOrder.setUpdatedAt(order.getUpdatedAt());
		resultOrder.setTotalDiscount(order.getTotalDiscount());
		resultOrder.setTotalPaid(order.getTotalPaid());
		resultOrder.setTotalTaxes(order.getTotalTaxes());
		resultOrder.setTotalShipment(order.getTotalShipment());
		resultOrder.setStatus(order.getStatus());
		resultOrder.setInvoiceNumber(order.getInvoiceNumber());
		resultOrder.setPaymentName(order.getPayment() != null ? order.getPayment().getName() : "-");
		resultOrder.setSubtotal(order.getSubtotal());

		List<ProductCartDto> carts = OrderDetailService.findByOrder(order_id);
		List<OrderBilling> orderBillings = OrderBillingService.getByOrder(order_id);
		List<HashMap<String, Object>> billings = new ArrayList<>();

		for (OrderBilling x : orderBillings) {
			HashMap<String, Object> xx = new HashMap<>();
			xx.put("name", x.getName());
			xx.put("description", x.getDescription());
			billings.add(xx);
		}

		Double discount = (order.getTotalDiscount() / order.getSubtotal()) * 100;
		Double taxes = (order.getTotalTaxes() / order.getSubtotal()) * 100;

		payload.put("discount", Math.round(discount));
		payload.put("taxes", Math.round(taxes));
		payload.put("shipment", order.getTotalShipment());
		payload.put("billings", billings);
		payload.put("carts", carts);
		payload.put("order", resultOrder);
		return payload;
	}

	@Override
	public Order findById(long id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Order findBySession(long user_id) {
		// TODO Auto-generated method stub
		return repo.findBySession(user_id);
	}

	@Override
	@Transactional
	public void AddWhislist(long product_id, long user_id) {
		// TODO Auto-generated method stub
		entityManager
				.createNativeQuery(
						"DELETE FROM products_wishlists  WHERE product_id = :product_id AND user_id = :user_id")
				.setParameter("product_id", product_id).setParameter("user_id", user_id).executeUpdate();

		entityManager
				.createNativeQuery("INSERT INTO products_wishlists(product_id, user_id) VALUES (:product_id, :user_id)")
				.setParameter("product_id", product_id).setParameter("user_id", user_id).executeUpdate();
	}

	@Override
	@Transactional
	public void AddToCart(long user_id, long product_id, long size_id, long colour_id, int qty) {
		// TODO Auto-generated method stub
		try {

			ProductInventory inventory = ProductInventoryService.getOneByProduct(product_id, size_id, colour_id);
			User user = UserService.getOneById(user_id);
			Instant now = Instant.now();
			long dotNetEpochTicks = 621355968000000000L;
			long ticks = now.toEpochMilli() * 10_000 + dotNetEpochTicks;

			if (inventory != null) {

				Product product = inventory.getProduct();
				Order order = repo.findBySession(user_id);
				Double price = product.getPrice();
				Double total = price * qty;

				if (order == null) {
					order = new Order();
					order.setTotalDiscount(0.0);
					order.setTotalTaxes(0.0);
					order.setTotalShipment(0.0);
					order.setUser(user);
					order.setInvoiceNumber(String.valueOf(ticks));
					order.setTotalItem(qty);
					order.setSubtotal(total);
					order.setTotalPaid(total);
					order.setStatus(0);
					order.setCreatedAt(Helper.DateNow());
					order.setUpdatedAt(Helper.DateNow());
					repo.save(order);
				} else {
					order.setTotalItem(order.getTotalItem() + qty);
					order.setSubtotal(order.getSubtotal() + total);
					order.setTotalPaid(order.getTotalPaid() + total);
					order.setUpdatedAt(Helper.DateNow());
					repo.save(order);
				}

				OrderDetail detail = OrderDetailService.getOneByOrderInventory(user_id, inventory.getId());

				if (detail == null) {
					detail = new OrderDetail();
					detail.setInventory(inventory);
					detail.setOrder(order);
					detail.setPrice(price);
					detail.setQty(qty);
					detail.setTotal(total);
					detail.setStatus(1);
					detail.setCreatedAt(Helper.DateNow());
					detail.setUpdatedAt(Helper.DateNow());
					OrderDetailService.saveOrUpdate(detail);
				} else {
					detail.setQty(detail.getQty() + qty);
					detail.setTotal(detail.getTotal() + total);
					detail.setUpdatedAt(Helper.DateNow());
					OrderDetailService.saveOrUpdate(detail);
				}

				entityManager
						.createNativeQuery(
								"DELETE FROM orders_carts  WHERE order_id = :order_id AND user_id = :user_id")
						.setParameter("order_id", order.getId()).setParameter("user_id", user_id).executeUpdate();

				entityManager
						.createNativeQuery("INSERT INTO orders_carts(order_id, user_id) VALUES (:order_id, :user_id)")
						.setParameter("order_id", order.getId()).setParameter("user_id", user_id).executeUpdate();

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public void AddReview(long user_id, long product_id, int rating, String review) {
		// TODO Auto-generated method stub
		try {
			User user = UserService.getOneById(user_id);
			Product product = ProductService.getOneById(product_id);
			ProductReview model = new ProductReview();
			model.setUser(user);
			model.setProduct(product);
			model.setRating(rating);
			model.setReview(review);
			model.setStatus(1);
			model.setCreatedAt(Helper.DateNow());
			model.setUpdatedAt(Helper.DateNow());
			ProductReviewService.saveOrUpdate(model);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public HashMap<String, Object> getSession(long user_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		Order order = repo.findBySession(user_id);
		DetailOrderDto resultOrder = new DetailOrderDto();

		if (order != null) {
			resultOrder.setId(order.getId());
			resultOrder.setCreatedAt(order.getCreatedAt());
			resultOrder.setUpdatedAt(order.getUpdatedAt());
			resultOrder.setTotalDiscount(order.getTotalDiscount());
			resultOrder.setTotalPaid(order.getTotalPaid());
			resultOrder.setTotalTaxes(order.getTotalTaxes());
			resultOrder.setTotalShipment(order.getTotalShipment());
			resultOrder.setStatus(order.getStatus());
			resultOrder.setInvoiceNumber(order.getInvoiceNumber());
			resultOrder.setPaymentName(order.getPayment() != null ? order.getPayment().getName() : "-");
			resultOrder.setSubtotal(order.getSubtotal());
		}

		long order_id = order != null ? order.getId() : 0;
		List<ProductCartDto> carts = OrderDetailService.findByOrder(order_id);
		List<ProductWishlistsDto> wishlists = repo.findByWishlistsByUser(user_id);
		payload.put("carts", carts);
		payload.put("order", resultOrder);
		payload.put("whislists", wishlists);
		return payload;
	}

	@Override
	public HashMap<String, Object> getInitiate(long id, long user_id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		ProductDisplayDto product = ProductService.getById(id);
		User AuthUser = UserService.getOneById(user_id);
		List<Size> sizes = SizeService.findAll();
		List<Colour> colours = ColourService.findAll();
		List<ProductInventory> inventories = ProductInventoryService.getAvailable(id);
		List<ProductImage> images = ProductImageService.getAllByProduct(id);
		List<ProductDisplayDto> getRecomended = ProductService.getRecomended(id);
		List<ProductImageDto> resultImages = new ArrayList<>();
		List<InventoryDto> resultInventory = new ArrayList<>();
		UserDetailDto UserDetail = UserService.detail(AuthUser);

		for (ProductImage img : images) {
			ProductImageDto x = new ProductImageDto();
			x.setId(img.getId());
			x.setPath(img.getPath());
			resultImages.add(x);
		}

		for (ProductInventory inv : inventories) {
			InventoryDto x = new InventoryDto();
			x.setId(inv.getId());
			x.setProductId(inv.getProduct().getId());
			x.setSizeId(inv.getSize().getId());
			x.setColourId(inv.getColour().getId());
			x.setStock(inv.getStock());
			resultInventory.add(x);
		}

		payload.put("images", resultImages);
		payload.put("product", product);
		payload.put("productRelated", getRecomended);
		payload.put("sizes", sizes);
		payload.put("colours", colours);
		payload.put("inventories", resultInventory);
		payload.put("user", UserDetail);
		return payload;
	}

	@Override
	public List<ProductReviewDto> getProductReview(long id) {
		// TODO Auto-generated method stub
		List<ProductReview> reviews = ProductReviewService.getAllByProduct(id);
		List<ProductReviewDto> result = new ArrayList<>();
		ProductReview TopRating = ProductReviewService.getTopRating(id);

		for (ProductReview row : reviews) {
			Double Rating = ((((double) row.getRating() / (double) TopRating.getRating()) * 100) / 20);
			Double Percentage = (((double) row.getRating() / (double) TopRating.getRating()) * 100);
			ProductReviewDto pr = new ProductReviewDto();
			pr.setId(row.getId());
			pr.setFirstName(row.getUser().getFirstName());
			pr.setLastName(row.getUser().getLastName());
			pr.setReviewDescription(row.getReview());
			pr.setRating(Math.round(Rating));
			pr.setPercentage(Math.round(Percentage));
			pr.setCreatedAt(row.getCreatedAt());
			result.add(pr);
		}

		return result;
	}

	@Override
	public HashMap<String, Object> getCheckoutInitiate(long id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> payload = new HashMap<>();
		Order order = repo.findBySession(id);

		User user = UserService.getOneById(id);
		long order_id = order != null ? order.getId() : 0;
		List<ProductCartDto> carts = OrderDetailService.findByOrder(order_id);
		UserDetailDto userDetail = UserService.detail(user);
		List<Payment> payments = PaymentService.getAll();

		String txtDiscount = SettingService.getValue("discount_value");
		String txtTaxes = SettingService.getValue("taxes_value");
		String txtShipment = SettingService.getValue("total_shipment");

		Double iDiscount = Double.parseDouble(txtDiscount);
		Double iTaxes = Double.parseDouble(txtTaxes);
		Double iShipment = Double.parseDouble(txtShipment);

		Double Subtotal = order.getSubtotal();
		Double TotalDiscount = Subtotal * (iDiscount / 100);
		Double TotalTaxes = Subtotal * (iTaxes / 100);
		Double GrandTotal = (Subtotal + TotalTaxes + iShipment) - TotalDiscount;

		order.setSubtotal(Subtotal);
		order.setTotalDiscount(TotalDiscount);
		order.setTotalTaxes(TotalTaxes);
		order.setTotalShipment(iShipment);
		order.setTotalPaid(GrandTotal);

		DetailOrderDto resultOrder = new DetailOrderDto();
		resultOrder.setId(order.getId());
		resultOrder.setCreatedAt(order.getCreatedAt());
		resultOrder.setUpdatedAt(order.getUpdatedAt());
		resultOrder.setTotalDiscount(order.getTotalDiscount());
		resultOrder.setTotalPaid(order.getTotalPaid());
		resultOrder.setTotalTaxes(order.getTotalTaxes());
		resultOrder.setTotalShipment(order.getTotalShipment());
		resultOrder.setStatus(order.getStatus());
		resultOrder.setInvoiceNumber(order.getInvoiceNumber());
		resultOrder.setPaymentName(order.getPayment() != null ? order.getPayment().getName() : "-");
		resultOrder.setSubtotal(order.getSubtotal());

		payload.put("order", resultOrder);
		payload.put("carts", carts);
		payload.put("user", userDetail);
		payload.put("payments", payments);
		payload.put("discount", txtDiscount);
		payload.put("taxes", txtTaxes);
		payload.put("shipment", txtShipment);
		return payload;
	}

	@Override
	@Transactional
	public void CheckOutOrder(long user_id, CheckoutSchema model) {
		// TODO Auto-generated method stub
		try {

			Order order = repo.findBySession(user_id);
			Payment payment = PaymentService.getOneById(model.getPaymentId());

			String txtDiscount = SettingService.getValue("discount_value");
			String txtTaxes = SettingService.getValue("taxes_value");
			String txtShipment = SettingService.getValue("total_shipment");

			Double iDiscount = Double.parseDouble(txtDiscount);
			Double iTaxes = Double.parseDouble(txtTaxes);
			Double iShipment = Double.parseDouble(txtShipment);

			Double Subtotal = order.getSubtotal();
			Double TotalDiscount = Subtotal * (iDiscount / 100);
			Double TotalTaxes = Subtotal * (iTaxes / 100);
			Double GrandTotal = (Subtotal + TotalTaxes + iShipment) - TotalDiscount;

			order.setStatus(1);
			order.setPayment(payment);
			order.setSubtotal(Subtotal);
			order.setTotalDiscount(TotalDiscount);
			order.setTotalTaxes(TotalTaxes);
			order.setTotalShipment(iShipment);
			order.setTotalPaid(GrandTotal);
			order.setUpdatedAt(Helper.DateNow());
			repo.save(order);

			List<OrderDetail> details = OrderDetailService.findByOrderId(order.getId());

			for (OrderDetail detail : details) {
				ProductInventory inventory = detail.getInventory();
				inventory.setStock(inventory.getStock() - detail.getQty());
				ProductInventoryService.saveOrUpdate(inventory);

				Product pr = inventory.getProduct();
				pr.setTotalOrder(pr.getTotalOrder() + detail.getQty());
				ProductService.saveOrUpdate(pr);

				entityManager
						.createNativeQuery(
								"DELETE FROM products_wishlists WHERE product_id = :product_id AND user_id = :user_id")
						.setParameter("product_id", pr.getId()).setParameter("user_id", user_id).executeUpdate();

			}

			OrderBillingService.saveOrUpdate(new OrderBilling(order, "First Name", model.getFirstName()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Las tName", model.getLastName()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Email", model.getEmail()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Phone", model.getPhone()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "City", model.getCity()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Country", model.getCountry()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Zip Code", model.getZipCode()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Street Address", model.getAddress()));
			OrderBillingService.saveOrUpdate(new OrderBilling(order, "Notes", model.getNotes()));

			entityManager.createNativeQuery("DELETE FROM orders_carts  WHERE order_id = :id")
					.setParameter("id", order.getId()).executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
