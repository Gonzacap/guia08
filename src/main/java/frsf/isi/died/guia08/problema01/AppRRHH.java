package frsf.isi.died.guia08.problema01;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import frsf.isi.died.guia08.problema01.modelo.AsignacionIncorrectaException;
import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.TareaInexistenteException;

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
	
	public void empezarTarea(Integer cuil,Integer idTarea) throws AsignacionIncorrectaException, TareaInexistenteException {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		
		
		Optional<Empleado> emp = this.buscarEmpleado(e -> e.getCuil() == cuil);

		if (emp.isPresent()) {
			emp.get().comenzar(idTarea);
			
		} else {
			throw new AsignacionIncorrectaException("Empleado no encontrado");
		}
		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) throws AsignacionIncorrectaException, TareaInexistenteException {
		// crear un empleado
		// agregarlo a la lista	
		
		Optional<Empleado> emp = this.buscarEmpleado(e -> e.getCuil() == cuil);

		if (emp.isPresent()) {
			emp.get().finalizar(idTarea);
			
		} else {
			throw new AsignacionIncorrectaException("Empleado no encontrado");
		}
		
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		
		
		try (Reader fileReader = new FileReader(nombreArchivo)) {
			
			try (BufferedReader in = new BufferedReader(fileReader)) {
				String linea = null;
				
				while ((linea = in.readLine()) != null) {
					String[] fila = linea.split(";");
					
					this.agregarEmpleadoContratado(Integer.valueOf(fila[0]), String.valueOf(fila[1]), Double.valueOf(fila[2]));
				}
				
			} catch(IOException e2) {
				System.out.println(e2.getMessage());
			}
			
		} catch(FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
		
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) throws FileNotFoundException, IOException {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado	
		
		try (Reader fileReader = new FileReader(nombreArchivo)) {
			
			try (BufferedReader in = new BufferedReader(fileReader)) {
				String linea = null;
				
				while ((linea = in.readLine()) != null) {
					String[] fila = linea.split(";");
					
					this.agregarEmpleadoEfectivo(Integer.valueOf(fila[0]), String.valueOf(fila[1]), Double.valueOf(fila[2]));		
				}
				
			} catch(IOException e2) {
				System.out.println(e2.getMessage());
			}
			
			
		} catch(FileNotFoundException e1) {
			System.out.println(e1.getMessage());
		}
		
	}

	public void cargarTareasCSV(String nombreArchivo) throws FileNotFoundException, IOException, NumberFormatException, AsignacionIncorrectaException {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
				
		
		try (Reader fileReader = new FileReader(nombreArchivo)) {
			
			try (BufferedReader in = new BufferedReader(fileReader)) {
				String linea = null;
				
				while ((linea = in.readLine()) != null) {
					String[] fila = linea.split(";");
					
					//asignarTarea(cuil, idtarea, desc, durcion)
					this.asignarTarea(Integer.valueOf(fila[0]), Integer.valueOf(fila[1]), String.valueOf(fila[2]), Integer.valueOf(fila[3]));
						
				}
			}
		}
		
		
	}
	
	private void guardarTareasTerminadasCSV() throws IOException {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV 
		
		for(Empleado emp : this.empleados){
			
			for(Tarea t: emp.getTareas()){
				
				if(t.getFacturada() == null && t.getFechaFin() != null){
					
					try(Writer fileWriter= new FileWriter("Tareas Terminadas Sin Facturar.csv")) {
						
						try(BufferedWriter out = new BufferedWriter(fileWriter)){
							
							out.write(t.getInfo() + System.getProperty("line.separator"));
							
						} catch(IOException e){
							System.out.println(e.getMessage());
						}
						
					} catch(IOException e){
						System.out.println(e.getMessage());
					}
				}
			}
		}
		
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() throws IOException {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
