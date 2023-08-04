package com.example.apiMidas.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="carrito")
public class Carrito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private long cantProductos = 0;
	
	@Column
	private long total;
	
	@Column
	private String estado; // activo o inactivo
	
	@Column
	private long usuarioId;
	
	@Column
	private List<Long> productos = new ArrayList<>();
	
	
	public void vaciarProductos() {
		productos.clear();
	}
	
	public void agregarProducto(long a) {
		productos.add(a);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCantProductos() {
		return cantProductos;
	}

	public void setCantProductos(long cantProductos) {
		this.cantProductos = cantProductos;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

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

	public void setProductos(List<Long> productos) {
		this.productos = productos;
	}

	public List<Long> getProductos() {
		return productos;
	}

	

	


	

	
	
}
