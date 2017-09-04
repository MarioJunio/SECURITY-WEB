package br.com.security.repository;

import br.com.security.model.Cidade;
import br.com.security.wrapper.CidadeView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeRepository extends CrudRepository<Cidade, Long> {

    @Query("select new br.com.security.wrapper.CidadeView(a.id, concat(a.nome, ' - ', a.uf)) from Cidade a order by a.uf, a.nome")
    List<CidadeView> findAllOrderByEstadoCidade();

}
