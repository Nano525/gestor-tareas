package mx.edu.utez.gestor_tareas.Service;

import mx.edu.utez.gestor_tareas.model.Accion;
import mx.edu.utez.gestor_tareas.model.Tarea;
import mx.edu.utez.gestor_tareas.util.ArbolBinario;
import mx.edu.utez.gestor_tareas.util.Cola;
import mx.edu.utez.gestor_tareas.util.Lista;
import mx.edu.utez.gestor_tareas.util.Pila;
import org.springframework.stereotype.Service;

// Servicio que gestiona las tareas utilizando las estructuras de datos:
// - Lista/Arreglo: Para almacenamiento principal de tareas
// - Pila: Para historial de acciones (LIFO)
// - Cola: Para procesar tareas por orden de llegada (FIFO)
// - Árbol Binario: Para organizar tareas por prioridad
@Service
public class TareaService {
    // Lista/Arreglo: Almacenamiento principal de tareas
    private Lista<Tarea> tareas;
    
    // Pila: Historial de acciones (LIFO - Last In, First Out)
    private Pila<Accion> historialAcciones;
    
    // Cola: Tareas pendientes por procesar (FIFO - First In, First Out)
    private Cola<Tarea> colaTareasPendientes;
    
    // Árbol Binario: Organización de tareas por prioridad
    private ArbolBinario arbolPorPrioridad;
    
    private Long contadorId;

    public TareaService() {
        this.tareas = new Lista<>();
        this.historialAcciones = new Pila<>();
        this.colaTareasPendientes = new Cola<>();
        this.arbolPorPrioridad = new ArbolBinario();
        this.contadorId = 1L;
    }

    // ========== OPERACIONES CON LISTA/ARREGLO ==========

    // Agrega una nueva tarea a la lista principal
    // También la agrega a la cola de pendientes, al árbol binario y registra la acción en la pila
    public Tarea agregarTarea(String titulo, String descripcion, Tarea.Prioridad prioridad) {
        Tarea nuevaTarea = new Tarea(contadorId++, titulo, descripcion, prioridad, Tarea.Estado.PENDIENTE);
        
        tareas.agregar(nuevaTarea);
        colaTareasPendientes.encolar(nuevaTarea);
        arbolPorPrioridad.insertar(nuevaTarea);
        historialAcciones.push(new Accion("CREAR", "Tarea creada: " + titulo));
        
        return nuevaTarea;
    }

    // Elimina una tarea de la lista principal por ID
    public boolean eliminarTarea(Long id) {
        Tarea tareaEliminada = buscarTareaPorId(id);
        if (tareaEliminada != null) {
            tareas.eliminar(tareaEliminada);
            arbolPorPrioridad.eliminar(tareaEliminada);
            historialAcciones.push(new Accion("ELIMINAR", "Tarea eliminada: " + tareaEliminada.getTitulo()));
            return true;
        }
        return false;
    }

