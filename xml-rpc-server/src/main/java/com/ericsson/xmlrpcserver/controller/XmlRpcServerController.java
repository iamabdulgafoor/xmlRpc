package com.ericsson.xmlrpcserver.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.XmlRpcErrorLogger;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.XmlRpcServletServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.xmlrpcserver.handlers.IHandler;
import com.ericsson.xmlrpcserver.handlers.SpringHandlerMapping;
import com.ericsson.xmlrpcserver.handlers.SpringRequestProcessorFactoryFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class XmlRpcServerController {

	@Autowired(required=false)
	private String encoding = XmlRpcServerConfigImpl.UTF8_ENCODING;
	@Autowired(required=false)
	private Boolean enabledForExceptions = true;
	@Autowired(required=false)
	private Boolean enabledForExtensions = true;
	@Autowired(required=false)
	private int maxThreads = 1;	
	@Autowired
	private Map<String, IHandler> handlers;

	private XmlRpcServletServer xmlRpcServer;

	@PostConstruct
	protected void init() throws Exception {
		XmlRpcServerConfigImpl config = new XmlRpcServerConfigImpl();
		config.setBasicEncoding(encoding);
		config.setEnabledForExceptions(enabledForExceptions);
		config.setEnabledForExtensions(enabledForExtensions);

		xmlRpcServer = new XmlRpcServletServer();
		xmlRpcServer.setConfig(config);
		xmlRpcServer.setErrorLogger(new XmlRpcErrorLogger());
		xmlRpcServer.setMaxThreads(maxThreads);

//	    PropertyHandlerMapping handlerMapping = new PropertyHandlerMapping();
//	    for (String key : handlers.keySet()) {
//	      handlerMapping.addHandler(key, handlers.get(key).getClass());
//	    }
		SpringHandlerMapping handlerMapping = new SpringHandlerMapping();
		handlerMapping.setRequestProcessorFactoryFactory(new SpringRequestProcessorFactoryFactory());
		handlerMapping.setHandlerMappings(handlers);

		xmlRpcServer.setHandlerMapping(handlerMapping);
	}

	@RequestMapping(value = "/services/xmlrpcserver", method = RequestMethod.POST)
	public void serve(R request, S response) throws XmlRpcException {		
		
		log.info("<======inside the serve method======>");
		
		try {
			 // xmlRpcServer.execute(request, response);
			xmlRpcServer.execute(request.getMethodName(), request.getParams());
						
		} catch (Exception e) {			
			log.error("XmlRpcException-- "+e.getMessage(), e);
			throw new XmlRpcException(e.getMessage(), e);
		}
	}

	
}
