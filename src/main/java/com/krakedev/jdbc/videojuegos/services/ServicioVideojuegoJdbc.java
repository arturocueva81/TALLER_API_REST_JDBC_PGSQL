package com.krakedev.jdbc.videojuegos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krakedev.jdbc.videojuegos.VideojuegoJdbc;
import com.krakedev.videojuegos.entidades.Videojuego;

@Service
public class ServicioVideojuegoJdbc {
	
	private VideojuegoJdbc vjJdbc = new VideojuegoJdbc();

	public Videojuego crear(Videojuego videojuego) {
		return vjJdbc.insertar(videojuego);
	}

	public List<Videojuego> listar() {
		return vjJdbc.listar();
	}

	public Videojuego buscarPorCodigo(String codigo) {
		return vjJdbc.buscar(codigo);
	}

	public Videojuego actualizar(String codigo, Videojuego videojuego) {
		return vjJdbc.actualizar(codigo, videojuego);
	}

	public boolean eliminar(String codigo) {
		return vjJdbc.eliminar(codigo);
	}

}
