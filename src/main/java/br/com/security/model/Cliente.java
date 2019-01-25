package br.com.security.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private Long id;
	private String nome;
	private String email;
	private String tipo;
	private String cpf;
	private String cnpj;
	private String telefone1;
	private String telefone2;
	private String logradouro;
	private int numero;
	private String bairro;
	private String cep;
	private Cidade cidade;
	private boolean ativo;
	private Date dataAlteracao;
	private int tmpCodigoValidacao;
	private Double latitude, longitude;
	private boolean excluido;
	private long excluir;

	public Cliente() {
		super();
	}

	public Cliente(Long id, String nome, String email, String tipo, String cpf, String cnpj, String telefone1,
			String telefone2, String logradouro, int numero, String bairro, String cep, Cidade cidade, boolean ativo,
			Date dataAlteracao, int tmpCodigoValidacao, long excluir) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.ativo = ativo;
		this.dataAlteracao = dataAlteracao;
		this.tmpCodigoValidacao = tmpCodigoValidacao;
		this.excluir = excluir;
	}

	public Cliente(Long id, String nome, String email, String tipo, String cpf, String cnpj, String telefone1,
			String telefone2, String logradouro, int numero, String bairro, String cep, Cidade cidade, boolean ativo,
			Date dataAlteracao, int tmpCodigoValidacao, Double latitude, Double longitude, long excluir) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.ativo = ativo;
		this.dataAlteracao = dataAlteracao;
		this.tmpCodigoValidacao = tmpCodigoValidacao;
		this.latitude = latitude;
		this.longitude = longitude;
		this.excluir = excluir;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message = "Nome é obrigatório")
	@Column()
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Email(message = "Email inválido")
	@Column(length = 300, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 1)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(length = 15)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(length = 20)
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@NotEmpty(message = "Celular é obrigatório")
	@Column(length = 20)
	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	@NotEmpty(message = "Logradouro é obrigatório")
	@Column
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Min(value = 1, message = "Número inválido")
	@Column
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@NotEmpty(message = "Bairro é obrigatório")
	@Column()
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@NotEmpty(message = "CEP é obrigatório")
	@Column
	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@NotNull(message = "Cidade deve ser selecionada")
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Column
	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	@Column
	public int getTmpCodigoValidacao() {
		return tmpCodigoValidacao;
	}

	public void setTmpCodigoValidacao(int tmpCodigoValidacao) {
		this.tmpCodigoValidacao = tmpCodigoValidacao;
	}

	@Column
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@ColumnDefault("false")
	public boolean isExcluido() {
		return excluido;
	}

	public void setExcluido(boolean excluido) {
		this.excluido = excluido;
	}

	@Transient
	public long getExcluir() {
		return excluir;
	}

	public void setExcluir(long excluir) {
		this.excluir = excluir;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", cnpj=" + cnpj
				+ ", telefone1=" + telefone1 + ", telefone2=" + telefone2 + ", logradouro=" + logradouro + ", numero="
				+ numero + ", bairro=" + bairro + ", cep=" + cep + ", cidade=" + cidade + ", ativo=" + ativo
				+ ", dataAlteracao=" + dataAlteracao + ", tmpCodigoValidacao=" + tmpCodigoValidacao + "]";
	}

}
