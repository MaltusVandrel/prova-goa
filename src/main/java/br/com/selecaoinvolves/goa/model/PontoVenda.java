package br.com.selecaoinvolves.goa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PontoVenda {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;
	
	@Column(name="nome", nullable=false)
    private String nome;
	
	@Column(name="cidade", nullable=false)
	private String cidade;
	
	@Column(name="endereco", nullable=false)
	private String endereco;
	
	@Column(name="cep", nullable=false)
	private String cep;
	
	@Column(name="tenant", nullable=false)
	private String tenant;

	public PontoVenda() {}

	public PontoVenda(String nome, String cidade, String endereco, String cep, String tenant) {
		this.nome = nome;
		this.cidade = cidade;
		this.endereco = endereco;
		this.cep = cep;
		this.tenant = tenant;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
    
	
	
}
