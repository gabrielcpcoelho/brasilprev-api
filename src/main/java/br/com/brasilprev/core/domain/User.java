package br.com.brasilprev.core.domain;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.brasilprev.core.util.IDomain;
import lombok.Data;

@Entity
@Data
@Table(name = "USER")
public class User implements UserDetails, Cloneable, IDomain {

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
	
	@Column(name="PASSWORD", nullable = false)
	private String password;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

}
