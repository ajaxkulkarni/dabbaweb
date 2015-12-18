package com.rns.tiffeat.web.bo.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.gson.JsonSyntaxException;
import com.rns.tiffeat.web.bo.api.CustomerBo;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.MealStatus;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.OrderStatus;
import com.rns.tiffeat.web.bo.domain.PayUDetails;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.bo.domain.VendorStatus;
import com.rns.tiffeat.web.dao.api.CustomerDao;
import com.rns.tiffeat.web.dao.api.CustomerMealDao;
import com.rns.tiffeat.web.dao.api.DailyMealDao;
import com.rns.tiffeat.web.dao.api.MealDao;
import com.rns.tiffeat.web.dao.api.OrderDao;
import com.rns.tiffeat.web.dao.api.TransactionDao;
import com.rns.tiffeat.web.dao.api.VendorDao;
import com.rns.tiffeat.web.dao.domain.CustomerMeal;
import com.rns.tiffeat.web.dao.domain.DailyMeal;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.dao.domain.Order;
import com.rns.tiffeat.web.dao.domain.Transaction;
import com.rns.tiffeat.web.google.GoogleUtil;
import com.rns.tiffeat.web.util.BusinessToDataConverters;
import com.rns.tiffeat.web.util.CommonUtil;
import com.rns.tiffeat.web.util.Constants;
import com.rns.tiffeat.web.util.DataToBusinessConverters;
import com.rns.tiffeat.web.util.MailUtil;

public class CustomerBoImpl implements CustomerBo, Constants {

	private VendorDao vendorDao;
	private CustomerDao customerDao;
	private DailyMealDao dailyMealDao;
	private OrderDao orderDao;
	private CustomerMealDao customerMealDao;
	private MealDao mealDao;
	private TransactionDao transactionDao;

	public void setTransactionDao(TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
	}

	public TransactionDao getTransactionDao() {
		return transactionDao;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		if (currentCustomer == null) {
			return;
		}
		com.rns.tiffeat.web.dao.domain.Customer registeredCustomer = customerDao.getCustomer(currentCustomer.getId());
		if (registeredCustomer == null) {
			return;
		}
		prepareHomePageContent(registeredCustomer, currentCustomer);
	}

	public MealDao getMealDao() {
		return mealDao;
	}

	public void setMealDao(MealDao mealDao) {
		this.mealDao = mealDao;
	}

	public CustomerMealDao getCustomerMealDao() {
		return customerMealDao;
	}

