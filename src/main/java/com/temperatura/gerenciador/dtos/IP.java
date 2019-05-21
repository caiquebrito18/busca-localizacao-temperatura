package com.temperatura.gerenciador.dtos;

import java.io.Serializable;

public class IP implements Serializable{
	
	private static final long serialVersionUID = 4915132258791738729L;

	private String status;
	
	private DataDto data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DataDto getData() {
		return data;
	}

	public void setData(DataDto data) {
		this.data = data;
	}
}
