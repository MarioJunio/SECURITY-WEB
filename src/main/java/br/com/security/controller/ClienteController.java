package br.com.security.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.security.filters.FiltroConsulta;
import br.com.security.model.Cliente;
import br.com.security.repository.CidadeRepository;
import br.com.security.repository.ClienteRepository;
import br.com.security.repository.ClienteRepositoryImpl;
import br.com.security.util.AppUtils;
import br.com.security.wrapper.CidadeView;
import br.com.security.wrapper.CustomPage;
import br.com.security.wrapper.PageWrapper;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

	public final String CADASTRO_VIEW = "/cliente/cadastro";
	public final String CONSULTA_VIEW = "/cliente/consulta";

	public final String PARAM_CIDADES = "cidades";
	public final String PARAM_FILTRO = "filtro";
	public final String PARAM_URL_PESQUISA = "url_pesquisa";
	public final String PARAM_URL_EXCLUIR = "url_excluir";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepositoryImpl clienteRepositoryCustomImpl;

	private List<CidadeView> cidadesView;

	@PostConstruct
	public void init() {
		cidadesView = cidadeRepository.findAllOrderByEstadoCidade();
	}

	@GetMapping("/novo")
	public ModelAndView novo() {

		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Cliente());
		mv.addObject("cidades", cidadesView);
		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		return mv;
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {

		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(clienteRepository.findOne(id));
		mv.addObject("cidades", cidadesView);
		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		return mv;
	}

	@GetMapping("/consulta")
	public String consultar(FiltroConsulta filtro, Model model, Pageable pageable) {

		if (filtro.getCampo() == null)
			filtro.setCampo("<domain>.nome");

		if (filtro.getValor() == null)
			filtro.setValor("");

		if (filtro.getStatus() == null)
			filtro.setStatus("");

		// segundo parametro é a quantidade de registros por página
		pageable = new PageRequest(pageable.getPageNumber(), 20);

		int countFiltrados = clienteRepositoryCustomImpl.countByField(pageable, filtro);
		List<Cliente> filtrados = clienteRepositoryCustomImpl.findByField(pageable, filtro);

		CustomPage<Cliente> clientePage = new CustomPage<>(countFiltrados, filtrados, pageable);

		PageWrapper<Cliente> page = new PageWrapper<Cliente>(clientePage, "/clientes/consulta");

		model.addAttribute("clientes", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("filtro", filtro);
		model.addAttribute(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		model.addAttribute(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		return CONSULTA_VIEW;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Cliente cliente, Errors errors, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName(CADASTRO_VIEW);
		mv.addObject("cidades", cidadesView);
		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		if (errors.hasErrors()) {
			return mv;
		}

		// valida se o email já foi cadastrado por outro cliente
		if (clienteRepository.emailAlreadyExists(cliente.getEmail(), AppUtils.checkId(cliente.getId()))) {
			errors.rejectValue("email", null, "Email já está sendo usado!");
			return mv;
		}

		// valida se o cpf já foi cadastrado por outro cliente
		if (clienteRepository.cpfAlreadyExists(cliente.getCpf(), AppUtils.checkId(cliente.getId()))) {
			errors.rejectValue("cpf", null, "CPF já está sendo usado!");
			return mv;
		}

		// valida se o cnpj já foi cadastrado por outro cliente
		if (clienteRepository.cnpjAlreadyExists(cliente.getCnpj(), AppUtils.checkId(cliente.getId()))) {
			errors.rejectValue("cnpj", null, "CNPJ já está sendo usado!");
			return mv;
		}

		// valida se o celular já foi cadastrado por outro cliente
		if (clienteRepository.celularAlreadyExists(cliente.getTelefone1(), AppUtils.checkId(cliente.getId()))) {
			errors.rejectValue("telefone1", null, "Celular já está sendo usado!");
			return mv;
		}

		clienteRepository.save(cliente);
		attributes.addFlashAttribute("message", "Cliente salvo com sucesso!");

		return new ModelAndView("redirect:/clientes/novo");
	}

	@PostMapping("/excluir")
	public String excluir(@RequestParam("records") String records) {

		if (!records.isEmpty()) {

			List<Long> ids = new ArrayList<>();
			String[] tokens = records.split("\\s*,\\s*");

			for (String id : tokens)
				ids.add(Long.parseLong(id));

			clienteRepository.deleteSelected(ids);
		}

		return "redirect:/clientes/consulta";
	}

	public String getParamUrlPesquisaValue() {
		return "/clientes/consulta";
	}

	public String getParamUrlExcluirValue() {
		return "/clientes/excluir";
	}

}
