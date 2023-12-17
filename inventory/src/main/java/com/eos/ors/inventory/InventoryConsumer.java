package com.eos.ors.inventory;

import com.eos.ors.model.Product;
import com.eos.ors.util.AppConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

	@Autowired
	InventoryRepository repository;

	@KafkaListener(topics = AppConstant.INVENTORY_TOPIC)
	public void updateInventory(ConsumerRecord<Long, Product> productRecord) {
        Product product = productRecord.value();
		Inventory inventory = repository.findByProductId(product.getId());
		if (inventory != null) {
			inventory.setQty(product.getQty() + inventory.getQty());
			repository.save(inventory);
		} else {
			repository.save(Inventory.builder().productId(product.getId()).qty(product.getQty()).build());
		}
	}
	

}
