package com.mercadolibre.app.iservice;

import java.util.List;

import org.springframework.http.RequestEntity;

import com.mercadolibre.app.entidades.SateliteWrapper;
import com.mercadolibre.app.utils.Validador;

public interface ISateliteService {

	double[] getLocation(double[][] coordenadas, double [] distancias);
	String getMessage(List<List<String>> mensajes);
	Validador getData(RequestEntity<SateliteWrapper> requestEntity);
	Validador getDataPorNombre(RequestEntity requestEntity,String nombreSatelite);
	
}
