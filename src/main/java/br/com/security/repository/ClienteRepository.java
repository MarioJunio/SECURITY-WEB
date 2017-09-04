package br.com.security.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.security.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.email = :email")
	boolean emailAlreadyExists(@Param("email") String email);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.email = :email and a.id <> :id")
	boolean emailAlreadyExists(@Param("email") String email, @Param("id") Long exceptId);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.cpf = :cpf")
	boolean cpfAlreadyExists(@Param("cpf") String cpf);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.cpf = :cpf and a.id <> :id")
	boolean cpfAlreadyExists(@Param("cpf") String cpf, @Param("id") Long exceptId);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.cnpj = :cnpj")
	boolean cnpjAlreadyExists(@Param("cnpj") String cnpj);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.cnpj = :cnpj and a.id <> :id")
	boolean cnpjAlreadyExists(@Param("cnpj") String cnpj, @Param("id") Long exceptId);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.telefone1 = :telefone1")
	boolean celularAlreadyExists(@Param("telefone1") String telefone1);

	@Query("select case when (count(a.id) > 0) then true else false end from Cliente a where a.telefone1 = :telefone1 and a.id <> :id")
	boolean celularAlreadyExists(@Param("telefone1") String telefone1, @Param("id") Long exceptId);

	List<Cliente> findByAtivo(boolean status);
	
	List<Cliente> findByNomeStartingWithOrderByNomeAsc(String nome);

	@Transactional
	@Modifying
	@Query("delete from Cliente a where a.id in :selected")
	void deleteSelected(@Param("selected") List<Long> ids);

}
