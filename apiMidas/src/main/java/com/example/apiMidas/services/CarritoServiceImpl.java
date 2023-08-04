package com.example.apiMidas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiMidas.dtos.AgregarProductoDTO;
import com.example.apiMidas.dtos.CarritoDTO;
import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.Producto;
import com.example.apiMidas.models.Usuario;
import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.repositories.CarritoRepository;
import com.example.apiMidas.repositories.ProductoRepository;
import com.example.apiMidas.repositories.UsuarioRepository;

@Service
public class CarritoServiceImpl implements CarritoService{

	@Autowired
	private CarritoRepository carritoRepository;
	
	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Carrito getMyCarrito(String user) {
		List<Usuario> u = usuarioRepository.findByUsuario(user);
		if(u.size() != 1) {
			return null;
		}
		Carrito carr = carritoRepository.findByUsuario(u.get(0).getId());
		
		if(carr == null) {
			
			return null;
		}
		System.out.println(carr.getId());
		return carr;
	}
	
	@Override
	public List<Carrito> findAvailables(){
		return carritoRepository.findAll().stream().filter(Carrito -> Carrito.getEstado() == "activo").collect(Collectors.toList());
		
	}
	
	@Override
	public List<Carrito> findAll(){
		return carritoRepository.findAll();
	}
	
	@Override
	public Carrito findById(long id) {
		return carritoRepository.findById(id).orElse(null);
	}
	
	@Override
 	public Carrito addCarrito(CarritoDTO carrito) {
		
		Carrito c = new Carrito();
		
		c.setProductos(null);
		c.setEstado(carrito.getEstado());
		c.setUsuarioId(carrito.getUsuarioId());
		c.setCantProductos(0);
		c.setTotal(0);
		carritoRepository.save(c);
		return c;
	}
	
	@Override
	public Carrito editCarrito(CarritoDTO carrito, long id) {
		
		Carrito c = carritoRepository.findById(id).orElse(null);
		
		c.setUsuarioId(carrito.getUsuarioId());
		c.setEstado(carrito.getEstado());
		
		carritoRepository.save(c);
		return c;
	}
	
	@Override
	public Carrito agregarProducto(AgregarProductoDTO producto, long carrito_id) {
		if(producto.getCantidad() ==0 || producto.getProducto_id() == 0) {
			return null;
		}
		
		Carrito c = carritoRepository.findById(carrito_id).orElse(null);
		if(c == null) {
			return null;
		}
		
		Producto p = productoRepository.findById(producto.getProducto_id()).orElse(null);
		if(p==null) {
			return null;
		}
		if(p.getCantDisponible() < producto.getCantidad() || p.getEstado() == "inactivo") {
			return null;
		}
		
		p.setCantDisponible(p.getCantDisponible()-producto.getCantidad());
		
		c.setTotal(c.getTotal()+(p.getPrecio()*producto.getCantidad()));
		
		c.setCantProductos(c.getCantProductos()+producto.getCantidad());
		
		
		if(c.getCantProductos() == 0) {
			List<Long> ll = new ArrayList<>();
			
			for(int i=0;i<producto.getCantidad();i++) {
				ll.add(producto.getProducto_id());
			}
			c.setProductos(ll);
		}else {
			for(int i=0;i<producto.getCantidad();i++) {
				c.agregarProducto(producto.getProducto_id());
			}
		}
		carritoRepository.save(c);
		return c;
	}
	
	@Override
	public void deleteCarrito(long id) {
		carritoRepository.deleteById(id);
	}

	@Override
	public long deleteAllCarritos() {
		long a = carritoRepository.count();
		carritoRepository.deleteAll();
		return a;
	}

}
