package com.resoneuronance.tiffinapp.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonSyntaxException;
import com.rns.tiffeat.web.bo.domain.Customer;
import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.Meal;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.domain.PaymentType;
import com.rns.tiffeat.web.bo.domain.Vendor;
import com.rns.tiffeat.web.bo.impl.CustomerBoImpl;
import com.rns.tiffeat.web.dao.impl.CustomerDaoImpl;
import com.rns.tiffeat.web.dao.impl.CustomerMealDaoImpl;
import com.rns.tiffeat.web.dao.impl.DailyMealDaoImpl;
import com.rns.tiffeat.web.dao.impl.MealDaoImpl;
import com.rns.tiffeat.web.dao.impl.OrderDaoImpl;
import com.rns.tiffeat.web.dao.impl.VendorDaoImpl;
import com.rns.tiffeat.web.google.GoogleUtil;
import com.rns.tiffeat.web.google.Location;
import com.rns.tiffeat.web.util.CommonUtil;

public class CustomerTest {
	
	private CustomerBoImpl customerBo;
	private CustomerDaoImpl customerDao;
	private MealDaoImpl mealDaoImpl;
	
	@Before
	public void before() {
		/*SessionFactory sessionFactory = createSessionFactory();
		VendorDaoImpl vendorDao = new VendorDaoImpl();
		vendorDao.setSessionFactory(sessionFactory);
		DailyMealDaoImpl dailyMealDao = new DailyMealDaoImpl();
		dailyMealDao.setSessionFactory(sessionFactory);
		customerBo = new CustomerBoImpl();
		customerBo.setVendorDao(vendorDao);
		customerDao = new CustomerDaoImpl();
		customerDao.setSessionFactory(sessionFactory);
		mealDaoImpl = new MealDaoImpl();
		mealDaoImpl.setSessionFactory(sessionFactory);
		customerBo.setCustomerDao(customerDao);
		customerBo.setDailyMealDao(dailyMealDao);
		OrderDaoImpl orderDao = new OrderDaoImpl();
		orderDao.setSessionFactory(sessionFactory);
		CustomerMealDaoImpl customerMealDao = new CustomerMealDaoImpl();
		customerMealDao.setSessionFactory(sessionFactory);
		customerBo.setOrderDao(orderDao);
		customerBo.setCustomerMealDao(customerMealDao);
		customerBo.setMealDao(mealDaoImpl);*/
	}

