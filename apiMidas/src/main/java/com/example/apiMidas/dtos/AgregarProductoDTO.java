package com.example.apiMidas.dtos;

import jakarta.persistence.Column;

public class AgregarProductoDTO {
	
	private long producto_id;
	
	private int cantidad;

	public long getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(long producto_id) {
		this.producto_id = producto_id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
	
}
