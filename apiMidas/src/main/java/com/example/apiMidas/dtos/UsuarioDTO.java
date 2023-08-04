package com.example.apiMidas.dtos;

import jakarta.persistence.Column;

public class UsuarioDTO {
	
	private String usuario;
	
	
	private String password;
	
	
	private String nombre;
	
	private long clave;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getClave() {
		return clave;
	}

	public void setClave(long clave) {
		this.clave = clave;
	}

	

}
