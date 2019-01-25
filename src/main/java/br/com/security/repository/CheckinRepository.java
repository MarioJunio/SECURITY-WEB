package br.com.security.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.security.model.Checkin;
import br.com.security.model.CheckinStatus;
import br.com.security.reports.VisitaCliente;
import br.com.security.wrapper.CheckinView;

public interface CheckinRepository extends PagingAndSortingRepository<Checkin, Long> {

	@Query("select new br.com.security.wrapper.CheckinView(a.empregado.nome, a.cliente.nome, a.data, a.status, a.latitude, a.longitude, a.descricao) from Checkin a order by a.data desc, a.empregado.nome asc, a.cliente.nome asc")
	public List<CheckinView> findAllOrdered(Pageable pageable);

	@Query("select new br.com.security.reports.VisitaCliente(DATE_FORMAT(a.data, '%d/%m/%Y'), DATE_FORMAT(a.data, '%H:%i:%s'), b.nome, a.status, a.descricao) from Checkin a join a.empregado b join a.cliente c where (c.id = :id) and (a.data >= :di and a.data <= :df) and (a.status in :status) order by a.data asc")
	public List<VisitaCliente> reportVisitasPorCliente(@Param("id") Long id, @Param("di") Date di, @Param("df") Date df,
			@Param("status") List<CheckinStatus> status);

}
