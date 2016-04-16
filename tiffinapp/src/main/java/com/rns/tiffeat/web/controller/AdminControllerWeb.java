package com.rns.tiffeat.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.rns.tiffeat.web.bo.api.CustomerBo;
import com.rns.tiffeat.web.bo.api.SessionManager;
import com.rns.tiffeat.web.bo.api.VendorBo;
import com.rns.tiffeat.web.bo.domain.Admin;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.OrderStatus;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.bo.domain.VendorInvoice;
import com.rns.tiffeat.web.bo.domain.VendorStatus;
import com.rns.tiffeat.web.util.Constants;

@Controller
public class AdminControllerWeb implements Constants {

	private VendorBo vendorBo;
	private CustomerBo customerBo;

	@Autowired(required = true)
	@Qualifier(value = "manager")
	private SessionManager manager;

	public void setManager(SessionManager manager) {
		this.manager = manager;
	}

	public SessionManager getManager() {
		return manager;
	}

	public CustomerBo getCustomerBo() {
		return customerBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "customerBo")
	public void setCustomerBo(CustomerBo customerBo) {
		this.customerBo = customerBo;
	}

	public VendorBo getVendorBo() {
		return vendorBo;
	}

	@Autowired(required = true)
	@Qualifier(value = "vendorBo")
	public void setCustomerBo(VendorBo vendorBo) {
		this.vendorBo = vendorBo;
	}

	@RequestMapping(value = "/admin.htm", method = RequestMethod.GET)
	public String initAdmin(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		prepareAdminHome(model);
		return "admin_home";
	}

	@RequestMapping(value = "/adminLogin.htm", method = RequestMethod.GET)
	public String adminLogin(ModelMap model) {
		prepareAdminLogin(model);
		return "admin_login";
	}

	private void prepareAdminLogin(ModelMap model) {
		model.addAttribute(MODEL_ADMIN, new Admin());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
	}

	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public RedirectView adminLogin(Admin admin, ModelMap model) {
		if (!admin.validateAdminDetails()) {
			return new RedirectView("adminLogin.htm");
		}
		manager.getAdmin().setUsername(admin.getUsername());
		return new RedirectView("admin.htm");
	}

	private void prepareAdminHome(ModelMap model) {
		model.addAttribute(MODEL_VENDORS, vendorBo.getAllVendors());
		manager.getAdmin().setTodaysOrders(vendorBo.getAllOrders(null));
		manager.getAdmin().setCounts();
		model.addAttribute(MODEL_ADMIN, manager.getAdmin());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.getAdmin().setCurrentOrder(null);
	}

	@RequestMapping(value = "/meals.htm", method = RequestMethod.GET)
	public String initMeals(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		manager.getAdmin().setCurrentVendor(null);
		model.addAttribute(MODEL_MEALS, vendorBo.getAllMeals());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_meals";
	}

	@RequestMapping(value = "/customers.htm", method = RequestMethod.GET)
	public String initCustomers(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		manager.getAdmin().setCurrentVendor(null);
		model.addAttribute(MODEL_CUSTOMERS, customerBo.getAllCustomers());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_customers";
	}

	@RequestMapping(value = "/addVendor.htm", method = RequestMethod.GET)
	public String initAddVendor(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		model.addAttribute(MODEL_VENDOR, new Vendor());
		model.addAttribute(MODEL_AREAS, AREAS);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_add_vendor";
	}

	@RequestMapping(value = "/editVendor.htm", method = RequestMethod.GET)
	public String initEditVendor(String vendor, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		Vendor registeredVendor = new Vendor();
		registeredVendor.setEmail(vendor);
		model.addAttribute(MODEL_VENDOR, customerBo.getVendorWithMeals(registeredVendor));
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		model.addAttribute(MODEL_VENDOR_STATUS, VendorStatus.values());
		return "admin_edit_vendor";
	}

	@RequestMapping(value = "/addVendor", method = RequestMethod.POST)
	public RedirectView initAddVendor(Vendor vendor, ModelMap model) {
		vendorBo.register(vendor);
		return new RedirectView("admin.htm");
	}

	@RequestMapping(value = "/editVendor", method = RequestMethod.POST)
	public RedirectView editVendor(Vendor vendor, MultipartFile image, ModelMap model) {
		manager.setResult(vendorBo.updateVendorProfile(vendor, image));
		if (!RESPONSE_OK.equals(manager.getResult())) {
			return new RedirectView("editVendor.htm?vendor=" + vendor.getEmail());
		}
		return new RedirectView("admin.htm");
	}

