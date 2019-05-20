package com.temperatura.gerenciador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.temperatura.gerenciador.dtos.ClienteDto;
import com.temperatura.gerenciador.entities.Cliente;
import com.temperatura.gerenciador.repositories.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}
	
	public Cliente salvar(ClienteDto clienteDto) {

		Cliente cliente = new Cliente();

		cliente.setNome(clienteDto.getNome());
		cliente.setIdade(clienteDto.getIdade());
		return clienteRepository.save(cliente);
	}

	public Cliente buscar(Long id) {
		Cliente cliente = clienteRepository.findOne(id);

		if (cliente == null) {
			System.out.println("Não existe este cliente cadastrado");
		}
		return cliente;
	}
}
