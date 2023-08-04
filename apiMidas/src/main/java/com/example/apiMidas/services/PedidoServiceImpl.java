package com.example.apiMidas.services;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.Pedido;
import com.example.apiMidas.repositories.CarritoRepository;
import com.example.apiMidas.repositories.PedidoRepository;

@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepository pr;
	
	@Autowired
	private CarritoRepository cr;
	
	@Override
	public Pedido generarPedido(long carrito_id) {
		Pedido p = new Pedido();
		Carrito c = cr.findById(carrito_id).orElse(null);
		
		if(carrito_id == 0) {
			return null;
		}
		
		p.setCarrito_id(carrito_id);
		p.setCantProductos(c.getCantProductos());
		p.setFechaCreado(Instant.now());
		p.setMontoTotal(c.getTotal());
		
		for(int i=0; i<c.getProductos().size();i++){
			p.agregarProducto(c.getProductos().get(i));
		}
		
		p.setUsuario_id(c.getUsuarioId());
		
		pr.save(p);
		
		c.setCantProductos(0);
		c.vaciarProductos();
		c.setTotal(0);
		
		cr.save(c);
		return p;
	}

	@Override
	public List<Pedido> findAll(){
		return pr.findAll();
	}
}
