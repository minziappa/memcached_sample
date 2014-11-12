package io.sample.service.impl;

import static org.junit.Assert.*;

import java.util.List;

import io.sample.bean.SampleBean;
import io.sample.bean.para.InsertUserPara;
import io.sample.bean.para.SelectUserPara;
import io.sample.service.SampleService;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class SampleServiceImplTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void testInsertSample() throws Exception {
		assertTrue();
	}

	@Test
	public void testSelectSample() throws Exception {
		assertNotNull();
	}

	@Test
	public void testDeleteSample() throws Exception {
		assertTrue();
	}

}