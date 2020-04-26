package br.com.brasilprev.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.brasilprev.core.constants.StatusEnum;
import br.com.brasilprev.core.util.FormatterUtils;
import br.com.brasilprev.core.util.IDomain;
import lombok.Data;

@Entity
@Data
@Table(name = "ORDERS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order implements Cloneable, IDomain, Serializable{

	private static final long serialVersionUID = -7591150626728623238L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true)
	private Long id;
	
	@Column(name="DATE", nullable = false)
	private Date date;

	@Enumerated(EnumType.STRING)
	@Column(name="STATUS", nullable = false)
	private StatusEnum status;
	
	public String getDateFormatted() {
		return FormatterUtils.toDate(date);
	}
	
	@Override
    public Order clone() throws CloneNotSupportedException {
        return (Order) super.clone();
    }

}
