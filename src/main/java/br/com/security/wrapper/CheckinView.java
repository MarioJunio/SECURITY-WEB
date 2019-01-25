package br.com.security.wrapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.security.model.CheckinStatus;

public class CheckinView {

	private String empregado;
	private String cliente;
	private Date data;
	private CheckinStatus status;
	private double latitude, longitude;
	private String descricao;

	public CheckinView() {
		super();
	}

	public CheckinView(String empregado, String cliente, Date data, CheckinStatus status, double latitude, double longitude, String descricao) {
		super();
		this.empregado = empregado;
		this.cliente = cliente;
		this.data = data;
		this.status = status;
		this.latitude = latitude;
		this.longitude = longitude;
		this.descricao = descricao;
	}

	public String getEmpregado() {
		return empregado;
	}

	public void setEmpregado(String empregado) {
		this.empregado = empregado;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "GMT-3")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public CheckinStatus getStatus() {
		return status;
	}

	public void setStatus(CheckinStatus status) {
		this.status = status;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
