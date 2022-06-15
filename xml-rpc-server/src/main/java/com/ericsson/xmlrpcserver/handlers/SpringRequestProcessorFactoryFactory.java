package com.ericsson.xmlrpcserver.handlers;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory.StatelessProcessorFactoryFactory;

public class SpringRequestProcessorFactoryFactory extends StatelessProcessorFactoryFactory
		implements RequestProcessorFactoryFactory {

	Map<Class<? extends IHandler>, IHandler> classHandlerMappings;

	protected void init(Map<String, IHandler> handlerMappings) {
		classHandlerMappings = new HashMap<Class<? extends IHandler>, IHandler>();
		for (String key : handlerMappings.keySet()) {
			IHandler handler = handlerMappings.get(key);
			Class<? extends IHandler> clazz = handler.getClass();
			classHandlerMappings.put(clazz, handler);
		}
	}

	@Override
	public RequestProcessorFactory getRequestProcessorFactory(Class clazz) throws XmlRpcException {
		final IHandler handler = classHandlerMappings.get(clazz);
		return new RequestProcessorFactory() {
			public Object getRequestProcessor(XmlRpcRequest pRequest) throws XmlRpcException {
				return handler;
			}
		};
	}

}
