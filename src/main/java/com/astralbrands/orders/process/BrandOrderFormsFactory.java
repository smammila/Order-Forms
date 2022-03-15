package com.astralbrands.orders.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandOrderFormsFactory {
	
	@Autowired
	private AloutteOrderProcessor aloutteOrderProcessor;
	
	@Autowired
	private CosmedixOrderProcessor cosmedixOrderProcessor;

	public BrandOrderForms getBrandOrderForms(String site) {
		if ("EXPO".equals(site)) {
			return cosmedixOrderProcessor;
		}
		return aloutteOrderProcessor;
	}

}
