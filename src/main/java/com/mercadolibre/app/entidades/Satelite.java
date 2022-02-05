package com.mercadolibre.app.entidades;

import java.util.List;

public class Satelite extends Nave{

	private String nombre;
	private List<String> mensaje;
	private double distancia;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<String> getMensaje() {
		return mensaje;
	}
	public void setMensaje(List<String> mensaje) {
		this.mensaje = mensaje;
	}
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
}
