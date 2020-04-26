package br.com.brasilprev.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.brasilprev.core.util.IDomain;
import lombok.Data;

@Entity
@Data
@Table(name = "PRODUCT")
public class Product implements Cloneable, IDomain, Serializable{

	private static final long serialVersionUID = -7922840665806454456L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true)
	private Long id;
	
	@Column(name="CODE", nullable = false)
	private String code;

	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="DESCRIPTION", nullable = false)
	private String description;

	@Column(name="PRICE", nullable = false)
	private Double price;
	
	@Override
    public Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }

}
