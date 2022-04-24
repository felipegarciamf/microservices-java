package br.com.alura.microservice.lojams.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.alura.microservice.lojams.dto.CompraDto;
import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;


@Service
public class CompraService {

	
	public void realizaCompra(CompraDto compra) {

		RestTemplate client = new RestTemplate();

		ResponseEntity<InfoFornecedorDto> exchange = client.exchange("http://fornecedor:8081/info/" + compra.getEndereco().getEstado(), HttpMethod.GET, null,
				InfoFornecedorDto.class);

		
		System.out.println(exchange.getBody().getEndereco());
	}

}
