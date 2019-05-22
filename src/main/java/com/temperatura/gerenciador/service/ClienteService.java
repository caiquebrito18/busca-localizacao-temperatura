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
	
	
	public ClienteDto salvar(ClienteDto clienteDto) {

		Cliente cliente = new Cliente();

		cliente.setNome(clienteDto.getNome());
		cliente.setLatitude(clienteDto.getLatitude());
		cliente.setLongitude(clienteDto.getLongitude());
		cliente.setTempMin(clienteDto.getTempMin());
		cliente.setTempMax(clienteDto.getTempMax());
		cliente.setIdade(clienteDto.getIdade());
		
		cliente = clienteRepository.save(cliente);
		
		if(cliente != null){
			clienteDto.setId(cliente.getId());
		}
		
		return clienteDto;
	}
	
	public List<Cliente> listar() {
		return clienteRepository.findAll();
	}


	public ClienteDto buscar(Long id) {
		Cliente cliente = clienteRepository.findById(id);
		ClienteDto clienteDto = new ClienteDto();
		
		if (cliente == null) {
			System.out.println("Nao existe este cliente cadastrado");
		}else{
			clienteDto.setId(cliente.getId());
			clienteDto.setNome(cliente.getNome());
			clienteDto.setLatitude(cliente.getLatitude());
			clienteDto.setLongitude(cliente.getLongitude());
			clienteDto.setTempMin(cliente.getTempMin());
			clienteDto.setTempMax(cliente.getTempMax());
			clienteDto.setIdade(cliente.getIdade());
		}
		
		return clienteDto;
	}

	public Cliente alterar(ClienteDto clienteDto) {

		Cliente clienteExistente = clienteRepository.findOne(clienteDto.getId());

		if(clienteExistente != null){
			clienteExistente.setNome(clienteDto.getNome());
			clienteExistente.setIdade(clienteDto.getIdade());	

			clienteRepository.save(clienteExistente);
		}

		return clienteExistente;
	}
	
	
	public ClienteDto deletar(Long id) {
		
		Cliente cliente = clienteRepository.findById(id);
		
		ClienteDto clienteDto = new ClienteDto();
		
		if (cliente != null) {
			clienteDto.setId(cliente.getId());
			clienteDto.setNome(cliente.getNome());
		}else if(cliente == null || cliente.getId() == null){
			return null;
		}
		
		clienteRepository.delete(id);
		
		return clienteDto;
	}
	
}
