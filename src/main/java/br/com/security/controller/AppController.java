package br.com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.security.repository.CheckinRepository;
import br.com.security.repository.ClienteRepository;
import br.com.security.repository.EmpregadoRepository;
import br.com.security.wrapper.CheckinView;

@Controller
public class AppController {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmpregadoRepository empregadoRepository;

	@Autowired
	private CheckinRepository checkinRepository;

	@GetMapping("/")
	public String inicio(Model model) {

		model.addAttribute("count_clientes", clienteRepository.countNonExcluded());
		model.addAttribute("count_funcionarios", empregadoRepository.count());
		model.addAttribute("count_checkin", checkinRepository.count());

		return "app";
	}

	@GetMapping("/auth")
	public String auth() {
		return "auth/login";
	}

	@GetMapping("/download-apps")
	public String downloadMobileApps() {
		return "download/index";
	}

	/**
	 * WebService interno para buscar os checkin, e retorna-los em um array JSON
	 */
	@GetMapping("/app/checkins")
	public ResponseEntity<List<CheckinView>> todosCheckin() {

		// foi criado o método abaixo para limitar o número de registros buscados,
		// usando a nova interface da versão do spring boot jpa
		return new ResponseEntity<List<CheckinView>>(checkinRepository.findAllOrdered(new PageRequest(0, 20)), HttpStatus.OK);
	}

}
