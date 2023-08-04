package com.example.apiMidas.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.apiMidas.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{

	@Query(value = "SELECT u FROM Usuario u WHERE u.usuario = ?1")
    List<Usuario> findByUsuario(String usuario);
	
	@Query(value = "SELECT u FROM Usuario u WHERE u.estado = ?1")
    List<Usuario> findByEstado(String estado);
}