    // Busca una tarea por ID
    public Tarea buscarTareaPorId(Long id) {
        for (int i = 0; i < tareas.tamanio(); i++) {
            Tarea tarea = tareas.obtener(i);
            if (tarea.getId().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

    // Busca tareas por título
    public Lista<Tarea> buscarTareasPorTitulo(String titulo) {
        Lista<Tarea> resultado = new Lista<>();
        String tituloLower = titulo.toLowerCase();
        for (int i = 0; i < tareas.tamanio(); i++) {
            Tarea tarea = tareas.obtener(i);
            if (tarea.getTitulo().toLowerCase().contains(tituloLower)) {
                resultado.agregar(tarea);
            }
        }
        return resultado;
    }

    // Obtiene todas las tareas
    public Lista<Tarea> obtenerTodasLasTareas() {
        return tareas;
    }

    // Actualiza una tarea existente
    public Tarea actualizarTarea(Long id, String titulo, String descripcion, Tarea.Prioridad prioridad, Tarea.Estado estado) {
        Tarea tarea = buscarTareaPorId(id);
        if (tarea != null) {
            Tarea.Prioridad prioridadAnterior = tarea.getPrioridad();
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descripcion);
            tarea.setPrioridad(prioridad);
            tarea.setEstado(estado);
            
            if (!prioridadAnterior.equals(prioridad)) {
                arbolPorPrioridad.eliminar(tarea);
                arbolPorPrioridad.insertar(tarea);
            }
            
            historialAcciones.push(new Accion("ACTUALIZAR", "Tarea actualizada: " + titulo));
            return tarea;
        }
        return null;
    }

    // Marca una tarea como completada y la elimina automáticamente
    public Tarea completarTarea(Long id) {
        Tarea tarea = buscarTareaPorId(id);
        if (tarea != null) {
            String tituloTarea = tarea.getTitulo();
            tarea.setEstado(Tarea.Estado.COMPLETADA);
            historialAcciones.push(new Accion("COMPLETAR", "Tarea completada: " + tituloTarea));
            
            eliminarTarea(id);
            historialAcciones.push(new Accion("ELIMINAR", "Tarea completada eliminada automáticamente: " + tituloTarea));
            
            return tarea;
        }
        return null;
    }

    // ========== OPERACIONES CON PILA (HISTORIAL) ==========

    // Obtiene el historial completo de acciones (sin modificar la pila)
    public Lista<Accion> obtenerHistorial() {
        return historialAcciones.obtenerTodos();
    }

    // Obtiene la última acción realizada (sin eliminarla)
    public Accion obtenerUltimaAccion() {
        return historialAcciones.peek();
    }

    // Deshace la última acción (elimina y retorna el último elemento de la pila)
    public Accion deshacerUltimaAccion() {
        return historialAcciones.pop();
    }

    // Obtiene el tamaño del historial
    public int obtenerTamanioHistorial() {
        return historialAcciones.tamanio();
    }

    // ========== OPERACIONES CON COLA (TAREAS PENDIENTES) ==========

    // Procesa la siguiente tarea pendiente (FIFO - primera en entrar, primera en salir)
    public Tarea procesarSiguienteTarea() {
        return colaTareasPendientes.desencolar();
    }

    // Obtiene la siguiente tarea a procesar sin eliminarla
    public Tarea verSiguienteTarea() {
        return colaTareasPendientes.frente();
    }

    // Obtiene todas las tareas pendientes en la cola (sin modificar la cola)
    public Lista<Tarea> obtenerTareasEnCola() {
        return colaTareasPendientes.obtenerTodos();
    }

    // Obtiene el tamaño de la cola
    public int obtenerTamanioCola() {
        return colaTareasPendientes.tamanio();
    }

    // Obtiene estadísticas del sistema
    public String obtenerEstadisticas() {
        int pendientes = 0;
        int enProgreso = 0;
        int completadas = 0;
        
        for (int i = 0; i < tareas.tamanio(); i++) {
            Tarea tarea = tareas.obtener(i);
            if (tarea.getEstado() == Tarea.Estado.PENDIENTE) {
                pendientes++;
            } else if (tarea.getEstado() == Tarea.Estado.EN_PROGRESO) {
                enProgreso++;
            } else if (tarea.getEstado() == Tarea.Estado.COMPLETADA) {
                completadas++;
            }
        }
        
        return String.format("Total: %d | Pendientes: %d | En Progreso: %d | Completadas: %d | En Cola: %d | Historial: %d | Árbol: %d",
                tareas.tamanio(), pendientes, enProgreso, completadas, 
                colaTareasPendientes.tamanio(), historialAcciones.tamanio(), arbolPorPrioridad.tamanio());
    }

    // ========== OPERACIONES CON ÁRBOL BINARIO ==========

    // Obtiene las tareas del árbol en orden inorden
    // Retorna: Lista de tareas ordenadas por prioridad (inorden)
    public Lista<Tarea> obtenerTareasInorden() {
        return arbolPorPrioridad.recorrerInorden();
    }

    // Obtiene las tareas del árbol en orden preorden
    // Retorna: Lista de tareas ordenadas por prioridad (preorden)
    public Lista<Tarea> obtenerTareasPreorden() {
        return arbolPorPrioridad.recorrerPreorden();
    }

    // Obtiene las tareas del árbol en orden postorden
    // Retorna: Lista de tareas ordenadas por prioridad (postorden)
    public Lista<Tarea> obtenerTareasPostorden() {
        return arbolPorPrioridad.recorrerPostorden();
    }

    // Obtiene todas las tareas del árbol
    // Retorna: Lista con todas las tareas del árbol
    public Lista<Tarea> obtenerTodasLasTareasDelArbol() {
        return arbolPorPrioridad.obtenerTodas();
    }
}

