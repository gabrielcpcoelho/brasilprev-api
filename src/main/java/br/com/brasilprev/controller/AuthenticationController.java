package br.com.brasilprev.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.brasilprev.core.exception.WarningMessageException;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@RequestMapping(method = GET)
	public void authenticate() throws WarningMessageException {
		// do nothing
	}
	
}
