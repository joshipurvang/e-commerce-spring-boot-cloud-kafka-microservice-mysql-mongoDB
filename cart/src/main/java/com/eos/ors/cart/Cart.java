package main.java.com.eos.ors.cart;

import java.util.List;

import main.java.com.eos.ors.lineitem.LineItem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private long customerId;
	
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cl_fid", referencedColumnName = "id")
	private List<LineItem> lineItem;
	
	

}
