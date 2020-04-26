package br.com.brasilprev.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brasilprev.core.domain.User;
import br.com.brasilprev.core.exception.WarningMessageException;

@RestController
@RequestMapping("/logged")
public class UsuarioLogadoController {

	@RequestMapping(method = GET)
	public User get() throws WarningMessageException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if(auth.getPrincipal() instanceof User)
        	return (User) auth.getPrincipal();
        
        // else (String como "anonymousUser")
		return null;
	}
	
}
