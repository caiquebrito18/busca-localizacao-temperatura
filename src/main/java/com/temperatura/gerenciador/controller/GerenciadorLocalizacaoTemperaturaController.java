package com.temperatura.gerenciador.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.temperatura.gerenciador.dtos.ClienteDto;
import com.temperatura.gerenciador.entities.Cliente;
import com.temperatura.gerenciador.responses.Response;
import com.temperatura.gerenciador.service.ClienteService;

@RestController
@RequestMapping("api/clientes")
public class GerenciadorLocalizacaoTemperaturaController {

	@Autowired
	private ClienteService clienteService;
	
	@PostMapping(path = "/new")
	public ResponseEntity<Response<Cliente>> cadastrar(@Valid @RequestBody ClienteDto clienteDto, BindingResult result) {
		Response<Cliente> response = new Response<Cliente>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Cliente clienteSalvo = this.clienteService.salvar(clienteDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(clienteDto.getId())
				.toUri();
		response.setData(clienteSalvo);
		return ResponseEntity.created(location).body(response);
	}
	
	@GetMapping
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> clientes = clienteService.listar();
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Response<Cliente>> buscar(@PathVariable("id") Long id) {
  
		Cliente cliente = clienteService.buscar(id);
		Response<Cliente> response = new Response<Cliente>();
		response.setData(cliente);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<List<Cliente>> delete(@PathVariable("id") Long id) {
		
		Cliente cliente = clienteService.buscarSemTratativa(id);
		List<Cliente> clientes = clienteService.deletar(cliente);

		return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	
	
	
}
