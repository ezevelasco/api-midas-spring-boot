package com.example.apiMidas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.apiMidas.dtos.UsuarioDTO;
import com.example.apiMidas.models.ConfiguracionSeguridad;
import com.example.apiMidas.models.Producto;
import com.example.apiMidas.models.Usuario;
import com.example.apiMidas.services.UsuarioServiceImpl;

import ch.qos.logback.core.subst.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class UsuarioController {
	
	@Autowired
	private UsuarioServiceImpl usi;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpServletRequest request){
		
		List<String> a = usi.login(username,password);
		//carrito.id
		//rol
		//token
		if(a.size()==1) {
			return new ResponseEntity<>(a.get(0),HttpStatus.OK);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("token-midas", a.get(2));
		
		return new ResponseEntity<>("Bienvenido "+username
									+", rol: "+a.get(1)
									+", carrito_id: "+a.get(0)
									+", token: "+a.get(2)
									+", duración: 5 minutos"
									, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UsuarioDTO usuario){
		return new ResponseEntity<>(usi.register(usuario), HttpStatus.OK);
	}

	//Blindado - Administrador
	@GetMapping("/usuarios/my/{user}")
	public ResponseEntity<List<Usuario>>getUsuarios(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			List<Usuario> ul = usi.findAll();
			return new ResponseEntity<>(ul,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@GetMapping("/usuarios/activos/my/{user}")
	public ResponseEntity<List<Usuario>>getUsuariosActivos(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			List<Usuario> ul = usi.findAvailables();
			return new ResponseEntity<>(ul,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable long id){
		Usuario p = usi.findById(id);
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		}
	}
	
	@PostMapping("/usuarios")
	public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
		if(usi.addUsuario(usuario)) {
			return new ResponseEntity<>(usuario, HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> editUsuario(@RequestBody Usuario usuario, @PathVariable long id) {
		if(usi.editUsuario(usuario, id)) {
			return new ResponseEntity<>(usuario,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<Usuario> deleteUsuario(@PathVariable long id) {
		Usuario p = usi.findById(id);
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			usi.deleteUsuario(id);
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/usuarios")
	public ResponseEntity<String> deleteUsuarios() {
		long a = usi.deleteAllUsuarios();
		return new ResponseEntity<>("Se eliminaron "+a+" registros.",HttpStatus.OK);	
	}
}

