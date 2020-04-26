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
@Table(name = "CLIENT")
public class Client implements Cloneable, IDomain, Serializable{

	private static final long serialVersionUID = -7922840665806454456L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", unique = true)
	private Long id;
	
	@Column(name="CPF", nullable = false)
	private String cpf;

	@Column(name="NAME", nullable = false)
	private String name;
	
	@Column(name="EMAIL", nullable = false)
	private String email;
	
	@ManyToOne
	@JoinColumn(name="ID_USER", nullable = true)
	private User user;
	
	@Override
    public Client clone() throws CloneNotSupportedException {
        return (Client) super.clone();
    }

}
