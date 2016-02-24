package com.rns.tiffeat.web.util;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public interface Constants {

	Map<String, String> AREAS = Collections.unmodifiableMap(new HashMap<String, String>(){{ 
        put("411030", "Sadashiv Peth");
        put("411052", "Budhwar peth");
        put("411004", "Deccan");
        put("411007", "Ganeshkhind");
        put("411005", "Shivajinagar");
        put("411053", "Shivajinagar Housing");
    }});
	
	String PAYMENT_PREFIX = "T";
	String HOST_URL = "localhost:8080/tiffeat-web";
	String RESULT_URL_QUICK = "http://"+ HOST_URL + "/paymentResult";
	String PRODUCT_INFO = "Tiffeat";
	String MERCHANT_KEY = "gtKFFx"; // DEV
	//String MERCHANT_KEY = "SvGyqj";
	String MERCHANT_SALT = "eCwWELxi"; // DEV
	//String MERCHANT_SALT = "3pfnUWXj";
	String ROOT_DIRECTORY = "F:/Resoneuronance/TiffEat/Data/Images/";
	//String ROOT_DIRECTORY = "/home/tiffeat/Images/";
	String ASSETS_ROOT = "/resources"; //DEV
	//String ASSETS_ROOT = "../../resources/assets";
	String PAYMENT_URL = "https://test.payu.in/_payment"; //DEV
	//String PAYMENT_URL = "https://secure.payu.in/_payment";
	
	/*String PAYMENT_PREFIX = "T";
	String HOST_URL = "www.tiffeat.com";
	String RESULT_URL_QUICK = "http://"+ HOST_URL + "/paymentResult";
	String PRODUCT_INFO = "Tiffeat";
	String MERCHANT_KEY = "SvGyqj";
	String MERCHANT_SALT = "3pfnUWXj";
	String ROOT_DIRECTORY = "F:/Resoneuronance/TiffEat/Data/Images/";
	String ASSETS_ROOT = "../../resources/assets";
	String PAYMENT_URL = "https://secure.payu.in/_payment";*/
	
	//Imp : Above fields affect the deployment
	
	String DATE_FORMAT = "yyyy-MM-dd";
	
	String ERROR_DAILYCONTENT_DOES_NOT_EXIST = "This dailycontent does not exist!";
	String ERROR_INVALID_MEAL_DETAILS = "Invalid Meal Details or meal does not exist!";
	String ERROR_PHONE_NUMBER_ALREADY_PRESENT = "Phone Number already present!";
	String RESPONSE_OK = "OK";
	String ERROR_EMAIL_ADDRESS_ALREADY_PRESENT = "Email Address already present!";
	String ERROR_INVALID_USER_DETAILS = "Invalid User Details!";
	String ERROR_MEAL_NOT_AVAILABLE_PLEASE_CHECK_AGAIN = "Meal not available!Please check again!";
	String ERROR_INVALID_ORDER_DETAILS = "Invalid order details!";
	String ERROR_INVALID_CUSTOMER_DETAILS = "Invalid customer details!";
	String ERROR_CAN_T_CANCEL_THE_MEAL = "Cannot cancel the meal now as vendors already started cooking the meals!";
	String ERROR_CANT_CHANGE_THE_MEAL = "Cannot change the meal now as vendors already started cooking the meals!";
	String ERROR_UPLOADING_IMAGE = "Error uploading Image!";
	String ERROR_INVALID_VENDOR_DETAILS = "Invalid Vendor details!";
	String ERROR_ALERADY_SCHEDULED_MEAL_TYPE = "Can't scheduled this Meal!You already have a meal scheduled for this timing!";
	String ERROR_ALERADY_SCHEDULED = "Can't schedule this meal!You already have lunch and dinner scheduled!";
	String ERROR_NO_TIFFINS_AVAILABLE = "No Tiffins are currently available in this area.";
	String ERROR_INVALID_ADDRESS_OR_LOCATION = "Invalid address or location!";
	String ERROR_INVALID_CREDENTIALS = "Invalid credentials!";
	String ERROR_MEAL_NOT_AVAILABLE_FOR_THIS_TIMING = "Meal not available for this timing!Please select different meal!";
	String ERROR_VENDOR_NOT_AVAILABLE = "This Tiffin vendor is currently not available!";
	String ERROR_MENU_NOT_AVAILABLE_YET = "Menu not available yet ..";
	
	
	String WARNING_DATE_CHANGED = "Date changed";
	
	String GOOGLE_CLIENT_ID = "915740607723-g31injd928voehja1gj3gf8hd14dm8bo.apps.googleusercontent.com";
	String GOOGLE_CLIENT_SECRET = "44rD8Uvi-hcCV742H47H2Idl";
	String GOOGLE_GET_USER_DATA_URL = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
	String GOOGLE_ACCESS_TOKEN = "access_token";
	String GOOGLE_GET_ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
	String GOOGLE_GET_CODE_URL = "https://accounts.google.com/o/oauth2/auth?" +
			  "scope=email"
			+ "&redirect_uri=http://"+ HOST_URL + "/getGoogleCode"
			+ "&response_type=code"
			+ "&client_id=" + GOOGLE_CLIENT_ID
			+ "&approval_prompt=force";
	
	String GOOGLE_ACCESS_TOKEN_URL_PARAMS = "&client_id=" + GOOGLE_CLIENT_ID
            				+ "&client_secret=" + GOOGLE_CLIENT_SECRET
            				+ "&redirect_uri=http://" + HOST_URL + "/getGoogleCode"
            				+ "&grant_type=authorization_code";
	
	
	String GOOGLE_DISTANCE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?";
	
	String MODEL_MEAL = "meal";
	String MODEL_RESULT = "result";
	String MODEL_CUSTOMER = "customer";
	String MODEL_MEAL_TYPE = "mealType";
	String MODEL_MEAL_FORMAT = "mealFormat";
	String MODEL_PAYMENT_TYPES = "paymentTypes";
	String MODEL_CUSTOMER_ORDER = "customerOrder";
	String MODEL_VENDOR = "vendor";
	String MODEL_PIN_CODE = "pinCode";
	String MODEL_VENDORS = "vendors";
	String MODEL_AREAS = "areas";
	String MODEL_PAY_U_DETAILS = "payUDetails";
	String MODEL_SELECTED_AREA_ENTRY = "selectedArea";
	String MODEL_ORDERS = "orders";
	String MODEL_DAILY_CONTENT = "dailyContent";
	String MEAL_ID = "mealId";
	String EXCEL_VIEW = "excelView";
	String MODEL_ORDER_STATUSES = "orderStatus";
	String MODEL_ORDER_STATUS = "status";
	String MODEL_ADMIN = "admin";
	String MODEL_RESOURCES = "resources";
	String MODEL_VENDOR_STATUS = "vendorStatus";
	String MODEL_LOCATION = "location";
	String MODEL_ERROR = "errorMsg";
	String MODEL_MEALS = "meals";
	String MODEL_CUSTOMERS = "customers";
	String MODEL_INVOICES = "invoices";
	String MODEL_ADDRESS = "address";
	
	String DAY_TOMORROW = "tomorrow";
	String PARAMETER_APPENDER = "&";
	String SPACE_CHAR = "%20";
	BigDecimal MAX_DISTANCE_IN_METERS = new BigDecimal(10000);
	String ADMIN_PASSWORD = "admin123";
	String ADMIN_USERNAME = "admin";
	BigDecimal VENDOR_COMMISSION = new BigDecimal(0.7);
	BigDecimal SCHEDULED_ORDER_DISCOUNT = new BigDecimal(0.8);
	
	String PAYMENT_RESULT_SUCCESS = "success";
	String PAYMENT_MODE = "mode";
	String PAYMENT_STATUS = "status";
	String URL_PREFIX = "/";
	
	String CHECK_LOGGED_IN_URL_GET = "checkRegistration.htm";
	String GET_VENDOR_MEALS_URL_GET = "getVendorMeals.htm";
	String MAKE_NEW_ORDER_URL_GET = "makeNewOrder.htm";
	String GET_GOOGLE_CODE_URL_GET = "getGoogleCode";
	String CONTACT_US_URL_GET = "contactUs.htm";
	String ABOUT_US_URL_GET = "aboutUs.htm";
	String TERMS_URL_GET = "terms.htm";
	String LOGOUT_URL_GET = "logout.htm";
	String SCHEDULED_ORDERS_URL_GET = "scheduledOrders.htm";
	String CUSTOMER_LOGIN_URL_GET = "customerLogin.htm";
	String ADD_MONEY_TO_WALLET_URL_GET = "addMoneyToWallet.htm";
	String PAYMENT_ANDROID_RESULT_GET_URL = "paymentAndroidResult.htm?result=";
	String PAYMENT_URL_GET = "payment.htm";
	String QUICK_ORDERS_HOME_URL_GET = "quickOrders.htm";
	String QUICK_ORDER_URL_GET = "quickOrder.htm";
	String REGISTER_CUSTOMER_URL_GET = "registerCustomer.htm";
	String CUSTOMER_HOME_URL_GET = "customerHome.htm";
	String SELECT_MEAL_FORMAT_URL_GET = "selectMealFormat.htm";
	String CHANGE_ORDER_URL_GET = "changeOrder.htm";
	String SCHEDULED_ORDER_URL_GET = "scheduledOrder.htm";
	String ADD_ORDER_URL_GET = "addOrder.htm";
	String INDEX_URL_GET = "home.htm";
	
	String REGISTER_CUSTOMER_URL_POST = "registerCustomer";
	String QUICK_ORDER_URL_POST = "quickOrder";
	String PAYMENT_RESULT_URL_POST = "paymentResult";
	String SELECT_MEAL_FORMAT_URL_POST = "selectMealFormat";
	String CUSTOMER_SELECT_VENDOR_URL_POST = "customerSelectVendor";
	String GET_NEARBY_VENDORS_URL_POST = "getNearbyVendors";
	String GET_NEARBY_VENDORS_FOR_CHANGE_ORDER_URL_POST = "getNearbyVendorsForChangeOrder";
	String GET_NEARBY_VENDORS_FOR_ADD_ORDER_URL_POST = "getNearbyVendorsForAddOrder";
	String CHANGE_ORDER_URL_POST = "changeOrder";
	String CUSTOMER_LOGIN_URL_POST = "customerLogin";
	String GET_MENU_URL_POST = "getMenu";
	String ADD_SCHEDULED_ORDER_URL_POST = "addScheduledOrder";
	String REPEAT_ORDER_URL_POST = "repeatOrder";
	String CHANGE_MEAL_URL_POST = "changeMeal";
	String CANCEL_ORDER_URL_POST = "cancelOrder";
	String ADD_MONEY_TO_WALLET_URL_POST = "addMoneyToWallet";
	String SCHEDULED_ORDER_URL_POST = "scheduledOrder";
	String ADD_LUNCH_DINNER_URL_POST = "addLunchOrDinner";
	
	String QUICK_HOME_PAGE = "customer_quick_home";
	String SCHEDULED_HOME_PAGE = "customer_scheduled_home";
	String ADD_TO_WALLET_PAGE = "customer_add_to_wallet";
	String CUSTOMER_LOGIN_PAGE = "customer_login";
	String PAYMENT_PAGE = "customer_payment";
	String REGISTER_PAGE = "customer_register";
	String ERROR_PAGE = "error";
	String QUICK_ORDER_DETAILS_PAGE = "customer_registered_quick_order_details";
	String SCHEDULED_ORDER_DETAILS_PAGE = "customer_scheduled_order_details";
	String SELECT_MEAL_FORMAT_PAGE = "customer_meal_format";
	String CHANGE_SCHEDULED_ORDER_DETAILS_PAGE = "customer_change_scheduled_order_details";
	String ADD_SCHEDULED_ORDER_DETAILS_PAGE = "customer_add_scheduled_order";
	String INDEX_PAGE = "index";
	String SELECT_MEALS_PAGE = "customer_select_meals";
	String CONTACT_US_PAGE = "customer_contact_us";
	String ABOUT_US_PAGE = "customer_about_us";
	String TERMS_PAGE = "customer_terms";
	
	
	//5123456789012346
	//quc1iGQZKN4HD
	//export JAVA_HOME=/opt/jdk1.7.0_79
	// /etc/init.d/easy-tomcat7 restart
	//Ticket number : YGR-166-61065
	// Jar -xvf warname.war
}
