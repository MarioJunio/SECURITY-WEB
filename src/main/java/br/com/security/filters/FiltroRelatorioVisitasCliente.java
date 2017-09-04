package br.com.security.filters;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.security.model.CheckinStatus;

public class FiltroRelatorioVisitasCliente {

	private Long clienteId;
	private String clienteNome;
	private Date dataInicial;
	private Date dataFinal;
	private CheckinStatus status;

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public CheckinStatus getStatus() {
		return status;
	}

	public void setStatus(CheckinStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FiltroRelatorioVisitasCliente [status=" + status + "]";
	}

}
