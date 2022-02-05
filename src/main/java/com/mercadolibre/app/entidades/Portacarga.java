package com.mercadolibre.app.entidades;

public class Portacarga extends Nave{

	private String mensaje;	
	
	public Portacarga() {}

	public Portacarga(Coordenada coordenada,String mensaje) {
		this.setCoordenada(coordenada);
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