	@RequestMapping(value = "/vendorDetails", method = RequestMethod.POST)
	public RedirectView gotoVendorDetails(Vendor vendor, ModelMap model) {
		manager.getAdmin().setCurrentVendor(vendor);
		return new RedirectView("vendorDetails.htm");
	}

	@RequestMapping(value = "/vendorDetails.htm", method = RequestMethod.GET)
	public String viewVendorDetails(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		Vendor vendorWithMeals = customerBo.getVendorWithMeals(manager.getAdmin().getCurrentVendor());
		if (vendorWithMeals == null) {
			prepareAdminHome(model);
			return "admin_home";
		}
		vendorBo.setDailyContents(vendorWithMeals.getMeals());
		manager.getAdmin().setCurrentVendor(vendorWithMeals);
		vendorBo.setDailyContents(manager.getAdmin().getCurrentVendor().getMeals());
		model.addAttribute(MODEL_VENDOR, manager.getAdmin().getCurrentVendor());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_vendor_details";
	}

	@RequestMapping(value = "/addNewMeal.htm", method = RequestMethod.GET)
	public String initAddMeal(String vendorEmail, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		Vendor registeredVendor = new Vendor();
		registeredVendor.setEmail(vendorEmail);
		model.addAttribute(MODEL_VENDOR, registeredVendor);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		model.addAttribute(MODEL_MEAL_TYPE, MealType.values());
		manager.setResult(null);
		return "admin_add_meal";
	}

	@RequestMapping(value = "/editMeal.htm", method = RequestMethod.GET)
	public String initEditMeal(long mealId, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		Meal meal = new Meal();
		meal.setId(mealId);
		Meal mealToEdit = vendorBo.getMeal(meal);
		model.addAttribute(MODEL_MEAL, mealToEdit);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		model.addAttribute(MODEL_MEAL_TYPE, MealType.values());
		manager.setResult(null);
		return "admin_edit_meal";
	}

	@RequestMapping(value = "/addMeal", method = RequestMethod.POST)
	public RedirectView addMeal(Meal meal, String vendorEmail, MultipartFile imageFile, ModelMap model) {
		Vendor vendor = new Vendor();
		vendor.setEmail(vendorEmail);
		meal.setVendor(vendor);
		String resultMsg = vendorBo.addMeal(meal, imageFile);
		if (!RESPONSE_OK.equals(resultMsg)) {
			manager.setResult(resultMsg);
			return new RedirectView("addNewMeal.htm?vendorEmail=" + vendor.getEmail());
		}
		return new RedirectView("vendorDetails.htm");
	}

	@RequestMapping(value = "/editMeal", method = RequestMethod.POST)
	public RedirectView editMeal(Meal meal, MultipartFile imageFile, ModelMap model) {

		String resultMsg = vendorBo.updateMeal(meal, imageFile);
		if (!RESPONSE_OK.equals(resultMsg)) {
			manager.setResult(resultMsg);
			return new RedirectView("editMeal.htm?vendorEmail=" + meal.getId());
		}
		return redirectToMealsScreen();
	}

