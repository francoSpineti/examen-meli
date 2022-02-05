package com.mercadolibre.app.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import com.mercadolibre.app.config.Propiedades;
import com.mercadolibre.app.entidades.Coordenada;
import com.mercadolibre.app.entidades.Portacarga;
import com.mercadolibre.app.entidades.Satelite;
import com.mercadolibre.app.entidades.SateliteWrapper;
import com.mercadolibre.app.iservice.ISateliteService;
import com.mercadolibre.app.utils.Validador;

@Service
public class SateliteServiceImpl implements ISateliteService {

	@Autowired
	Propiedades props;

	@Override
	public double[] getLocation(double[][] coordenadas, double [] distancias) {
        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(coordenadas, distancias);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new LevenbergMarquardtOptimizer());
        return  nSolver.solve().getPoint().toArray();
    }

	@Override
	public String getMessage(List<List<String>> arregloMensaje) {
		return this.armarFrase(arregloMensaje);
	}

	@Override
	public Validador getData(RequestEntity<SateliteWrapper> requestEntity) {
		Validador validador = new Validador();
		validador = this.verificarBodyParteUno(validador, requestEntity);
		if(validador.isExito()) {
			SateliteWrapper wrapper = (SateliteWrapper)requestEntity.getBody();
			this.cargarCoordenadas(wrapper);		
			double [] coordenadasNave = this.getLocation(wrapper.obtenerCordenadas(), wrapper.obtenerDistancias());
		    Coordenada coordenada = new Coordenada(coordenadasNave);
		    String mensaje = this.getMessage(wrapper.obtenerMensajes());
		    validador.setObj(new Portacarga(coordenada, mensaje));
		}
	    return validador;
	}

	@Override
	public Validador getDataPorNombre(RequestEntity requestEntity, String nombreSatelite) {
			Validador validador = new Validador();
			HashMap objBody = (HashMap) requestEntity.getBody();
			validador = this.verificarBodyParteDos(validador, objBody);
			if(validador.isExito()) {
				SateliteWrapper wrapper = new SateliteWrapper();
				if (nombreSatelite.equalsIgnoreCase(props.getKenobi())) {
					wrapper.setSatelites(this.obtenerListaSatelitesPorNombre(nombreSatelite, (double)objBody.get("distancia"), (List<String>)objBody.get("mensaje")));
					double [] coordenadasNave = this.getLocation(wrapper.obtenerCordenadas(), wrapper.obtenerDistancias());
					Coordenada coordenada = new Coordenada(coordenadasNave);
					 validador.setObj(new Portacarga(coordenada, this.mensajePorDefecto()));
					return validador;
				} else if (nombreSatelite.equalsIgnoreCase(props.getSkywalker())) {
					wrapper.setSatelites(this.obtenerListaSatelitesPorNombre(nombreSatelite, (double)objBody.get("distancia"), (List<String>)objBody.get("mensaje")));
					double [] coordenadasNave = this.getLocation(wrapper.obtenerCordenadas(), wrapper.obtenerDistancias());
					Coordenada coordenada = new Coordenada(coordenadasNave);
					 validador.setObj(new Portacarga(coordenada, this.mensajePorDefecto()));
					 return validador;
				} else if (nombreSatelite.equalsIgnoreCase(props.getSato())) {
					wrapper.setSatelites(this.obtenerListaSatelitesPorNombre(nombreSatelite, (double)objBody.get("distancia"), (List<String>)objBody.get("mensaje")));
					double [] coordenadasNave = this.getLocation(wrapper.obtenerCordenadas(), wrapper.obtenerDistancias());
					Coordenada coordenada = new Coordenada(coordenadasNave);
					 validador.setObj(new Portacarga(coordenada, this.mensajePorDefecto()));
					 return validador;
				}
			}
		return validador;
	}
	
    private String armarFrase(List<List<String>> listaMensajes){

        String frase = "";
        for(List<String> m : listaMensajes){
            if(m.size()>0 && !m.get(0).equals("")){
            	frase = (m.size() == 1) ? m.get(0) : m.get(0) + " ";
                listaMensajes.stream().forEach( s -> s.remove(0));
                return  frase + armarFrase(listaMensajes);
            }
        }
        return "";
    }	

	private void cargarCoordenadas(SateliteWrapper wrapper) {
		for (int i = 0; i < wrapper.getSatelites().size(); i++) {
			if(wrapper.getSatelites().get(i).getNombre().equalsIgnoreCase(props.getKenobi())) {
				wrapper.getSatelites().get(i).setCoordenada(this.obtenerCoordenadasPorNombre(props.getKenobi()));
			}else if(wrapper.getSatelites().get(i).getNombre().equalsIgnoreCase(props.getSkywalker())) {
				wrapper.getSatelites().get(i).setCoordenada(this.obtenerCoordenadasPorNombre(props.getSkywalker()));
			}else if(wrapper.getSatelites().get(i).getNombre().equalsIgnoreCase(props.getSato())) {
				wrapper.getSatelites().get(i).setCoordenada(this.obtenerCoordenadasPorNombre(props.getSato()));
			}
		}
	}

	private Coordenada obtenerCoordenadasPorNombre(String nombre){

		Coordenada coordenada = new Coordenada();

		if(nombre.equalsIgnoreCase(props.getKenobi())) {
			String coordenadasProperties[] = props.getKenobiCoordenadas().split(",");
			coordenada.setCoordenadaX(Double.parseDouble(coordenadasProperties[0]));
			coordenada.setCoordenadaY(Double.parseDouble(coordenadasProperties[1]));
		}else if(nombre.equalsIgnoreCase(props.getSkywalker())) {
			String coordenadasProperties[] = props.getSkywalkerCoordenadas().split(",");
			coordenada.setCoordenadaX(Double.parseDouble(coordenadasProperties[0]));
			coordenada.setCoordenadaY(Double.parseDouble(coordenadasProperties[1]));
		}else if(nombre.equalsIgnoreCase(props.getSato())){
			String coordenadasProperties[] = props.getSatoCoordenadas().split(",");
			coordenada.setCoordenadaX(Double.parseDouble(coordenadasProperties[0]));
			coordenada.setCoordenadaY(Double.parseDouble(coordenadasProperties[1]));
		}
		return coordenada;
	}

	private Satelite crearSatelitePorNombre(String nombreSatelite,double distancia,List<String> mensajes) {
		Satelite retorno = new Satelite();
		retorno.setNombre(nombreSatelite);
		retorno.setDistancia(distancia);
		retorno.setMensaje(mensajes);
		retorno.setCoordenada(this.obtenerCoordenadasPorNombre(nombreSatelite));
		return retorno;
	}
	
	private String mensajePorDefecto() {
		return "el mensaje no pudo ser recibido";
	}

	private List<Satelite> obtenerListaSatelitesPorNombre(String nombreSatelite,double distancia,List<String> mensajes){
		List<Satelite> listaSatelites = new ArrayList<Satelite>();
		if (nombreSatelite.equalsIgnoreCase(props.getKenobi())) {
			listaSatelites.add(this.crearSatelitePorNombre(nombreSatelite, distancia, mensajes));
			listaSatelites.add(this.crearSatelitePorNombre(props.getSkywalker(), 1.0, new ArrayList<String>()));
			listaSatelites.add(this.crearSatelitePorNombre(props.getSato(), 1.0, new ArrayList<String>()));
		} else if (nombreSatelite.equalsIgnoreCase(props.getSkywalker())) {
			listaSatelites.add(this.crearSatelitePorNombre(props.getKenobi(), 1.0, new ArrayList<String>()));
			listaSatelites.add(this.crearSatelitePorNombre(nombreSatelite, distancia, mensajes));
			listaSatelites.add(this.crearSatelitePorNombre(props.getSato(), 1.0, new ArrayList<String>()));
		} else if (nombreSatelite.equalsIgnoreCase(props.getSato())) {
			listaSatelites.add(this.crearSatelitePorNombre(nombreSatelite, distancia, mensajes));
			listaSatelites.add(this.crearSatelitePorNombre(props.getSkywalker(), 1.0, new ArrayList<String>()));
			listaSatelites.add(this.crearSatelitePorNombre(props.getKenobi(), 1.0, new ArrayList<String>()));
		}
		return listaSatelites;
	}
	
	private Validador verificarBodyParteUno(Validador validador, RequestEntity requestEntity) {
		SateliteWrapper wrapper = (SateliteWrapper)requestEntity.getBody();
		validador.setExito(true);
		validador.setMensaje("");
		for (int i = 0; i < wrapper.getSatelites().size(); i++) {
			
			if(wrapper.getSatelites().get(i).getNombre().equals("") || wrapper.getSatelites().get(i).getNombre() == null) {
				validador.setExito(false);
				validador.setMensaje("Por favor ingrese un nombre.");
			}else if(wrapper.getSatelites().get(i).getDistancia() == 0.0) {
				validador.setExito(false);
				validador.setMensaje("Por favor ingrese una distancia.");
			}else if(wrapper.getSatelites().get(i).getMensaje().isEmpty() || wrapper.getSatelites().get(i).getMensaje().contains(null)) {
				validador.setExito(false);
				validador.setMensaje("Por favor ingrese un arreglo de mensajes.");
			}
		}
		return validador;
	}
	
	private Validador verificarBodyParteDos(Validador validador, HashMap objBody) {
		validador.setExito(true);
		validador.setMensaje("");
		List<String> mensajes = (List<String>)objBody.get("mensaje");
		if((double)objBody.get("distancia") == 0.0) {
			validador.setExito(false);
			validador.setMensaje("por favor ingrese una distancia.");
		}else if(mensajes.isEmpty() || mensajes == null) {
			validador.setExito(false);
			validador.setMensaje("por favor ingrese un arreglo de mensajes.");
		}
		return validador;
	}
}
