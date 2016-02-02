package com.rns.tiffeat.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rns.tiffeat.web.bo.api.CustomerBo;
import com.rns.tiffeat.web.bo.api.SessionManager;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealFormat;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.PayUDetails;
import com.rns.tiffeat.web.bo.domain.PaymentType;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.google.GoogleUtil;
import com.rns.tiffeat.web.google.Location;
import com.rns.tiffeat.web.util.CommonUtil;
import com.rns.tiffeat.web.util.Constants;
import com.rns.tiffeat.web.util.MailUtil;
import com.rns.tiffeat.web.util.PaymentUtils;

@Controller
public class CustomerControllerWeb implements Constants {
	
	private static Logger logger = Logger.getLogger(CustomerControllerWeb.class.getName());
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

	@RequestMapping(value = URL_PREFIX + INDEX_URL_GET, method = RequestMethod.GET)
	public String initPage(ModelMap model) {
		prepareIndexPage(model);
		return INDEX_PAGE;
	}

	private void prepareIndexPage(ModelMap model) {
		CustomerOrder orderInProcess = manager.getCustomer().getOrderInProcess();
		model.addAttribute(MODEL_AREAS, Constants.AREAS);
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		//List<Vendor> latestVendors = getLatestVendors();
		//model.addAttribute(MODEL_VENDORS, latestVendors);
		List<Meal> meals = customerBo.getAvailableMeals(orderInProcess);
		model.addAttribute(MODEL_MEALS, meals);
		if (orderInProcess.getLocation() != null) {
			model.addAttribute(MODEL_LOCATION, orderInProcess.getLocation().getAddress());
		}
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
	}

	private List<Vendor> getLatestVendors() {
		CustomerOrder orderInProcess = manager.getCustomer().getOrderInProcess();
		if (orderInProcess.getLocation() == null || StringUtils.isEmpty(orderInProcess.getLocation().getAddress())) {
			return null;
		}
		List<Vendor> availableVendors = customerBo.getAvailableVendors(orderInProcess.getLocation().getAddress());
		if (CollectionUtils.isEmpty(availableVendors)) {
			logger.info("No Tiffins for:" + orderInProcess.getLocation().getAddress());
			manager.setResult(ERROR_NO_TIFFINS_AVAILABLE);
		}
		return availableVendors;
	}

	@RequestMapping(value = URL_PREFIX + GET_NEARBY_VENDORS_URL_POST, method = RequestMethod.POST)
	public RedirectView getVendorsNearby(/* String address */CustomerOrder order, String orderDate, ModelMap model) {
		// setCustomerOrderLocation(address);
		order.setDate(new Date());
		if ("tomorrow".equalsIgnoreCase(orderDate)) {
			order.setDate(CommonUtil.addDay());
		}
		manager.getCustomer().setOrderInProcess(order);
		return new RedirectView(INDEX_URL_GET);
	}

	private void setCustomerOrderLocation(String address) {
		Location location = new Location();
		location.setAddress(address);
		manager.getCustomer().getOrderInProcess().setLocation(location);
	}

	@RequestMapping(value = URL_PREFIX + CUSTOMER_SELECT_VENDOR_URL_POST, method = RequestMethod.POST)
	public RedirectView selectVendors(String pinCode, ModelMap model) {
		return new RedirectView(INDEX_URL_GET);
	}
	
