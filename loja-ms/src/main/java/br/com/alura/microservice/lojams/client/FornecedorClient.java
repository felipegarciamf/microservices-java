package br.com.alura.microservice.lojams.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;
import br.com.alura.microservice.lojams.dto.InfoPedidoDto;
import br.com.alura.microservice.lojams.dto.ItemDaCompraDto;

@FeignClient("fornecedor")
public interface FornecedorClient {

	@RequestMapping(method = RequestMethod.GET, value="/info/{estado}")
	InfoFornecedorDto getInfoPorEstado(@PathVariable String estado);

	@RequestMapping(method = RequestMethod.POST, value="/pedido")
	InfoPedidoDto realizaPedido(List<ItemDaCompraDto> itens);

}
