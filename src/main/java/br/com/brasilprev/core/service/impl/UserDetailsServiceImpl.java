package br.com.brasilprev.core.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	public UserRepository usuarioRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User usuario = usuarioRepository.findByEmail(username);

		if (Objects.isNull(usuario))
			throw new UsernameNotFoundException("User not found.");

		return usuario;
	}
}
