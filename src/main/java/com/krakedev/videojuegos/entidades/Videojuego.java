package com.krakedev.videojuegos.entidades;

public class Videojuego {
	
	// parte 5 atributos de la clase
	private String codigo;
	private String nombre;
	private String plataforma;
	private double precio;
	private boolean disponible;
	private String genero;
	
	//parte 5 getters y setters
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	
	//parte 5 constructores
		//vacio
	public Videojuego() {
		super();
	}
	//con parametros
	public Videojuego(String codigo, String nombre, String plataforma, double precio, boolean disponible,
			String genero) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.plataforma = plataforma;
		this.precio = precio;
		this.disponible = disponible;
		this.genero = genero;
	}
	
	//parte 5 metodo toString
	@Override
	public String toString() {
		return " [codigo = " + codigo + 
				", nombre = " + nombre + 
				", plataforma = " + plataforma + 
				", precio = " + precio + 
				", disponible = " + disponible + 
				", genero = " + genero + 
				"]";
	}
}