package br.com.selecaoinvolves.goa.controller;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import br.com.selecaoinvolves.goa.dao.PontoVendaDAO;
import br.com.selecaoinvolves.goa.model.PontoVenda;

@Service
public class PontoVendaController {

	@Inject private PontoVendaDAO pontoVendaDAO;
	
	private static final int COLUNA_NOME=0,COLUNA_CIDADE=1,COLUNA_ENDERECO=2,COLUNA_CEP=3;
	private static final String NOME_DESCONHECIDO="NOME DESCONHECIDO",CEP_DESCONHECIDO="00000000";
	private static final int CEP_LENGTH=8;
	public PontoVenda createBy(String[] linha, String tenant) {
		if(linha.length==4&&StringUtils.isNotBlank(tenant)) {
			String nome=linha[COLUNA_NOME];
			String cidade=linha[COLUNA_CIDADE];
			String endereco=linha[COLUNA_ENDERECO];
			String cep=linha[COLUNA_CEP];
			
			if(StringUtils.isBlank(nome))nome=NOME_DESCONHECIDO;
			if(StringUtils.isBlank(cidade))cidade=NOME_DESCONHECIDO;
			if(StringUtils.isBlank(endereco))endereco=NOME_DESCONHECIDO;
			if(StringUtils.isBlank(cep)||cep.length()!=CEP_LENGTH)cep=CEP_DESCONHECIDO;
			
			return new PontoVenda(nome,cidade,endereco,cep,tenant);
			
		}else{
			return null;
		}		
	}
	public void save(String[] linha, String tenant) throws Exception {
		PontoVenda pontoVenda = createBy(linha,tenant);
		if(pontoVenda!=null){
			pontoVendaDAO.save(pontoVenda);
		}else {
			throw new Exception("Impossivel gerar instancia de PDV a partir da linha informada.");
		}
	}
	public PontoVendaDAO dao() {
		return this.pontoVendaDAO;
	}
}
