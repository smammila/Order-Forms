package com.astralbrands.orders.router;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.astralbrands.orders.process.CsvFileProcessor;
import com.astralbrands.orders.process.EmailProcessor;
import com.astralbrands.orders.process.ReadXslxFileProcessor;

@Component
public class AppRouter extends RouteBuilder {

	Logger log = LoggerFactory.getLogger(AppRouter.class);

	@Autowired
	private ReadXslxFileProcessor readXslxFileProcessor;

	@Autowired
	private EmailProcessor emailProcessor;

	@Autowired
	private CsvFileProcessor csvFileProcessor;

	@Override
	public void configure() throws Exception {
		log.info("route configuration started ");
		
		onException(Exception.class).log("Exception while processing order detail, please check log for more details")
				.end();
		from("file:{{direct-deposit.input.file-path}}?delete=false&delay={{input.process.delay}}&moveFailed={{direct-deposit.error.file.path}}")
				.id("inputRouter").log("Read EXCEL file ${exchange}").process(readXslxFileProcessor).choice().when()
				.exchangeProperty("isDataPresent").to("direct:exportFile").to("direct:preparedCsvFile").otherwise()
				.log("file is already processed or empty").endChoice().end();
		from("direct:preparedCsvFile").id("direct:preparedCsvFile").log("preparedCsvFile").process(csvFileProcessor)
				.to("direct:exportFile").to("direct:sendNotification").end();
		from("direct:exportFile").id("exportFile").log("export file : ${header('CamelFileName')}")
				.to("file:{{direct-deposit.output.file-path}}").end();

		from("direct:sendNotification").id("sendNotification").log("send Notification")//.process(emailProcessor)
				.log("email notification sent").end();
	}

}
