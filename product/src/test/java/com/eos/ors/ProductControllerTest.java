package com.eos.ors;

import com.eos.ors.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductControllerTest {
	

	
 	@Autowired
	private WebTestClient webClient;
	
	@Test
	void createProductTest() {
		Product product=Product.builder().id(20000l).name("product-test").price(7).build();
		webClient.post()
        .uri("api/products")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(product), Product.class)
        .exchange()
        .expectStatus().isCreated();
	}
	
	
	@Test
	void getProductByIdNotFoundTest() {
		webClient
		  .get()
		  .uri("api/products/{id}",200l)
		  .exchange()
		  .expectStatus()
		  .isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	@Test
	void deleteProductByIdTest() {
		webClient
		  .delete()
		  .uri("api/products/{id}",20000l)
		  .exchange()
		  .expectStatus()
		  .isEqualTo(HttpStatus.NO_CONTENT);
	}
}
