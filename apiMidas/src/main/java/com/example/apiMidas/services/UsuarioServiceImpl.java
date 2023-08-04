package com.example.apiMidas.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.example.apiMidas.dtos.UsuarioDTO;
import com.example.apiMidas.models.Carrito;
import com.example.apiMidas.models.ConfiguracionSeguridad;
import com.example.apiMidas.models.Usuario;
import com.example.apiMidas.models.Usuario;
import com.example.apiMidas.repositories.CarritoRepository;
import com.example.apiMidas.repositories.UsuarioRepository;


@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private CarritoRepository cr;
	
	private HashMap<String, Integer> tokensActivos = new HashMap<String, Integer>();
	
	@Override
	public List<String> login(String user, String pass) {
		List<Usuario> l = ur.findByUsuario(user);
		List<String> output = new ArrayList<>();
		//carrito.id
		//rol
		//token
		
		if(l.size() == 0){
			output.add("No existe el usuario.");
			return output;
		}
		
		if(l.size() >1) {
			output.add("Bad data.");
			return output;
		}
		
		if(l.size() == 1){
			if(!l.get(0).getPassword().toString().equals(pass)){
				output.add("Contraseña errónea. Ingresada: "+pass+" , en db: "+l.get(0).getPassword());
				return output;
			}
			
			//Añado el token a la Configuración Global
			ConfiguracionSeguridad cs = ConfiguracionSeguridad.getInstancia();
			
			//Genéro el token único
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hashBytes = md.digest((user+Instant.now()).getBytes());
				StringBuilder sb = new StringBuilder();
	            for (byte b : hashBytes) {
	                sb.append(String.format("%02x", b));
	            }
	            String token = sb.toString();
	      
				
				cs.addToken(token, l.get(0).getRol(), user);
				
				List<Carrito> carr = cr.findAll();
				Carrito carrConcreto = null;
				for(int i=0; i< carr.size(); i++) {
					if(carr.get(i).getUsuarioId() == l.get(0).getId()) {
						carrConcreto = carr.get(i);
					}
				}
				System.out.println(l.get(0).getId());
				
				output.add(""+carrConcreto.getId());
				output.add(l.get(0).getRol());
				output.add(token);
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				output.add("Error generando el hash para el token.");
			}
			
		}
		return output;
	}
	
	@Override
	public String register(UsuarioDTO usuario) {
		
		if(ur.findByUsuario(usuario.getUsuario()).size() > 0) {
			return "El usuario ya existe, intente con uno distinto.";
		}else {
			Usuario u = new Usuario();
			u.setNombre(usuario.getNombre());
			u.setUsuario(usuario.getUsuario());
			u.setPassword(usuario.getPassword());
			u.setEstado("activo");
			
			if(usuario.getClave() == 8991) {
				u.setRol("administrador");
			}else{
				u.setRol("cliente");
			}
			ur.save(u);
			
			List<Usuario> ulist = ur.findByUsuario(u.getUsuario());
			Carrito c = new Carrito();
			c.setCantProductos(0);
			c.setEstado("activo");
			c.setUsuarioId(ulist.get(0).getId());
			cr.save(c);
			;
			return "Usuario creado correctamente! Su id de carrito es: "+c.getId();
		}
	}
	
	@Override
	public List<Usuario> findAvailables(){
		return ur.findByEstado("activo");
	
	}
	
	@Override
	public List<Usuario> findAll(){
		return ur.findAll();
	}
	
	@Override
	public Usuario findById(long id) {
		return ur.findById(id).orElse(null);
	}
	
	@Override
	public Usuario findByUsername(String name) {
		if(ur.findByUsuario(name).size() == 1) {
			return ur.findByUsuario(name).get(0);
		}
		return null;
	}
	
	@Override
 	public boolean addUsuario(Usuario Usuario) {
		if(ur.save(Usuario) !=  null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean editUsuario(Usuario Usuario, long id) {
		
		Usuario p = ur.findById(id).orElse(null);
		if(p == null) {
			return false;
		}
		p.setNombre(Usuario.getNombre());
		p.setUsuario(Usuario.getUsuario());
		p.setPassword(Usuario.getPassword());
		p.setRol(Usuario.getRol());
		p.setEstado(Usuario.getEstado());
		;
		if(ur.save(p) == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public void deleteUsuario(long id) {
		ur.deleteById(id);
	}

	@Override
	public long deleteAllUsuarios() {
		long a = ur.count();
		ur.deleteAll();
		return a;
	}
}
