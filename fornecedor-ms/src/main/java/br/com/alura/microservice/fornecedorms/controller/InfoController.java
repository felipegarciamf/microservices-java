package br.com.alura.microservice.fornecedorms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.microservice.fornecedorms.model.InfoFornecedor;
import br.com.alura.microservice.fornecedorms.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

	@Autowired
	private InfoService infoService;

	@GetMapping("/{estado}")
	public InfoFornecedor getInfoPorEstado(String estado) {
		return infoService.getInfoPorEstado(estado);
	}

}
