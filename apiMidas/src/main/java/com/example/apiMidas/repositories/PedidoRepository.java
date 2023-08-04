package com.example.apiMidas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long>{

}
