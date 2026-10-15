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
	// parte 7 crea clase JDBC
	private static final Logger Log = LogManager.getLogger(VideojuegoJdbc.class);

	// insertar
	public Videojuego insertar(Videojuego videojuego) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = Conexion.getConnection();
			String sql;
			sql = """
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
		} catch (SQLException e) {
			Log.error("Error al insertar videojuego: " + e.getMessage());
			return null;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			String sql;
			sql = """
					SELECT * FROM videojuegos
					""";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Videojuego v = new Videojuego(rs.getString("codigo"), rs.getString("nombre"),
						rs.getString("plataforma"), rs.getDouble("precio"), rs.getBoolean("disponible"),
						rs.getString("genero"));
				lista.add(v);
			}
			Log.info("Videojuegos listados: " + lista.size());
		} catch (SQLException e) {
			Log.error("Error al listar videojuegos: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
			String sql;
			sql = """
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
		} catch (SQLException e) {
			Log.error("Error al buscar videojuego: " + e.getMessage());
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return v;
	}

	// actualizar
	public Videojuego actualizar(String codigo, Videojuego videojuego) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = Conexion.getConnection();
			String sql;
			sql = """
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
		} catch (SQLException e) {
			Log.error("Error al actualizar videojuego: " + e.getMessage());
			return null;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		} catch (SQLException e) {
			Log.error("Error al eliminar videojuego: " + e.getMessage());
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//prueba
	public static void main(String[] args) {
		VideojuegoJdbc jdbc = new VideojuegoJdbc();
		
		Videojuego nuevo = new Videojuego("VJ99", "Hollow Knight", "PC", 14.99, true, "Metroidvania");
		System.out.println("INSERTAR: " + jdbc.insertar(nuevo));

		// 2. Listar
		System.out.println("LISTAR:");
		for (Videojuego v : jdbc.listar()) {
			System.out.println("  " + v);
		}

		// 3. Buscar
		System.out.println("BUSCAR: " + jdbc.buscar("VJ99"));

		// 4. Actualizar
		Videojuego cambios = new Videojuego("VJ99", "HOLLOW", "SWITCH2", 19.99, false, "AVENTURA");
		System.out.println("ACTUALIZAR: " + jdbc.actualizar("VJ99", cambios));
		System.out.println("BUSCAR (después de actualizar): " + jdbc.buscar("VJ99"));

		// 5. Eliminar
		System.out.println("ELIMINAR: " + jdbc.eliminar("VJ99"));
		System.out.println("BUSCAR (después de eliminar): " + jdbc.buscar("VJ99"));
	}

}
