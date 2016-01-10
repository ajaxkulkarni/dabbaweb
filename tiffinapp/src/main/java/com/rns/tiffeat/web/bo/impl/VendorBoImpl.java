package com.rns.tiffeat.web.bo.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rns.tiffeat.web.bo.api.VendorBo;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.MealStatus;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.OrderStatus;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.dao.api.CustomerMealDao;
import com.rns.tiffeat.web.dao.api.DailyMealDao;
import com.rns.tiffeat.web.dao.api.MealDao;
import com.rns.tiffeat.web.dao.api.OrderDao;
import com.rns.tiffeat.web.dao.api.VendorDao;
import com.rns.tiffeat.web.dao.domain.Customer;
import com.rns.tiffeat.web.dao.domain.CustomerMeal;
import com.rns.tiffeat.web.dao.domain.DailyMeal;
import com.rns.tiffeat.web.dao.domain.Order;
import com.rns.tiffeat.web.util.BusinessToDataConverters;
import com.rns.tiffeat.web.util.CommonUtil;
import com.rns.tiffeat.web.util.Constants;
import com.rns.tiffeat.web.util.DataToBusinessConverters;
import com.rns.tiffeat.web.util.ImageUtil;

public class VendorBoImpl implements VendorBo, Constants {

	private VendorDao vendorDao;
	private MealDao mealDao;
	private DailyMealDao dailyMealDao;
	private OrderDao orderDao;
	private Vendor currentVendor;
	private CustomerMealDao customerMealDao;

	public void setCustomerMealDao(CustomerMealDao customerMealDao) {
		this.customerMealDao = customerMealDao;
	}

