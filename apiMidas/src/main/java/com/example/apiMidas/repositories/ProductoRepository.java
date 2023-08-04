package com.example.apiMidas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiMidas.models.Producto;
import com.example.apiMidas.models.Usuario;


public interface ProductoRepository  extends JpaRepository<Producto,Long>{
	
	@Query(value = "SELECT u FROM Producto u WHERE u.estado = ?1")
    List<Producto> findByEstado(String estado);
}
