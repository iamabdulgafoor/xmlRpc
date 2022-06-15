package com.ericsson.xmlrpcserver.handlers;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("publisher")
public class PublishHandler implements IHandler {

	public int insert(String id, String title, String body) {
		log.info("inserting [" + id + "," + title + "," + body + "]");
		return 0;
	}

	public int update(String id, String title, String body) {
		log.info("updating [" + id + "," + title + "," + body + "]");
		return 0;
	}

	public int delete(String id, String title, String body) {
		log.info("deleting [" + id + "," + title + "," + body + "]");
		return 0;
	}
	
	public String invokeServerMethod(String str) {
		log.info(str);
		return str+" Server";
	}
}
