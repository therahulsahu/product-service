package com.product.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.Product;
import com.product.model.UserBean;

@SpringBootTest
public class ProductControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	UserBean userBean;
	
	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		userBean = new UserBean();
		userBean.setUserName("abc");
		userBean.setUserPassword("abc");
	}
		
	
	@Test
	void mockRequest() throws Exception {
		mockMvc.perform(get("/api/productlist/v1/getlist").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void getAllTest() throws Exception {	
		MvcResult response = mockMvc.perform(get("/api/productlist/v1/getlist").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String resultContent = response.getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		List<Product> list = objectMapper.readValue(resultContent, List.class);
//		assertNotNull(list);
		assertTrue(list.size() >= 0);
	}
	
	@Test
	void loginTest_successful() throws Exception {
		MvcResult response = mockMvc.perform(post("/api/productlist/v1/login").content(objectMapper.writeValueAsString(userBean)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = response.getResponse().getContentAsString();
		UserBean actualUserBean = objectMapper.readValue(resultContent, UserBean.class);
		assertEquals("200", actualUserBean.getErrorCode());
	}
	
	@Test
	void loginTest_fail() throws Exception {
		userBean.setUserName("def");
		MvcResult response = mockMvc.perform(post("/api/productlist/v1/login").content(objectMapper.writeValueAsString(userBean)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String resultContent = response.getResponse().getContentAsString();
		UserBean actualUserBean = objectMapper.readValue(resultContent, UserBean.class);
		assertEquals("400", actualUserBean.getErrorCode());
	}

	
	@Test
	void createProductTest() throws Exception {
		// creating a sample product
		Product product1 = new Product();
		product1.setProductId("1");
		product1.setProductName("ApplePhone");
		product1.setProductDesc("Apple is best phone");
		product1.setProductPrice("100000");
		product1.setProductQuantity("30");
		product1.setProductType("Mobile");
		product1.setProductmul("new mobile");
		product1.setProductUpload("");
		product1.setChecked(false);
		
		List<Product> contentBody = List.of(product1);
		MvcResult response = mockMvc.perform(post("/api/productlist/v1/createproduct").content(objectMapper.writeValueAsString(contentBody)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		@SuppressWarnings("unchecked")
		Map<String, String> res = objectMapper.readValue(response.getResponse().getContentAsString(), Map.class);
		assertEquals("Successfully product created", res.get("response"));
	}
	
	@Test
	void testDeleteProductSuccessful() throws Exception {
		
		List<Product> deleteProductList = new ArrayList<>();
		
		Product product3 = new Product();
		product3.setProductId("2");
		product3.setProductName("1+ ");
		product3.setProductDesc("best phone");
		product3.setProductPrice("50000");
		product3.setProductQuantity("50");
		product3.setProductType("Mobile");
		product3.setProductmul("new mobile");
		product3.setProductUpload("");
		product3.setChecked(false);
		
		deleteProductList.add(product3);
		
		MvcResult response = mockMvc.perform(post("/api/productlist/v1/deleteproduct").content(objectMapper.writeValueAsString(deleteProductList)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		@SuppressWarnings("unchecked")
		Map<String, String> res = objectMapper.readValue(response.getResponse().getContentAsString(), Map.class);
		
		assertEquals("Succesfully Product Deleted", res.get("response"));
	}
}
