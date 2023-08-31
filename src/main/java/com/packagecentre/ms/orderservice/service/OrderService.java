package com.packagecentre.ms.orderservice.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.packagecentre.ms.orderservice.controller.OrderServiceController;
import com.packagecentre.ms.orderservice.jpa.dto.ItemAddress;
import com.packagecentre.ms.orderservice.jpa.entity.Order;
import com.packagecentre.ms.orderservice.jpa.repository.OrdersJPARepository;
import com.packagecentre.ms.orderservice.utils.OrderConstants;

@Transactional
@Service
public class OrderService {
	
	static Logger logger = Logger.getLogger(OrderService.class.getName());

	
	@Autowired
	private OrdersJPARepository ordJPARepo;
	
	@Autowired
	KafkaTemplate<String, List<ItemAddress>> kafkaTemplate; //worked fine with list of items
	
	public void createOrder(List<ItemAddress> itemAddrList, int custID) throws InterruptedException, Exception {
		
				
		//save the orders received
		List<Order> orders = saveOrderedItems(itemAddrList);
		
		
		try {
			publishToKafka(itemAddrList);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			//update order status as failed if Kafka exception occurs after multiple tryouts
			logger.info("Order svc Kafka publish failure --- revert ORDER creation");
			for(Order ord: orders) {
				ordJPARepo.updateOrderStatusPkgID("ORDER CREATION FAILED", LocalDateTime.now(), ord.getOrderID(), ord.getStatus());
			}
		}
		
				
		
	}

	/**
	 * save orders in DB
	 * @param itemAddrList
	 * @return saved orders
	 */
	private List<Order> saveOrderedItems(List<ItemAddress> itemAddrList) {
		System.out.println("entered saveOrderedItems :"+ itemAddrList.size());
		
		List<Order> ordList = new ArrayList();
		for(ItemAddress itemaddr : itemAddrList) {
			//create order object from incoming items and create order list
			Order ord = new Order( itemaddr.getOrderID(), itemaddr.getCustID(), itemaddr.getAddrID(), "", itemaddr.getQuantity(), itemaddr.getTotal(), "CREATED", itemaddr.getItemID());
			
			ord.setOrderStatusDate(LocalDateTime.now());
			
			ordList.add(ord);
		}
		
		System.out.println("List of orders :"+ ordList.size());
		
				
		ordJPARepo.saveAll(ordList);//save all orders in single step
		
		System.out.println("orders created in DB");
		
		return ordList;
	}
	
	private void publishToKafka(List<ItemAddress> itemAddrList) throws Exception {
		
		String PKG_TOPIC = OrderConstants.PKG_TOPIC;		
		
		System.out.println("Publish to Kafka topic: "+ PKG_TOPIC );
		
		kafkaTemplate.send(PKG_TOPIC,  itemAddrList);//commented to test header publishing
	
		
	}
	
	
	
}
