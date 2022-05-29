package br.com.alura.microservice.lojams.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.alura.microservice.lojams.dto.InfoEntregaDto;
import br.com.alura.microservice.lojams.dto.VoucherDto;



@FeignClient("transportador")
public interface TransportadorClient {

	@RequestMapping(path="/entrega", method = RequestMethod.POST)
	public VoucherDto reservaEntrega(InfoEntregaDto pedidoDTO);
	
	
}
