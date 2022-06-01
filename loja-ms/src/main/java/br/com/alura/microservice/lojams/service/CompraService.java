package br.com.alura.microservice.lojams.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.alura.microservice.lojams.client.FornecedorClient;
import br.com.alura.microservice.lojams.client.TransportadorClient;
import br.com.alura.microservice.lojams.dto.CompraDto;
import br.com.alura.microservice.lojams.dto.InfoEntregaDto;
import br.com.alura.microservice.lojams.dto.InfoFornecedorDto;
import br.com.alura.microservice.lojams.dto.InfoPedidoDto;
import br.com.alura.microservice.lojams.dto.VoucherDto;
import br.com.alura.microservice.lojams.model.Compra;
import br.com.alura.microservice.lojams.model.CompraState;
import br.com.alura.microservice.lojams.repository.CompraRepository;

@Service
public class CompraService {

	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);

	@Autowired
	private CompraRepository compraRepository;

	@Autowired
	private FornecedorClient fornecedor;

	@Autowired
	private TransportadorClient transportador;

	@HystrixCommand(threadPoolKey = "getByIdThreadPool")
	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
	
	public Compra reprocessaCompra(Long id) {
		return null;
	}
	
	public Compra cancelaCompra(Long id) {
		return null;
	}
	

	@HystrixCommand(fallbackMethod = "realizaCompraFallback", threadPoolKey = "realizaCompraThreadPool")
	public Compra realizaCompra(CompraDto compra) {

		Compra compraSalva = new Compra();
		compraSalva.setEnderecoDestino(compra.getEndereco().toString());
		compraSalva.setState(CompraState.RECEBIDO);
		compraRepository.save(compraSalva);
		compra.setCompraId(compraSalva.getId());

		InfoFornecedorDto info = fornecedor.getInfoPorEstado(compra.getEndereco().getEstado());
		InfoPedidoDto pedido = fornecedor.realizaPedido(compra.getItens());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setPedidoId(pedido.getId());

		compraSalva.setState(CompraState.PEDIDO_REALIZADO);
		compraRepository.save(compraSalva);

		if (1 == 1) {
			throw new RuntimeException();
		}

		InfoEntregaDto entregaDto = new InfoEntregaDto();
		entregaDto.setPedidoId(pedido.getId());
		entregaDto.setDataParaEntrega(LocalDate.now().plusDays(pedido.getTempoDePreparo()));
		entregaDto.setEnderecoOrigem(info.getEndereco());
		entregaDto.setEnderecoDestino(compra.getEndereco().toString());
		VoucherDto voucher = transportador.reservaEntrega(entregaDto);
		compraSalva.setDataParaEntrega(voucher.getPrevisaoParaEntrega());
		compraSalva.setVoucher(voucher.getNumero());

		compraSalva.setState(CompraState.RESERVA_ENTREGA_REALIZADA);
		compraRepository.save(compraSalva);

		return compraSalva;

	}

	public Compra realizaCompraFallback(CompraDto compra) {
		Optional<Compra> compraSalva = compraRepository.findById(compra.getCompraId());
		if (compraSalva.isEmpty()) {
			Compra compraFallback = new Compra();
			compraFallback.setEnderecoDestino(compra.getEndereco().toString());
			return compraFallback;
		}

		return compraSalva.get();
	}

}
