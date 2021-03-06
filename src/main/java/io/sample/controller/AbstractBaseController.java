package io.sample.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

public abstract class AbstractBaseController {

	final Locale LOCALE = Locale.JAPAN;
	final Logger logger = LoggerFactory.getLogger(AbstractBaseController.class);

	private String getFileName(String fileName, String extend) {

		StringBuffer sb = new StringBuffer();
		String [] arrayFileName = fileName.split("\\.");
		if(arrayFileName.length < 2) {
			sb.append(fileName).append(".").append(extend);
			fileName = sb.toString();
		}

		return fileName;
	}

	public void handleFileDownload(String fileName, String extend, byte[] byteOut, HttpServletResponse response) throws IOException {

		fileName = this.getFileName(fileName, extend);

		response.setHeader("Content-Length", String.valueOf(byteOut.length));
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream");
        OutputStream os = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(byteOut);
        int n = -1;
        while((n = in.read(byteOut)) > 0) {
        	os.write(byteOut, 0, n);
        }
        in.close();
        os.flush();
        os.close();
	}

	public void handleFileSave(String fileName, String extend, byte[] byteOut, String path) throws IOException {

		fileName = this.getFileName(fileName, extend);

		File file = new File(path + fileName);
		logger.info("saving path = " + file.getPath());
		OutputStream os = new FileOutputStream(file);
        InputStream in = new ByteArrayInputStream(byteOut);
        int n = -1;
        while((n = in.read(byteOut)) > 0) {
        	os.write(byteOut, 0, n);
        }
        in.close();
        os.flush();
        os.close();
	}

	public void handleValidator(List<ObjectError> errorList) throws IOException {

        for(int i=0; i< errorList.size();i++) {
            ObjectError error = errorList.get(i);
            logger.error("DefaultMessage = " + error.getDefaultMessage());
            logger.error("ObjectName = " + error.getObjectName());

            Object[] codes = error.getCodes();
            for(int m=0; m < codes.length; m++) {
            	String str = (String) codes[m];
            	logger.error("codes = " + str);
            }
        }
	}

	public Map<String, String> handleErrorMessages(List<ObjectError> errorList) throws IOException {

		Map<String, String> mapErrorMsg = new HashMap<String, String>();

		for (Object objectError : errorList) {
		    FieldError fieldError = (FieldError) objectError;
            mapErrorMsg.put(fieldError.getField(), fieldError.getDefaultMessage());
			logger.error("field name = " + fieldError.getField());
            logger.error("error message = " + fieldError.getDefaultMessage());
        }

		return mapErrorMsg;
	}

	public boolean handleIphone(HttpSession session) throws Exception {

		Boolean blnIphone = (Boolean)session.getAttribute("blnIphone");

		return blnIphone;
	}

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e, HttpServletResponse response, 
			HttpServletRequest request) throws IOException {

		Enumeration<?> en = (Enumeration<?>) request.getParameterNames();

		int i = 0;
        while(en.hasMoreElements()) {
        	i++;
            String name = (String)en.nextElement();
            String value = request.getParameter(name);
            logger.error("Exception:" + i + " parameter is name=" + name + ", value=" + value);
        }
        
        logger.error("Exception's trace:", e);

		response.setContentLength(0);
		response.setStatus(500);
	}

	@ExceptionHandler(SQLException.class)
	public void handleException(SQLException e, HttpServletResponse response, 
			HttpServletRequest request) throws IOException {

		Enumeration<?> en = (Enumeration<?>) request.getParameterNames();

		int i = 0;
        while(en.hasMoreElements()) {
        	i++;
            String name = (String)en.nextElement();
            String value = request.getParameter(name);
            logger.error("SQLException:" + i + " parameter is name=" + name + ", value=" + value);
        }

		response.setContentLength(0);
		response.setStatus(500);
	}

}