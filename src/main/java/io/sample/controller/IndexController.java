package io.sample.controller;

import io.sample.bean.para.CommonPara;
import io.sample.service.TokenService;
import io.sample.utility.DateUtility;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * The <code>IndexController</code> class represents action controller.
 * 1. Explain for a method .....
 * 
 * @author  Kim Joon
 * @version 0.1, 14/10/17
 * @see     io.sample.controller.IndexController#index()
 * @since   JDK1.7
 */
@Controller
public class IndexController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private MessageSource message;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = {"/", "", "index"})
	public String index() throws Exception {
		return "index";
	}

	@RequestMapping(value = {"oneToken"}, method=RequestMethod.POST)
	public String oneToken(@ModelAttribute("commonPara")CommonPara commonPara, 
							ModelMap model, 
							HttpServletRequest request) throws Exception {

		String id = (String) request.getAttribute("id");

		if (id == null) {
			id = commonPara.getUserUuId();
		}

		Date afterOneHourDate = DateUtility.getMinuteOfHourMoveTime(DateUtility.getNowTime(), 10);
		// tokened digit number is 12
		String oneToken = tokenService.saveToken(id, afterOneHourDate);

		model.addAttribute("oneToken", oneToken);

		return "oneToken";
	}

}