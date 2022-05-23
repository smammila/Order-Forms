package com.astralbrands.orders.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandOrderFormsFactory {
	
	@Autowired
	private AloutteOrderProcessor aloutteOrderProcessor;
	
	@Autowired
	private CosmedixOrderProcessor cosmedixOrderProcessor;
	
	@Autowired
	private CommerceHubProcessor commerceHubProcessor;
	

	public BrandOrderForms getBrandOrderForms(String site) {
		if ("COS".equals(site)) {
			return cosmedixOrderProcessor;
		}
		else if("HUB".equalsIgnoreCase(site)) {
			return commerceHubProcessor;
		}
		return aloutteOrderProcessor;
	}

}
