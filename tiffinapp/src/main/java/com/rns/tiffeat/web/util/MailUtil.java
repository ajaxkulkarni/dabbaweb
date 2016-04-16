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
	//private static final String MAIL_NEW_LINE = "\n";
	private static final String MAIL_PORT = "25";
	private static final String MAIL_HOST = "115.124.124.220";
	private static final String MAIL_AUTH = "true";
	private static final String MAIL_PASSWORD = "support_tiffeat";
	private static final String MAIL_ID = "support@tiffeat.com";
	public static final String SENDER_ID = "v=spf1 a ptr ip4:115.124.124.220 ~all";
	public static final String MAIL_TYPE_QUICK = "quick";
	public static final String MAIL_TYPE_SCHEDULED = "scheduled";
	public static final String MAIL_TYPE_ACTIVATION = "activation";
	public static final String MAIL_TYPE_WALLET = "wallet";
	private static final String WALLET_MAIL_SUBJECT = "Money Added to TiffEat Wallet!";
	
	private CustomerOrder order;
	private String type;
	
	public MailUtil(CustomerOrder customerOrder, String mailType) {
		this.order = customerOrder;
		this.type = mailType;
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
			prepareMailContent(message, order);
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


	private String prepareMailContent(Message message, CustomerOrder order) {

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
			result = StringUtils.replace(result, "${amount}", String.valueOf(order.getPrice()));
			CommonUtil.calculateTiffinsRemaining(order.getCustomer());
			result = StringUtils.replace(result, "${noOfTiffins}", String.valueOf(order.getCustomer().getNoOfTiffinsRemaining()));
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
		sendMail(order);
	}

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
		if(MAIL_TYPE_ACTIVATION.equals(type)) {
			contentPath = "activation_email.html";
			message.setSubject(ACTIVATION_MAIL_SUBJECT);
		} else if(MAIL_TYPE_SCHEDULED.equals(type)) {
			contentPath = "scheduled_email.html";
		} else if (MAIL_TYPE_WALLET.equals(type)) {
			contentPath = "add_to_wallet_mail.html";
			message.setSubject(WALLET_MAIL_SUBJECT);
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
