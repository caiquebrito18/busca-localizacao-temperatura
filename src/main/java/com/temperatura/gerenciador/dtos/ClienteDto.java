package com.temperatura.gerenciador.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class ClienteDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String idade;
	private String latitude;
	private String longitude;
	private String tempMax;
	private String tempMin;


	public ClienteDto() {
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(message = "Nome é obrigatório")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotNull(message = "Idade é obrigatória")
	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}
	
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTempMax() {
		return tempMax;
	}

	public void setTempMax(String tempMax) {
		this.tempMax = tempMax;
	}

	public String getTempMin() {
		return tempMin;
	}

	public void setTempMin(String tempMin) {
		this.tempMin = tempMin;
	}

	@Override
	public String toString() {
		return "ClienteDto [id=" + id + ", nome=" + nome + ", idade=" + idade + "]";
	}
	
}
