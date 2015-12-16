package com.resoneuronance.tiffinapp.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.DailyContent;
import com.rns.tiffeat.web.bo.domain.MealType;
import com.rns.tiffeat.web.bo.impl.VendorBoImpl;
import com.rns.tiffeat.web.dao.domain.Meal;
import com.rns.tiffeat.web.dao.domain.Vendor;
import com.rns.tiffeat.web.dao.impl.CustomerDaoImpl;
import com.rns.tiffeat.web.dao.impl.CustomerMealDaoImpl;
import com.rns.tiffeat.web.dao.impl.DailyMealDaoImpl;
import com.rns.tiffeat.web.dao.impl.MealDaoImpl;
import com.rns.tiffeat.web.dao.impl.OrderDaoImpl;
import com.rns.tiffeat.web.dao.impl.VendorDaoImpl;

public class TiffinDBTest {

	private VendorDaoImpl vendorDao;
	private MealDaoImpl mealDao;
	private VendorBoImpl vendorBo;

	@Ignore
	@Before
	public void before() {
		/*Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
		// builds a session factory from the service registry*/
		SessionFactory sessionFactory = createSessionFactory();
		// obtains the session
		vendorDao = new VendorDaoImpl();
		mealDao = new MealDaoImpl();
		vendorBo = new VendorBoImpl();
		vendorDao.setSessionFactory(sessionFactory);
		mealDao.setSessionFactory(sessionFactory);
		vendorBo.setVendorDao(vendorDao);
		DailyMealDaoImpl dailyMealDao = new DailyMealDaoImpl();
		dailyMealDao.setSessionFactory(sessionFactory);
		vendorBo.setDailyMealDao(dailyMealDao);
		vendorBo.setMealDao(mealDao);
		CustomerMealDaoImpl customerMealDao = new CustomerMealDaoImpl();
		customerMealDao.setSessionFactory(sessionFactory);
		vendorBo.setCustomerMealDao(customerMealDao);
		OrderDaoImpl orderDao = new OrderDaoImpl();
		orderDao.setSessionFactory(sessionFactory);
		vendorBo.setOrderDao(orderDao);
		CustomerDaoImpl customerDao = new CustomerDaoImpl();
		customerDao.setSessionFactory(sessionFactory);
		//vendorBo.setCustomerDao(customerDao);
	}
	
	private SessionFactory createSessionFactory() {
		Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
		return sessionFactory;
	}
	
	@Test
	public void testAddVendorWithMeals() {
		Vendor vendor = new Vendor();
		vendor.setName("AngatPangat");
		vendor.setEmail("angatPangat@gmail.com");
		vendor.setPhone("020-12312323");
		vendor.setAddress("Kothrud, Pune 52");
		List<Meal> meals = new ArrayList<Meal>();
		Meal meal = new Meal();
		meal.setTitle("Non Veg Combo");
		meal.setDescription("Kadak Kombdi");
		meal.setType(MealType.LUNCH.name());
		meal.setVendor(vendor);
		meals.add(meal);
		vendor.setMeals(meals);
		vendorDao.addVendor(vendor);
	}
	
	@Test
	public void testAddVendorWithoutMeals() {
		Vendor vendor = new Vendor();
		vendor.setName("Kulkarnis Special");
		vendor.setEmail("kulkarnis@gmail.com");
		vendor.setPhone("020-12312323");
		vendor.setAddress("Navi peth, Pune 30");
		vendorDao.addVendor(vendor);
	}
	
	@Test
	public void testGetAllVendors() {
		Assert.assertEquals(1, vendorDao.getAllVendors().size());
		Assert.assertEquals(1, vendorDao.getAllVendors().get(0).getMeals().size());
	}
	
	@Test
	public void testLogin() {
		com.rns.tiffeat.web.bo.domain.Vendor vendor = new com.rns.tiffeat.web.bo.domain.Vendor();
		vendor.setEmail("angatPangat@gmail.com");
		Assert.assertEquals(true, vendorBo.loginWithEmail(vendor));
		Assert.assertEquals(1, vendor.getMeals().size());
	}
	
	@Test
	public void registerVendor() {
		com.rns.tiffeat.web.bo.domain.Vendor vendor = new com.rns.tiffeat.web.bo.domain.Vendor();
		vendor.setName("Kulkarni");
		vendor.setEmail("asd@gmail.com");
		vendor.setPhone("020-12312323");
		vendor.setAddress("Kothrud, Pune 52");
		vendor.setPinCode("411030");
		Assert.assertEquals("OK",vendorBo.register(vendor));
	}
	
	@Test
	public void testUpdateVendor() {
		Vendor vendor = vendorDao.getAllVendors().get(0);
		//vendor.setName("The Angat Pangat");
		//vendor.setLocationX(new BigDecimal(1));
		//vendor.setLocationY(new BigDecimal(1));
		vendor.setPinCode("411030");
		vendorDao.updateVendor(vendor);
	}
	
