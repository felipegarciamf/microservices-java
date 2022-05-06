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
import br.com.alura.microservice.lojams.dto.InfoPedidoDto;
import br.com.alura.microservice.lojams.model.Compra;

@Service
public class CompraService {


	@Autowired
	private FornecedorClient fornecedor;

	public Compra realizaCompra(CompraDto compra) {

		InfoFornecedorDto infoPorEstado = fornecedor.getInfoPorEstado(compra.getEndereco().getEstado());
		
		InfoPedidoDto pedido = fornecedor.realizaPedido(compra.getItens());
		
		System.out.println(infoPorEstado.getEndereco());
		
		Compra compraSalva = new Compra();
		
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		
		return compraSalva;

	}

}
