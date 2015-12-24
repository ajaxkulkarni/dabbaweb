package com.rns.tiffeat.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rns.tiffeat.web.bo.api.CustomerBo;
import com.rns.tiffeat.web.bo.api.VendorBo;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.PayUDetails;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.util.CommonUtil;
import com.rns.tiffeat.web.util.Constants;
import com.rns.tiffeat.web.util.ImageUtil;
import com.rns.tiffeat.web.util.PaymentUtils;

@Controller
public class CustomerContollerAndroid implements Constants {

	private CustomerBo customerBo;
	private VendorBo vendorBo;

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

	@RequestMapping(value = "/getAreasAndroid", method = RequestMethod.POST)
	public @ResponseBody String getAllColleges(ModelMap model) {
		List<String> areas = new ArrayList<String>(Constants.AREAS.values());
		return new Gson().toJson(areas);
	}

	@RequestMapping(value = "/getVendorsForAreaAndroid", method = RequestMethod.POST)
	public @ResponseBody String getVendors(@RequestParam(value = MODEL_PIN_CODE) String pinCode, ModelMap model) {
		List<Vendor> vendors = new ArrayList<Vendor>();
		String areaPinCode = CommonUtil.getPinCode(pinCode);
		if (StringUtils.isNotEmpty(areaPinCode)) {
			vendors = customerBo.getAvailableVendors(areaPinCode);
		}
		return new Gson().toJson(vendors);
	}

	@RequestMapping(value = "/getVendorMealsAndroid", method = RequestMethod.POST)
	public @ResponseBody String getVendorWithMeals(@RequestParam(value = MODEL_VENDOR) String vendor, ModelMap model) {
		Vendor vendorJson = new Gson().fromJson(vendor, Vendor.class);
		System.out.println("Vendor received:" + vendorJson);
		return new Gson().toJson(customerBo.getVendorWithMeals(vendorJson));
	}

	// IF RESPONSE_OK .. then registered..IMP Returns an ID

	@RequestMapping(value = "/registerCustomerAndroid", method = RequestMethod.POST)
	public @ResponseBody String register(@RequestParam(value = MODEL_CUSTOMER) String customer, ModelMap model) {
		Customer customerObject = new Gson().fromJson(customer, Customer.class);
		String result = customerBo.register(customerObject);
		if (RESPONSE_OK.equals(result)) {
			return new Gson().toJson(customerObject);
		}
		return new Gson().toJson(result);
	}

	// LUNCH/DINNER.. which is available

	/*@RequestMapping(value = "/getAvailableMealTypeAndroid", method = RequestMethod.POST)
	public @ResponseBody String getAvailableMealFormat(
			@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder, ModelMap model) {
		CustomerOrder customerOrderObject = new Gson().fromJson(customerOrder, CustomerOrder.class);
		MealType[] availableMealTypes = customerBo.getAvailableMealType(customerOrderObject);
		List<MealType> mealTypes = new ArrayList<MealType>();
		if(availableMealTypes != null && availableMealTypes.length > 0) {
			mealTypes = Arrays.asList(availableMealTypes);
		}
		if(customerOrderObject.getDate() == null) {
			customerOrderObject.setDate(new Date());
		}
		Map<String,Object> availableMealsMap = new HashMap<String, Object>();
		removeCircularReferences(customerOrderObject);
		availableMealsMap.put(MODEL_CUSTOMER_ORDER, new Gson().toJson(customerOrderObject));
		availableMealsMap.put(MODEL_MEAL_TYPE, mealTypes);
		return new Gson().toJson(availableMealsMap);
	}*/
	
	@RequestMapping(value = "/getAvailableMealTypeDatesAndroid", method = RequestMethod.POST)
	public @ResponseBody String getAvailableMealFormatDates(
			@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder, ModelMap model) {
		CustomerOrder customerOrderObject = new Gson().fromJson(customerOrder, CustomerOrder.class);
		Map<MealType, Date> availableMealTypes = customerBo.getAvailableMealTypeDates(customerOrderObject);
		if(customerOrderObject.getDate() == null) {
			customerOrderObject.setDate(new Date());
		}
		Map<String,Object> availableMealsMap = new HashMap<String, Object>();
		removeCircularReferences(customerOrderObject);
		availableMealsMap.put(MODEL_CUSTOMER_ORDER, new Gson().toJson(customerOrderObject));
		availableMealsMap.put(MODEL_MEAL_TYPE, new Gson().toJson(availableMealTypes));
		return new Gson().toJson(availableMealsMap);
	}
	

