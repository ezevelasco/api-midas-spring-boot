package com.example.apiMidas.services;

import java.util.List;

import com.example.apiMidas.models.Pedido;

public interface PedidoService {

	public List<Pedido> findAll();
	public Pedido generarPedido(long carrito_id);
}
