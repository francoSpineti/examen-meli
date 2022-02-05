package com.mercadolibre.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Propiedades{

	@Value("${satelite.uno}")
	private String kenobi;	
	
	@Value("${satelite.dos}")
	private String skywalker;

	@Value("${satelite.tres}")
	private String sato;
	
	@Value("${kenobi.coordenadas}")
	private String kenobiCoordenadas;
	
	@Value("${skywalker.coordenadas}")
	private String skywalkerCoordenadas;
	
	@Value("${sato.coordenadas}")
	private String satoCoordenadas;

	public String getKenobi() {
		return kenobi;
	}

	public void setKenobi(String kenobi) {
		this.kenobi = kenobi;
	}

	public String getSkywalker() {
		return skywalker;
	}

	public void setSkywalker(String skywalker) {
		this.skywalker = skywalker;
	}

	public String getSato() {
		return sato;
	}

	public void setSato(String sato) {
		this.sato = sato;
	}

	public String getKenobiCoordenadas() {
		return kenobiCoordenadas;
	}

	public void setKenobiCoordenadas(String kenobiCoordenadas) {
		this.kenobiCoordenadas = kenobiCoordenadas;
	}

	public String getSkywalkerCoordenadas() {
		return skywalkerCoordenadas;
	}

	public void setSkywalkerCoordenadas(String skywalkerCoordenadas) {
		this.skywalkerCoordenadas = skywalkerCoordenadas;
	}

	public String getSatoCoordenadas() {
		return satoCoordenadas;
	}

	public void setSatoCoordenadas(String satoCoordenadas) {
		this.satoCoordenadas = satoCoordenadas;
	}
}
