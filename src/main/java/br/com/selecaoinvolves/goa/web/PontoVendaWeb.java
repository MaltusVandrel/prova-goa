package br.com.selecaoinvolves.goa.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.opencsv.CSVReader;

import br.com.selecaoinvolves.goa.controller.PontoVendaController;
import br.com.selecaoinvolves.goa.dao.PontoVendaDAO;
import br.com.selecaoinvolves.goa.model.PontoVenda;
import br.com.selecaoinvolves.goa.util.Message;
import br.com.selecaoinvolves.goa.util.TableAjax;

@Controller
@RequestMapping("/")
public class PontoVendaWeb {

	@Inject private PontoVendaController pontoVendaController;
	
	
	@GetMapping(value = "/get/{tenant}/")
    public String listaPontosVenda(@PathVariable("tenant") String tenant,
    		@PathVariable("page") int page,
    		Model model) {
		PontoVenda pontoVenda = new PontoVenda();
		pontoVenda.setTenant(tenant);
		Example<PontoVenda> example = Example.of(pontoVenda);   
		ExampleMatcher.matching().withIgnoreNullValues();
		
          Page<PontoVenda> pagePontoVenda = pontoVendaController.dao().findAll(example,PageRequest.of(page, 20));
        		  //.findAll(example, pageable)ByTenant(tenant);
          if (pagePontoVenda != null) {
                model.addAttribute("pagePontosVenda", pagePontoVenda);
          }
          return "listaPontosVenda";
    }
	
	@PostMapping("/grid/{tenant}")
	public Page<PontoVenda> listaPontosVenda(@PathVariable("tenant") String tenant,
			@PathVariable("table") TableAjax<PontoVenda> table,
    		Model model) {
		table.getObject().setTenant(tenant);
		Example<PontoVenda> example = Example.of(table.getObject());   
		ExampleMatcher.matching().withIgnoreNullValues();
		return pontoVendaController
        		  .dao()
        		  .findAll(example,PageRequest
        				  	.of(table.getPage(),table.getResultsPerPage(),Sort.by(table.getOrdenation())));
	}
	
	@PostMapping("/upload/")
	@ResponseBody
    public Message upload(
    		@RequestParam("data") MultipartFile data,
    		@RequestParam("tenant") String tenant)  throws IOException {
		
		
		InputStreamReader isr = new InputStreamReader(data.getInputStream(), 
                StandardCharsets.UTF_8);
        CSVReader reader = new CSVReader(isr);
        String[] linha;
        List<PontoVenda> pontosVenda = new ArrayList<PontoVenda>();
        while ((linha = reader.readNext()) != null) {
        	try {
        		pontosVenda.add(pontoVendaController.createBy(linha, tenant));
        	} catch (Exception e) {
				e.printStackTrace();
			}
        }
        pontoVendaController.dao().saveAll(pontosVenda);
        return new Message("Pontos de venda foram salvos com sucesso.");
    }
	@GetMapping("/delete/")
	@ResponseBody
    public Message delete()   {
		pontoVendaController.dao().deleteAll();
		return new Message("Pontos de venda foram excluidos com sucesso.");
    }
	
}
