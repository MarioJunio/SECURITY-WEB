package br.com.security.repository;

import br.com.security.model.Cidade;
import br.com.security.model.Empregado;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpregadoRepository extends CrudRepository<Empregado, Long> {

	@Query("select case when (count(a.id) > 0) then true else false end from Empregado a where a.login = :login")
	boolean loginAlreadyExist(@Param("login") String login);

	@Query("select case when (count(a.id) > 0) then true else false end from Empregado a where a.login = :login and a.id <> :id")
	boolean loginAlreadyExist(@Param("login") String login, @Param("id") Long id);

	@Transactional
	@Modifying
	@Query("delete from Empregado a where a.id in :selected")
	void deleteSelected(@Param("selected") List<Long> ids);

	@Transactional
	@Modifying
	@Query("update Empregado a set a.nome = :nome, a.login = :login, a.telefone1 = :telefone1, a.telefone2 = :telefone2, a.logradouro = :logradouro, a.numero = :numero, a.bairro = :bairro, a.cep = :cep, a.cidade = :cidade, a.ativo = :ativo, a.dataAlteracao = :dataAlteracao where a.id = :id")
	void edit(@Param("nome") String nome, @Param("login") String login, @Param("telefone1") String telefone1,
			@Param("telefone2") String telefone2, @Param("logradouro") String logradouro, @Param("numero") int numero,
			@Param("bairro") String bairro, @Param("cep") String cep, @Param("cidade") Cidade cidade,
			@Param("ativo") boolean ativo, @Param("dataAlteracao") Date dataAlteracao, @Param("id") Long id);
}
