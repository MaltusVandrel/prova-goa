package br.com.selecaoinvolves.goa.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.selecaoinvolves.goa.controller.PontoVendaController;
import br.com.selecaoinvolves.goa.dao.TenantDAO;
import br.com.selecaoinvolves.goa.model.PontoVenda;
import br.com.selecaoinvolves.goa.model.Tenant;
import br.com.selecaoinvolves.goa.util.Message;
import br.com.selecaoinvolves.goa.util.TableAjax;

@Controller
@RequestMapping("/")
public class PontoVendaWeb {

	@Inject private PontoVendaController pontoVendaController;
	@Inject private TenantDAO tenantDAO;
	
	@GetMapping("/")
    public String index() {
          return "index";
    }
	
	@GetMapping("/consulta/{tenant}/")
    public String consulta(@PathVariable("tenant") String tenant,Model model) {
		model.addAttribute("tenant", tenant);
		return "consulta";
    }
	
	@GetMapping("/get/codigos/")
	@ResponseBody
    public List<Tenant> getCodigos() {
          return tenantDAO.findAll();
    }
	
	@PostMapping("/grid/{tenant}/")
	@ResponseBody
	public Page<PontoVenda> listaPontosVenda(@PathVariable("tenant") String tenant,
			@RequestBody TableAjax<PontoVenda> table,
    		Model model) {
		table.getObject().setTenant(tenant);
		Example<PontoVenda> example = 
				Example.of(table.getObject(),ExampleMatcher
											.matching()
											.withIgnoreNullValues()
											.withStringMatcher(ExampleMatcher.StringMatcher.STARTING)
											.withMatcher("endereco", ExampleMatcher.GenericPropertyMatchers.contains()));   
		return pontoVendaController
        		  .dao()
        		  .findAll(example,PageRequest
        				  	.of(table.getPage(),table.getResultsPerPage(),table.getSort()));
	}
	
	@PostMapping("/upload/")
	@ResponseBody
    public Message upload(
    		@RequestParam("data") MultipartFile data,
    		@RequestParam("tenant") String tenant){
		try {
			pontoVendaController.persistePontosByCSV(data,tenant);
			return new Message("Pontos de venda foram recebidos e serão salvos em sequência.");
		}catch (Exception e) {
			return new Message(e.getMessage(),Boolean.TRUE);
		}
	}
	
	@GetMapping("/delete/")
	@ResponseBody
    public Message delete()   {
		pontoVendaController.dao().deleteAll();
		return new Message("Pontos de venda foram excluidos com sucesso.");
    }
	
}
