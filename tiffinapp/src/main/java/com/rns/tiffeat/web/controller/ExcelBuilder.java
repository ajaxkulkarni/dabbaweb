package com.rns.tiffeat.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.rns.tiffeat.web.bo.domain.CustomerOrder;
import com.rns.tiffeat.web.bo.domain.OrderStatus;
import com.rns.tiffeat.web.util.Constants;
import com.rns.tiffeat.web.util.ExcelUtil;

public class ExcelBuilder extends AbstractExcelView implements Constants {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<CustomerOrder> orders = (List<CustomerOrder>) model.get(MODEL_ORDERS);
		OrderStatus status = (OrderStatus) model.get(MODEL_ORDER_STATUS);
		ExcelUtil.prepareOrdersExcel(workbook, orders, status);
	}


}
