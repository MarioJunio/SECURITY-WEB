package br.com.security.repository;

import br.com.security.model.Grupo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Long> {
}
