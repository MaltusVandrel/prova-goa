package br.com.selecaoinvolves.goa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.selecaoinvolves.goa.model.Tenant;

public interface TenantDAO  extends JpaRepository<Tenant, Long>{
	Tenant findByCodigo(String codigo);
}
