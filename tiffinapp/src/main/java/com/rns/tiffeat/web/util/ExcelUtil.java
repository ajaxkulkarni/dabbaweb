package com.rns.tiffeat.web.util;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.OrderStatus;

public class ExcelUtil {

	private static final String EXCEL_FONT_ARIAL = "Arial";
	private static final String EXCEL_SHEET_ORDERS = "Orders";

	public static void prepareOrdersExcel(HSSFWorkbook workbook, List<CustomerOrder> customerOrders, OrderStatus status) {

		if (CollectionUtils.isEmpty(customerOrders)) {
			return;
		}

		HSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_ORDERS);
		sheet.setDefaultColumnWidth(30);

		// create style for header cells
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName(EXCEL_FONT_ARIAL);
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		// create header row
		HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("Id");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Name");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Email");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Phone");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Area");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Address");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Date");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("Format");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Price");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Meal");
		header.getCell(9).setCellStyle(style);

		header.createCell(10).setCellValue("Meal Code");
		header.getCell(10).setCellStyle(style);

		header.createCell(11).setCellValue("Vendor");
		header.getCell(11).setCellStyle(style);

		header.createCell(12).setCellValue("Time");
		header.getCell(12).setCellStyle(style);

		header.createCell(13).setCellValue("Payment Type");
		header.getCell(13).setCellStyle(style);

		header.createCell(14).setCellValue("Status");
		header.getCell(14).setCellStyle(style);

		int rowCount = 1;

		for (CustomerOrder order : customerOrders) {
			if (status == null || status.equals(order.getStatus())) {
				HSSFRow aRow = sheet.createRow(rowCount++);
				aRow.createCell(0).setCellValue(order.getId());
				if (order.getCustomer() != null) {
					aRow.createCell(1).setCellValue(order.getCustomer().getName());
					aRow.createCell(2).setCellValue(order.getCustomer().getEmail());
					aRow.createCell(3).setCellValue(order.getCustomer().getPhone());
				}
				aRow.createCell(4).setCellValue(order.getArea());
				aRow.createCell(5).setCellValue(order.getAddress());
				aRow.createCell(6).setCellValue(order.getDate());
				if (order.getMealFormat() != null) {
					aRow.createCell(7).setCellValue(order.getMealFormat().toString());
				}
				if (order.getMeal() != null) {
					if (order.getMeal().getPrice() != null) {
						aRow.createCell(8).setCellValue(order.getMeal().getPrice().toString());
					}
					aRow.createCell(9).setCellValue(order.getMeal().getTitle());
					aRow.createCell(10).setCellValue(order.getMeal().getId());
					if (order.getMeal().getVendor() != null) {
						aRow.createCell(11).setCellValue(order.getMeal().getVendor().getName());
					}
				}
				if (order.getMealType() != null) {
					aRow.createCell(12).setCellValue(order.getMealType().name());
				}
				if (order.getPaymentType() != null) {
					aRow.createCell(13).setCellValue(order.getPaymentType().name());
				}
				if (order.getStatus() != null) {
					aRow.createCell(14).setCellValue(order.getStatus().name());
				}
			}
		}

	}

}
