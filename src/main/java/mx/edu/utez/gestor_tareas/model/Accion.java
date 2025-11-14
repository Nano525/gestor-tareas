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
}