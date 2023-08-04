package com.example.apiMidas.services;

import java.util.List;

import com.example.apiMidas.dtos.AgregarProductoDTO;
import com.example.apiMidas.dtos.CarritoDTO;
import com.example.apiMidas.models.Carrito;

public interface CarritoService {
	public List<Carrito> findAvailables();
	
	public List<Carrito> findAll();
	
	public Carrito getMyCarrito(String user);
	
	public Carrito findById(long id);
	
	public Carrito addCarrito(CarritoDTO carrito);
	
	public Carrito editCarrito(CarritoDTO carrito, long id);
	
	public Carrito agregarProducto(AgregarProductoDTO producto, long carrito_id);
	
	public void deleteCarrito(long id);
	
	public long deleteAllCarritos();
}
