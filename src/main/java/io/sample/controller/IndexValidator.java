package io.sample.controller;

import io.sample.bean.message.MyMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IndexValidator implements Validator {

	final Logger logger = LoggerFactory.getLogger(IndexValidator.class);

	public boolean supports(Class<?> clazz) {
		if(MyMessage.class.equals(clazz)) {
			return true;
		}else {
			// Though It is not target, execute the controller.
			return true;
		}
	}

	public void validate(Object object, Errors errors) {

	}

}