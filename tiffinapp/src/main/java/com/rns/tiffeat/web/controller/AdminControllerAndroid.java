package com.rns.tiffeat.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.rns.tiffeat.web.bo.api.CustomerBo;
import com.rns.tiffeat.web.bo.api.VendorBo;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealStatus;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.util.CommonUtil;

@Controller
public class AdminControllerAndroid {
	
	private VendorBo vendorBo;
	private CustomerBo customerBo;

	@Autowired(required = true)
	@Qualifier(value = "manager")


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
	
	@RequestMapping(value = "/adminGetAllOrdersAndroid", method = RequestMethod.GET)
	public @ResponseBody String getAllColleges(ModelMap model,String dateRange) {
		return new Gson().toJson(vendorBo.getAllOrders(dateRange));
	}
	
	@RequestMapping(value = "/adminGetAllMealsAndroid", method = RequestMethod.GET)
	public @ResponseBody String getAllMeals(ModelMap model) {
		return new Gson().toJson(vendorBo.getAllMeals());
	}
	
	@RequestMapping(value = "/adminChangeMealStatusAndroid", method = RequestMethod.GET)
	public @ResponseBody String changeStatus(ModelMap model, String meal, String phase, String mealType, String dailyContent) {
		Meal mealObject = new Gson().fromJson(meal, Meal.class);
		DailyContent content = new Gson().fromJson(dailyContent, DailyContent.class);
		if(MealStatus.PREPARE.name().equals(phase)) {
			if(getDailyContent(mealObject, mealType) != null) {
				vendorBo.updateDailyContent(content);
			} else {
				vendorBo.addDailyContent(content);
			}
		} else if (MealStatus.COOKING.name().equals(phase)) {
			vendorBo.startCooking(mealObject, CommonUtil.getMealType(mealType));
		} else if (MealStatus.DISPATCH.name().equals(phase)) {
			vendorBo.startDispatch(mealObject, CommonUtil.getMealType(mealType));
		}
		return new Gson().toJson("OK");
	}
	
	@RequestMapping(value = "/adminCancelCustomerOrder", method = RequestMethod.POST)
	public @ResponseBody String cancelOrder(String order, ModelMap model) {
		CustomerOrder orderToCancel = new Gson().fromJson(order, CustomerOrder.class);
		return vendorBo.cancelOrder(orderToCancel);
	}
	
	@RequestMapping(value = "/adminDeliverCustomerOrder", method = RequestMethod.POST)
	public @ResponseBody String deliverOrder(String order, ModelMap model) {
		CustomerOrder orderToDeliver = new Gson().fromJson(order, CustomerOrder.class);
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		orders.add(orderToDeliver);
		vendorBo.delieverTiffins(orders);
		return "OK";
	}
	
	@RequestMapping(value = "/adminAddBalance", method = RequestMethod.POST)
	public @ResponseBody String addBalance(String customer, ModelMap model) {
		Customer currentCustomer = new Gson().fromJson(customer, Customer.class);
		customerBo.addMoneyToWallet(currentCustomer);
		return "OK";
	}
	
	@RequestMapping(value = "/adminCookAll", method = RequestMethod.POST)
	public @ResponseBody String cookAll(String mealType, ModelMap model) {
		vendorBo.startCookingAll(CommonUtil.getMealType(mealType));
		return "OK";
	}
	
	@RequestMapping(value = "/adminDispatchAll", method = RequestMethod.POST)
	public @ResponseBody String dispatchAll(String mealType, ModelMap model) {
		vendorBo.startDispatchAll(CommonUtil.getMealType(mealType));
		return "OK";
	}
	
	@RequestMapping(value = "/adminGetAllCustomers", method = RequestMethod.POST)
	public @ResponseBody String getAllCustomers(ModelMap model) {
		return new Gson().toJson(customerBo.getAllCustomers());
	}

	private DailyContent getDailyContent(Meal mealObject, String mealType) {
		if(MealType.LUNCH.name().equals(mealType)) {
			return mealObject.getLunchMenu();
		}
		return mealObject.getDinnerMenu();
	}

}