	public CustomerMealDao getCustomerMealDao() {
		return customerMealDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public Vendor getCurrentVendor() {
		return currentVendor;
	}

	public void setCurrentVendor(Vendor currentVendor) {
		this.currentVendor = currentVendor;
	}

	public VendorDao getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public MealDao getMealDao() {
		return mealDao;
	}

	public void setMealDao(MealDao mealDao) {
		this.mealDao = mealDao;
	}

	public DailyMealDao getDailyMealDao() {
		return dailyMealDao;
	}

	public void setDailyMealDao(DailyMealDao dailyMealDao) {
		this.dailyMealDao = dailyMealDao;
	}

	public String addDailyContent(DailyContent dailyMeal) {
		DailyMeal dailyMealToBeAdded = new DailyMeal();
		String result = prepareDailyMeal(dailyMealToBeAdded, dailyMeal);
		if (RESPONSE_OK.equals(result)) {
			dailyMealDao.addDailyMeal(dailyMealToBeAdded);
		}
		return result;
	}

	public String updateDailyContent(DailyContent dailyContent) {
		DailyMeal dailyMeal = dailyMealDao.getDailyMeal(dailyContent.getId());
		if (dailyMeal == null) {
			return ERROR_DAILYCONTENT_DOES_NOT_EXIST;
		}
		String result = updateDailyMeal(dailyMeal, dailyContent);
		if (RESPONSE_OK.equals(result)) {
			dailyMealDao.updateDailyMeal(dailyMeal);
		}
		return result;
	}

	private String updateDailyMeal(DailyMeal dailyMealToBeAdded, DailyContent dailyContent) {
		if (dailyContent == null) {
			return ERROR_DAILYCONTENT_DOES_NOT_EXIST;
		}
		BusinessToDataConverters.convertDailyMeal(dailyMealToBeAdded, dailyContent);
		return RESPONSE_OK;
	}

	private String prepareDailyMeal(DailyMeal dailyMealToBeAdded, DailyContent dailyContent) {
		if (dailyContent == null) {
			return ERROR_DAILYCONTENT_DOES_NOT_EXIST;
		}
		if (dailyContent.getMeal() == null || dailyContent.getMealType() == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		BusinessToDataConverters.convertDailyMeal(dailyMealToBeAdded, dailyContent);
		com.rns.tiffeat.web.dao.domain.Meal meal = mealDao.getMeal(dailyContent.getMeal().getId());
		if (meal == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		List<CustomerMeal> meals = customerMealDao.getScheduledMealsForMeal(meal.getId(), dailyContent.getMealType());
		for (CustomerMeal customerMeal : meals) {
			List<Order> orders = new ArrayList<Order>();
			Order order = prepareOrder(customerMeal, dailyContent.getDate());
			if (order != null) {
				orders.add(order);
			}
			customerMeal.setOrders(orders);
		}
		if (CollectionUtils.isNotEmpty(meals)) {
			mealDao.addCustomerMeals(meals, meal);
		}
		// String result =
		// generateCurrentOrder(meal,dailyContent.getMealType());
		/*
		 * if(!RESPONSE_OK.equals(result)) { return result; }
		 */
		CommonUtil.setMealStatus(meal, dailyContent.getMealType(), MealStatus.PREPARE);
		dailyMealToBeAdded.setMeal(meal);
		return RESPONSE_OK;
	}

	public List<DailyMeal> getDailyMealsByDate(Vendor vendor, Date date) {
		if (vendor == null) {
			return new ArrayList<DailyMeal>();
		}
		return dailyMealDao.getDailyMealsForVendor(vendor.getId(), date);
	}

	/*public String generateCurrentOrder(com.rns.tiffeat.web.dao.domain.Meal meal, MealType mealType) {
		if (meal == null || mealType == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		List<CustomerMeal> scheduledMeals = customerMealDao.getScheduledMealsForMeal(meal.getId(), mealType);
		if (CollectionUtils.isEmpty(scheduledMeals)) {
			return RESPONSE_OK;
		}
		List<Order> orders = new ArrayList<Order>();
		for (CustomerMeal scheduledMeal : scheduledMeals) {
			if (!checkCustomerBalance(scheduledMeal)) {
				continue;
			}
			if (MealType.BOTH.equals(scheduledMeal.getMealType())
					|| mealType.name().equals(scheduledMeal.getMealType())) {
				orders.add(prepareOrder(scheduledMeal));
			}
		}
		if (CollectionUtils.isEmpty(orders)) {
			return RESPONSE_OK;
		}
		orderDao.addOrders(orders);
		return RESPONSE_OK;
	}*/

	private boolean checkCustomerBalance(CustomerMeal meal) {
		Customer customer = meal.getCustomer();
		if (customer == null || customer.getBalance() == null || meal.getMeal() == null
				|| meal.getMeal().getPrice() == null) {
			return false;
		}
		if (customer.getBalance().compareTo(meal.getMeal().getPrice()) < 0) {
			meal.setStatus(OrderStatus.PAYABLE.name());
			return false;
		}
		return true;
	}

	private Order prepareOrder(CustomerMeal mealToBeAdded, Date date) {
		if (mealToBeAdded == null || mealToBeAdded.getCustomer() == null) {
			return null;
		}
		Order existingOrder = orderDao.getCustomerScheduledOrder(mealToBeAdded.getCustomer().getId(), date,mealToBeAdded.getMealType());
		if (existingOrder != null) {
			return null;
		}
		Order order = new Order();
		order.setCustomerMeal(mealToBeAdded);
		order.setStatus(OrderStatus.ORDERED.name());
		if (!checkCustomerBalance(mealToBeAdded)) {
			order.setStatus(OrderStatus.PAYABLE.name());
		}
		order.setDate(mealToBeAdded.getDate());
		if (MealFormat.SCHEDULED.name().equals(mealToBeAdded.getFormat())) {
			order.setDate(date);
		}
		return order;
	}

	public boolean loginWithEmail(Vendor vendor) {
		if (vendor == null || StringUtils.isEmpty(vendor.getEmail())) {
			return false;
		}
		com.rns.tiffeat.web.dao.domain.Vendor registeredVendor = vendorDao.getVendorByEmail(vendor.getEmail());
		if (registeredVendor == null || !StringUtils.equals(vendor.getPassword(), registeredVendor.getPassword())) {
			return false;
		}
		setCurrentVendor(registeredVendor);
		return true;
	}

	private void setCurrentVendor(com.rns.tiffeat.web.dao.domain.Vendor registeredVendor) {
		currentVendor = new Vendor();
		DataToBusinessConverters.convertVendor(currentVendor, registeredVendor);
		currentVendor.setMeals(vendorDao.getVendorMeals(registeredVendor));
	}

	public void setDailyContents(List<Meal> meals) {
		if (CollectionUtils.isEmpty(meals)) {
			return;
		}
		for (Meal meal : meals) {
			meal.setLunchMenu(getDailyContentForType(meal, MealType.LUNCH));
			meal.setDinnerMenu(getDailyContentForType(meal, MealType.DINNER));
		}
	}

	private DailyContent getDailyContentForType(Meal meal, MealType mealType) {
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(meal.getId(), mealType);
		if (dailyMeal == null) {
			return null;
		}
		if(MealStatus.DISPATCH.equals(CommonUtil.getMealStatus(mealType, meal)) && CommonUtil.checkIfToday(dailyMeal.getDate())) {
			return null;
		}
		return DataToBusinessConverters.convertDailyContent(dailyMeal);
	}

	/*
	 * private DailyContent getDailyContent(DailyMeal dailyMeal) { DailyContent
	 * content = new DailyContent(); content.setId(dailyMeal.getId());
	 * content.setMainItem(dailyMeal.getMainCourse());
	 * content.setSubItem1(dailyMeal.getSubItem1());
	 * content.setSubItem2(dailyMeal.getSubItem2());
	 * content.setSubItem3(dailyMeal.getSubItem3());
	 * content.setSubItem4(dailyMeal.getSubItem4());
	 * content.setMealType(CommonUtil.getMealType(dailyMeal.getType()));
	 * content.setMeal(getMealDetails(dailyMeal.getMeal())); return content; }
	 */

	public String register(Vendor vendor) {
		if (vendor == null) {
			return ERROR_INVALID_USER_DETAILS;
		}
		com.rns.tiffeat.web.dao.domain.Vendor vendorToBeRegistered = new com.rns.tiffeat.web.dao.domain.Vendor();
		if (!isEmailAvailable(vendor.getEmail())) {
			return ERROR_EMAIL_ADDRESS_ALREADY_PRESENT;
		}
		if (!isPhoneAvailable(vendor.getPhone())) {
			return ERROR_PHONE_NUMBER_ALREADY_PRESENT;
		}
		BusinessToDataConverters.convertVendor(vendorToBeRegistered, vendor);
		vendorDao.addVendor(vendorToBeRegistered);
		return RESPONSE_OK;
	}

	private boolean isPhoneAvailable(String phone) {
		if (vendorDao.getVendorByPhone(phone) == null) {
			return true;
		}
		return false;
	}

	private boolean isEmailAvailable(String email) {
		if (vendorDao.getVendorByEmail(email) == null) {
			return true;
		}
		return false;
	}

	public String addMeal(Meal meal, MultipartFile imageFile) {
		if (meal == null || meal.getVendor() == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		com.rns.tiffeat.web.dao.domain.Meal mealToBeAdded = new com.rns.tiffeat.web.dao.domain.Meal();
		BusinessToDataConverters.convertMeal(mealToBeAdded, meal);
		com.rns.tiffeat.web.dao.domain.Vendor vendor = vendorDao.getVendorByEmail(meal.getVendor().getEmail());
		if (vendor == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		mealToBeAdded.setVendor(vendor);
		try {
			ImageUtil.storeMealImage(mealToBeAdded, imageFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return ERROR_UPLOADING_IMAGE;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_UPLOADING_IMAGE;
		}
		mealDao.addMeal(mealToBeAdded);
		return RESPONSE_OK;
	}

	public String updateMeal(Meal meal, MultipartFile file) {
		if (meal == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		com.rns.tiffeat.web.dao.domain.Meal mealToBeUpdated = mealDao.getMeal(meal.getId());
		if (mealToBeUpdated == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		try {
			if (!file.isEmpty()) {
				ImageUtil.storeMealImage(mealToBeUpdated, file);
			}
		} catch (Exception e) {

		}
		BusinessToDataConverters.convertMeal(mealToBeUpdated, meal);
		mealDao.editMeal(mealToBeUpdated);
		return RESPONSE_OK;
	}

	public void deleteMeal(Meal meal) {
		com.rns.tiffeat.web.dao.domain.Meal mealToBeDeleted = mealDao.getMeal(meal.getId());
		mealDao.deleteMeal(mealToBeDeleted);
	}

	public void startCooking(Meal meal, MealType mealType) {
		if (meal == null || mealType == null) {
			return;
		}
		com.rns.tiffeat.web.dao.domain.Meal mealToCook = mealDao.getMeal(meal.getId());
		CommonUtil.setMealStatus(mealToCook, mealType, MealStatus.COOKING);
		List<Order> orders = orderDao.getMealOrders(mealToCook.getId(), mealType.name());
		updateCustomerBalances(orders);
		mealDao.editMeal(mealToCook, orders);
	}

	private void updateCustomerBalances(List<Order> orders) {
		for (Order order : orders) {
			if (!OrderStatus.ORDERED.name().equals(order.getStatus())) {
				continue;
			}
			CustomerMeal customerMeal = order.getCustomerMeal();
			Customer customer = customerMeal.getCustomer();
			com.rns.tiffeat.web.dao.domain.Meal meal = customerMeal.getMeal();
			if (customerMeal == null || customer == null || meal == null) {
				continue;
			}
			if (!MealFormat.SCHEDULED.name().equals(customerMeal.getFormat())) {
				continue;
			}
			if (customer.getBalance() == null || customer.getBalance().compareTo(meal.getPrice()) < 0) {
				order.setStatus(OrderStatus.PAYABLE.name());
				continue;
			}
			customer.setBalance(customer.getBalance().subtract(meal.getPrice()));
		}
	}

	public String startDispatch(Meal meal, MealType mealType) {
		if (meal == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}

		com.rns.tiffeat.web.dao.domain.Meal mealToDispatch = mealDao.getMeal(meal.getId());
		CommonUtil.setMealStatus(mealToDispatch, mealType, MealStatus.DISPATCH);
		mealDao.editMeal(mealToDispatch);
		return RESPONSE_OK;
	}

	public void delieverTiffins(List<CustomerOrder> orders) {
		if (CollectionUtils.isEmpty(orders)) {
			return;
		}
		List<Order> delieveries = new ArrayList<Order>();
		for (CustomerOrder order : orders) {
			Order currentOrder = orderDao.getOrder(order.getId());
			if (currentOrder == null || currentOrder.getCustomerMeal() == null
					|| currentOrder.getCustomerMeal().getMeal() == null
					|| currentOrder.getCustomerMeal().getCustomer() == null) {
				continue;
			}
			CustomerMeal customerMeal = currentOrder.getCustomerMeal();
			// Customer customer = customerMeal.getCustomer();
			// com.rns.tiffeat.web.dao.domain.Meal meal =
			// customerMeal.getMeal();
			// customerMeal.setCustomer(customer);
			customerMeal.setStatus(MealStatus.DELIVERED.name());
			currentOrder.setCustomerMeal(customerMeal);
			currentOrder.setStatus(OrderStatus.DELIVERED.name());
			delieveries.add(currentOrder);
		}
		orderDao.updateOrders(delieveries);
	}

	public List<CustomerOrder> getAllVendorOrders(com.rns.tiffeat.web.bo.domain.Vendor vendor, String dateRange) {
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		if (vendor == null) {
			return customerOrders;
		}
		/*List<DailyMeal> dailyMeals = dailyMealDao.getDailyMealsForVendor(vendor.getId(), new Date());
		if (CollectionUtils.isEmpty(dailyMeals)) {
			return customerOrders;
		}*/
		List<Date> dates = extractDates(dateRange);
		List<Order> orders = orderDao.getVendorOrdersInBetween(vendor.getId(), dates.get(0), dates.get(1));
		if (CollectionUtils.isEmpty(orders)) {
			return customerOrders;
		}
		for (Order order : orders) {
			customerOrders.add(DataToBusinessConverters.convertOrder(order));
		}
		return customerOrders;
	}

	/*
	 * private CustomerOrder prepareCustomerOrder(Order order) {
	 * if(order.getCustomerMeal() == null) { return null; } CustomerOrder
	 * customerOrder = new CustomerOrder();
	 * customerOrder.setAddress(order.getCustomerMeal().getAddress());
	 * customerOrder.setArea(order.getCustomerMeal().getPinCode());
	 * customerOrder.setId(order.getId());
	 * customerOrder.setMeal(getMealDetails(order.getCustomerMeal().getMeal()));
	 * customerOrder
	 * .setCustomer(getCustomerDetails(order.getCustomerMeal().getCustomer()));
	 * customerOrder.setDate(order.getDate());
	 * customerOrder.setPaymentType(CommonUtil
	 * .getPaymentType(order.getPaymentType()));
	 * customerOrder.setStatus(CommonUtil.getOrderStatus(order.getStatus()));
	 * customerOrder
	 * .setMealFormat(CommonUtil.getMealFormat(order.getCustomerMeal
	 * ().getFormat()));
	 * customerOrder.setMealType(CommonUtil.getMealType(order.getCustomerMeal
	 * ().getMealType())); return customerOrder; }
	 */

	/*
	 * private com.rns.tiffeat.web.bo.domain.Customer
	 * getCustomerDetails(Customer customer) { if (customer == null) { return
	 * null; } com.rns.tiffeat.web.bo.domain.Customer customerToBeAdded = new
	 * com.rns.tiffeat.web.bo.domain.Customer();
	 * customerToBeAdded.setEmail(customer.getEmail());
	 * customerToBeAdded.setPassword(customer.getPassword());
	 * customerToBeAdded.setPhone(customer.getPhone());
	 * customerToBeAdded.setDeviceId(customer.getDeviceId());
	 * customerToBeAdded.setRegId(customer.getRegId());
	 * customerToBeAdded.setName(customer.getName()); return customerToBeAdded;
	 * }
	 */

	/*
	 * private com.rns.tiffeat.web.bo.domain.Meal
	 * getMealDetails(com.rns.tiffeat.web.dao.domain.Meal meal) { if(meal ==
	 * null) { return null; } com.rns.tiffeat.web.bo.domain.Meal mealToBeAdded =
	 * new com.rns.tiffeat.web.bo.domain.Meal();
	 * mealToBeAdded.setId(meal.getId());
	 * mealToBeAdded.setPrice(meal.getPrice());
	 * mealToBeAdded.setTitle(meal.getTitle());
	 * mealToBeAdded.setStatus(CommonUtil.getMealPhase(meal.getStatus()));
	 * mealToBeAdded.setVendor(currentVendor); return mealToBeAdded; }
	 */

	public List<Vendor> getAllVendors() {
		List<com.rns.tiffeat.web.dao.domain.Vendor> allVendors = vendorDao.getAllVendors();
		List<Vendor> allRegisteredVendors = new ArrayList<Vendor>();
		for (com.rns.tiffeat.web.dao.domain.Vendor registeredVendor : allVendors) {
			setCurrentVendor(registeredVendor);
			allRegisteredVendors.add(currentVendor);
		}

		return allRegisteredVendors;
	}

	public String updateVendorProfile(Vendor vendor, MultipartFile image) {
		if (vendor == null || vendor.getEmail() == null) {
			return ERROR_INVALID_VENDOR_DETAILS;
		}
		com.rns.tiffeat.web.dao.domain.Vendor registVendor = vendorDao.getVendorByEmail(vendor.getEmail());
		try {
			if (!image.isEmpty()) {
				ImageUtil.storeVendorProfilePic(registVendor, image);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_UPLOADING_IMAGE;
		}
		BusinessToDataConverters.convertVendor(registVendor, vendor);
		vendorDao.updateVendor(registVendor);
		return RESPONSE_OK;
	}

	public InputStream getVendorProfilePic(Vendor vendor) throws FileNotFoundException {
		if (vendor == null) {
			return null;
		}
		com.rns.tiffeat.web.dao.domain.Vendor registeredVendor = vendorDao.getVendorByEmail(vendor.getEmail());
		if (registeredVendor == null || StringUtils.isEmpty(registeredVendor.getImage())) {
			return null;
		}
		return new FileInputStream(registeredVendor.getImage());
	}

	public InputStream getMealImage(Meal meal) throws FileNotFoundException {
		if (meal == null) {
			return null;
		}
		com.rns.tiffeat.web.dao.domain.Meal mealWithImage = mealDao.getMeal(meal.getId());
		if (mealWithImage == null || mealWithImage.getImage() == null) {
			return null;
		}
		return new FileInputStream(mealWithImage.getImage());
	}

	public DailyContent getDailyContent(DailyContent content) {
		if (content == null) {
			return null;
		}
		DailyMeal meal = dailyMealDao.getDailyMeal(content.getId());
		return DataToBusinessConverters.convertDailyContent(meal);
	}

	public List<CustomerOrder> getAllOrdersForMeal(Meal meal, MealType type, String dateRange) {
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		if (meal == null || type == null) {
			return customerOrders;
		}
		List<Date> dates = extractDates(dateRange);
		List<Order> orders = orderDao.getMealOrdersInBetween(meal.getId(), type.name(),dates.get(0), dates.get(1));
		for (Order order : orders) {
			customerOrders.add(DataToBusinessConverters.convertOrder(order));
		}
		return customerOrders;
	}

	public List<CustomerOrder> getAllOrders(String dateRange) {
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		List<Order> orders = new ArrayList<Order>();
		List<Date> dates = extractDates(dateRange);
		orders = orderDao.getOrdersBetween(dates.get(0), dates.get(1));
		if (CollectionUtils.isEmpty(orders)) {
			return customerOrders;
		}
		for (Order order : orders) {
			customerOrders.add(DataToBusinessConverters.convertOrder(order));
		}
		return customerOrders;
	}

	private List<Date> extractDates(String dateRange) {
		//List<Order> orders = null;
		List<Date> dateList = new ArrayList<Date>();
		String[] dates = StringUtils.split(dateRange, "to");
		Date fromDate = null;
		Date toDate = null;
		if(dates != null && dates.length > 0) {
			fromDate = CommonUtil.convertDate(dates[0]);
			if(dates.length > 1) {
				toDate = CommonUtil.convertDate(dates[1]);
			}
		}
		if(fromDate == null || toDate == null) {
			dateList.add(new Date());
			dateList.add(new Date());
		}
		else {
			dateList.add(fromDate);
			dateList.add(toDate);
		}
		return dateList;
	}

	public String cancelOrder(CustomerOrder order) {
		if (order == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		Order orderToCancel = orderDao.getOrder(order.getId());
		if (orderToCancel == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		orderToCancel.setStatus(OrderStatus.CANCELLED.name());
		orderDao.editOrder(orderToCancel);
		return RESPONSE_OK;
	}

	public Meal getMeal(Meal meal) {
		if (meal == null) {
			return null;
		}
		return DataToBusinessConverters.convertMeal(mealDao.getMeal(meal.getId()));
	}

	public List<Meal> getAllMeals() {
		List<com.rns.tiffeat.web.dao.domain.Meal> meals = mealDao.getAllMeals();
		if(CollectionUtils.isEmpty(meals)) {
			return null;
		}
		List<Meal> currentMeals = new ArrayList<Meal>();
		for(com.rns.tiffeat.web.dao.domain.Meal meal:meals) {
			currentMeals.add(DataToBusinessConverters.convertMeal(meal));
		}
		setDailyContents(currentMeals);
		return currentMeals;
	}

}
