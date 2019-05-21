package com.temperatura.gerenciador.responses;

import com.temperatura.gerenciador.dtos.ClienteDto;


public class Response {
	
	private String mensagem;
	
	private ClienteDto cliente;
	
	
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public Response(String mensagem, ClienteDto cliente) {
		this.mensagem = mensagem;
		this.cliente = cliente;
	}

	public Response() {
		
	}
	
}
