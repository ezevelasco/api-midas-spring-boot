package com.example.apiMidas.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long carrito_id;
	
	@Column
	private Instant fechaCreado;
	
	@Column
	private long usuario_id;
	
	@Column
	private long montoTotal;
	
	@Column
	private long cantProductos;
	
	@Column
	private List<Long> productos = new ArrayList<>();
	
	public void agregarProducto(long l) {
		productos.add(l);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCarrito_id() {
		return carrito_id;
	}

	public void setCarrito_id(long carrito_id) {
		this.carrito_id = carrito_id;
	}

	

	public Instant getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(Instant fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	public long getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(long usuario_id) {
		this.usuario_id = usuario_id;
	}

	public long getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(long montoTotal) {
		this.montoTotal = montoTotal;
	}

	public long getCantProductos() {
		return cantProductos;
	}

	public void setCantProductos(long cantProductos) {
		this.cantProductos = cantProductos;
	}

	public List<Long> getProductos() {
		return productos;
	}

	public void setProductos(List<Long> productos) {
		this.productos = productos;
	}
	
	
}
