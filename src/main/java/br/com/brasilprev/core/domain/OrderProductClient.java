package br.com.brasilprev.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.brasilprev.core.util.IDomain;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDER_PRODUCT_CLIENT")
public class OrderProductClient implements Cloneable, IDomain, Serializable{

	private static final long serialVersionUID = -7922840665806454456L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="ID_ORDER", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name="ID_CLIENT", nullable = false)
	private Client client;
	
	@ManyToOne
	@JoinColumn(name="ID_PRODUCT", nullable = false)
	private Product product;

	@Column(name="AMOUNT", nullable = false)
	private Integer amount;

	@Column(name="PRICE", nullable = false)
	private Double price;
	
	@Override
    public OrderProductClient clone() throws CloneNotSupportedException {
        return (OrderProductClient) super.clone();
    }

}
