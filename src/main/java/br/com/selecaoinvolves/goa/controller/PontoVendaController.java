package br.com.selecaoinvolves.goa.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;

import br.com.selecaoinvolves.goa.dao.PontoVendaDAO;
import br.com.selecaoinvolves.goa.dao.TenantDAO;
import br.com.selecaoinvolves.goa.model.PontoVenda;
import br.com.selecaoinvolves.goa.model.Tenant;

@Service
public class PontoVendaController {

	@Inject private PontoVendaDAO pontoVendaDAO;
	@Inject private TenantDAO tenantDAO;
	
	private static final int COLUNA_NOME=0,COLUNA_CIDADE=1,COLUNA_ENDERECO=2,COLUNA_CEP=3;
	private static final String NOME_DESCONHECIDO="NOME DESCONHECIDO",CEP_DESCONHECIDO="00000000";
	private static final int CEP_LENGTH=8;
	private static final String HEADER="nome,cidade,endereco,cep";
	public PontoVenda createBy(String[] linha, String tenant) {
			String nome=linha[COLUNA_NOME];
			String cidade=linha[COLUNA_CIDADE];
			String endereco=linha[COLUNA_ENDERECO];
			String cep=linha[COLUNA_CEP];
			
			
			return new PontoVenda(nome,cidade,endereco,cep,tenant);
	}
	
	public void trataPontoVenda(PontoVenda pontoVenda) throws Exception{
		if(HEADER.equals(pontoVenda.toString())) {
			throw new Exception();
		}		
		if(StringUtils.isBlank(pontoVenda.getNome()))pontoVenda.setNome(NOME_DESCONHECIDO);
		if(StringUtils.isBlank(pontoVenda.getCidade()))pontoVenda.setCidade(NOME_DESCONHECIDO);
		if(StringUtils.isBlank(pontoVenda.getEndereco()))pontoVenda.setEndereco(NOME_DESCONHECIDO);
		if(StringUtils.isBlank(pontoVenda.getCep())||pontoVenda.getCep().length()!=CEP_LENGTH)pontoVenda.setCep(CEP_DESCONHECIDO);
		
		
		pontoVenda.setCidade(removeQuotationMarks(pontoVenda.getCidade()));
		pontoVenda.setEndereco(removeQuotationMarks(pontoVenda.getEndereco()));
		pontoVenda.setCep(removeQuotationMarks(pontoVenda.getCep()));
		trataNome(pontoVenda);
	}
	private String removeQuotationMarks(String value) {
		return Pattern.compile("\"")
			       .matcher(value)
			       .replaceAll("");
	}
	
	private void trataNome(PontoVenda pontoVenda) {
		String value = removeQuotationMarks(pontoVenda.getNome());
//		value=value.replaceAll("RUA "+pontoVenda.getEndereco(),"");
//		value=value.replaceAll(pontoVenda.getEndereco(),"");
//		value=value.replaceAll(pontoVenda.getCidade(),"");
//		
//		
//		value=value.replaceAll("(.*)( |)\\,\\d*","$1");
//		value=value.replaceAll("\\d*( |)\\-( |)(.*)","$3");
//		value=value.replaceAll("(.*)((( |)- )|( |)(,)( |))","$1");
		String[] parts = value.split(" - "+pontoVenda.getEndereco());
		if(parts.length>1) {
			value=parts[0];
			pontoVenda.setEndereco(pontoVenda.getEndereco()+parts[1]);
		}
		value=Pattern.compile("\\d*( - |-| -|- )")
	       .matcher(value)
	       .replaceAll("");		
		pontoVenda.setNome(value);
		
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

	public void persistirPontosVenda(List<PontoVenda> pontosVenda, String tenant) {
		int index=0;
		while (index<pontosVenda.size()) {
			try{
				trataPontoVenda(pontosVenda.get(index));
				index++;
			}catch(Exception e) {
				pontosVenda.remove(index);
			}
		}
		dao().saveAll(pontosVenda);
        Tenant codigo = tenantDAO.findByCodigo(tenant);
        if(codigo==null) {
        	codigo = new Tenant();
        	codigo.setCodigo(tenant);
        	tenantDAO.save(codigo);
        }
		
	}
	
	@Async
	public void persistePontosByCSV(MultipartFile data, String tenant) throws IOException {
		InputStreamReader isr = new InputStreamReader(data.getInputStream(), 
                StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(isr);
        String[] linha;
        List<PontoVenda> pontosVenda = new ArrayList<PontoVenda>();
	    while ((linha = reader.readNext()) != null) {
        	try {
        		pontosVenda.add(createBy(linha, tenant));
        	} catch (Exception e) {
				e.printStackTrace();
			}
        }
	    persistirPontosVenda(pontosVenda,tenant);
	}
}
