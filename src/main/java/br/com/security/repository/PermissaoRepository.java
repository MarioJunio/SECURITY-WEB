package br.com.security.repository;

import br.com.security.model.Permissao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends CrudRepository<Permissao, Long> {

}
