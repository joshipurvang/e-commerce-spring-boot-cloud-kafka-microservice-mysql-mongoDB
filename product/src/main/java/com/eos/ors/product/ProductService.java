package com.eos.ors.product;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eos.ors.exception.ProductNotFoundException;



@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        }
        throw new ProductNotFoundException("Requested product not found in db !!!");
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

}
