package br.com.security.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.security.filters.FiltroRelatorioVisitasCliente;
import br.com.security.model.CheckinStatus;
import br.com.security.reports.Reports;
import br.com.security.reports.VisitaCliente;
import br.com.security.repository.CheckinRepository;
import net.sf.jasperreports.engine.JRException;

@Controller
@RequestMapping("/relatorio/")
public class RelatorioVisitasClienteController {

	public static final String VISITAS_CLIENTE_VIEW = "/relatorios/visitas_cliente";

	@Autowired
	private CheckinRepository checkinRepository;

	@GetMapping("/visitas-cliente")
	public ModelAndView clientes() {

		ModelAndView mv = new ModelAndView(VISITAS_CLIENTE_VIEW);
		mv.addObject("relatorio", new FiltroRelatorioVisitasCliente());

		return mv;
	}

	@PostMapping("/visitas-cliente/download")
	public String gerarClientes(HttpServletResponse response, Model model, FiltroRelatorioVisitasCliente filtro,
			RedirectAttributes redirect) throws Exception {

		List<CheckinStatus> status = Arrays
				.asList(new CheckinStatus[] { filtro.getStatus() != null ? filtro.getStatus() : CheckinStatus.NORMAL,
						CheckinStatus.SUSPEITO, CheckinStatus.PERIGO });

		if (filtro.getStatus() != null)
			status = Arrays.asList(new CheckinStatus[] { filtro.getStatus() });
		else
			status = Arrays
					.asList(new CheckinStatus[] { CheckinStatus.NORMAL, CheckinStatus.SUSPEITO, CheckinStatus.PERIGO });

		List<VisitaCliente> list = checkinRepository.reportVisitasPorCliente(filtro.getClienteId(),
				filtro.getDataInicial(), filtro.getDataFinal(), status);

		if (list.isEmpty()) {
			model.addAttribute("relatorio", new FiltroRelatorioVisitasCliente());
			model.addAttribute("message", "Nenhuma visita encontrada para a busca informada!");
			return VISITAS_CLIENTE_VIEW;
		} else {
			response.setContentType("application/pdf");
			gerarRelatorio(list, filtro, response.getOutputStream());
			response.flushBuffer();
			return null;
		}

	}

	/**
	 * Gera o relatório
	 * 
	 * @param list
	 * @param out
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JRException
	 */
	private void gerarRelatorio(List<VisitaCliente> list, FiltroRelatorioVisitasCliente filtro, OutputStream out)
			throws FileNotFoundException, IOException, JRException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Map<String, Object> params = new HashMap<>();
		params.put("FILTRO_CLIENTE", filtro.getClienteNome());
		params.put("FILTRO_DATA_INICIO", format.format(filtro.getDataInicial()));
		params.put("FILTRO_DATA_FIM", format.format(filtro.getDataFinal()));
		params.put("FILTRO_STATUS", filtro.getStatus());
		params.put("FILTRO_EMPRESA", "Security - Sistemas de Segurança");
		params.put("FILTRO_EMP_ENDERECO", "Av. Doná Clara, 177 - Centro, Monte Carmelo - MG, 38500-000");
		params.put("FILTRO_EMP_FONE", "(34) 3819-4644");
		params.put("FILTRO_LOGO", new FileInputStream(new ClassPathResource("static/img/logo.png").getFile()));

		Reports.generate(Reports.VISITAS_CLIENTE, out, params, list);

	}
}
