package main.java.com.eos.ors.cart;

import java.util.List;
import java.util.Optional;

import main.java.com.eos.ors.exception.CartNotFoundException;
import main.java.com.eos.ors.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

	
	@Autowired
	CartRepository cartRepository;
	
	public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart updateCart(Long id, Cart updatedCart) {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isPresent()) {
            updatedCart.setId(id);
            return cartRepository.save(updatedCart);
        }
        throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }
    
    public Cart updateCartByCustomerId(Long customerId, Cart updatedCart) {
        Cart cart = cartRepository.findByCustomerId(customerId).orElse(null);
        if (cart!=null) {
        	updatedCart.setId(cart.getId());
        	updatedCart.setCustomerId(customerId);
            return cartRepository.save(updatedCart);
        }
        throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }
    
    public Cart getCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId).orElse(null);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public void deleteCartById(Long id) {
    	cartRepository.deleteById(id);
    }



}