	private void removeCircularReferences(CustomerOrder customerOrderObject) {
		if(customerOrderObject == null || customerOrderObject.getMeal() == null || customerOrderObject.getCustomer() == null) {
			return;
		}
		if(customerOrderObject.getMeal().getVendor() != null) {
			customerOrderObject.getMeal().getVendor().setMeals(null);
		}
		removeCircularReferences(customerOrderObject.getCustomer());
	}
	
	@RequestMapping(value = "/validateQuickOrderAndroid", method = RequestMethod.POST)
	public @ResponseBody String validateQuickOrder(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder,
			ModelMap model) {
		CustomerOrder customerOrderObject = new Gson().fromJson(customerOrder, CustomerOrder.class);
		String result = customerBo.validateQuickOrder(customerOrderObject);
		Map<MealType, Date> availableMealTypes = customerBo.getAvailableMealTypeDates(customerOrderObject);
		Map<String,Object> availableMealsMap = new HashMap<String, Object>();
		removeCircularReferences(customerOrderObject);
		availableMealsMap.put(MODEL_CUSTOMER_ORDER, new Gson().toJson(customerOrderObject));
		availableMealsMap.put(MODEL_MEAL_TYPE, new Gson().toJson(availableMealTypes));
		availableMealsMap.put(MODEL_RESULT, result);
		return new Gson().toJson(availableMealsMap);
	}

	@RequestMapping(value = "/quickOrderAndroid", method = RequestMethod.POST)
	public @ResponseBody String quickOrder(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder,
			ModelMap model) {
		return new Gson().toJson(customerBo.quickOrder(new Gson().fromJson(customerOrder, CustomerOrder.class)));
	}
	
	@RequestMapping(value = "/scheduledOrderAndroid", method = RequestMethod.POST)
	public @ResponseBody String scheduledOrder(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder,
			ModelMap model) {
		Type typelist=new TypeToken<ArrayList<CustomerOrder>>(){}.getType();
		List<CustomerOrder> scheduledOrders = new Gson().fromJson(customerOrder, typelist);
		return new Gson().toJson(customerBo.scheduledOrder(scheduledOrders));
	}
	
	@RequestMapping(value = "/cancelOrderAndroid", method = RequestMethod.POST)
	public @ResponseBody String cancelOrder(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder,
			ModelMap model) {
		return new Gson().toJson(customerBo.cancelScheduledOrder(new Gson().fromJson(customerOrder, CustomerOrder.class)));
	}
	
	@RequestMapping(value = "/changeOrderAndroid", method = RequestMethod.POST)
	public @ResponseBody String changeOrder(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder,
			ModelMap model) {
		return new Gson().toJson(customerBo.changeScheduledOrder(new Gson().fromJson(customerOrder, CustomerOrder.class)));
	}

	
	@RequestMapping(value = "/addToWalletAndroid", method = RequestMethod.POST)
	public @ResponseBody String addToWallet(@RequestParam(value = MODEL_CUSTOMER) String customer,
			ModelMap model) {
		return new Gson().toJson(customerBo.addMoneyToWallet(new Gson().fromJson(customer, Customer.class)));
	}
	
	@RequestMapping(value = "/loginCustomerAndroid", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestParam(value = MODEL_CUSTOMER) String customer, ModelMap model) {
		Customer customerObject = new Gson().fromJson(customer, Customer.class);
		if(!customerBo.login(customerObject)) {
			return ERROR_INVALID_CUSTOMER_DETAILS;
		}
		customerBo.setCurrentCustomer(customerObject);
		removeCircularReferences(customerObject);
		return new Gson().toJson(customerObject);
	}
	
