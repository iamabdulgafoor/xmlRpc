package com.ericsson.xmlrpcserver.handlers;

import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.AbstractReflectiveHandlerMapping;
import org.springframework.util.Assert;

public class SpringHandlerMapping extends AbstractReflectiveHandlerMapping {

	public void setHandlerMappings(Map<String, IHandler> handlerMappings) throws XmlRpcException {
		SpringRequestProcessorFactoryFactory ff = (SpringRequestProcessorFactoryFactory) getRequestProcessorFactoryFactory();
		Assert.notNull(ff, "RequestProcessorFactoryFactory must be set");
		ff.init(handlerMappings);
		for (String serviceName : handlerMappings.keySet()) {
			IHandler serviceBean = handlerMappings.get(serviceName);
			registerPublicMethods(serviceName, serviceBean.getClass());
		}
	}

}
