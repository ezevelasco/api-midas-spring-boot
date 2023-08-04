package com.example.apiMidas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.apiMidas.models.Producto;


public interface ProductoService {

	public List<Producto> findAvailables();
	
	public List<Producto> findAll();
	
	public Producto findById(long id);
	
	public boolean addProducto(Producto producto);
	
	public boolean editProducto(Producto producto, long id);
	
	public void deleteProducto(long id);
	
	public long deleteAllProductos();
}
