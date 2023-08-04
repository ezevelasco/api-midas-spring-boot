package com.example.apiMidas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.apiMidas.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito,Long>{
	
	@Query(value = "SELECT u FROM Carrito u WHERE u.usuarioId= ?1")
	public Carrito findByUsuario(long usuarioId);
}
