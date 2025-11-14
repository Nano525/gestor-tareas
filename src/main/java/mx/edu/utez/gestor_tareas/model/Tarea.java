package mx.edu.utez.gestor_tareas.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa una Tarea en el sistema.
 * Contiene la información básica de una tarea: título, descripción,
 * prioridad y estado.
 */
public class Tarea {
    private Long id;
    private String titulo;
    private String descripcion;
    private Prioridad prioridad;
    private Estado estado;
    private LocalDateTime fechaCreacion;

    // Constructor vacío
    public Tarea() {
        this.fechaCreacion = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Tarea(Long id, String titulo, String descripcion, Prioridad prioridad, Estado estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", prioridad=" + prioridad +
                ", estado=" + estado +
                '}';
    }

    /**
     * Enum para representar las prioridades de una tarea
     */
    public enum Prioridad {
        ALTA, MEDIA, BAJA
    }

    /**
     * Enum para representar los estados de una tarea
     */
    public enum Estado {
        PENDIENTE, EN_PROGRESO, COMPLETADA
    }
}

