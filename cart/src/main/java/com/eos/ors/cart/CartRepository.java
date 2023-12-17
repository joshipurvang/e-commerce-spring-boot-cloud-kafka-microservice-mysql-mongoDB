package main.java.com.eos.ors.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findByCustomerId(Long customerId);

}
