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
import org.springframework.web.bind.annotation.RestController;

import com.example.apiMidas.dtos.AgregarProductoDTO;
import com.example.apiMidas.dtos.CarritoDTO;
import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.ConfiguracionSeguridad;
import com.example.apiMidas.services.CarritoServiceImpl;
import com.example.apiMidas.services.UsuarioServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class CarritoController {
	
	@Autowired
	private CarritoServiceImpl csi;
	
	@Autowired
	private UsuarioServiceImpl usi;

	//Blindado - Administrador
	@GetMapping("/carritos/all/{user}")
	public ResponseEntity<List<Carrito>>getAllCarritos(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			List<Carrito> ul = csi.findAll();
			return new ResponseEntity<>(ul, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado
	@GetMapping("/carritos/my/{user}")
	public ResponseEntity <Carrito> getMyCarrito(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarToken(tokenActual, user)){
			Carrito c = csi.getMyCarrito(user);
			
			if(c == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			return new ResponseEntity<>(c, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
		
		
	}
	
	@GetMapping("/carritos/{id}")
	public ResponseEntity<Carrito> getCarrito(@PathVariable long id){
		Carrito p = csi.findById(id);
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		}
	}
	
	@PostMapping("/carritos")
	public ResponseEntity<Carrito> addCarrito(@RequestBody CarritoDTO carrito) {
			Carrito a = csi.addCarrito(carrito);
			return new ResponseEntity<>(a, HttpStatus.CREATED);
	}
	
	@PutMapping("/carritos/{id}")
	public ResponseEntity<Carrito> editCarrito(@RequestBody CarritoDTO carritoDTO, @PathVariable long id) {
		Carrito a = csi.editCarrito(carritoDTO, id);
		return new ResponseEntity<>(a,HttpStatus.OK);
	}
	
	//Blindado
	@PostMapping("/carritos/{carrito_id}/agregar_producto/my/{user}")
	public ResponseEntity<Carrito> agregarProducto(@RequestBody AgregarProductoDTO producto, @PathVariable long carrito_id, HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarToken(tokenActual, user)){
			if(csi.findById(carrito_id).getUsuarioId() != usi.findByUsername(user).getId()) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			Carrito a = csi.agregarProducto(producto, carrito_id);
			return new ResponseEntity<>(a,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	
	@DeleteMapping("/carritos/{id}")
	public ResponseEntity<Carrito> deleteCarrito(@PathVariable long id) {
		Carrito p = csi.findById(id);
		if(p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			csi.deleteCarrito(id);
			return new ResponseEntity<>(p, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/carritos")
	public ResponseEntity<String> deleteAllCarritos() {
		long a = csi.deleteAllCarritos();
		return new ResponseEntity<>("Se eliminaron "+a+" registros.",HttpStatus.OK);	
	}
}

