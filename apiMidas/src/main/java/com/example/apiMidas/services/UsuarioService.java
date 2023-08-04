package com.example.apiMidas.services;

import java.util.List;

import com.example.apiMidas.dtos.UsuarioDTO;
import com.example.apiMidas.models.Producto;
import com.example.apiMidas.models.Usuario;

public interface UsuarioService {
	public List<String> login(String user, String pass);
	
	public String register(UsuarioDTO usuario);
	
	public List<Usuario> findAvailables();
	
	public List<Usuario> findAll();
	
	public Usuario findById(long id);
	
	public Usuario findByUsername(String name);
	
	public boolean addUsuario(Usuario usuario);
	
	public boolean editUsuario(Usuario usuario, long id);
	
	public void deleteUsuario(long id);
	
	public long deleteAllUsuarios();
}