	@RequestMapping(value = "/loginWithGoogleCustomerAndroid", method = RequestMethod.POST)
	public @ResponseBody String loginGoogle(@RequestParam(value = MODEL_CUSTOMER) String customer, ModelMap model) {
		Customer customerObject = new Gson().fromJson(customer, Customer.class);
		if(!RESPONSE_OK.equals(customerBo.loginWithGoogle(customerObject))) {
			return ERROR_INVALID_CUSTOMER_DETAILS;
		}
		customerBo.setCurrentCustomer(customerObject);
		removeCircularReferences(customerObject);
		return new Gson().toJson(customerObject);
	}
	
	@RequestMapping(value = "/getCurrentCustomerAndroid", method = RequestMethod.POST)
	public @ResponseBody String getCurrentCustomer(@RequestParam(value = MODEL_CUSTOMER) String customer, ModelMap model) {
		Customer customerObject = new Gson().fromJson(customer, Customer.class);
		customerBo.setCurrentCustomer(customerObject);
		removeCircularReferences(customerObject);
		return new Gson().toJson(customerObject);
	}
	
	@RequestMapping(value = "/paymentAndroid", method = RequestMethod.GET)
	public String payment(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder, ModelMap model) {
		try {
			CustomerOrder customerOrderObject = new Gson().fromJson(customerOrder,CustomerOrder.class);
			if(customerOrderObject == null) {
				return "error";
			}
			PayUDetails payUDetails = PaymentUtils.preparePayUDetails(customerOrderObject);
			payUDetails.setId("T" + customerBo.addTransaction(payUDetails));
			payUDetails.setHashKey(PaymentUtils.prepareHashKey(payUDetails));
			model.addAttribute(MODEL_PAY_U_DETAILS,payUDetails);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "error";
		}
		return "customer_payment";
	}
	
	@RequestMapping(value = "/getMenuAndroid", method = RequestMethod.POST)
	public String getMenuAndroid(@RequestParam(value = MODEL_CUSTOMER_ORDER) String customerOrder, ModelMap model) {
		CustomerOrder order = new Gson().fromJson(customerOrder, CustomerOrder.class);
		return new Gson().toJson(customerBo.getDailyContentForMeal(order.getMeal(), order.getMealType()));
	}

	private void removeCircularReferences(Customer customerObject) {
		if(customerObject == null) {
			return;
		}
		removeCircularReferncesCustomerOrders(customerObject.getScheduledOrder());
		removeCircularReferncesCustomerOrders(customerObject.getPreviousOrders());
		removeCircularReferncesCustomerOrders(customerObject.getQuickOrders());
	}
	
	private void removeCircularReferncesCustomerOrders(List<CustomerOrder> orders) {
		if(CollectionUtils.isEmpty(orders)) {
			return;
		}
		for(CustomerOrder order:orders) {
			order.setCustomer(null);
		}
	}

	@RequestMapping(value = "/downloadVendorImageAndroid", method = RequestMethod.GET)
	public void downloadDocument(@RequestParam(value = MODEL_VENDOR) String vendor, HttpServletResponse response,
			ModelMap model) {
		InputStream is = null;
		try {
			Vendor currentVendor = new Vendor();
			currentVendor.setEmail(vendor);
			System.out.println("Got EMail :" + vendor);
			is = vendorBo.getVendorProfilePic(currentVendor);
			if (is != null) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(ImageUtil.compressImage(is), "jpeg", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
				IOUtils.copy(in, response.getOutputStream());
				//response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\"image\"");
				response.flushBuffer();
				is.close();
				in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/downloadMealImageAndroid", method = RequestMethod.GET)
	public void downloadMealImageAndroid(@RequestParam(value = MODEL_MEAL) String meal, HttpServletResponse response,ModelMap model) {
		InputStream is = null;
		try {
			//Meal currentMeal = new Gson().fromJson(meal, Meal.class);
			Meal currentMeal = new Meal();
			currentMeal.setId(new Long(meal));
			is = vendorBo.getMealImage(currentMeal);
			if (is != null) {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(ImageUtil.compressImage(is), "jpeg", os);
				InputStream in = new ByteArrayInputStream(os.toByteArray());
				IOUtils.copy(in, response.getOutputStream());
				//response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\"image\"");
				response.flushBuffer();
				is.close();
				in.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
