package br.com.alura.microservice.lojams.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.converters.Auto;

import br.com.alura.microservice.lojams.client.FornecedorClient;
import br.com.alura.microservice.lojams.dto.CompraDto;
import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;

@Service
public class CompraService {


	@Autowired
	private FornecedorClient fornecedor;

	public void realizaCompra(CompraDto compra) {

		InfoFornecedorDto infoPorEstado = fornecedor.getInfoPorEstado(compra.getEndereco().getEstado());
		
		System.out.println(infoPorEstado.getEndereco());

	}

}
