package br.com.security.reports;

import br.com.security.model.CheckinStatus;

public class VisitaCliente {

	private String data;
	private String horario;
	private String empregado;
	private CheckinStatus status;
	private String descricao;

	public VisitaCliente() {
		super();
	}

	public VisitaCliente(String data, String horario, String empregado, CheckinStatus status, String descricao) {
		super();
		this.data = data;
		this.horario = horario;
		this.empregado = empregado;
		this.status = status;
		this.descricao = descricao;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getEmpregado() {
		return empregado;
	}

	public void setEmpregado(String empregado) {
		this.empregado = empregado;
	}

	public CheckinStatus getStatus() {
		return status;
	}

	public void setStatus(CheckinStatus status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "VisitaCliente [data=" + data + ", horario=" + horario + ", empregado=" + empregado + ", status="
				+ status + ", descricao=" + descricao + "]";
	}

}
