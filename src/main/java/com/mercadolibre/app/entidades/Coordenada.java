package com.mercadolibre.app.entidades;

public class Coordenada {

	private double coordenadaX;
	private double coordenadaY;
	
	public Coordenada() {}

	public Coordenada(double[] coordenadas) {
		this.coordenadaX = coordenadas[0];
		this.coordenadaY = coordenadas[1];
	}

	public double getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public double getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	@Override
	public String toString() {
		 return coordenadaX+","+coordenadaY;
	}
	
}
