package com.example.apiMidas.services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.apiMidas.models.Producto;
import com.example.apiMidas.repositories.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{
		
	@Autowired
	private ProductoRepository productoRepository;
	
	@Override
	public List<Producto> findAvailables(){
		return productoRepository.findByEstado("activo");
	}
	
	@Override
	public List<Producto> findAll(){
		return productoRepository.findAll();
	}
	
	@Override
	public Producto findById(long id) {
		return productoRepository.findById(id).orElse(null);
	}
	
	@Override
 	public boolean addProducto(Producto producto) {
		if(productoRepository.save(producto) !=  null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean editProducto(Producto producto, long id) {
		
		Producto p = productoRepository.findById(id).orElse(null);
		if(p == null) {
			return false;
		}
		p.setNombre(producto.getNombre());
		p.setCantDisponible(producto.getCantDisponible());
		p.setPrecio(producto.getPrecio());
		p.setEstado(producto.getEstado());
		;
		if(productoRepository.save(p) == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public void deleteProducto(long id) {
		productoRepository.deleteById(id);
	}

	@Override
	public long deleteAllProductos() {
		long a = productoRepository.count();
		productoRepository.deleteAll();
		return a;
	}
}
