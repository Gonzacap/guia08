package frsf.isi.died.guia08.problema01.modelo;

public class TareaInexistenteException extends Exception {

	public TareaInexistenteException(String mesagge) {
		super("La tarea a realizar es incorrecta, seleccione otra tarea.");
	}
}
