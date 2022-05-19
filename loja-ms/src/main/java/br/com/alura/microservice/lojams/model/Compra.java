package br.com.alura.microservice.lojams.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Compra {

	@Id
	private Long pedidoId;

	private int tempoDePreparo;

	private String enderecoDestino;

	public String getEnderecoDestino() {
		return enderecoDestino;
	}

	public void setEnderecoDestino(String enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}

	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public int getTempoDePreparo() {
		return tempoDePreparo;
	}

	public void setTempoDePreparo(int tempoDePreparo) {
		this.tempoDePreparo = tempoDePreparo;
	}

}
