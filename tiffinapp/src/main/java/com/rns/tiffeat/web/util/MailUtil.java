package com.rns.tiffeat.web.util;

import java.util.Properties;

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

public class MailUtil {

	private static final String MAIL_NEW_LINE = "\n";
	private static final String MAIL_PORT = "25";
	private static final String MAIL_HOST = "115.124.124.220";
	private static final String MAIL_AUTH = "true";
	private static final String MAIL_PASSWORD = "support_tiffeat";
	private static final String MAIL_ID = "support@tiffeat.com";
	public static final String SENDER_ID = "v=spf1 a ptr ip4:115.124.124.220 ~all";

	public static void sendMail(CustomerOrder order) {

		if (order == null || order.getMeal() == null || order.getCustomer() == null
				|| StringUtils.isEmpty(order.getCustomer().getEmail())) {
			return;
		}

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

		try {

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(MAIL_ID));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(order.getCustomer().getEmail()));
			message.setSubject("Thank you for ordering tiffin...");

			if (MealFormat.SCHEDULED.equals(order.getMealFormat())) {
				message.setText(prepareScheduledMailContent(order));
			} else {
				message.setText(prepareQuickOrderMailContent(order));
			}

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private static String prepareScheduledMailContent(CustomerOrder order) {
		StringBuilder mailBuilder = new StringBuilder();
		if (order.getMealType() == null) {
			return mailBuilder.toString();
		}
		mailBuilder.append("Greetings ").append(order.getCustomer().getName()).append("!").append(MAIL_NEW_LINE);
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

	private static String prepareQuickOrderMailContent(CustomerOrder order) {
		StringBuilder mailBuilder = new StringBuilder();
		if (order.getMealType() == null) {
			return mailBuilder.toString();
		}
		mailBuilder.append("Hello ").append(order.getCustomer().getName()).append("!").append(MAIL_NEW_LINE);
		mailBuilder.append("You have ordered ").append(order.getMeal().getTitle()).append(" for ")
				.append(order.getMealType().getDescription()).append(" for the date ")
				.append(CommonUtil.convertDateToString(order.getDate())).append(MAIL_NEW_LINE)
				.append("Your order ID is - ").append(order.getId()).append(MAIL_NEW_LINE)
				.append("You can track your order status from www.tiffeat.com").append(MAIL_NEW_LINE)
				.append("Thank you for ordering tiffin from us!!").append(MAIL_NEW_LINE)
				.append("For any queries please feel free to contact us at support@tiffeat.com or 8087538194");

		return mailBuilder.toString();
	}

}
