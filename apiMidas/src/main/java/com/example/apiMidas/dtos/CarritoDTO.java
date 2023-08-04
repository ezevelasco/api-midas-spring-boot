package com.example.apiMidas.dtos;

public class CarritoDTO {

	private String estado; // activo o inactivo
	
	private long usuarioId;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(long usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
