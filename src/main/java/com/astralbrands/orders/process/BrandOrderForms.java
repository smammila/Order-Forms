package com.astralbrands.orders.process;

import org.apache.camel.Exchange;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

public interface BrandOrderForms {

	public void process(Exchange exchange, String site, String[] fileName);

	default String getData(Cell cell) {
		return new DataFormatter().formatCellValue(cell);
	}
}
