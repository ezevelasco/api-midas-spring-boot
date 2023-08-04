package com.example.apiMidas.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;


// Clase tipo Singleton para el manejo de tokens
@Component
public final class ConfiguracionSeguridad {
	
	// Instancia única
	private static ConfiguracionSeguridad cs = null;
	
	// Formato: token, rol
	private Map<String, String> listadoTokensRol;
	
	// Formato: token, username
	private Map<String, String> listadoTokensUsuario;
	
	// Formato: token, fechahora
	private Map<String, Instant> listadoTokensFecha;
	
	// método único para devolver la misma instancia
	public static ConfiguracionSeguridad getInstancia() {
		if(cs == null) {
			cs = new ConfiguracionSeguridad();
		}
		return cs;
	}
	
	// Constructor privado
	private ConfiguracionSeguridad() {	
			listadoTokensRol = new HashMap<>();
			listadoTokensUsuario = new HashMap<>();
			listadoTokensFecha = new HashMap<>();
		}
		
	// Métodos
	public void addToken(String token, String rol, String user) {
		listadoTokensRol.put(token, rol);
		Instant i = Instant.now();
		i = i.plusSeconds(300);
		listadoTokensFecha.put(token, i);
		listadoTokensUsuario.put(token, user);
	}
	
	public boolean verificarToken(String token, String user) {	

		if(listadoTokensUsuario.containsKey(token) && listadoTokensUsuario.get(token).equals(user)) {
			
			if(Instant.now().isBefore(listadoTokensFecha.get(token))) {
				return true;
			}else {
				listadoTokensFecha.remove(token);
				return false;
			}
		}
		return false;
	}
	
	public boolean verificarTokenAdministrador(String token, String user) {	
		if(listadoTokensUsuario.containsKey(token) && listadoTokensUsuario.get(token).equals(user) && listadoTokensRol.get(token).equals("administrador")) {
			
			if(Instant.now().isBefore(listadoTokensFecha.get(token))) {
				return true;
			}else {
				listadoTokensFecha.remove(token);
				return false;
			}
		}
		return false;
	}
	
	
}
