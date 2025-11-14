package mx.edu.utez.gestor_tareas.Service;

import mx.edu.utez.gestor_tareas.model.Accion;
import mx.edu.utez.gestor_tareas.model.Tarea;
import mx.edu.utez.gestor_tareas.util.Cola;
import mx.edu.utez.gestor_tareas.util.Pila;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las tareas utilizando las estructuras de datos:
 * - Lista/Arreglo: Para almacenamiento principal de tareas
 * - Pila: Para historial de acciones (LIFO)
 * - Cola: Para procesar tareas por orden de llegada (FIFO)
 */
@Service
public class TareaService {
    // Lista/Arreglo: Almacenamiento principal de tareas
    private List<Tarea> tareas;
    
    // Pila: Historial de acciones (LIFO - Last In, First Out)
    private Pila<Accion> historialAcciones;
    
    // Cola: Tareas pendientes por procesar (FIFO - First In, First Out)
    private Cola<Tarea> colaTareasPendientes;
    
    private Long contadorId;

    public TareaService() {
        this.tareas = new ArrayList<>();
        this.historialAcciones = new Pila<>();
        this.colaTareasPendientes = new Cola<>();
        this.contadorId = 1L;
    }

    // ========== OPERACIONES CON LISTA/ARREGLO ==========

    /**
     * Agrega una nueva tarea a la lista principal
     * También la agrega a la cola de pendientes y registra la acción en la pila
     */
    public Tarea agregarTarea(String titulo, String descripcion, Tarea.Prioridad prioridad) {
        Tarea nuevaTarea = new Tarea(contadorId++, titulo, descripcion, prioridad, Tarea.Estado.PENDIENTE);
        
        // Agregar a la lista principal
        tareas.add(nuevaTarea);
        
        // Agregar a la cola de pendientes (FIFO)
        colaTareasPendientes.encolar(nuevaTarea);
        
        // Registrar acción en la pila de historial (LIFO)
        historialAcciones.push(new Accion("CREAR", "Tarea creada: " + titulo));
        
        return nuevaTarea;
    }

    /**
     * Elimina una tarea de la lista principal por ID
     */
    public boolean eliminarTarea(Long id) {
        Tarea tareaEliminada = buscarTareaPorId(id);
        if (tareaEliminada != null) {
            tareas.remove(tareaEliminada);
            historialAcciones.push(new Accion("ELIMINAR", "Tarea eliminada: " + tareaEliminada.getTitulo()));
            return true;
        }
        return false;
    }

    /**
     * Busca una tarea por ID
     */
    public Tarea buscarTareaPorId(Long id) {
        return tareas.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Busca tareas por título
     */
    public List<Tarea> buscarTareasPorTitulo(String titulo) {
        return tareas.stream()
                .filter(t -> t.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las tareas
     */
    public List<Tarea> obtenerTodasLasTareas() {
        return new ArrayList<>(tareas);
    }

    /**
     * Actualiza una tarea existente
     */
    public Tarea actualizarTarea(Long id, String titulo, String descripcion, Tarea.Prioridad prioridad, Tarea.Estado estado) {
        Tarea tarea = buscarTareaPorId(id);
        if (tarea != null) {
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descripcion);
            tarea.setPrioridad(prioridad);
            tarea.setEstado(estado);
            historialAcciones.push(new Accion("ACTUALIZAR", "Tarea actualizada: " + titulo));
            return tarea;
        }
        return null;
    }

    /**
     * Marca una tarea como completada
     */
    public Tarea completarTarea(Long id) {
        Tarea tarea = buscarTareaPorId(id);
        if (tarea != null) {
            tarea.setEstado(Tarea.Estado.COMPLETADA);
            historialAcciones.push(new Accion("COMPLETAR", "Tarea completada: " + tarea.getTitulo()));
            return tarea;
        }
        return null;
    }

    // ========== OPERACIONES CON PILA (HISTORIAL) ==========

    /**
     * Obtiene el historial completo de acciones (sin modificar la pila)
     */
    public List<Accion> obtenerHistorial() {
        return historialAcciones.obtenerTodos();
    }

    /**
     * Obtiene la última acción realizada (sin eliminarla)
     */
    public Accion obtenerUltimaAccion() {
        return historialAcciones.peek();
    }

    /**
     * Deshace la última acción (elimina y retorna el último elemento de la pila)
     */
    public Accion deshacerUltimaAccion() {
        return historialAcciones.pop();
    }

    /**
     * Obtiene el tamaño del historial
     */
    public int obtenerTamanioHistorial() {
        return historialAcciones.tamanio();
    }

    // ========== OPERACIONES CON COLA (TAREAS PENDIENTES) ==========

    /**
     * Procesa la siguiente tarea pendiente (FIFO - primera en entrar, primera en salir)
     */
    public Tarea procesarSiguienteTarea() {
        return colaTareasPendientes.desencolar();
    }

    /**
     * Obtiene la siguiente tarea a procesar sin eliminarla
     */
    public Tarea verSiguienteTarea() {
        return colaTareasPendientes.frente();
    }

    /**
     * Obtiene todas las tareas pendientes en la cola (sin modificar la cola)
     */
    public List<Tarea> obtenerTareasEnCola() {
        return colaTareasPendientes.obtenerTodos();
    }

    /**
     * Obtiene el tamaño de la cola
     */
    public int obtenerTamanioCola() {
        return colaTareasPendientes.tamanio();
    }

    /**
     * Obtiene estadísticas del sistema
     */
    public String obtenerEstadisticas() {
        long pendientes = tareas.stream().filter(t -> t.getEstado() == Tarea.Estado.PENDIENTE).count();
        long enProgreso = tareas.stream().filter(t -> t.getEstado() == Tarea.Estado.EN_PROGRESO).count();
        long completadas = tareas.stream().filter(t -> t.getEstado() == Tarea.Estado.COMPLETADA).count();
        
        return String.format("Total: %d | Pendientes: %d | En Progreso: %d | Completadas: %d | En Cola: %d | Historial: %d",
                tareas.size(), pendientes, enProgreso, completadas, 
                colaTareasPendientes.tamanio(), historialAcciones.tamanio());
    }
}

