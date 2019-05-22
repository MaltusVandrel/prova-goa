package br.com.selecaoinvolves.goa.util;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import br.com.selecaoinvolves.goa.deserializers.CustomSortDeserializer;

public  class TableAjax<T> {
	
	private T object;
	private Integer page;
	private Integer resultsPerPage;
	private Sort sort;//property and direction
	
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

	public Sort getSort() {
		return sort;
	}
	@JsonDeserialize(using=CustomSortDeserializer.class)
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	
}


