package br.com.security.controller;

import br.com.security.filters.FiltroConsulta;
import br.com.security.model.Empregado;
import br.com.security.repository.CidadeRepository;
import br.com.security.repository.EmpregadoRepository;
import br.com.security.repository.IEmpregadoRepository;
import br.com.security.util.AppUtils;
import br.com.security.util.Debug;
import br.com.security.wrapper.CidadeView;
import br.com.security.wrapper.CustomPage;
import br.com.security.wrapper.PageWrapper;

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

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/funcionarios/")
public class EmpregadoController {

	public final String CADASTRO_VIEW = "/empregado/cadastro";
	public final String CONSULTA_VIEW = "/empregado/consulta";

	public final String PARAM_CIDADES = "cidades";
	public final String PARAM_FILTRO = "filtro";

	public final String PARAM_URL_PESQUISA = "url_pesquisa";
	public final String PARAM_URL_EXCLUIR = "url_excluir";

	@Autowired
	private EmpregadoRepository empregadoRepository;

	@Autowired
	private IEmpregadoRepository customEmpregadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	private List<CidadeView> cidadesView;

	@PostConstruct
	public void init() {
		cidadesView = cidadeRepository.findAllOrderByEstadoCidade();
	}

	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("cidades", cidadesView);

		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		Empregado empregado = new Empregado();
		empregado.setAtivo(true);

		mv.addObject(empregado);

		return mv;
	}

	@PostMapping("/salvar")
	public ModelAndView salvar(@Validated Empregado empregado, Errors errors, RedirectAttributes attributes) {

		ModelAndView mv = new ModelAndView();
		mv.setViewName(CADASTRO_VIEW);
		mv.addObject("cidades", cidadesView);

		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		Debug.log("Salvar", empregado.toString());

		if (errors.hasErrors()) {

			if (empregado.getId() == null) {
				return mv;
			} else if (!errors.hasFieldErrors("senha") && !errors.hasFieldErrors("senhaC")) {
				return mv;
			}

		}

		// valida se o login já foi cadastrado
		if (empregadoRepository.loginAlreadyExist(empregado.getLogin(), AppUtils.checkId(empregado.getId()))) {
			errors.rejectValue("login", null, "Login já está sendo usado!");
			return mv;
		}

		if (empregado.getId() == null && !empregado.getSenha().equals(empregado.getSenhaC())) {
			errors.rejectValue("senha", null, "As senhas não correspondem!");
			return mv;
		}

		if (empregado.getId() == null)
			empregadoRepository.save(empregado);
		else
			empregadoRepository.edit(empregado.getNome(), empregado.getLogin(), empregado.getTelefone1(),
					empregado.getTelefone2(), empregado.getLogradouro(), empregado.getNumero(), empregado.getBairro(),
					empregado.getCep(), empregado.getCidade(), empregado.isAtivo(), empregado.getDataAlteracao(),
					empregado.getId());

		attributes.addFlashAttribute("message", "Funcionário salvo com sucesso!");

		return new ModelAndView("redirect:/funcionarios/novo");
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

		int countFiltrados = customEmpregadoRepository.countByField(pageable, filtro);
		List<Empregado> filtrados = customEmpregadoRepository.findByField(pageable, filtro);

		CustomPage<Empregado> empregadosPage = new CustomPage<>(countFiltrados, filtrados, pageable);

		PageWrapper<Empregado> page = new PageWrapper<Empregado>(empregadosPage, "/funcionarios/consulta");

		model.addAttribute("empregados", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("filtro", filtro);

		model.addAttribute(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		model.addAttribute(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		return CONSULTA_VIEW;
	}

	public String getParamUrlValue() {
		return "/funcionarios/consulta";
	}

	@GetMapping("/editar/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(empregadoRepository.findOne(id));
		mv.addObject("cidades", cidadesView);

		mv.addObject(PARAM_URL_PESQUISA, getParamUrlPesquisaValue());
		mv.addObject(PARAM_URL_EXCLUIR, getParamUrlExcluirValue());

		return mv;
	}

	@PostMapping("/excluir")
	public String excluir(@RequestParam("records") String records) {

		if (!records.isEmpty()) {

			List<Long> ids = new ArrayList<>();
			String[] tokens = records.split("\\s*,\\s*");

			for (String id : tokens)
				ids.add(Long.parseLong(id));

			empregadoRepository.deleteSelected(ids);
		}

		return "redirect:/funcionarios/consulta";
	}

	public String getParamUrlPesquisaValue() {
		return "/funcionarios/consulta";
	}

	public String getParamUrlExcluirValue() {
		return "/funcionarios/excluir";
	}
}
