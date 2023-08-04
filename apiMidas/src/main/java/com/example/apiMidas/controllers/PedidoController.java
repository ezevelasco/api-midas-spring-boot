package com.example.apiMidas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiMidas.dtos.CarritoDTO;
import com.example.apiMidas.dtos.PedidoDTO;
import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.ConfiguracionSeguridad;
import com.example.apiMidas.models.Pedido;
import com.example.apiMidas.services.CarritoServiceImpl;
import com.example.apiMidas.services.PedidoServiceImpl;
import com.example.apiMidas.services.UsuarioServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/")
public class PedidoController {
	
	@Autowired
	private PedidoServiceImpl psi;
	@Autowired
	private CarritoServiceImpl csi;
	@Autowired
	private UsuarioServiceImpl usi;
	
	//Blindado
	@PostMapping("/pedidos/my/{user}")
	public ResponseEntity<Pedido> addPedido(@RequestBody PedidoDTO pedido, HttpServletRequest request, @PathVariable String user) {
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarToken(tokenActual, user)){
			if(csi.findById(pedido.getCarrito_id()).getUsuarioId() != usi.findByUsername(user).getId()) {
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
			Pedido p = psi.generarPedido(pedido.getCarrito_id());
			if(p == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(p, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	//Blindado - Administrador
	@GetMapping("/pedidos/my/{user}")
	public ResponseEntity<List<Pedido>>getPedidos(HttpServletRequest request, @PathVariable String user){
		//Verifico el token
		HttpSession session = request.getSession();
		String tokenActual = (String) session.getAttribute("token-midas");
		if(ConfiguracionSeguridad.getInstancia().verificarTokenAdministrador(tokenActual, user)){
			List<Pedido> ul = psi.findAll();
			return new ResponseEntity<>(ul, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}
}
