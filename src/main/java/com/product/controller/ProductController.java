package com.product.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.product.model.Product;
import com.product.model.ProductResponse;
import com.product.model.UserBean;
import com.product.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.product.service.ProductService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/productlist/v1")
public class ProductController {
	
	@Autowired
	ProductService productService;

	@Autowired
	ReportService reportService;

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

	@GetMapping("/generateReport/{format}")
	public String generateReport(@PathVariable String format) throws JRException, FileNotFoundException {
		return reportService.exportReport(format);
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> download() throws IOException, JRException {

//		reportService.exportReport("pdf");

		File file = new File("products.pdf");
		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", file.getName()));
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.valueOf(mimeType))
				.body(resource);
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
