package com.example.apiMidas.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.ConfiguracionSeguridad;
import com.example.apiMidas.models.Producto;
import com.example.apiMidas.services.ProductoServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class ProductoController {
	
	@Autowired
	private ProductoServiceImpl psi;
	
	//Blindado - Administrador
	@GetMapping("/productos/all/{user}")
	public ResponseEntity<List<Producto>>getProductos(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			List<Producto> pl = psi.findAll();
			return new ResponseEntity<>(pl, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	//Blindado
	@GetMapping("/productos/activos/my/{user}")
	public ResponseEntity<List<Producto>>getProductosActivos(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarToken(tokenActual, user)){
			List<Producto> pl = psi.findAvailables();
			return new ResponseEntity<>(pl, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@GetMapping("/productos/{id}/my/{user}")
	public ResponseEntity<Producto> getProducto(@PathVariable long id, HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			Producto p = psi.findById(id);
			System.out.println(p.getNombre());
			return new ResponseEntity<>(p, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@PostMapping("/productos/add/my/{user}")
	public ResponseEntity<Producto> addProducto(@RequestBody Producto producto, HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			if(psi.addProducto(producto)) {
				return new ResponseEntity<>(producto, HttpStatus.CREATED);
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@PutMapping("/productos/{id}/my/{user}")
	public ResponseEntity<Producto> editProducto(@RequestBody Producto producto, @PathVariable long id, HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			if(psi.editProducto(producto, id)) {
				return new ResponseEntity<>(producto,HttpStatus.OK);
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@DeleteMapping("/productos/{id}/my/{user}")
	public ResponseEntity<Producto> deleteProducto(@PathVariable long id, HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			Producto p = psi.findById(id);
			if(p == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else{
				psi.deleteProducto(id);
				return new ResponseEntity<>(p, HttpStatus.OK);
			}
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@DeleteMapping("/productos/my/{user}")
	public ResponseEntity<String> deleteProductos(HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			long a = psi.deleteAllProductos();
			return new ResponseEntity<>("Se eliminaron "+a+" registros.",HttpStatus.OK);	
		}else{
			return new ResponseEntity<>( "Error de autenticación, debe logearse primero.",HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
