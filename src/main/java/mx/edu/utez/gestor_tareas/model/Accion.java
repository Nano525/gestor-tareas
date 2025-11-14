package mx.edu.utez.gestor_tareas.model;

import java.time.LocalDateTime;

/**
 * Modelo que representa una acción realizada en el sistema.
 * Se utiliza para almacenar en la pila de historial de acciones.
 */
public class Accion {
    private String tipo; // CREAR, ELIMINAR, ACTUALIZAR, COMPLETAR
    private String descripcion;
    private LocalDateTime fechaHora;

    public Accion() {
        this.fechaHora = LocalDateTime.now();
    }

    public Accion(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaHora = LocalDateTime.now();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Accion{" +
                "tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaHora=" + fechaHora +
                '}';
    }
}

