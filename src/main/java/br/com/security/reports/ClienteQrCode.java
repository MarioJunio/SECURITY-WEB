package br.com.security.reports;

public class ClienteQrCode {

	private String id;
	private String code;
	private String nome;

	public ClienteQrCode(String id, String code, String nome) {
		super();
		this.id = id;
		this.code = code;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public static Long getCode(Long id) {
		return id * 3 + 1050;
	}

}