	@Test
	public void deleteVendor() {
		Vendor vendor = vendorDao.getAllVendors().get(0);
		//System.out.println(vendor.getMeals().size());
		//Assert.assertNotNull(mealDao.getMeal(new Long(3)));
		vendorDao.deleteVendor(vendor);
		//Assert.assertNull(mealDao.getMeal(new Long(3)));
	}
	
	@Test
	public void testUpdateMeal() {
		Vendor vendor = vendorDao.getAllVendors().get(0);
		com.rns.tiffeat.web.bo.domain.Meal meal = new com.rns.tiffeat.web.bo.domain.Meal();
		meal.setId(3);
		meal.setTitle("Non Veg Special");
		meal.setPrice(new BigDecimal(55));
		com.rns.tiffeat.web.bo.domain.Vendor currentVendor = new com.rns.tiffeat.web.bo.domain.Vendor();
		currentVendor.setId(vendor.getId());
		currentVendor.setEmail(vendor.getEmail());
		vendorBo.setCurrentVendor(currentVendor);
		//vendorBo.updateMeal(meal);
	}
	
	@Test
	public void testAddMeal() {
		Vendor vendor = vendorDao.getAllVendors().get(0);
		com.rns.tiffeat.web.bo.domain.Meal meal = new com.rns.tiffeat.web.bo.domain.Meal();
		meal.setTitle("Non Veg Special");
		meal.setPrice(new BigDecimal(50));
		com.rns.tiffeat.web.bo.domain.Vendor currentVendor = new com.rns.tiffeat.web.bo.domain.Vendor();
		currentVendor.setId(vendor.getId());
		currentVendor.setEmail(vendor.getEmail());
		vendorBo.setCurrentVendor(currentVendor);
		vendorBo.addMeal(meal, null);
	}
	
	@Test
	public void getMeal() {
		Meal meal = mealDao.getMeal(new Long(9));
		Assert.assertNotNull(meal.getVendor());
		Assert.assertNotNull(meal.getVendor().getName());
	}
	
	@Test
	public void testDeleteMeal() {
		com.rns.tiffeat.web.bo.domain.Meal meal = new com.rns.tiffeat.web.bo.domain.Meal();
		meal.setId(3);
		/*com.resoneuronance.tiffinapp.bo.domain.Vendor currentVendor = new com.resoneuronance.tiffinapp.bo.domain.Vendor();
		Vendor vendor = vendorDao.getAllVendors().get(0);
		currentVendor.setEmail(vendor.getEmail());
		vendorBo.setCurrentVendor(currentVendor);*/
		vendorBo.deleteMeal(meal);
	}
	
	@Test
	public void testAddDailyContent() {
		DailyContent content = new DailyContent();
		content.setDate(new Date());
		content.setMainItem("Veg Kabaab");
		content.setMealType(MealType.DINNER);
		content.setSubItem1("tandoor Roti");
		com.rns.tiffeat.web.bo.domain.Meal mealBusiness = new com.rns.tiffeat.web.bo.domain.Meal();
		mealBusiness.setId(10);
		content.setMeal(mealBusiness);
		Assert.assertEquals("OK", vendorBo.addDailyContent(content));
		//Assert.assertEquals("OK", vendorBo.generateCurrentOrder(mealDao.getMeal(content.getMeal().getId()), content.getMealType()));
	}
	
	@Test
	public void testUpdateDailyContent() {
		DailyContent content = new DailyContent();
		content.setId(2);
		content.setDate(new Date());
		content.setMainItem("Kombadi special");
		content.setMealType(MealType.DINNER);
		content.setSubItem1("poli");
		Meal meal = mealDao.getMeal(new Long(3));
		com.rns.tiffeat.web.bo.domain.Meal mealBusiness = new com.rns.tiffeat.web.bo.domain.Meal();
		mealBusiness.setId(meal.getId());
		content.setMeal(mealBusiness);
		Assert.assertEquals("OK",vendorBo.updateDailyContent(content));
	}
	
	
	/*@Test
	public void getMealsByDate() {
		
		Vendor vendor = new Vendor();
		vendor.setId(new Long(1));
		Assert.assertEquals(3, vendorBo.getDailyMealsByDate(vendor , new Date()).size());
	}*/
	
	@Test
	public void testSendMail() {
		final String username = "support@tiffeat.com";
		final String password = "support_tiffeat";
		
		/*final String username = "ajinkyashiva@gmail.com";
		final String password = "acknash#1491";*/

		Properties props = new Properties();
		/*props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "cpanel.vlitevps.com");
		props.put("mail.smtp.port", "465");*/
		
		/*props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");*/

		props.put("mail.smtp.auth", "true");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "support.tiffeat.com");
		//props.put("mail.smtp.host", "cpanel.vlitevps.com");
		//props.put("mail.smtp.host", "mail.tiffeat.com");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			/*message.setFrom(new InternetAddress("support@tiffeat.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("ajinkyashiva@gmail.com"));
			message.setSubject("Thank you for ordering tiffin...");
			message.setText("Thank you for ordering tiffin from us!");*/
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("ajinkyashiva@gmail.com"));
			message.setSubject("Thank you for ordering tiffin...");
			message.setText("Thank you for ordering tiffin from us!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