	private SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
		return sessionFactory;
	}
	
	@Test
	public void initialTest() {
		//check for database creation.. Change hibernate.hbm2ddl.auto value in hibernate.cfg.xml to create..uncomment it only for this run and them comment it again
	}
	
	@Test
	public void testGetAvailableVendors() {
		List<Vendor> availableVendors = customerBo.getAvailableVendors("411030");
		Assert.assertEquals(1,availableVendors.size());
		Assert.assertNotNull(availableVendors.get(0));
		Assert.assertNotNull(availableVendors.get(0).getName());
	}
	
	
	@Test
	public void testRegisterCustomer() {
		Customer customer = new Customer();
		customer.setName("Ajinkya");
		customer.setEmail("anand@gmail.com");
		customer.setPhone("124312571");
		customer.setPassword("asd");
		List<CustomerOrder> orders = new ArrayList<CustomerOrder>();
		CustomerOrder quick = new CustomerOrder();
		quick.setMealType(MealType.LUNCH);
		quick.setDate(new Date());
		quick.setAddress("Navi peth");
		quick.setArea("Navi peth");
		quick.setCustomer(customer);
		Meal meal = new Meal();
		meal.setId(5);
		quick.setMeal(meal);
		orders.add(quick);
		customer.setQuickOrders(orders);
		Assert.assertEquals("OK",customerBo.register(customer));
	}
	
	@Test
	public void testQuickOrder() {
		Customer customer = new Customer();
		customer.setId(117);
		Meal meal = new Meal();
		meal.setId(14);
		CustomerOrder quick = new CustomerOrder();
		quick.setMealType(MealType.DINNER);
		quick.setDate(new Date());
		quick.setAddress("Navi peth");
		quick.setArea("Navi peth");
		quick.setCustomer(customer);
		quick.setMeal(meal);
		quick.setPaymentType(PaymentType.CASH);
		Assert.assertEquals("OK", customerBo.quickOrder(quick));
	}
	
	@Test
	public void testLogin() {
		Customer customer = new Customer();
		customer.setEmail("ajinkyashiva@gmail.com");
		customer.setPassword("asd");
		Assert.assertEquals(true, customerBo.login(customer));
		/*Assert.assertNotNull(customerBo.getCurrentCustomer());
		Assert.assertEquals(1, customerBo.getCurrentCustomer().getQuickOrders().size());
		Assert.assertEquals(MealFormat.QUICK, customerBo.getCurrentCustomer().getQuickOrders().get(0).getMealFormat());*/
		//Assert.assertNotNull(customerBo.getCurrentCustomer().getDailyContent());
	}
	
	@Test
	public void testScheduledOrder() {
		Customer customer = new Customer();
		customer.setId(20);
		Meal meal = new Meal();
		meal.setId(10);
		CustomerOrder scheduled = new CustomerOrder();
		scheduled.setMealType(MealType.DINNER);
		scheduled.setDate(new Date());
		scheduled.setAddress("Navi peth");
		scheduled.setArea("Navi peth");
		scheduled.setCustomer(customer);
		scheduled.setMeal(meal);
		List<CustomerOrder> scheduledOrders = new ArrayList<CustomerOrder>();
		scheduledOrders.add(scheduled);
		//Assert.assertEquals("OK", customerBo.scheduledOrder(scheduledOrders));
	}
	
	@Test
	public void testChangeScheduledOrder() {
		Customer customer = new Customer();
		customer.setId(4);
		CustomerOrder scheduled = new CustomerOrder();
		Meal meal = new Meal();
		meal.setId(8);
		scheduled.setMeal(meal);
		scheduled.setCustomer(customer);
		Assert.assertEquals("OK", customerBo.changeScheduledOrder(scheduled));
	}
	
	@Test
	public void testCancelScheduledOrder() {
		Customer customer = new Customer();
		customer.setId(20);
		CustomerOrder scheduled = new CustomerOrder();
		Meal meal = new Meal();
		meal.setId(10);
		scheduled.setMeal(meal);
		scheduled.setCustomer(customer);
		scheduled.setId(38);
		Assert.assertEquals("OK", customerBo.cancelScheduledOrder(scheduled));
	}
	
	@Test
	public void testAddMoneyToWallet() {
		Customer customer = new Customer();
		customer.setId(20);
		customerBo.setCurrentCustomer(customer);
		customer.setBalance(new BigDecimal(1100));
		Assert.assertEquals("OK", customerBo.addMoneyToWallet(customer));
	}
	
	@Test
	public void testGetPreviousOrders() {
		System.out.println(customerBo.getCustomerMealDao().getQuickOrdersByDate(7,new Date()).size());
	}
	
	@Test
	public void testAddRating() {
		CustomerOrder order = new CustomerOrder();
		Meal meal = new Meal();
		Vendor vendor = new Vendor();
		vendor.setId(10);
		meal.setId(13);
		meal.setVendor(vendor);
		order.setMeal(meal);
		customerBo.rateMeal(order, new BigDecimal(3));
	}
	
	/*@Test
	public void testCalculateDistance() {
		Location location = new Location();
		location.setLat(new BigDecimal(18.511636));
		location.setLng(new BigDecimal(73.84344299999998));
		Vendor vendor = new Vendor();
		Location vendorLoc= new Location();
		vendorLoc.setLat(new BigDecimal(18.5122825));
		vendorLoc.setLng(new BigDecimal(73.84336819999999));
		vendor.setLocation(vendorLoc);
		System.out.println("Distance:" + CommonUtil.distance(location, vendor, "K"));
	}*/
	
	@Test
	public void testCalculateDistance() throws JsonSyntaxException, MalformedURLException, IOException {
		System.out.println(GoogleUtil.getDistanceBetweenLocations("Alka Theatre, Sadashiv Peth, Pune, Maharashtra, India", "Hinjewadi Phase II, Pimpri-Chinchwad, Maharashtra, India"));
	}
}
