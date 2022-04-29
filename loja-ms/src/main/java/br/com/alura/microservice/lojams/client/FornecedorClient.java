package br.com.alura.microservice.lojams.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;


@FeignClient("fornecedor")
public interface FornecedorClient {

	@RequestMapping("/info/{estado}")
	InfoFornecedorDto getInfoPorEstado(@PathVariable String estado);

}
