package frsf.isi.died.guia08.problema01;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.AsignacionIncorrectaException;
import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.TareaInexistenteException;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		
		Tipo contr = Tipo.CONTRATADO;
		
		Empleado nuevoEmpleado = new Empleado(cuil , nombre, costoHora, contr);
		
		this.empleados.add(nuevoEmpleado);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista		
		
		Tipo contr = Tipo.EFECTIVO;
		
		Empleado nuevoEmpleado = new Empleado(cuil , nombre, costoHora, contr);
		
		this.empleados.add(nuevoEmpleado);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws AsignacionIncorrectaException {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista	
				
		Empleado emp = this.empleados.stream().filter(empleado->empleado.getCuil() == cuil).findFirst().orElse(null);
		
		if(emp!=null) {
			
			Tarea nuevaTarea = new Tarea(idTarea, descripcion, duracionEstimada);
			
			nuevaTarea.asignarEmpleado(emp);
			
		}
		else {
			throw new AsignacionIncorrectaException("Empleado no encontrado");
		}

	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista		
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado		
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
