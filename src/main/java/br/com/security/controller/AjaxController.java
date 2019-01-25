package br.com.security.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.security.model.Cliente;
import br.com.security.repository.ClienteRepository;

@Controller
@RequestMapping("/ajax")
public class AjaxController {

	@Autowired
	public ClienteRepository clienteRepository;

	@GetMapping("/clientes/buscar")
	public ResponseEntity<List<Cliente>> buscar(@Param("nome") String nome) {
		List<Cliente> clientes = clienteRepository.find(Optional.ofNullable(nome).orElse(""));
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}

}
