package br.com.selecaoinvolves.goa.util;

import java.util.List;

import org.springframework.data.domain.Sort.Order;

public  class TableAjax<T> {
	
	private T object;
	private Integer page;
	private Integer resultsPerPage;
	private List<Order> ordenation;//property and direction
	
	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getResultsPerPage() {
		return resultsPerPage;
	}

	public void setResultsPerPage(Integer resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}

	public List<Order> getOrdenation() {
		return ordenation;
	}

	public void setOrdenation(List<Order> ordenation) {
		this.ordenation = ordenation;
	}

	
}


