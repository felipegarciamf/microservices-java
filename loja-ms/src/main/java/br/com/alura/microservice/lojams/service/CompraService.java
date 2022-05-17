package br.com.alura.microservice.lojams.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.lojams.client.FornecedorClient;
import br.com.alura.microservice.lojams.dto.CompraDto;
import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;
import br.com.alura.microservice.lojams.dto.InfoPedidoDto;
import br.com.alura.microservice.lojams.model.Compra;

@Service
public class CompraService {

	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	@Autowired
	private FornecedorClient fornecedor;


	@HystrixCommand(fallbackMethod = "realizaCompraFallback")
	public Compra realizaCompra(CompraDto compra) {
		
		String estado = compra.getEndereco().getEstado();
		LOG.info("buscando informações do fornecedor de {}", estado);
		InfoFornecedorDto infoFornecedor = fornecedor.getInfoPorEstado(estado);

		
		LOG.info("realizando um pedido");
		InfoPedidoDto pedido = fornecedor.realizaPedido(compra.getItens());

		System.out.println(infoFornecedor.getEndereco());

		Compra compraSalva = new Compra();

		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return compraSalva;

	}
	
	public Compra realizaCompraFallback(CompraDto compra) {
		
	
		Compra compraFallback = new Compra();
		
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		
		return compraFallback;
	}

}
