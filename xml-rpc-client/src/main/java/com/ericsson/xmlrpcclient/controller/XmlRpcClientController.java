package com.ericsson.xmlrpcclient.controller;

import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class XmlRpcClientController {

	@Autowired(required = false)
	private Boolean enabledForExceptions = true;
	@Autowired(required = false)
	private Boolean enabledForExtensions = true;

	@Autowired(required = false)
	private String encoding = XmlRpcClientConfigImpl.UTF8_ENCODING;
	
	
	private XmlRpcClient xmlRpcClient;
	
	@PostConstruct
	protected void init() throws Exception {

		org.apache.xmlrpc.client.XmlRpcClientConfigImpl xmlRpcClientconfig = new org.apache.xmlrpc.client.XmlRpcClientConfigImpl();

		xmlRpcClientconfig.setServerURL(new URL("http://127.0.0.1:6789/services/xmlrpcserver"));
		xmlRpcClientconfig.setEnabledForExtensions(enabledForExtensions);
		xmlRpcClientconfig.setEnabledForExceptions(enabledForExceptions);
		xmlRpcClientconfig.setBasicEncoding(encoding);

		xmlRpcClientconfig.setConnectionTimeout(60 * 1000);
		xmlRpcClientconfig.setReplyTimeout(60 * 1000);

		xmlRpcClient = new XmlRpcClient();

		xmlRpcClient.setTransportFactory(new XmlRpcCommonsTransportFactory(xmlRpcClient));

		xmlRpcClient.setConfig(xmlRpcClientconfig);

		// public int insert(String id, String title, String body)
		
        // Object[] params = new Object[]{ "1", "one", "one-body"};
		// String result = (Integer) xmlRpcClient.execute("publisher.invokeServerMethod", params);
        
        log.info("Executing the client request==>START");
		String result = (String) xmlRpcClient.execute("publisher.invokeServerMethod", Collections.singletonList("Hi from RPC-Client"));
		 
		log.info("result==>",result);
		log.info("Executed the client request==>End");

	}
	
	/*
	 * @RequestMapping("/client") public void sendRequest() {
	 * 
	 * }
	 */
	
	
}