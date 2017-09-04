package br.com.security.filters;

public class FiltroConsulta {

	private String campo;
	private String valor;
	private String status;

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "FiltroConsulta [campo=" + campo + ", valor=" + valor + ", status=" + status + "]";
	}

}
