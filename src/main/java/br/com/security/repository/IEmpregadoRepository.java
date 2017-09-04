package br.com.security.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;

import br.com.security.filters.FiltroConsulta;
import br.com.security.model.Empregado;

public interface IEmpregadoRepository {
	
	int countByField(Pageable pageable, FiltroConsulta filtro);
	List<Empregado> findByField(Pageable pageable, FiltroConsulta filtro);
}