	public void setCustomerMealDao(CustomerMealDao customerMealDao) {
		this.customerMealDao = customerMealDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public DailyMealDao getDailyMealDao() {
		return dailyMealDao;
	}

	public void setDailyMealDao(DailyMealDao dailyMealDao) {
		this.dailyMealDao = dailyMealDao;
	}

	public VendorDao getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(VendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public void submitFeedback(Meal meal) {

	}

	/*
	 * public List<Vendor> getAvailableVendors(BigDecimal locationX, BigDecimal
	 * locationY) { List<Vendor> vendors = vendorDao.getAllVendors();
	 * List<Vendor> nearestVendors = new ArrayList<Vendor>(); double minDistance
	 * = 10; for(Vendor vendor:vendors) { double calculatedDistance =
	 * calculateDistance(vendor, locationX, locationY); if(calculatedDistance <
	 * 0) { continue; } if(minDistance > calculatedDistance) {
	 * nearestVendors.add(vendor); } } return nearestVendors; }
	 */

	/*
	 * private double calculateDistance(Vendor vendor, BigDecimal locationX,
	 * BigDecimal locationY) { if(vendor.getLocationX()==null ||
	 * vendor.getLocationY()==null || locationX == null || locationY == null) {
	 * return -1; } BigDecimal xDistance =
	 * (vendor.getLocationX().subtract(locationX)).abs(); xDistance =
	 * xDistance.multiply(xDistance); BigDecimal yDistance =
	 * (vendor.getLocationX().subtract(locationX)).abs(); yDistance =
	 * yDistance.multiply(yDistance); BigDecimal addition =
	 * xDistance.add(yDistance); return Math.sqrt(addition.doubleValue()); }
	 */

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public String register(Customer customer) {
		com.rns.tiffeat.web.dao.domain.Customer customerToBeAdded = new com.rns.tiffeat.web.dao.domain.Customer();
		String result = prepareCustomer(customerToBeAdded, customer);
		if (RESPONSE_OK.equals(result)) {
			customerDao.addCustomer(customerToBeAdded);
		}
		customer.setId(customerToBeAdded.getId());
		return result;
	}

	private String prepareCustomer(com.rns.tiffeat.web.dao.domain.Customer customerToBeAdded, Customer customer) {
		if (customer == null) {
			return ERROR_INVALID_CUSTOMER_DETAILS;
		}
		if (customerDao.getCustomerByEmail(customer.getEmail()) != null) {
			return ERROR_EMAIL_ADDRESS_ALREADY_PRESENT;
		}
		if (customerDao.getCustomerByPhone(customer.getPhone()) != null) {
			return ERROR_PHONE_NUMBER_ALREADY_PRESENT;
		}
		BusinessToDataConverters.convertCustomer(customerToBeAdded, customer);
		return RESPONSE_OK;
	}

	public List<Vendor> getAvailableVendors(String area) {
		List<com.rns.tiffeat.web.dao.domain.Vendor> vendors = vendorDao.getAllVendors();
		List<Vendor> availableVendors = new ArrayList<Vendor>();
		if (CollectionUtils.isEmpty(vendors)) {
			return availableVendors;
		}
		for (com.rns.tiffeat.web.dao.domain.Vendor vendor : vendors) {
			Vendor currentVendor = new Vendor();
			DataToBusinessConverters.convertVendor(currentVendor, vendor);
			if (!isVendorAvailable(currentVendor, area)) {
				continue;
			}
			availableVendors.add(currentVendor);
		}
		return availableVendors;
	}

	private boolean isVendorAvailable(Vendor currentVendor, String address) {
		if (currentVendor.getLocation() == null || StringUtils.isEmpty(currentVendor.getLocation().getAddress())) {
			return false;
		}
		BigDecimal distance = null;
		try {
			distance = GoogleUtil.getDistanceBetweenLocations(address, currentVendor.getLocation().getAddress());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (distance == null || MAX_DISTANCE_IN_METERS.compareTo(distance) < 0) {
			return false;
		}
		if (VendorStatus.NA.equals(currentVendor.getStatus()) || VendorStatus.CLOSED.equals(currentVendor.getStatus())) {
			return false;
		}
		return true;
	}

	public Vendor getVendorWithMeals(Vendor vendor) {
		if (vendor == null) {
			return null;
		}
		com.rns.tiffeat.web.dao.domain.Vendor registeredVendor = vendorDao.getVendorByEmail(vendor.getEmail());
		if (registeredVendor == null) {
			return null;
		}
		DataToBusinessConverters.convertVendor(vendor, registeredVendor);
		vendor.setMeals(vendorDao.getVendorMeals(registeredVendor));
		return vendor;
	}

	/*
	 * public MealType[] getAvailableMealType(CustomerOrder order) { if (order
	 * == null || order.getMeal() == null) { return null; } Meal meal =
	 * mealDao.getMeal(order.getMeal().getId()); if (meal == null) { return
	 * null; }
	 * 
	 * DailyMeal lunchMenu = dailyMealDao.getDailyMealsForMealType(meal.getId(),
	 * MealType.LUNCH); List<MealType> mealTypesList = new
	 * ArrayList<MealType>(); if (checkIfAvailable(meal, lunchMenu)) {
	 * mealTypesList.add(MealType.LUNCH); } DailyMeal dinnerMenu =
	 * dailyMealDao.getDailyMealsForMealType(meal.getId(), MealType.DINNER); if
	 * (checkIfAvailable(meal, dinnerMenu)) {
	 * mealTypesList.add(MealType.DINNER); } return (MealType[])
	 * mealTypesList.toArray(new MealType[mealTypesList.size()]); }
	 */

	private boolean checkIfAvailable(Meal meal, DailyMeal menu) {
		if (menu == null) {
			return true;
		}
		if (!CommonUtil.checkIfToday(menu.getDate())) {
			return false;
		}
		if (MealStatus.PREPARE.equals(CommonUtil.getMealStatus(CommonUtil.getMealType(menu.getType()), meal))) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * private MealType[] getBothMealTypes() { MealType[] mealTypes = new
	 * MealType[2]; mealTypes[0] = MealType.LUNCH; mealTypes[1] =
	 * MealType.DINNER; return mealTypes; }
	 * 
	 * private MealType[] getOneMealType(MealType type) { MealType[] mealTypes =
	 * new MealType[1]; mealTypes[0] = type; return mealTypes; }
	 */

	public String quickOrder(CustomerOrder customerOrder) {
		String validationResult = validateQuickOrder(customerOrder);
		if (!RESPONSE_OK.equals(validationResult)) {
			return validationResult;
		}
		com.rns.tiffeat.web.dao.domain.Customer customer = customerDao.getCustomer(customerOrder.getCustomer().getId());
		CustomerMeal mealToBeAdded = new CustomerMeal();
		mealToBeAdded.setFormat(MealFormat.QUICK.name());
		customerOrder.setMealFormat(MealFormat.QUICK);
		customer.setPhone(customerOrder.getCustomer().getPhone());
		BusinessToDataConverters.convertCustomerMeal(customer, mealToBeAdded, customerOrder);
		addOrder(mealToBeAdded, customerOrder);
		customerOrder.setId(customerMealDao.addCustomerMeal(mealToBeAdded));
		MailUtil.sendMail(customerOrder);
		return RESPONSE_OK;
	}

	public String validateQuickOrder(CustomerOrder customerOrder) {
		if (customerOrder == null || customerOrder.getCustomer() == null || customerOrder.getMeal() == null || customerOrder.getDate() == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		if(StringUtils.isEmpty(customerOrder.getAddress()) || customerOrder.getLocation() == null) {
			return ERROR_INVALID_ADDRESS_OR_LOCATION;
		}
		if (!checkIfMealTypeAvailableForDate(customerOrder)) {
			return WARNING_DATE_CHANGED;
		}
		com.rns.tiffeat.web.dao.domain.Customer customer = customerDao.getCustomer(customerOrder.getCustomer().getId());
		if (customer == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		Meal meal = mealDao.getMeal(customerOrder.getMeal().getId());
		if (meal == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		if (!isMealAvailableForMealType(meal, customerOrder.getMealType())) {
			return ERROR_MEAL_NOT_AVAILABLE_PLEASE_CHECK_AGAIN;
		}
		return RESPONSE_OK;
	}

	private boolean checkIfMealTypeAvailableForDate(CustomerOrder customerOrder) {
		Map<MealType, Date> mealTypeDates = getAvailableMealTypeDates(customerOrder);
		for (MealType type : mealTypeDates.keySet()) {
			if (type.equals(customerOrder.getMealType()) && DateUtils.isSameDay(mealTypeDates.get(type), customerOrder.getDate())) {
				return true;
			}
		}
		return false;
	}

	private void addOrder(CustomerMeal mealToBeAdded, CustomerOrder customerOrder) {
		mealToBeAdded.getOrders().add(BusinessToDataConverters.convertOrder(mealToBeAdded, customerOrder));
	}

	public boolean login(Customer customer) {
		if (customer == null) {
			return false;
		}
		com.rns.tiffeat.web.dao.domain.Customer registeredCustomer = customerDao.getCustomerByEmail(customer.getEmail());
		if (registeredCustomer == null || !StringUtils.equals(registeredCustomer.getPassword(), customer.getPassword())) {
			return false;
		}
		customer.setId(registeredCustomer.getId());
		return true;
	}

	private void prepareHomePageContent(com.rns.tiffeat.web.dao.domain.Customer registeredCustomer, Customer currentCustomer) {
		DataToBusinessConverters.convertCustomer(registeredCustomer, currentCustomer);
		populateCustomerMeals(registeredCustomer, currentCustomer);
	}

	private void populateCustomerMeals(com.rns.tiffeat.web.dao.domain.Customer registeredCustomer, Customer currentCustomer) {
		currentCustomer.setQuickOrders(new ArrayList<CustomerOrder>());
		currentCustomer.setPreviousOrders(new ArrayList<CustomerOrder>());
		currentCustomer.setScheduledOrder(new ArrayList<CustomerOrder>());
		List<CustomerMeal> scheduledMeals = customerMealDao.getScheduledMeals(registeredCustomer.getId());
		setScheduledMeal(scheduledMeals, currentCustomer);
		List<CustomerMeal> meals = customerMealDao.getQuickOrdersByDate(registeredCustomer.getId(), new Date());
		currentCustomer.setQuickOrders(getCustomerOrders(meals, currentCustomer));
		List<CustomerMeal> previousMeals = customerMealDao.getPreviousQuickOrders(registeredCustomer.getId());
		currentCustomer.setPreviousOrders(getPreviousOrders(previousMeals, currentCustomer));
	}

	private List<CustomerOrder> getPreviousOrders(List<CustomerMeal> previousMeals, Customer currentCustomer) {
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		for (CustomerMeal customerMeal : previousMeals) {
			CustomerOrder customerOrder = new CustomerOrder();
			DataToBusinessConverters.convertCustomerOrder(customerMeal, customerOrder, currentCustomer);
			orders.add(customerOrder);
		}
		return orders;
	}

	private void setScheduledMeal(List<CustomerMeal> scheduledMeals, Customer currentCustomer) {
		if (CollectionUtils.isEmpty(scheduledMeals)) {
			return;
		}

		for (CustomerMeal scheduledMeal : scheduledMeals) {
			CustomerOrder customerOrder = new CustomerOrder();
			DataToBusinessConverters.convertCustomerOrder(scheduledMeal, customerOrder, currentCustomer);
			prepareScheduledOrderDetails(currentCustomer, customerOrder);
			currentCustomer.getScheduledOrder().add(customerOrder);
		}
		calculateTiffinsRemaining(currentCustomer);
	}

	private void calculateTiffinsRemaining(Customer currentCustomer) {

		if (currentCustomer.getBalance() == null || currentCustomer.getBalance().compareTo(BigDecimal.ZERO) == 0 || currentCustomer.getBalance().compareTo(BigDecimal.ZERO) == -1) {
			currentCustomer.setNoOfTiffinsRemaining(0);
			return;
		}
		BigDecimal totalDailyCost = BigDecimal.ZERO;
		for (CustomerOrder order : currentCustomer.getScheduledOrder()) {
			if (order.getMeal() == null || order.getMeal().getPrice() == null) {
				continue;
			}
			totalDailyCost = totalDailyCost.add(order.getMeal().getPrice());
		}
		if (totalDailyCost.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal tiffins = (currentCustomer.getBalance()).divideToIntegralValue(totalDailyCost);
			currentCustomer.setNoOfTiffinsRemaining(tiffins.intValue());
			return;
		}
		currentCustomer.setNoOfTiffinsRemaining(0);
	}

	private void prepareScheduledOrderDetails(Customer currentCustomer, CustomerOrder customerOrder) {
		prepareDailyContent(customerOrder);
		Date date = new Date();
		if (customerOrder.getContent() != null) {
			date = customerOrder.getContent().getDate();
		}
		if (customerOrder.getMealType() == null) {
			return;
		}
		Order order = orderDao.getCustomerScheduledOrder(currentCustomer.getId(), date, customerOrder.getMealType().name());
		if (order == null) {
			customerOrder.setStatus(OrderStatus.NA);
			return;
		}
		customerOrder.setStatus(CommonUtil.getOrderStatus(order.getStatus()));
	}

	private void prepareQuickOrderDetails(Customer currentCustomer, CustomerOrder customerOrder) {
		prepareDailyContent(customerOrder);
		Order order = orderDao.getCustomerQuickOrder(customerOrder.getId());
		if (order == null) {
			customerOrder.setStatus(OrderStatus.NA);
			return;
		}
		customerOrder.setStatus(CommonUtil.getOrderStatus(order.getStatus()));
	}

	private List<CustomerOrder> getCustomerOrders(List<CustomerMeal> customerMeals, Customer currentCustomer) {
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		for (CustomerMeal customerMeal : customerMeals) {
			CustomerOrder customerOrder = new CustomerOrder();
			DataToBusinessConverters.convertCustomerOrder(customerMeal, customerOrder, currentCustomer);
			prepareQuickOrderDetails(currentCustomer, customerOrder);
			orders.add(customerOrder);
		}
		return orders;
	}

	private void prepareDailyContent(CustomerOrder customerOrder) {
		if (customerOrder == null || customerOrder.getMeal() == null || customerOrder.getMealFormat() == null || customerOrder.getDate() == null) {
			return;
		}
		if (MealFormat.SCHEDULED.equals(customerOrder.getMealFormat())) {
			DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(customerOrder.getMeal().getId(), CommonUtil.addDay(), customerOrder.getMealType());
			if (dailyMeal == null) {
				dailyMeal = dailyMealDao.getDailyMealsForMealType(customerOrder.getMeal().getId(), new Date(), customerOrder.getMealType());
			}
			if (dailyMeal != null && dailyMeal.getDate() != null && customerOrder.getDate().compareTo(dailyMeal.getDate()) <= 0) {
				customerOrder.setContent(DataToBusinessConverters.convertDailyContent(dailyMeal));
			}
			return;
		}
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(customerOrder.getMeal().getId(), customerOrder.getDate(), customerOrder.getMealType());
		if (dailyMeal == null) {
			return;
		}
		customerOrder.setContent(DataToBusinessConverters.convertDailyContent(dailyMeal));
	}

	public String scheduledOrder(List<CustomerOrder> customerOrders) {
		if (CollectionUtils.isEmpty(customerOrders)) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		if (customerOrders.get(0).getCustomer() == null) {
			return ERROR_INVALID_USER_DETAILS;
		}
		List<CustomerMeal> scheduledMeals = customerMealDao.getScheduledMeals(customerOrders.get(0).getCustomer().getId());
		if (CollectionUtils.isNotEmpty(scheduledMeals) && scheduledMeals.size() > 1) {
			return ERROR_ALERADY_SCHEDULED;
		}
		List<CustomerMeal> customerMealsToBeAdded = new ArrayList<CustomerMeal>();
		for (CustomerOrder order : customerOrders) {
			if (order == null || order.getCustomer() == null || order.getMeal() == null) {
				return ERROR_INVALID_ORDER_DETAILS;
			}
			if(StringUtils.isEmpty(order.getAddress()) || order.getLocation() == null) {
				return ERROR_INVALID_ADDRESS_OR_LOCATION;
			}
			if (isAlreadyScheduled(order, scheduledMeals)) {
				return ERROR_ALERADY_SCHEDULED_MEAL_TYPE;
			}
			CustomerMeal mealToBeAdded = new CustomerMeal();
			mealToBeAdded.setFormat(MealFormat.SCHEDULED.name());
			order.setMealFormat(MealFormat.SCHEDULED);
			com.rns.tiffeat.web.dao.domain.Customer customer = customerDao.getCustomer(order.getCustomer().getId());
			if (customer == null) {
				return ERROR_INVALID_ORDER_DETAILS;
			}
			customer.setPhone(order.getCustomer().getPhone());
			BusinessToDataConverters.convertCustomerMeal(customer, mealToBeAdded, order);
			if (isOrdersGeneratedByVendor(order)) {
				addOrder(mealToBeAdded, order);
			}
			customerMealsToBeAdded.add(mealToBeAdded);
		}
		customerOrders.get(0).setId(customerMealDao.addCustomerMeals(customerMealsToBeAdded));
		MailUtil.sendMail(customerOrders.get(0));
		return RESPONSE_OK;
	}

	private boolean isOrdersGeneratedByVendor(CustomerOrder order) {
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(order.getMeal().getId(), order.getDate(), order.getMealType());
		if (dailyMeal == null) {
			return false;
		}
		return true;
	}

	private boolean isAlreadyScheduled(CustomerOrder order, List<CustomerMeal> scheduledMeals) {
		for (CustomerMeal meal : scheduledMeals) {
			if (order.getMealType() != null && order.getMealType().name().equals(meal.getMealType())) {
				return true;
			}
		}
		return false;
	}

	public String changeScheduledOrder(CustomerOrder customerOrder) {
		if (customerOrder == null || customerOrder.getContent() == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		if (customerOrder.getCustomer() == null || customerOrder.getMeal() == null) {
			return ERROR_INVALID_MEAL_DETAILS;
		}
		if(StringUtils.isEmpty(customerOrder.getAddress()) || customerOrder.getLocation() == null) {
			return ERROR_INVALID_ADDRESS_OR_LOCATION;
		}
		Meal oldMeal = mealDao.getMeal(customerOrder.getMeal().getId());
		if (oldMeal == null) {
			return ERROR_MEAL_NOT_AVAILABLE_PLEASE_CHECK_AGAIN;
		}
		if (!MealStatus.PREPARE.equals(CommonUtil.getMealStatus(customerOrder.getMealType(), oldMeal))) {
			return ERROR_CANT_CHANGE_THE_MEAL;
		}
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(oldMeal.getId(), customerOrder.getMealType());
		if (dailyMeal != null && !DateUtils.isSameDay(dailyMeal.getDate(), customerOrder.getContent().getDate())) {
			return ERROR_CANT_CHANGE_THE_MEAL;
		}
		CustomerMeal meal = customerMealDao.getCustomerMeal(customerOrder.getId());
		if (meal == null) {
			return ERROR_MEAL_NOT_AVAILABLE_PLEASE_CHECK_AGAIN;
		}
		Meal mealToBeAdded = new Meal();
		BusinessToDataConverters.convertMeal(mealToBeAdded, customerOrder.getMeal());
		meal.setMeal(mealToBeAdded);
		meal.setAddress(customerOrder.getAddress());
		meal.setLocation(customerOrder.getLocation().getAddress());
		customerMealDao.editCustomerMeal(meal);
		return RESPONSE_OK;
	}

	public String cancelScheduledOrder(CustomerOrder customerOrder) {
		if (customerOrder == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		CustomerMeal meal = customerMealDao.getCustomerMeal(customerOrder.getId());
		if (meal == null || meal.getMeal() == null || meal.getCustomer() == null) {
			return ERROR_MEAL_NOT_AVAILABLE_PLEASE_CHECK_AGAIN;
		}
		Meal oldMeal = meal.getMeal();
		if (!MealStatus.PREPARE.equals(CommonUtil.getMealStatus(customerOrder.getMealType(), oldMeal))) {
			return ERROR_CANT_CHANGE_THE_MEAL;
		}
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(meal.getMeal().getId(), customerOrder.getMealType());
		if (dailyMeal == null) {
			return ERROR_DAILYCONTENT_DOES_NOT_EXIST;
		}
		Order scheduledOrder = orderDao.getCustomerScheduledOrder(meal.getCustomer().getId(), dailyMeal.getDate(), dailyMeal.getType());
		if (scheduledOrder == null || scheduledOrder.getCustomerMeal() == null) {
			return ERROR_INVALID_ORDER_DETAILS;
		}
		scheduledOrder.setStatus(OrderStatus.CANCELLED.name());
		orderDao.editOrder(scheduledOrder);
		return RESPONSE_OK;
	}

	public String addMoneyToWallet(Customer currentCustomer) {
		if (currentCustomer == null || currentCustomer.getBalance() == null) {
			return ERROR_INVALID_CUSTOMER_DETAILS;
		}
		com.rns.tiffeat.web.dao.domain.Customer customer = customerDao.getCustomer(currentCustomer.getId());
		if (customer == null) {
			return ERROR_INVALID_CUSTOMER_DETAILS;
		}
		if (customer.getBalance() == null) {
			customer.setBalance(currentCustomer.getBalance());
		} else {
			customer.setBalance((currentCustomer.getBalance()).add(customer.getBalance()));
		}
		customerDao.editCustomer(customer);
		return RESPONSE_OK;
	}

	public long addTransaction(PayUDetails payUDetails) {
		Transaction transaction = new Transaction();
		if (payUDetails == null || payUDetails.getAmount() == null) {
			return 0;
		}
		BusinessToDataConverters.convertTransaction(payUDetails, transaction);
		transactionDao.addTransaction(transaction);
		return transaction.getId();
	}

	public void rateMeal(CustomerOrder order, BigDecimal rating) {
		if (order == null || order.getMeal() == null) {
			return;
		}
		Meal meal = mealDao.getMeal(order.getMeal().getId());
		if (meal == null) {
			return;
		}
		calculateMealRating(meal, rating);
		mealDao.editMeal(meal);
	}

	private void calculateMealRating(Meal meal, BigDecimal rating) {
		if (meal.getVendor() == null) {
			return;
		}
		com.rns.tiffeat.web.dao.domain.Vendor vendor = meal.getVendor();
		if (meal.getFeedbacks() == null || meal.getRating() == null) {
			meal.setRating(rating);
			meal.setFeedbacks(1);
			calculateVendorRating(rating, vendor);
			return;
		}
		BigDecimal total = meal.getRating().multiply(new BigDecimal(meal.getFeedbacks()));
		total = total.add(rating);
		meal.setFeedbacks(meal.getFeedbacks() + 1);
		meal.setRating(total.divide(new BigDecimal(meal.getFeedbacks())));

		calculateVendorRating(rating, vendor);
	}

	private void calculateVendorRating(BigDecimal rating, com.rns.tiffeat.web.dao.domain.Vendor vendor) {
		if (vendor.getFeedbacks() == null || vendor.getRating() == null) {
			vendor.setRating(rating);
			vendor.setFeedbacks(1);
			return;
		}
		BigDecimal vendorTotal = vendor.getRating().multiply(new BigDecimal(vendor.getFeedbacks()));
		vendorTotal = vendorTotal.add(rating);
		vendor.setFeedbacks(vendor.getFeedbacks() + 1);
		vendor.setRating(vendorTotal.divide(new BigDecimal(vendor.getFeedbacks()), 2, RoundingMode.HALF_UP));
	}

	public Map<MealType, Date> getAvailableMealTypeDates(CustomerOrder order) {
		// MealType[] mealTypes = getAvailableMealType(order);
		Map<MealType, Date> mealTypeDates = new HashMap<MealType, Date>();
		if (order == null || order.getMeal() == null) {
			return null;
		}
		Meal meal = mealDao.getMeal(order.getMeal().getId());
		if (meal == null) {
			return null;
		}
		if (isMealAvailableForMealType(meal, MealType.LUNCH)) {
			DailyMeal lunchMenu = dailyMealDao.getDailyMealsForMealType(meal.getId(), MealType.LUNCH);
			if (checkIfAvailable(meal, lunchMenu)) {
				mealTypeDates.put(MealType.LUNCH, CommonUtil.convertDate(new Date()));
			} else {
				mealTypeDates.put(MealType.LUNCH, CommonUtil.addDay());
			}
		}
		if (isMealAvailableForMealType(meal, MealType.DINNER)) {
			DailyMeal dinnerMenu = dailyMealDao.getDailyMealsForMealType(meal.getId(), MealType.DINNER);
			if (checkIfAvailable(meal, dinnerMenu)) {
				mealTypeDates.put(MealType.DINNER, new Date());
			} else {
				mealTypeDates.put(MealType.DINNER, CommonUtil.addDay());
			}
		}
		return mealTypeDates;
	}

	private boolean isMealAvailableForMealType(Meal meal, MealType mealType) {
		MealType type = CommonUtil.getMealType(meal.getType());
		if (type != null && !MealType.BOTH.equals(type) && !type.equals(mealType)) {
			return false;
		}
		return true;
	}

	public DailyContent getDailyContentForMeal(com.rns.tiffeat.web.bo.domain.Meal meal, MealType mealType) {
		if (meal == null) {
			return null;
		}
		DailyMeal dailyMeal = dailyMealDao.getDailyMealsForMealType(meal.getId(), mealType);
		return DataToBusinessConverters.convertDailyContent(dailyMeal);
	}

	public boolean loginWithGoogle(Customer googleCustomer) {
		if (googleCustomer == null) {
			return false;
		}
		com.rns.tiffeat.web.dao.domain.Customer registeredCustomer = customerDao.getCustomerByEmail(googleCustomer.getEmail());
		if (registeredCustomer == null) {
			return false;
		}
		googleCustomer.setId(registeredCustomer.getId());
		return true;
	}

}