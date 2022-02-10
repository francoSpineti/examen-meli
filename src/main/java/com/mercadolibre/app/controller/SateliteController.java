package com.mercadolibre.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolibre.app.entidades.SateliteWrapper;
import com.mercadolibre.app.iservice.ISateliteService;
import com.mercadolibre.app.utils.Validador;

@RestController
@RequestMapping (path = "${satelite.controlador.ruta}")
public class SateliteController {

	@Autowired
	ISateliteService sateliteService;

	@PostMapping(path = "/topsecret", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity obtenerData(RequestEntity<SateliteWrapper> requestEntity) {
		Validador respuesta = null;
		try {	
				respuesta = sateliteService.getData(requestEntity);
				if(!respuesta.isExito()){
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
				}
				return ResponseEntity.status(HttpStatus.OK).body(respuesta.getObj());
		}
		 catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "/topsecret_split", method = { RequestMethod.GET, RequestMethod.POST }, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity obtenerDataPorNombre(RequestEntity requestEntity,@RequestParam String nombreSatelite){
		Validador respuesta = null;
		try {
			if(requestEntity.getMethod().matches("GET")) {
				respuesta = sateliteService.getDataPorNombre(requestEntity,nombreSatelite);
				if(!respuesta.isExito()) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
				}
				return ResponseEntity.status(HttpStatus.OK).body(respuesta.getObj());
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay suficiente informacion.");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

}