	@RequestMapping(value = URL_PREFIX + MAKE_NEW_ORDER_URL_GET, method = RequestMethod.GET)
	public RedirectView makeNewOrder(ModelMap model) {
		manager.getCustomer().setOrderInProcess(null);
		return new RedirectView(INDEX_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + GET_VENDOR_MEALS_URL_GET, method = RequestMethod.GET)
	public String getVendorMeals(String vendorEmail, ModelMap model) {
		if (manager.getCustomer().getOrderInProcess().getLocation() == null) {
			prepareIndexPage(model);
			return INDEX_PAGE;
		}
		Vendor vendor = new Vendor();
		vendor.setEmail(vendorEmail);
		model.addAttribute(MODEL_VENDOR, customerBo.getVendorWithMeals(vendor));
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return SELECT_MEALS_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + SELECT_MEAL_FORMAT_URL_POST, method = RequestMethod.POST)
	public RedirectView selectMealFormat(Meal meal, ModelMap model) {
		CustomerOrder customerOrder = manager.getCustomer().getOrderInProcess();
		customerOrder.setMeal(meal);
		customerOrder.setCustomer(manager.getCustomer());
		if (StringUtils.isNotEmpty(manager.getCustomer().getEmail()) && customerOrder != null && MealFormat.SCHEDULED.equals(customerOrder.getMealFormat())) {
			if (customerOrder.getId() == 0) {
				return new RedirectView(SCHEDULED_ORDER_URL_GET);
			}
			return new RedirectView(CHANGE_ORDER_URL_GET);
		}
		model.addAttribute(MODEL_CUSTOMER_ORDER, customerOrder);
		manager.setResult(null);
		return new RedirectView(CHECK_LOGGED_IN_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + CHANGE_ORDER_URL_GET, method = RequestMethod.GET)
	public String initChangeOrder(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER_ORDER, manager.getCustomer().getOrderInProcess());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return CHANGE_SCHEDULED_ORDER_DETAILS_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + CHANGE_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView changeOrder(CustomerOrder customerOrder, ModelMap model) {
		CustomerOrder orderInProcess = manager.getCustomer().getOrderInProcess();
		orderInProcess.setAddress(customerOrder.getAddress());
		String changeScheduledOrderResult = customerBo.changeScheduledOrder(orderInProcess);
		if (!RESPONSE_OK.equals(changeScheduledOrderResult)) {
			manager.setResult(changeScheduledOrderResult);
		}
		System.out.println("Result of change order :" + changeScheduledOrderResult);
		return new RedirectView(CUSTOMER_HOME_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + SELECT_MEAL_FORMAT_URL_GET, method = RequestMethod.GET)
	public String initSelectMealFormat(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER_ORDER, manager.getCustomer().getOrderInProcess());
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return SELECT_MEAL_FORMAT_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + SCHEDULED_ORDER_URL_GET, method = RequestMethod.GET)
	public String scheduledOrder(ModelMap model) {
		if (manager == null || StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			return ERROR_PAGE;
		}
		prepareOrderDetails(manager.getCustomer().getOrderInProcess(), model);
		MealType[] mealTypes = new MealType[1];
		Map<MealType, Date> mealTypesMap = (Map<MealType, Date>) model.get(MODEL_MEAL_TYPE);
		MealType mealTypeSelected = manager.getCustomer().getOrderInProcess().getMealType();
		if (mealTypeSelected != null) {
			handleAddMealForSelectedTime(model, mealTypes, mealTypesMap, mealTypeSelected);
		} else {
			handleNewScheduledOrderScenario(model, mealTypesMap);
		}
		// customerBo.getAvailableMealType(manager.getCustomer().getOrderInProcess());
		model.addAttribute(MODEL_AREAS, Constants.AREAS);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		manager.setResult(null);
		return SCHEDULED_ORDER_DETAILS_PAGE;
	}

	private void handleNewScheduledOrderScenario(ModelMap model, Map<MealType, Date> mealTypesMap) {
		MealType[] availableMealTypesForCustomer = CommonUtil.filterScheduledMealTypes(manager.getCustomer(), mealTypesMap);
		if (availableMealTypesForCustomer != null) {
			model.addAttribute(MODEL_MEAL_TYPE, availableMealTypesForCustomer);
			if (!CommonUtil.checkIfToday(mealTypesMap.get(availableMealTypesForCustomer[0]))) {
				manager.getCustomer().getOrderInProcess().setDate(mealTypesMap.get(availableMealTypesForCustomer[0]));
			}
		} else {
			model.addAttribute(MODEL_MEAL_TYPE, null);
			manager.setResult(ERROR_ALERADY_SCHEDULED);
		}
	}

	private void handleAddMealForSelectedTime(ModelMap model, MealType[] mealTypes, Map<MealType, Date> mealTypesMap, MealType mealTypeSelected) {
		MealType mealType = manager.getCustomer().getOrderInProcess().getMealType();
		if (mealTypesMap.get(mealType) == null) {
			manager.setResult(ERROR_MEAL_NOT_AVAILABLE_FOR_THIS_TIMING);
			model.addAttribute(MODEL_MEAL_TYPE, null);
			return;
		}
		mealTypes[0] = mealType;
		model.addAttribute(MODEL_MEAL_TYPE, mealTypes);
		if (!CommonUtil.checkIfToday(mealTypesMap.get(mealTypeSelected))) {
			manager.getCustomer().getOrderInProcess().setDate(mealTypesMap.get(mealTypeSelected));
		}
	}

	private void prepareOrderDetails(CustomerOrder order, ModelMap model) {
		order.setDate(new Date());
		order.setCustomer(manager.getCustomer());
		prepareOrderAdress(order);
		model.addAttribute(MODEL_SELECTED_AREA_ENTRY, CommonUtil.getSelectedArea(CommonUtil.getPinCode(order.getArea())));
		// model.addAttribute(MODEL_MEAL_TYPE,
		// customerBo.getAvailableMealType(order));
		model.addAttribute(MODEL_MEAL_TYPE, customerBo.getAvailableMealTypeDates(order));
		model.addAttribute(MODEL_CUSTOMER_ORDER, order);
		model.addAttribute(MODEL_PAYMENT_TYPES, PaymentType.values());
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
	}

	private void prepareOrderAdress(CustomerOrder order) {
		if (order == null || order.getCustomer() == null || order.getAddress() != null) {
			return;
		}
		checkForEqualAddress(order.getCustomer().getQuickOrders(), order);
		checkForEqualAddress(order.getCustomer().getPreviousOrders(), order);
	}

	private void checkForEqualAddress(List<CustomerOrder> orders, CustomerOrder customerOrder) {
		if (CollectionUtils.isNotEmpty(orders) && customerOrder.getAddress() == null) {
			for (CustomerOrder order : orders) {
				if (StringUtils.equals(customerOrder.getArea(), order.getArea())) {
					customerOrder.setAddress(order.getAddress());
					return;
				}
			}
		}
	}

	@RequestMapping(value = URL_PREFIX + QUICK_ORDER_URL_GET, method = RequestMethod.GET)
	public String quickOrder(ModelMap model) {
		if (manager == null || StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			return ERROR_PAGE;
		}
		prepareOrderDetails(manager.getCustomer().getOrderInProcess(), model);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		manager.setResult(null);
		return QUICK_ORDER_DETAILS_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + REGISTER_CUSTOMER_URL_POST, method = RequestMethod.POST)
	public RedirectView registerCustomer(Customer customer, ModelMap model) {
		String result = customerBo.register(customer);
		if (!RESPONSE_OK.equals(result)) {
			manager.setResult(result);
			return new RedirectView(CUSTOMER_LOGIN_URL_GET);
		}
		return postLoginSuccess(customer);
	}

	@RequestMapping(value = URL_PREFIX + REGISTER_CUSTOMER_URL_GET, method = RequestMethod.GET)
	public String initRegisterCustomer(ModelMap model) {
		Customer customer = new Customer();
		model.addAttribute(MODEL_CUSTOMER, customer);
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return REGISTER_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + CHECK_LOGGED_IN_URL_GET, method = RequestMethod.GET)
	public RedirectView checkRegistration(/*MealFormat format*/ModelMap model) {
		customerBo.setCurrentCustomer(manager.getCustomer());
		//manager.getCustomer().getOrderInProcess().setMealFormat(format);
		if (manager == null || StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			return new RedirectView(CUSTOMER_LOGIN_URL_GET);
		}
		if (MealFormat.QUICK.equals(manager.getCustomer().getOrderInProcess().getMealFormat())) {
			return new RedirectView(QUICK_ORDER_URL_GET);
		}
		return new RedirectView(SCHEDULED_ORDER_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + QUICK_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView quickOrder(CustomerOrder customerOrder, String orderDate, ModelMap model) {
		customerOrder.setDate(CommonUtil.convertDate(orderDate));
		if (PaymentType.CASH.equals(customerOrder.getPaymentType())) {
			String result = customerBo.validateQuickOrder(customerOrder);
			if (WARNING_DATE_CHANGED.equals(result)) {
				manager.setResult("Today's meal for " + customerOrder.getMealType() + " is not available!" + "You can order for tomorrow.");
				return new RedirectView(QUICK_ORDER_URL_GET);
			}
			if (!RESPONSE_OK.equals(result)) {
				manager.setResult(result);
				return new RedirectView(QUICK_ORDER_URL_GET);
			}
			customerBo.quickOrder(customerOrder);
			return new RedirectView(QUICK_ORDERS_HOME_URL_GET);
		}

		String result = customerBo.validateQuickOrder(customerOrder);
		if (WARNING_DATE_CHANGED.equals(result)) {
			manager.setResult("Today's meal for " + customerOrder.getMealType() + " is not available!" + "You can order for tomorrow.");
			return new RedirectView(QUICK_ORDER_URL_GET);
		}
		if (!RESPONSE_OK.equals(result)) {
			manager.setResult(result);
			return new RedirectView(QUICK_ORDER_URL_GET);
		}
		manager.setResult(result);
		manager.getCustomer().setOrderInProcess(customerOrder);
		return new RedirectView(PAYMENT_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + PAYMENT_URL_GET, method = RequestMethod.GET)
	public String payment(ModelMap model) {
		if (StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			return ERROR_PAGE;
		}
		try {
			PayUDetails payUDetails = PaymentUtils.preparePayUDetails(manager.getCustomer().getOrderInProcess());
			payUDetails.setId(PAYMENT_PREFIX + customerBo.addTransaction(payUDetails));
			payUDetails.setHashKey(PaymentUtils.prepareHashKey(payUDetails));
			manager.getCustomer().getOrderInProcess().setTransactionId(payUDetails.getId());
			model.addAttribute(MODEL_PAY_U_DETAILS, payUDetails);
			model.addAttribute(MODEL_CUSTOMER_ORDER, manager.getCustomer().getOrderInProcess());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return PAYMENT_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + PAYMENT_RESULT_URL_POST, method = RequestMethod.POST)
	public RedirectView paymentResult(ModelMap model, HttpServletRequest request) {
		RedirectView view = new RedirectView(CUSTOMER_HOME_URL_GET);
		String paymentResult = request.getParameter(PAYMENT_STATUS);
		System.out.println("Result of payment :" + paymentResult);
		CustomerOrder orderInProcess = manager.getCustomer().getOrderInProcess();
		if (paymentResult != null && orderInProcess.getMeal() == null) {
			return new RedirectView(PAYMENT_ANDROID_RESULT_GET_URL + paymentResult);
		}
		orderInProcess.setPaymentType(PaymentUtils.getPaymenType(request.getParameter(PAYMENT_MODE)));
		if (StringUtils.equalsIgnoreCase(PAYMENT_RESULT_SUCCESS, paymentResult)) {
			if (MealFormat.SCHEDULED.equals(orderInProcess.getMealFormat())) {
				view = processScheduledPayment(orderInProcess);
			} else {
				view = processQuickOrderPayment(orderInProcess);
			}
		} else {
			manager.setResult("Payment failed!!");
			if (MealFormat.SCHEDULED.equals(orderInProcess.getMealFormat())) {
				view = new RedirectView(ADD_MONEY_TO_WALLET_URL_GET);
			} else {
				view = new RedirectView(QUICK_ORDER_URL_GET);
			}
		}
		MailUtil.sendMail(orderInProcess);
		return view;
	}

	private RedirectView processQuickOrderPayment(CustomerOrder orderInProcess) {
		String quickOrderResult = customerBo.quickOrder(orderInProcess);
		if (!RESPONSE_OK.equals(quickOrderResult)) {
			manager.setResult(quickOrderResult);
			return new RedirectView(QUICK_ORDERS_HOME_URL_GET);
		}
		return new RedirectView(QUICK_ORDERS_HOME_URL_GET);
	}

	private RedirectView processScheduledPayment(CustomerOrder orderInProcess) {
		RedirectView view;
		String addMoneyToWalletResult = customerBo.addMoneyToWallet(manager.getCustomer());
		if (!RESPONSE_OK.equals(addMoneyToWalletResult)) {
			manager.setResult(addMoneyToWalletResult);
			view = new RedirectView(ADD_MONEY_TO_WALLET_URL_GET);
		} else {
			if (orderInProcess.getMeal() == null || orderInProcess.getMeal().getId() > 0) {
				customerBo.scheduledOrder(orderInProcess);
			}
			view = new RedirectView(CUSTOMER_HOME_URL_GET);
		}
		return view;
	}

	@RequestMapping(value = URL_PREFIX + CUSTOMER_LOGIN_URL_GET, method = RequestMethod.GET)
	public String prepareCustomerLogin(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER, new Customer());
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return CUSTOMER_LOGIN_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + CUSTOMER_LOGIN_URL_POST, method = RequestMethod.POST)
	public RedirectView customerLogin(Customer customer, ModelMap model) {
		if (!customerBo.login(customer)) {
			return postLoginFailure(ERROR_INVALID_CREDENTIALS);
		}
		return postLoginSuccess(customer);
	}

	private RedirectView postLoginFailure(String loginResult) {
		manager.setResult(loginResult);
		return new RedirectView(CUSTOMER_LOGIN_URL_GET);
	}

	private RedirectView postLoginSuccess(Customer customer) {
		manager.setResult(null);
		manager.getCustomer().setId(customer.getId());
		if (manager.getCustomer().getOrderInProcess() != null) {
			customerBo.setCurrentCustomer(manager.getCustomer());
			if (MealFormat.QUICK.equals(manager.getCustomer().getOrderInProcess().getMealFormat())) {
				return new RedirectView(QUICK_ORDER_URL_GET);
			} else if (MealFormat.SCHEDULED.equals(manager.getCustomer().getOrderInProcess().getMealFormat())) {
				return new RedirectView(SCHEDULED_ORDER_URL_GET);
			}
		}
		return new RedirectView(CUSTOMER_HOME_URL_GET);
	}

	
	@RequestMapping(value = URL_PREFIX + SCHEDULED_ORDERS_URL_GET, method = RequestMethod.GET)
	public String scheduledHomePage(ModelMap model) {
		customerBo.setCurrentCustomer(manager.getCustomer());
		manager.getCustomer().setOrderInProcess(null);
		if (StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			prepareIndexPage(model);
			return INDEX_PAGE;
		}
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return SCHEDULED_HOME_PAGE;
	}
	
	@RequestMapping(value = URL_PREFIX + CUSTOMER_HOME_URL_GET, method = RequestMethod.GET)
	public String customerHomePage(ModelMap model) {
		customerBo.setCurrentCustomer(manager.getCustomer());
		manager.getCustomer().setOrderInProcess(null);
		if (StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			prepareIndexPage(model);
			return INDEX_PAGE;
		}
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		if (CollectionUtils.isEmpty(manager.getCustomer().getScheduledOrder())) {
			return QUICK_HOME_PAGE;
		}
		return SCHEDULED_HOME_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + QUICK_ORDERS_HOME_URL_GET, method = RequestMethod.GET)
	public String showQuickOrders(ModelMap model) {
		customerBo.setCurrentCustomer(manager.getCustomer());
		manager.getCustomer().setOrderInProcess(null);
		if (StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			prepareIndexPage(model);
			return INDEX_PAGE;
		}
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return QUICK_HOME_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + SCHEDULED_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView scheduleOrder(CustomerOrder customerOrder, ModelMap model) {
		if (customerOrder.getDate() == null) {
			customerOrder.setDate(manager.getCustomer().getOrderInProcess().getDate());
		}
		manager.getCustomer().setPhone(customerOrder.getCustomer().getPhone());
		// manager.getCustomer().setQuickOrders(scheduledOrders);
		String scheduledOrderResult = "";
		boolean walletRequired = false;
		if (manager.getCustomer().getBalance() == null || manager.getCustomer().getBalance().compareTo(customerOrder.getMeal().getPrice()) < 0) {
			scheduledOrderResult = customerBo.validateScheduledOrder(customerOrder);
			walletRequired = true;
		} else {
			scheduledOrderResult = customerBo.scheduledOrder(customerOrder);
		}
		if (!RESPONSE_OK.equals(scheduledOrderResult)) {
			manager.setResult(scheduledOrderResult);
			return new RedirectView(SCHEDULED_ORDER_URL_GET);
		}
		manager.setResult(null);
		customerOrder.setMealFormat(MealFormat.SCHEDULED);
		manager.getCustomer().setOrderInProcess(customerOrder);
		if(walletRequired) {
			return new RedirectView(ADD_MONEY_TO_WALLET_URL_GET);
		}
		return new RedirectView(CUSTOMER_HOME_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + ADD_MONEY_TO_WALLET_URL_GET, method = RequestMethod.GET)
	public String initAddToWallet(ModelMap model) {
		if (StringUtils.isEmpty(manager.getCustomer().getEmail())) {
			return ERROR_PAGE;
		}
		if (manager.getCustomer().getBalance() == null) {
			manager.getCustomer().setBalance(new BigDecimal(0));
		}
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESULT, manager.getResult());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		manager.setResult(null);
		return ADD_TO_WALLET_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + ADD_MONEY_TO_WALLET_URL_POST, method = RequestMethod.POST)
	public RedirectView addToWallet(BigDecimal amount, ModelMap model) {
		manager.getCustomer().setBalance(amount);
		CustomerOrder orderInProcess = manager.getCustomer().getOrderInProcess();
		if (orderInProcess.getMealFormat() == null) {
			orderInProcess.setMealFormat(MealFormat.SCHEDULED);
		}
		orderInProcess.setCustomer(manager.getCustomer());
		if (orderInProcess.getMeal() == null) {
			orderInProcess.setMeal(new Meal());
		}
		return new RedirectView(PAYMENT_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + CANCEL_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView cancelOrder(CustomerOrder customerOrder, ModelMap model) {
		String result = customerBo.cancelScheduledOrder(customerOrder);
		if (!RESPONSE_OK.equals(result)) {
			manager.setResult(result);
		}
		return new RedirectView(CUSTOMER_HOME_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + CHANGE_MEAL_URL_POST, method = RequestMethod.POST)
	public RedirectView changeMeal(CustomerOrder customerOrder, String menuDate, ModelMap model) {
		if (StringUtils.isEmpty(menuDate)) {
			System.out.println("Can't change this meal!!");
			manager.setResult(ERROR_CANT_CHANGE_THE_MEAL);
			return new RedirectView(CUSTOMER_HOME_URL_GET);
		}
		Date contentDate = CommonUtil.convertDate(menuDate);
		Map<MealType, Date> mealTypeDates = customerBo.getAvailableMealTypeDates(customerOrder);
		Date mealTypeAvailableDate = mealTypeDates.get(customerOrder.getMealType());
		if (mealTypeAvailableDate == null || !DateUtils.isSameDay(mealTypeAvailableDate, contentDate)) {
			System.out.println("Can't change this meal!!");
			manager.setResult(ERROR_CANT_CHANGE_THE_MEAL);
			return new RedirectView(CUSTOMER_HOME_URL_GET);
		}
		customerOrder.getContent().setDate(contentDate);
		// manager.setAvailableVendors(customerBo.getAvailableVendors(CommonUtil.getPinCode(customerOrder.getArea())));
		manager.getCustomer().setOrderInProcess(customerOrder);
		return new RedirectView(INDEX_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + REPEAT_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView repeat(CustomerOrder customerOrder/* ,Meal meal */, ModelMap model) {
		// customerOrder.setMeal(meal);
		manager.getCustomer().setOrderInProcess(customerOrder);
		return new RedirectView(SELECT_MEAL_FORMAT_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + ADD_SCHEDULED_ORDER_URL_POST, method = RequestMethod.POST)
	public RedirectView addScheduledOrder(CustomerOrder customerOrder, ModelMap model) {
		if (MealType.LUNCH.equals(customerOrder.getMealType())) {
			customerOrder.setMealType(MealType.DINNER);
		} else {
			customerOrder.setMealType(MealType.LUNCH);
		}
		customerOrder.setMealFormat(MealFormat.SCHEDULED);
		manager.getCustomer().setOrderInProcess(customerOrder);
		// manager.setAvailableVendors(customerBo.getAvailableVendors(CommonUtil.getPinCode(customerOrder.getArea())));
		return new RedirectView(INDEX_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + GET_MENU_URL_POST, method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String initEditDepartment(long mealId, MealType mealType) {
		Gson gson = new GsonBuilder().create();
		Meal meal = new Meal();
		meal.setId(mealId);
		return gson.toJson(customerBo.getDailyContentForMeal(meal, mealType));
	}

	@RequestMapping(value = URL_PREFIX + LOGOUT_URL_GET, method = RequestMethod.GET)
	public RedirectView logout(ModelMap model) {
		manager.logoutCustomer();
		return new RedirectView(INDEX_URL_GET);
	}

	@RequestMapping(value = URL_PREFIX + TERMS_URL_GET, method = RequestMethod.GET)
	public String terms(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return TERMS_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + ABOUT_US_URL_GET, method = RequestMethod.GET)
	public String aboutUs(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return ABOUT_US_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + CONTACT_US_URL_GET, method = RequestMethod.GET)
	public String contactUs(ModelMap model) {
		model.addAttribute(MODEL_CUSTOMER, manager.getCustomer());
		model.addAttribute(MODEL_RESOURCES, ASSETS_ROOT);
		return CONTACT_US_PAGE;
	}

	@RequestMapping(value = URL_PREFIX + GET_GOOGLE_CODE_URL_GET, method = RequestMethod.GET)
	public RedirectView getGoogleCode(@RequestParam(value = "code") String code, ModelMap model) {
		String loginResult = "";
		try {
			Customer googleCustomer = GoogleUtil.getGoogleCustomer(code);
			loginResult = customerBo.loginWithGoogle(googleCustomer);
			if (RESPONSE_OK.equals(loginResult)) {
				return postLoginSuccess(googleCustomer);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return postLoginFailure(loginResult);
	}

	@ExceptionHandler(HibernateException.class)
	public ModelAndView onHibernateException(HibernateException hibernateException) {
		ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
		modelAndView.addObject(MODEL_ERROR, "Error connecting to the database!!");
		return modelAndView;
	}

	@ExceptionHandler(Exception.class)
	public String onGenericException() {
		return ERROR_PAGE;
	}

}