	@RequestMapping(value = "/getMealOrders.htm", method = RequestMethod.GET)
	public RedirectView getMealOrders(long mealId, MealType type, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			return new RedirectView("adminLogin.htm");
		}
		Meal meal = new Meal();
		meal.setId(mealId);
		CustomerOrder currentOrder = new CustomerOrder();
		currentOrder.setMeal(meal);
		currentOrder.setMealType(type);
		manager.getAdmin().setCurrentVendor(null);
		manager.getAdmin().setCurrentOrder(currentOrder);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return new RedirectView("orders.htm");
	}

	@RequestMapping(value = "/allOrders.htm", method = RequestMethod.GET)
	public RedirectView allOrders(ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			return new RedirectView("adminLogin.htm");
		}
		manager.getAdmin().setCurrentVendor(null);
		manager.getAdmin().setCurrentOrder(null);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return new RedirectView("orders.htm?status=" + OrderStatus.ORDERED);
	}
	
	@RequestMapping(value = "/vendorOrders.htm", method = RequestMethod.GET)
	public RedirectView vendorOrders(long vendorId, String dateRange,ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			return new RedirectView("adminLogin.htm");
		}
		Vendor vendor = new Vendor();
		vendor.setId(vendorId);
		manager.getAdmin().setCurrentVendor(vendor);
		manager.getAdmin().setCurrentOrder(null);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return new RedirectView("orders.htm?dateRange=" + dateRange);
	}

	@RequestMapping(value = "/orders.htm", method = RequestMethod.GET)
	public String getOrders(ModelMap model, OrderStatus status, String dateRange) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		CustomerOrder currentOrder = manager.getAdmin().getCurrentOrder();
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		if (currentOrder != null) {
			orders = vendorBo.getAllOrdersForMeal(currentOrder.getMeal(), currentOrder.getMealType(), dateRange);
		} else if (manager.getAdmin().getCurrentVendor() != null) {
			orders = vendorBo.getAllVendorOrders(manager.getAdmin().getCurrentVendor(), dateRange);
		} else {
			orders = vendorBo.getAllOrders(dateRange);
		}
		model.addAttribute(MODEL_ORDERS, orders);
		model.addAttribute(MODEL_ORDER_STATUSES, OrderStatus.values());
		model.addAttribute(MODEL_ORDER_STATUS, status);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_view_orders";
	}

	@RequestMapping(value = "/addDailyContent.htm", method = RequestMethod.GET)
	public String initAddDailyContent(long mealId, MealType mealType, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		model.addAttribute(MEAL_ID, mealId);
		// model.addAttribute(MODEL_MEAL_TYPE, CommonUtil.getMealTypes());
		CustomerOrder order = new CustomerOrder();
		Meal meal = new Meal();
		meal.setId(mealId);
		order.setMeal(meal);
		DailyContent dailyContent = new DailyContent();
		dailyContent.setMeal(meal);
		dailyContent.setMealType(mealType);
		Map<MealType, Date> availableMealTypeDates = customerBo.getAvailableMealTypeDates(order);
		if(availableMealTypeDates != null) {
			dailyContent.setDate(availableMealTypeDates.get(mealType));
		}
		model.addAttribute(MODEL_DAILY_CONTENT, dailyContent);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_add_daily_content";
	}

	@RequestMapping(value = "/addDailyContent", method = RequestMethod.POST)
	public RedirectView addDailyContent(DailyContent dailyMeal, long mealId, ModelMap model) {
		Meal meal = new Meal();
		meal.setId(mealId);
		CustomerOrder order = new CustomerOrder();
		order.setMeal(meal);
		dailyMeal.setMeal(meal);
		dailyMeal.setDate(customerBo.getAvailableMealTypeDates(order).get(dailyMeal.getMealType()));
		String resultMsg = vendorBo.addDailyContent(dailyMeal);
		if (!RESPONSE_OK.equals(resultMsg)) {
			manager.setResult(resultMsg);
			return new RedirectView("addDailyContent.htm?mealId=" + mealId);
		}
		return redirectToMealsScreen();
	}
	
	@RequestMapping(value = "/getLastDailyContent.htm", method = RequestMethod.GET)
	public RedirectView getLastDailyContent(MealType mealType, long mealId, ModelMap model) {
		Meal meal = new Meal();
		meal.setId(mealId);
		DailyContent dailyMeal = vendorBo.getLastDailyContent(meal,mealType);
		if(dailyMeal == null) {
			return redirectToMealsScreen();
		}
		CustomerOrder order = new CustomerOrder();
		order.setMeal(meal);
		dailyMeal.setMeal(meal);
		dailyMeal.setDate(customerBo.getAvailableMealTypeDates(order).get(dailyMeal.getMealType()));
		String resultMsg = vendorBo.addDailyContent(dailyMeal);
		/*if (!RESPONSE_OK.equals(resultMsg)) {
			manager.setResult(resultMsg);
			return new RedirectView("addDailyContent.htm?mealId=" + mealId);
		}*/
		return redirectToMealsScreen();
	}

	private RedirectView redirectToMealsScreen() {
		if (manager.getAdmin().getCurrentVendor() == null) {
			return new RedirectView("meals.htm");
		}
		return new RedirectView("vendorDetails.htm");
	}

	@RequestMapping(value = "/updateDailyContent.htm", method = RequestMethod.GET)
	public String initUpdateDailyContent(long contentId, MealType type, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		DailyContent content = new DailyContent();
		content.setId(contentId);
		model.addAttribute(MODEL_MEAL_TYPE, type);
		model.addAttribute(MODEL_DAILY_CONTENT, vendorBo.getDailyContent(content));
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_update_daily_content";
	}

	@RequestMapping(value = "/updateDailyContent", method = RequestMethod.POST)
	public RedirectView updateDailyContent(DailyContent dailyMeal, ModelMap model) {
		String resultMsg = vendorBo.updateDailyContent(dailyMeal);
		if (!RESPONSE_OK.equals(resultMsg)) {
			manager.setResult(resultMsg);
			return new RedirectView("updateDailyContent.htm?mealId=" + dailyMeal.getId());
		}
		return redirectToMealsScreen();
	}

	@RequestMapping(value = "/startCooking", method = RequestMethod.GET)
	public RedirectView startCooking(long mealId, MealType mealType, ModelMap model) {
		Meal meal = new Meal();
		meal.setId(mealId);
		vendorBo.startCooking(meal, mealType);
		return redirectToMealsScreen();
	}
	
	@RequestMapping(value = "/startCookingAll", method = RequestMethod.POST)
	public RedirectView startCookingAll(MealType mealType, ModelMap model) {
		vendorBo.startCookingAll(mealType);
		return redirectToMealsScreen();
	}

	@RequestMapping(value = "/startDispatch", method = RequestMethod.GET)
	public RedirectView startDispatch(long mealId, MealType mealType, ModelMap model) {
		Meal meal = new Meal();
		meal.setId(mealId);
		vendorBo.startDispatch(meal, mealType);
		return redirectToMealsScreen();
	}
	
	@RequestMapping(value = "/startDispatchAll", method = RequestMethod.POST)
	public RedirectView startDispatchAll(MealType mealType, ModelMap model) {
		vendorBo.startDispatchAll(mealType);
		return redirectToMealsScreen();
	}

	@RequestMapping(value = "/delilverTiffins", method = RequestMethod.POST)
	public RedirectView delieverTiffins(long[] orders, ModelMap model) {
		if (orders != null && orders.length > 0) {
			List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
			int i;
			for (i = 0; i < orders.length; i++) {
				CustomerOrder order = new CustomerOrder();
				order.setId(orders[i]);
				customerOrders.add(order);
			}
			vendorBo.delieverTiffins(customerOrders);
		}
		return new RedirectView("orders.htm");
	}

	@RequestMapping(value = "/cancelCustomerOrder", method = RequestMethod.POST)
	public @ResponseBody String cancelOrder(long order, ModelMap model) {
		CustomerOrder orderToCancel = new CustomerOrder();
		orderToCancel.setId(order);
		return vendorBo.cancelOrder(orderToCancel);
	}

	@RequestMapping(value = "/billing.htm", method = RequestMethod.GET)
	public String vendorBilling(String dateRange, ModelMap model) {
		if (StringUtils.isEmpty(manager.getAdmin().getUsername())) {
			prepareAdminLogin(model);
			return "admin_login";
		}
		List<VendorInvoice> vendorInvoices = vendorBo.generateInvoices(dateRange);
		model.addAttribute(MODEL_INVOICES, vendorInvoices);
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return "admin_billing";
	}

	@RequestMapping(value = "/getVendorProfilePic.htm", method = RequestMethod.GET)
	public void getVendorImage(String vendorEmail, HttpServletResponse response, ModelMap model) {
		try {
			Vendor vendor = new Vendor();
			vendor.setEmail(vendorEmail);
			InputStream documentStream = vendorBo.getVendorProfilePic(vendor);
			if (documentStream != null) {
				IOUtils.copy(documentStream, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/getMealImage.htm", method = RequestMethod.GET)
	public void getMealImage(long mealId, HttpServletResponse response, ModelMap model) {
		try {
			Meal meal = new Meal();
			meal.setId(mealId);
			InputStream documentStream = vendorBo.getMealImage(meal);
			if (documentStream != null) {
				IOUtils.copy(documentStream, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST)
	public ModelAndView downloadExcel(ModelMap model, OrderStatus status) {

		ModelAndView modelAndView = new ModelAndView(EXCEL_VIEW, MODEL_ORDERS, vendorBo.getAllOrders(null));
		modelAndView.addObject(MODEL_ORDER_STATUS, status);
		return modelAndView;
	}

}
