package com.shopify.jaxbproviders;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

public class ShopifySdkJacksonFeature implements Feature {

	@Override
	public boolean configure(final FeatureContext context) {
		context.register(ShopifySdkJsonProvider.class, MessageBodyReader.class, MessageBodyWriter.class);
		return true;
	}
}