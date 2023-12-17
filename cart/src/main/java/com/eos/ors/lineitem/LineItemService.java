package main.java.com.eos.ors.lineitem;

import java.util.List;
import java.util.Optional;

import main.java.com.eos.ors.util.AppConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.com.eos.ors.exception.CartNotFoundException;

@Service
public class LineItemService {

	
	@Autowired
	LineItemRepository lineItemRepository;
	
	public LineItem createLineItem(LineItem lineItem) {
        return lineItemRepository.save(lineItem);
    }

    public LineItem updateLineItem(Long id, LineItem updatedlineItem) {
        Optional<LineItem> optionalItem = lineItemRepository.findById(id);
        if (optionalItem.isPresent()) {
        	updatedlineItem.setId(id);
            return lineItemRepository.save(updatedlineItem);
        }
        throw new CartNotFoundException(AppConstant.NO_DATA_FOUND_ERROR_MESSAGE);
    } 

    public LineItem getLineItemById(Long id) {
        return lineItemRepository.findById(id).orElse(null);
    }
    
   
    public List<LineItem> getAllLineItems() {
        return lineItemRepository.findAll();
    }

    public void deleteLineItemById(Long id) {
    	lineItemRepository.deleteById(id);
    }



}
