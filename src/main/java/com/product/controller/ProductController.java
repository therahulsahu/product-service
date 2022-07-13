package com.product.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.client.EmailNotificationClient;
import com.product.client.ReportGenerationClient;
import com.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/productlist/v1")
public class ProductController {

	private static final String UPLOADED_FOLDER = "/Users/rahul.b.sahu/dev/demo-project/product-service/uploads";
	@Autowired
	ProductService productService;

	@Autowired
	ReportGenerationClient reportGenerationClient;

	@Autowired
	EmailNotificationClient emailNotificationClient;

	@GetMapping("/getlist")
	public List<Product> getlist() {
		System.out.println("Get list called");
		return productService.getProductList();
	}

	@PostMapping("/createproduct")
	public Map<String, String> createProduct(@RequestBody List<Product> productRequest) {
		System.out.println(">>>>>productRequest--->>>>" + productRequest);
		HashMap<String, String> map = new HashMap<>();
		boolean res = productService.createProducts(productRequest);
		if(res) {
			map.put("response", "Successfully product created");
			EmailDetails emailDetails = new EmailDetails();
			emailDetails.setRecipient("therahulsahu7@gmail.com");
			emailDetails.setSubject("Product Spring App - New Product Created");
			emailDetails.setMsgBody("A new product - " + productRequest.get(0).getProductName() + " has been created.");
			Smsrequest sms = new Smsrequest("+917000571622", "Hello, A new Product has been created");
			try{
				emailNotificationClient.sendMail(emailDetails);
				System.out.println("email sent");
				emailNotificationClient.sendMessage(sms);
				System.out.println("message sent");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("Email Notification service might be down !");
			}
		} else {
			map.put("response", "Products not created");
		}
		return map;
	}
	
	@PostMapping("/deleteproduct")
	public ProductResponse deleteproduct(@RequestBody List<Product> productRequest) {
		System.out.println("Start 2>>>>>In newlistController::deleteproduct()>>>>>>>>>>>>");
		System.out.println("Before Size of product List" + productService.getProductsCount());
		ProductResponse response = new ProductResponse();
		
		boolean res = productService.deleteProducts(productRequest);
		
		if(res) {
			response.setStatusCode("200");
			response.setStatusMessage("Successfully Deleted");
		} else {
			response.setStatusCode("401");
			response.setStatusMessage("Not Deleted");
		}
		return response;
	}

	@PostMapping("/updateProduct")
	public ProductResponse updateProduct(@RequestBody Product product) {
		boolean res = productService.updateProduct(product);
		ProductResponse response = new ProductResponse();
		if(res) {
			response.setStatusCode("200");
			response.setStatusMessage("Successfully Updated");
		} else {
			response.setStatusCode("401");
			response.setStatusMessage("Not Updated");
		}
		return response;
	}

	@PostMapping("/upload")
	public String singleFileUpload(@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			return "no file selected";
		}
		try {
			Path path = Paths.get(UPLOADED_FOLDER);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
			Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "uploaded successfully";
	}
	
	@PostMapping("/login")
	public UserBean login(@RequestBody UserBean userBean) {
		System.out.println("Start 2 In HelloWorldController::login()>>>>>>>>>>>>");
		
		if ((userBean.getUserName().equals("abc") && userBean.getUserPassword().equals("abc"))) {
			System.out.println("User is login succesful ");
			userBean.setErrorCode("200");
			// navigate to welcome screen where it shows product page with links or tabs
			return userBean;
		} else {
			System.out.println("User is login faild due to invalid credientials ");
			userBean.setErrorCode("400");
			// show same login page
			return userBean;
		}
	}

	@GetMapping("/download/pdf")
	public ResponseEntity<Resource> downloadPdf(HttpServletResponse res) {
		return reportGenerationClient.downloadPdf();
	}

	@GetMapping("/download/excel")
	public void downloadExcel(HttpServletResponse res) {
		reportGenerationClient.exportToExcelAndDownload();
	}

	@GetMapping("/download/csv")
	public void downloadCsv(HttpServletResponse res) {
		reportGenerationClient.exportToCSV();
	}

	// single product
//	@PostMapping("/pro/{id}")
//	public product getlist(@pathVariable("id")int id) {
//		return productList.getListById(id);
//	}
//	
//	
//	@PostMapping("/pro/{proid}")
//	public List deleteproduct(@pathvariable("proid") int proId)
//	{
//		this.productList.deleteproduct(proId);
//	}

}
