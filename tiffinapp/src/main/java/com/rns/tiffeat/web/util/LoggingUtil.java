package com.rns.tiffeat.web.util;

//import org.apache.log4j.Logger;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;

public class LoggingUtil {

	//private static Logger reportLogger = Logger.getLogger(LoggingUtil.class);

	public static void logMessage(String message) {
		//reportLogger.info(message);
	}

	public static void logOrderDetails(CustomerOrder order) {
		if (order == null || order.getCustomer() == null) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("Order For :" + order.getCustomer().getId());
		if (order.getMeal() != null) {
			builder.append(" Meal :" + order.getMeal().getTitle());
		}
		//reportLogger.info(builder);
	}

}
