package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;

public class Tarea {

	private Integer id;
	private String descripcion;
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	

	//constructor--------------------------
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada){
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		//this.empleadoAsignado = empleadoAsignado;
	}
	
	//-------------------------------------
	
	
	
	public void asignarEmpleado(Empleado e) throws AsignacionIncorrectaException {
		// si la tarea ya tiene un empleado asignado
		// y tiene fecha de finalizado debe lanzar una excepcion
		
		if(this.getFechaFin()!=null) {
			throw new AsignacionIncorrectaException("Aignacion incorrecta: la tarea ya ha finalizado");
		}
		if(this.getEmpleadoAsignado()!=null) {
			throw new AsignacionIncorrectaException("Aignacion incorrecta: la tarea ya tiene un empleado asignado");
		}
		this.empleadoAsignado = e;
		
	}
	
	
	//getters y setters--------------------

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public void setFacturada(Boolean facturada) {
		this.facturada = facturada;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}
	
	//-------------------------------------
	
	
}
