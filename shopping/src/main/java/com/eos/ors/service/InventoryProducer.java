package com.eos.ors.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.eos.ors.model.Product;
import com.eos.ors.util.AppConstant;
@Component
public class InventoryProducer {
	
	@Autowired
	KafkaTemplate<Long,Product> kafkaTemplate;
	
	public void sendProductToKafkaTopic(Product product) {
		kafkaTemplate.send(AppConstant.INVENTORY_TOPIC,product.getId(),product);
	}

}
