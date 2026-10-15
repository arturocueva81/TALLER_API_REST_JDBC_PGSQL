package com.krakedev.jdbc.videojuegos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.krakedev.jdbc.Conexion;
import com.krakedev.videojuegos.entidades.Videojuego;

public class VideojuegoJdbc {
	private static final Logger Log = LogManager.getLogger(VideojuegoJdbc.class);

	// insertar
	public Videojuego insertar(Videojuego videojuego) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = Conexion.getConnection();
			String sql = """
					INSERT INTO videojuegos (codigo, nombre, plataforma, precio, disponible, genero) VALUES (?, ?, ?, ?, ?, ?)
					""";
			ps = con.prepareStatement(sql);
			ps.setString(1, videojuego.getCodigo());
			ps.setString(2, videojuego.getNombre());
			ps.setString(3, videojuego.getPlataforma());
			ps.setDouble(4, videojuego.getPrecio());
			ps.setBoolean(5, videojuego.isDisponible());
			ps.setString(6, videojuego.getGenero());
			ps.executeUpdate();
			Log.info("Videojuego insertado: " + videojuego.getCodigo());
			return videojuego;
		} catch (Exception e) {
			Log.error("Error al insertar videojuego: " + e.getMessage());
			return null;
		} finally {
			cerrar(con, ps, null);
		}
	}

	// listar
	public List<Videojuego> listar() {
		List<Videojuego> lista = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Conexion.getConnection();
			String sql = """
					SELECT * FROM videojuegos
					""";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Videojuego v = new Videojuego(
						rs.getString("codigo"), 
						rs.getString("nombre"),
						rs.getString("plataforma"), 
						rs.getDouble("precio"), 
						rs.getBoolean("disponible"),
						rs.getString("genero"));
				lista.add(v);
			}
			Log.info("Videojuegos listados: " + lista.size());
		} catch (Exception e) {
			Log.error("Error al listar videojuegos: " + e.getMessage());
		} finally {
			cerrar(con, ps, rs);
		}
		return lista;
	}

	// buscar
	public Videojuego buscar(String codigo) {
		Videojuego v = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = Conexion.getConnection();
			String sql = """
					SELECT codigo, nombre, plataforma, precio, disponible, genero FROM videojuegos WHERE codigo = ?
					""";
			ps = con.prepareStatement(sql);
			ps.setString(1, codigo);
			rs = ps.executeQuery();
			if (rs.next()) {
				v = new Videojuego(rs.getString("codigo"), rs.getString("nombre"), rs.getString("plataforma"),
						rs.getDouble("precio"), rs.getBoolean("disponible"), rs.getString("genero"));
				Log.info("Videojuego encontrado: " + codigo);
			} else {
				Log.warn("No existe el videojuego: " + codigo);
			}
		} catch (Exception e) {
			Log.error("Error al buscar videojuego: " + e.getMessage());
		} finally {
			cerrar(con, ps, rs);
		}
		return v;
	}

	// actualizar
	public Videojuego actualizar(String codigo, Videojuego videojuego) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = Conexion.getConnection();
			String sql = """
					UPDATE videojuegos SET nombre = ?, plataforma = ?, precio = ?, disponible = ?, genero = ? WHERE codigo = ?
					""";
			ps = con.prepareStatement(sql);
			ps.setString(1, videojuego.getNombre());
			ps.setString(2, videojuego.getPlataforma());
			ps.setDouble(3, videojuego.getPrecio());
			ps.setBoolean(4, videojuego.isDisponible());
			ps.setString(5, videojuego.getGenero());
			ps.setString(6, codigo);
			int filas = ps.executeUpdate();
			if (filas > 0) {
				videojuego.setCodigo(codigo);
				Log.info("Videojuego actualizado: " + codigo);
				return videojuego;
			}
			Log.warn("No se actualizó, no existe el código: " + codigo);
			return null;
		} catch (Exception e) {
			Log.error("Error al actualizar videojuego: " + e.getMessage());
			return null;
		} finally {
			cerrar(con, ps, null);
		}
	}

	// eliminar
	public boolean eliminar(String codigo) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = Conexion.getConnection();
			ps = con.prepareStatement("DELETE FROM videojuegos WHERE codigo = ?");
			ps.setString(1, codigo);
			int filas = ps.executeUpdate();
			if (filas > 0) {
				Log.info("Videojuego eliminado: " + codigo);
				return true;
			}
			Log.warn("No se eliminó, no existe el código: " + codigo);
			return false;
		} catch (Exception e) {
			Log.error("Error al eliminar videojuego: " + e.getMessage());
			return false;
		} finally {
			cerrar(con, ps, null);
		}
	}

	// cerrar conexiones
	private void cerrar(
			Connection con, 
			PreparedStatement ps, 
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
				Log.info("Conexión cerrada");
			}
		} catch (SQLException e) {
			Log.error("Error al cerrar : " + e.getMessage());
		}
	}
}