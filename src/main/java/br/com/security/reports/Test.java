package br.com.security.reports;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.springframework.core.io.ClassPathResource;

import br.com.security.model.CheckinStatus;
import net.sf.jasperreports.engine.JRException;

public class Test {

	public static void main(String[] args) {

		LocalDateTime now = LocalDateTime.now();

		LocalDateTime yest = LocalDateTime.now();
		yest = yest.minusDays(2);

		VisitaCliente vc1 = new VisitaCliente();
		vc1.setData(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		vc1.setHorario(now.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		vc1.setEmpregado("Mario Junio Marques Martins");
		vc1.setStatus(CheckinStatus.NORMAL);
		vc1.setDescricao("Casa da Bruna Silva Gostosona e da Raphaela pernuda e bucetuda...");

		VisitaCliente vc3 = new VisitaCliente();
		vc3.setData(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		vc3.setHorario(now.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		vc3.setEmpregado("Mario Junio Marques Martins");
		vc3.setStatus(CheckinStatus.NORMAL);
		vc3.setDescricao("Casa da Bruna Silva Gostosona e da Raphaela pernuda e bucetuda...");

		VisitaCliente vc2 = new VisitaCliente();
		vc2.setData(yest.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		vc2.setHorario(yest.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
		vc2.setEmpregado("Kaio Guilherme Dummont e Ferraz");
		vc2.setStatus(CheckinStatus.NORMAL);
		vc2.setDescricao(null);

		List<VisitaCliente> visitasClientes = new ArrayList<>();
		visitasClientes.add(vc2);
		visitasClientes.add(vc3);
		visitasClientes.add(vc1);

		try {

			Map<String, Object> params = new HashMap<>();
			params.put("FILTRO_CLIENTE", "Teste de cliente para filtro");
			params.put("FILTRO_DATA_INICIO", yest.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			params.put("FILTRO_DATA_FIM", now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			params.put("FILTRO_STATUS", null);
			params.put("FILTRO_EMPRESA", "Security - Sistemas de Segurança");
			params.put("FILTRO_EMP_ENDERECO", "Av. Dona Clara 1756, Centro - Monte Carmelo MG");
			params.put("FILTRO_EMP_FONE", "(34) 98700-5712");
			params.put("FILTRO_LOGO", new FileInputStream(new ClassPathResource("static/img/logo.png").getFile()));

			Reports.generate(Reports.VISITAS_CLIENTE, null, params, visitasClientes);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}
