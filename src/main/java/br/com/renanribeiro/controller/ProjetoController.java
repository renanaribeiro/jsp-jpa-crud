package br.com.renanribeiro.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.renanribeiro.model.Pessoa;
import br.com.renanribeiro.model.Projeto;
import br.com.renanribeiro.repository.PessoaRepository;
import br.com.renanribeiro.repository.ProjetoRepository;
import br.com.renanribeiro.util.RiscoEnum;
import br.com.renanribeiro.util.StatusEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	ProjetoRepository projetoRepository;
	
	@Autowired
	PessoaRepository pessoaRepository;
	
	public static List<Projeto> _projetos = new ArrayList<Projeto>();
	
	@PostConstruct
	private void init() {
		_projetos = projetoRepository.findAll();
	}
	
	@GetMapping()
	public ModelAndView listarProjetosView() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("projetos", _projetos);
		return mav;
	}
	
	@GetMapping("/novo")
	public ModelAndView novoProjetoView() {
		ModelAndView mav = new ModelAndView("projeto-form");
		List<Pessoa> funcionarios = pessoaRepository.findAllByFuncionarioTrue();
		mav.addObject("projeto", null);
		mav.addObject("funcionarios", funcionarios);
		return mav;
	}
	
	@PostMapping("/criar")
	public void criarProjeto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			String nome = request.getParameter("nome");
			String _dataInicio = request.getParameter("dataInicio");
			String _dataPrevisaoFim = request.getParameter("dataPrevisaoFim");
			String _dataFim = request.getParameter("dataFim");
			String descricao = request.getParameter("descricao");
			String _orcamento = request.getParameter("orcamento");
			String _risco = request.getParameter("risco");
			String _gerenteId = request.getParameter("gerenteId");
			
			LocalDate dataInicio = !Strings.isBlank(_dataInicio) ? LocalDate.parse(_dataInicio) : null;
			LocalDate dataPrevisaoFim = !Strings.isBlank(_dataPrevisaoFim) ? LocalDate.parse(_dataPrevisaoFim) : null;
			LocalDate dataFim = !Strings.isBlank(_dataFim) ? LocalDate.parse(_dataFim) : null;
			double orcamento = !Strings.isBlank(_orcamento) ? Double.parseDouble(_orcamento) : 0;
			RiscoEnum risco = !Strings.isBlank(_risco) ? RiscoEnum.getRisco(_risco) : RiscoEnum.Baixo;
			Pessoa gerente = null;
			
			if (!Strings.isBlank(_gerenteId)) {
				Long gerenteId = Long.parseLong(_gerenteId);
				Optional<Pessoa> found = pessoaRepository.findById(gerenteId);
				if (found.isPresent()) {
					gerente = found.get();
				}
			}

			Projeto projeto = new Projeto();
			projeto.setNome(nome);
			projeto.setDataInicio(dataInicio);
			projeto.setDataPrevisaoFim(dataPrevisaoFim);
			projeto.setDataFim(dataFim);
			projeto.setDescricao(descricao);
			projeto.setOrcamento(orcamento);
			projeto.setRisco(risco);
			projeto.setGerente(gerente);
			projeto = projetoRepository.save(projeto);
			_projetos.add(projeto);
			log.info("Projeto criado com sucesso: {}", projeto);
			response.sendRedirect("/projetos");
		} catch (NumberFormatException e1) {
			log.error("Falha ao converter objeto em Projeto: {}", e1);
			request.setAttribute("message", e1.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (IOException e2) {
			log.error("Falha ao criar Projeto: {}", e2);
			request.setAttribute("message", e2.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	@GetMapping("/atualizar/{id}")
	public ModelAndView atualizarProjetoView(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("projeto-form");
		try {
			Projeto found = _projetos.stream().filter(proj -> proj.getId().equals(id)).findAny().orElse(null);
			
			if (found != null) {
				List<Pessoa> funcionarios = pessoaRepository.findAllByFuncionarioTrue();
				mav.addObject("projeto", found);
				mav.addObject("funcionarios", funcionarios);				
			} else {
				throw new Exception("Projeto não encontrado para editar!");
			}			
		} catch (Exception e) {
			log.error("Falha ao identificar Projeto com ID = {}: {}", id, e.getMessage());
			mav.setViewName("error");
			mav.addObject("message", e.getMessage());
		}
		
		return mav;
	}
	
	@PostMapping("/editar")
	public void editarProjeto(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {
			String _id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String _dataInicio = request.getParameter("dataInicio");
			String _dataPrevisaoFim = request.getParameter("dataPrevisaoFim");
			String _dataFim = request.getParameter("dataFim");
			String descricao = request.getParameter("descricao");
			String _orcamento = request.getParameter("orcamento");
			String _risco = request.getParameter("risco");
			String _gerenteId = request.getParameter("gerenteId");
			
			Long id = Long.valueOf(_id);
			LocalDate dataInicio = !Strings.isBlank(_dataInicio) ? LocalDate.parse(_dataInicio) : null;
			LocalDate dataPrevisaoFim = !Strings.isBlank(_dataPrevisaoFim) ? LocalDate.parse(_dataPrevisaoFim) : null;
			LocalDate dataFim = !Strings.isBlank(_dataFim) ? LocalDate.parse(_dataFim) : null;
			double orcamento = !Strings.isBlank(_orcamento) ? Double.parseDouble(_orcamento) : 0;
			RiscoEnum risco = !Strings.isBlank(_risco) ? RiscoEnum.getRisco(_risco) : RiscoEnum.Baixo;
			Pessoa gerente = null;
			
			if (!Strings.isBlank(_gerenteId)) {
				Long gerenteId = Long.parseLong(_gerenteId);
				Pessoa gerenteFound = pessoaRepository.getById(gerenteId);
				if (gerenteFound != null) {
					gerente = gerenteFound;
				}
			}
			
			Projeto found = _projetos.stream().filter(proj -> proj.getId().equals(id)).findAny().orElse(null);
			
			if (found != null) {
				found.setNome(nome);
				found.setDataInicio(dataInicio);
				found.setDataPrevisaoFim(dataPrevisaoFim);
				found.setDataFim(dataFim);
				found.setDescricao(descricao);
				found.setOrcamento(orcamento);
				found.setRisco(risco);
				found.setGerente(gerente);
				found = projetoRepository.save(found);
				response.sendRedirect("/projetos");
			} else {
				log.error("Projeto com ID = {} não encontrado para editar!", id);
				throw new Exception("Projeto não encontrado!");
			}
		} catch (NumberFormatException e1) {
			log.error("Falha ao converter objeto em Projeto: {}", e1);
			request.setAttribute("message", e1.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		} catch (Exception e2) {
			log.error("Falha ao editar Projeto: {}", e2);
			request.setAttribute("message", e2.getMessage());
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	@GetMapping("/excluir/{id}")
	public ModelAndView excluirProjeto(@PathVariable("id") Long id) {
		ModelAndView mav = new ModelAndView("index");
		
		try {
			Projeto found = _projetos.stream().filter(proj -> proj.getId().equals(id)).findAny().orElse(null);
			
			if (found != null) {
				
				StatusEnum currentStatus = found.getStatus();
				if (currentStatus != null && (
					currentStatus != StatusEnum.Iniciado && 
					currentStatus != StatusEnum.EmAndamento && 
					currentStatus != StatusEnum.Encerrado)
				) {
					projetoRepository.delete(found);
					_projetos.remove(found);
					log.info("Projeto excluído com sucesso: {}", found);
					mav.addObject("projetos", _projetos);
				} else {
					throw new Exception("Não é permitido excluir o projeto no status atual de " + currentStatus);
				}
				
			} else {
				log.error("Projeto com ID = {} não encontrado para excluir!", id);
				throw new Exception("Projeto não encontrado para excluir!");
			}			
		} catch (Exception e) {
			log.error("Falha ao excluir Projeto com ID = {}: {}", id, e.getMessage());
			mav.setViewName("error");
			mav.addObject("message", e.getMessage());
		}
		
		return mav;
	}
}
