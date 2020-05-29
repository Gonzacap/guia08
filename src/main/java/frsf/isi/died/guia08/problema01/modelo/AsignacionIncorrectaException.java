package frsf.isi.died.guia08.problema01.modelo;

public class AsignacionIncorrectaException extends Exception {
	
	public AsignacionIncorrectaException(String mesagge) {
		super("La tarea a realizar es incorrecta, seleccione otra tarea.");
	}
	
}
