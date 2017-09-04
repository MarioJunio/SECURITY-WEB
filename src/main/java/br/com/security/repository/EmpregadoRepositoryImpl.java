package br.com.security.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.security.filters.FiltroConsulta;
import br.com.security.model.Empregado;

@Repository
public class EmpregadoRepositoryImpl implements IEmpregadoRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private String createConcat(String campo) {

		String concat = "concat(";
		String[] tokens = campo.split("\\+");

		if (tokens.length > 1) {

			for (String token : tokens)
				concat += token + ",";

			concat = concat.substring(0, concat.length() - 1) + ")";

		} else {
			concat += campo + ")";
		}

		return concat;
	}

	@Override
	public int countByField(Pageable pageable, FiltroConsulta filtro) {

		String countJPQL;
		Query query;
		String campo;

		if (filtro.getCampo().endsWith("ativo") && !filtro.getStatus().equals("ambos")) {
			countJPQL = "select count(a.id) from Empregado a where a.ativo = :ativo";
			query = entityManager.createQuery(countJPQL);
			query.setParameter("ativo", filtro.getStatus().equals("ativo") ? true : false);
		} else if (filtro.getCampo().endsWith("ativo") && filtro.getStatus().equals("ambos")) {
			countJPQL = "select count(a.id) from Empregado a";
			query = entityManager.createQuery(countJPQL);
		} else {
			campo = createConcat(filtro.getCampo());
			countJPQL = "select count(a.id) from Empregado a where " + campo + " like :value";
			query = entityManager.createQuery(countJPQL.replace("<domain>", "a"));
			query.setParameter("value", String.format("%%%s%%", filtro.getValor()));
		}

		return ((Number) query.getSingleResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empregado> findByField(Pageable pageable, FiltroConsulta filtro) {

		String jpql;
		Query query;
		String campo;
		
		String select = "select new br.com.security.model.Empregado(a.id, a.nome, a.login, a.senha, a.telefone1, a.telefone2, a.logradouro, a.numero, a.bairro, a.cep, a.cidade, a.ativo, a.dataAlteracao, (select count(ch.empregado.id) from Checkin ch where ch.empregado.id = a.id)) ";
		String orderBy = " order by a.nome asc";

		if (filtro.getCampo().endsWith("ativo") && !filtro.getStatus().equals("ambos")) {
			jpql = select + "from Empregado a where a.ativo = :ativo" + orderBy;
			query = entityManager.createQuery(jpql);
			query.setParameter("ativo", filtro.getStatus().equals("ativo") ? true : false);
		} else if (filtro.getCampo().endsWith("ativo") && filtro.getCampo().equals("ambos")) {
			jpql = select + "from Empregado a" + orderBy;
			query = entityManager.createQuery(jpql);
		} else {
			campo = createConcat(filtro.getCampo());
			jpql = select + "from Empregado a where " + campo + " like :value" + orderBy;
			query = entityManager.createQuery(jpql.replace("<domain>", "a"));
			query.setParameter("value", String.format("%%%s%%", filtro.getValor()));
		}

		int offset = pageable.getPageNumber() * pageable.getPageSize();

		return query.setFirstResult(offset).setMaxResults(pageable.getPageSize()).getResultList();
	}

}
