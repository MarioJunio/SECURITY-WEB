package br.com.security.reports;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public interface Reports {

	String CLIENTES_QRCODE = "report-clientes-qrcode.jrxml";
	String VISITAS_CLIENTE = "report-visitas-por-cliente.jrxml";
	
	public static void generate(String jrxml, OutputStream out, Map<String, Object> params, List<?> dataSource) throws IOException, JRException {

		InputStream reportFile = new ClassPathResource("static/reports/" + jrxml).getInputStream();
		JasperReport jr = JasperCompileManager.compileReport(reportFile);
		JasperPrint print = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(dataSource));
		
		JasperExportManager.exportReportToPdfStream(print, out);
		
		// TESTE APENAS
//		JasperViewer.viewReport(print, false);
	}

}
