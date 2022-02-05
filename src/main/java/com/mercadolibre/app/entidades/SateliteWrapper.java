package com.mercadolibre.app.entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SateliteWrapper {

	private List<Satelite> satelites;

	public List<Satelite> getSatelites() {
		return satelites;
	}

	public void setSatelites(List<Satelite> satelites) {
		this.satelites = satelites;
	}
	
	public double[][] obtenerCordenadas(){
		
		double[][] retorno = new double[this.satelites.size()][];
		String[] aux = null;
		for (int i = 0; i < this.satelites.size(); i++) {
				aux = this.satelites.get(i).getCoordenada().toString().split(",");
				retorno[i] = Arrays.stream(aux).mapToDouble(Double::valueOf).toArray();
		}
		return retorno;
	}

	public double[] obtenerDistancias() {
		double[] distancias = new double[this.satelites.size()];
		for (int i = 0; i < this.satelites.size(); i++) {
			distancias[i] = this.satelites.get(i).getDistancia();
		}
		return distancias;
	}

	public List<List<String>> obtenerMensajes(){
		List<List<String>> mensajes = new ArrayList<List<String>>();
		for (int i = 0; i < this.satelites.size(); i++) {
			mensajes.add(this.satelites.get(i).getMensaje());
		}
		return mensajes;
	}
}
