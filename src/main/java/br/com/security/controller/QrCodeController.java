package br.com.security.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.security.model.Cliente;
import br.com.security.reports.ClienteQrCode;
import br.com.security.reports.Reports;
import br.com.security.repository.ClienteRepository;
import br.com.security.util.AppUtils;

@Controller
@RequestMapping("/qrcode")
public class QrCodeController {

	public static final String CLIENTES_VIEW = "/qrcode/clientes_qrcode";

	@Autowired
	public ClienteRepository clienteRepository;

	@GetMapping("/clientes")
	public String clientes() {
		return CLIENTES_VIEW;
	}

	@GetMapping("/clientes/buscar")
	public ResponseEntity<List<Cliente>> buscar(@Param("nome") String nome) {
		List<Cliente> clientes = clienteRepository
				.findByNomeStartingWithOrderByNomeAsc(Optional.ofNullable(nome).orElse(""));
		return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
	}

	@PostMapping("/clientes/download")
	public void gerarClientes(HttpServletResponse response, @Param("ids") String ids) throws Exception {
		response.setContentType("application/pdf");

		List<ClienteQrCode> lista = new ArrayList<>();
		String[] tokens = ids.split(",");

		try {

			for (String token : tokens) {

				int index = token.indexOf(':');
				String id = AppUtils.md5(Long.parseLong(token.substring(0, index)));
				String nome = token.substring(index + 1, token.length());

				lista.add(new ClienteQrCode(id, nome));
			}

			Reports.generate(Reports.CLIENTES_QRCODE, response.getOutputStream(), null, lista);
			response.flushBuffer();

		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Algoritmo hash MD5 não encontrado");
		}

	}

}
