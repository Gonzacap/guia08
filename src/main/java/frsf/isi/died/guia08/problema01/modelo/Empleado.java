package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Tarea> puedeAsignarTarea;

	
	//constructor--------------------------
	
	public Empleado(Integer cuil,String nombre,Double costoHora) {
		this.cuil = cuil;
		this.nombre = nombre;
		this.costoHora = costoHora;
	}
	
	//-------------------------------------
	
	//getters y setters--------------------
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void setCalculoPagoPorTarea(Function<Tarea, Double> calculoPagoPorTarea) {
		this.calculoPagoPorTarea = calculoPagoPorTarea;
	}
	
	public Integer getCuil() {
		return this.cuil;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Tipo getTipo() {
		return this.tipo;
	}
	
	public Double getCostoHora() {
		return this.costoHora;
	}
	
	public List<Tarea> getTareas() {
		return this.tareasAsignadas;
	}
	
	public Function<Tarea, Double> getCalculoPagoPorTarea() {
		return calculoPagoPorTarea;
	}
	
	//-------------------------------------
	
	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
		
		double salario = 0;
		
		List<Tarea> tareasSinFacturar = this.tareasAsignadas.parallelStream().filter(t -> t.getFacturada() == null).collect(Collectors.toList());
		
		for (Tarea t: tareasSinFacturar) {
			
			salario += this.calculoPagoPorTarea.apply(t);
			t.setFacturada(true);
		}

		return salario;
		
	}
	
	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		return 0.0;
	}
		
	public Boolean asignarTarea(Tarea t) throws AsignacionIncorrectaException {
		
		if(t.getFechaFin()!=null) {
			throw new AsignacionIncorrectaException("Aignacion incorrecta: la tarea ya ha finalizado");
		}
		if(t.getEmpleadoAsignado()!=null) {
			throw new AsignacionIncorrectaException("Aignacion incorrecta: la tarea ya tiene un empleado asignado");
		}
		
		Tipo contr = Tipo.CONTRATADO; 
		Tipo ef = Tipo.EFECTIVO;
		
		if(this.tipo == contr && this.tareasAsignadas.size()<5) {
			return true;
		}
		else if(this.tipo == ef && this.horasPendientes()<=15) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Integer horasPendientes() {
		
		Integer sum = 0;
		
		for(Tarea unaTarea:this.tareasAsignadas) {
			sum += unaTarea.getDuracionEstimada();
		}
		
		return sum;		
	}
	
	
	public void comenzar(Integer idTarea) throws TareaInexistenteException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		
		Tarea t = this.tareasAsignadas.stream().filter(tarea->tarea.getId()==idTarea).findFirst().orElse(null);
		
		if(t!=null) {
			t.setFechaInicio(LocalDateTime.now());
		}else {
			throw new TareaInexistenteException("Tarea Inexistente");
		}
			
	}
	
	public void finalizar(Integer idTarea) throws TareaInexistenteException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea t = this.tareasAsignadas.stream().filter(tarea->tarea.getId()==idTarea).findFirst().orElse(null);
		
		if(t!=null) {
			t.setFechaFin(LocalDateTime.now());
		}else {
			throw new TareaInexistenteException("Tarea Inexistente");
		}
			
	}

	
	public void comenzar(Integer idTarea,String fecha) throws TareaInexistenteException{
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea t = this.tareasAsignadas.stream().filter(tarea->tarea.getId()==idTarea).findFirst().orElse(null);
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("“dd-MM-yyyy HH:mm”");
		LocalDateTime fechaInicio = LocalDateTime.parse(fecha, formato);
		
		if(t!=null) {
			t.setFechaInicio(fechaInicio);
		}else {
			throw new TareaInexistenteException("Tarea Inexistente");
		}
		
	}
	
	public void finalizar(Integer idTarea,String fecha) throws TareaInexistenteException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		
		Tarea t = this.tareasAsignadas.stream().filter(tarea->tarea.getId()==idTarea).findFirst().orElse(null);
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("“dd-MM-yyyy HH:mm”");
		LocalDateTime fechaInicio = LocalDateTime.parse(fecha, formato);
		
		if(t!=null) {
			t.setFechaInicio(fechaInicio);
		}else {
			throw new TareaInexistenteException("Tarea Inexistente");
		}
		
	}
	
	
	
}
