package br.com.selecaoinvolves.goa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import br.com.selecaoinvolves.goa.model.PontoVenda;


public interface PontoVendaDAO  extends JpaRepository<PontoVenda, Long>{
	List<PontoVenda> findByTenant(String tenant);
}
