package com.packagecentre.ms.orderservice.controller;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.packagecentre.ms.orderservice.beans.OrderAppStatus;
import com.packagecentre.ms.orderservice.config.Configuration;
import com.packagecentre.ms.orderservice.jpa.dto.ItemAddress;
import com.packagecentre.ms.orderservice.service.OrderService;

@RestController

public class OrderServiceController {
	

	static Logger logger = Logger.getLogger(OrderServiceController.class.getName());
	
	@Autowired
	private Configuration conf;
	
	@Autowired
	private OrderService ordSvc;
	
	@GetMapping("/order-svc-check")
	public OrderAppStatus checkOrderSvcHlth() {
		logger.info("appName from ppty -->"+ conf.getAppName());
	
		
		return new OrderAppStatus("OK", new Date());
	}
	
	
	@PostMapping("/orders/{custID}")
	@HystrixCommand(fallbackMethod="createOrderFallback")
	public ResponseEntity<Object> createOrder(@RequestBody List<ItemAddress> itemAddrList, @PathVariable Integer custID) {
		logger.info("List size: "+ itemAddrList.size());
		logger.info("Customer ID:" + custID);
		
		
		try {
			ordSvc.createOrder(itemAddrList, custID);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		logger.info("Order created");

		return ResponseEntity.created(null).build();
		
		
	}
	
	//Hystrix fallback API
	public ResponseEntity<Object> createOrderFallback(List<ItemAddress> itemAddrList,Integer custID) {
		
		return ResponseEntity.internalServerError().build();
		
	}
	
	
}
