package com.astralbrands.orders.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.astralbrands.orders.constants.AppConstants;

@Component
public class ReadXslxFileProcessor implements Processor, AppConstants {
	
	@Autowired
	private BrandOrderFormsFactory orderFormsFactory;

	Logger log = LoggerFactory.getLogger(ReadXslxFileProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		String fileName = exchange.getIn().getHeader("CamelFileName", String.class);
		if (fileName == null) {
			exchange.setProperty(IS_DATA_PRESENT, false);
			return;
		}
		log.info("exchange  " + fileName);
		String[] fileNameData = fileName.split(UNDERSCORE);
		if (fileNameData.length < 3) {
			log.error("Invalid file name :" + fileName + ", File name should be customerId_date_site.xls");
			throw new RuntimeException("Invalid File name");
		}
		String site = fileName.substring(fileName.lastIndexOf(UNDERSCORE) + 1, fileName.indexOf(DOT));
		BrandOrderForms orderFormProcessor = orderFormsFactory.getBrandOrderForms(site);
		orderFormProcessor.process(exchange, site, fileNameData);
	}


}
