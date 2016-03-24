package com.rns.tiffeat.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.MealFormat;

public class MailUtil implements Runnable {

	private static final String ACTIVATION_MAIL_SUBJECT = "Just A few steps away from becoming a TiffEater...";
	private static final String ORDER_MAIL_SUBJECT = "Thank you for ordering tiffin...";
	private static final String MAIL_NEW_LINE = "\n";
	private static final String MAIL_PORT = "25";
	private static final String MAIL_HOST = "115.124.124.220";
	private static final String MAIL_AUTH = "true";
	private static final String MAIL_PASSWORD = "support_tiffeat";
	private static final String MAIL_ID = "support@tiffeat.com";
	public static final String SENDER_ID = "v=spf1 a ptr ip4:115.124.124.220 ~all";
	
	private CustomerOrder order;
	
	public MailUtil(CustomerOrder customerOrder) {
		this.order = customerOrder;
	}

	public void sendMail(CustomerOrder order) {

		if (order == null || order.getCustomer() == null || StringUtils.isEmpty(order.getCustomer().getEmail())) {
			return;
		}
		Session session = prepareMailSession();
		
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(MAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(order.getCustomer().getEmail()));
			
			//String msgText = "";
			//if (MealFormat.SCHEDULED.equals(order.getMealFormat())) {
			//	msgText = prepareScheduledMailContent(order);
			//} else {
			prepareMailContent(message, order);
			//}
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private static Session prepareMailSession() {
		Properties props = new Properties();

		props.put("mail.smtp.auth", MAIL_AUTH);
		// props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", MAIL_HOST);
		props.put("mail.smtp.port", MAIL_PORT);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(MAIL_ID, MAIL_PASSWORD);
			}
		});
		return session;
	}

	private String prepareScheduledMailContent(CustomerOrder order) {
		StringBuilder mailBuilder = new StringBuilder();
		if (order.getMealType() == null) {
			return mailBuilder.toString();
		}
		mailBuilder.append("<h1>Greetings ").append(order.getCustomer().getName()).append("</h1>").append(MAIL_NEW_LINE);
		mailBuilder.append("You have scheduled ").append(order.getMeal().getTitle()).append(" for ").append(order.getMealType().getDescription())
				.append(" starting from ").append(CommonUtil.convertDateToString(order.getDate())).append(MAIL_NEW_LINE)
				.append("Your order ID is - ").append(order.getId()).append(MAIL_NEW_LINE)
				.append("You can track your order status everyday from www.tiffeat.com").append(MAIL_NEW_LINE)
				.append("You can also choose to change or cancel your tiffin everyday as you wish, also track your everyday menu, all from www.tiffeat.com").append(MAIL_NEW_LINE)
				.append("You will need to have sufficient amount of money in your wallet in order to get tiffin everyday.").append(MAIL_NEW_LINE)
				.append("Thank you for ordering tiffin from us!!").append(MAIL_NEW_LINE)
				.append("For any queries please feel free to contact us at support@tiffeat.com or 8087538194");

		
		
		return mailBuilder.toString();
	}

	private String prepareMailContent(Message message, CustomerOrder order) {
		/*StringBuilder mailBuilder = new StringBuilder();
		if (order.getMealType() == null) {
			return mailBuilder.toString();
		}
		mailBuilder.append("<h1>Hello ").append(order.getCustomer().getName()).append("!</h1>").append(MAIL_NEW_LINE);
		mailBuilder.append("You have ordered ").append(order.getQuantity()).append(" ").append(order.getMeal().getTitle()).append(" for ")
				.append(order.getMealType().getDescription()).append(" for the date ")
				.append(CommonUtil.convertDateToString(order.getDate())).append(MAIL_NEW_LINE)
				.append("Your order ID is - ").append(order.getId()).append(MAIL_NEW_LINE)
				.append("The total payable amount is :").append(CommonUtil.calculatePrice(order)).append(MAIL_NEW_LINE)
				.append("You can track your order status from www.tiffeat.com").append(MAIL_NEW_LINE)
				.append("Thank you for ordering your meal from us!!").append(MAIL_NEW_LINE)
				.append("For any queries please feel free to contact us at support@tiffeat.com or 8087538194").append(MAIL_NEW_LINE).append(MAIL_NEW_LINE)
				.append("We would love it if you like us on Facebook at: https://www.facebook.com/tiffeat/").append(MAIL_NEW_LINE)
				.append("<i>We often post something that you will find interesting.</i>");*/

		try {
			String result = readMailContent(message, order);
			result = StringUtils.replace(result, "${username}", order.getCustomer().getName());
			result = StringUtils.replace(result, "${email}", order.getCustomer().getEmail());
			result = StringUtils.replace(result, "${phone}", order.getCustomer().getPhone());
			if(order.getMeal() != null) {
				result = StringUtils.replace(result, "${mealTitle}", order.getMeal().getTitle());
				if(order.getMeal().getVendor() != null) {
					result = StringUtils.replace(result, "${vendorName}", order.getMeal().getVendor().getName());
				}
			}
			result = StringUtils.replace(result, "${orderId}", String.valueOf(order.getId()));
			if(order.getMealType() != null) {
				result = StringUtils.replace(result, "${mealType}", order.getMealType().getDescription());
			}
			result = StringUtils.replace(result, "${orderDate}", CommonUtil.convertDateToString(order.getDate()));
			result = StringUtils.replace(result, "${price}", String.valueOf(CommonUtil.calculatePrice(order)));
			if(MealFormat.SCHEDULED.equals(order.getMealFormat())) {
				result = StringUtils.replace(result, "${price}", String.valueOf(order.getMeal().getPrice()));
			}
			result = StringUtils.replace(result, "${quantity}", String.valueOf(order.getQuantity()));
			if(order.getPaymentType() != null) {
				result = StringUtils.replace(result, "${paymentType}", order.getPaymentType().getDescription());
			}
			result = StringUtils.replace(result, "${address}", order.getAddress());
			if(order.getLocation() != null) {
				result = StringUtils.replace(result, "${location}", order.getLocation().getAddress());
			}
			BigDecimal balance = order.getCustomer().getBalance();
			result = StringUtils.replace(result, "${balance}", String.valueOf(balance));
			if(balance == null) {
				result = StringUtils.replace(result, "${balance}", "0");
			}
			result = StringUtils.replace(result, "${link}", prepareActivationMailContent(order));
			message.setContent(result, "text/html");
			return result;
			
		} catch (FileNotFoundException e) {
		} catch (MessagingException e) {
		}
		
		return "";
	}

	public void run() {
		if(order == null) {
			return;
		}
		/*if (order.getCustomer()!=null && order.getCustomer().getId() == 0) {
			sendActivationMail(order);
		} else {*/
		sendMail(order);
		//}
	}

	/*private void sendActivationMail(CustomerOrder order2) {
		Session session = prepareMailSession();
		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(MAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(order.getCustomer().getEmail()));
			message.setSubject(ACTIVATION_MAIL_SUBJECT);
			message.setText(prepareActivationMailContent(order));
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}*/

	private String prepareActivationMailContent(CustomerOrder order) {
		StringBuilder builder = new StringBuilder();
		builder.append("http://").append(Constants.HOST_URL).append(Constants.URL_PREFIX).append(Constants.NEW_ACTIVATION_URL_GET).append("?")
				.append(Constants.MODEL_CUSTOMER).append("=").append(order.getCustomer().getEmail()).append("&")
				.append(Constants.MODEL_ACTIVATION_CODE).append("=").append(order.getCustomer().getActivationCode());
		return builder.toString();
	}
	
	private String readMailContent(Message message, CustomerOrder order) throws FileNotFoundException, MessagingException {
		ClassLoader classLoader = getClass().getClassLoader();
		String contentPath = "invoice_mail.html";
		message.setSubject(ORDER_MAIL_SUBJECT);
		if(order.getCustomer()!= null && order.getCustomer().getId() == 0) {
			contentPath = "activation_email.html";
			message.setSubject(ACTIVATION_MAIL_SUBJECT);
		}
		else if(MealFormat.SCHEDULED.equals(order.getMealFormat())) {
			contentPath = "scheduled_email.html";
		}
		File file = new File(classLoader.getResource(contentPath).getFile());
		Scanner scanner = new Scanner(file);
		StringBuilder result = new StringBuilder();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result.append(line).append("\n");
		}

		scanner.close();
			
		return result.toString();
	}

}
