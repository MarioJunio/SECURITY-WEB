package br.com.security.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "empregado")
public class Empregado implements Serializable {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    private String senhaC;
    private String telefone1;
    private String telefone2;
    private String logradouro;
    private int numero;
    private String bairro;
    private String cep;
    private Cidade cidade;
    private boolean ativo;
    private Date dataAlteracao;
    private long excluir;

    public Empregado() {
		super();
	}

	public Empregado(Long id, String nome, String login, String senha, String telefone1,
			String telefone2, String logradouro, int numero, String bairro, String cep, Cidade cidade, boolean ativo,
			Date dataAlteracao, long excluir) {
		super();
		this.id = id;
		this.nome = nome;
		this.login = login;
		this.senha = senha;
		this.telefone1 = telefone1;
		this.telefone2 = telefone2;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.ativo = ativo;
		this.dataAlteracao = dataAlteracao;
		this.excluir = excluir;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Nome é obrigatório")
    @Size(min = 3, max = 60, message = "O nome deve conter no mínimo 3 e no máximo 60 caracteres")
    @Column
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message = "Login é obrigário")
    @Size(max = 32, message = "Login deve conter no máximo 32 caracteres")
    @Column(unique = true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @NotEmpty(message = "Senha é obrigatória")
    @Size(min = 4, max = 16, message = "Senha deve conter no mínimo 4 e no máximo 16 caracteres")
    @Column
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message = "Telefone 1 é obrigatório")
    @Column(length = 20)
    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    @Column(length = 20)
    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    @Column()
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    @Column
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Column
    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Column(length = 12)
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    @ManyToOne()
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

    @Transient
    public String getSenhaC() {
        return senhaC;
    }

    public void setSenhaC(String senhaC) {
        this.senhaC = senhaC;
    }
    
    @Transient
    public long getExcluir() {
		return excluir;
	}

	public void setExcluir(long excluir) {
		this.excluir = excluir;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Empregado empregado = (Empregado) o;

        return id != null ? id.equals(empregado.id) : empregado.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Empregado{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", senhaC='" + senhaC + '\'' +
                ", telefone1='" + telefone1 + '\'' +
                ", telefone2='" + telefone2 + '\'' +
                ", logradouro='" + logradouro + '\'' +
                ", numero=" + numero +
                ", bairro='" + bairro + '\'' +
                ", cep='" + cep + '\'' +
                ", cidade=" + cidade +
                ", ativo=" + ativo +
                ", dataAlteracao=" + dataAlteracao +
                '}';
    }
}
