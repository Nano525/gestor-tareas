package mx.edu.utez.gestor_tareas.controller;

import mx.edu.utez.gestor_tareas.model.Accion;
import mx.edu.utez.gestor_tareas.model.Tarea;
import mx.edu.utez.gestor_tareas.model.TareaRequest;
import mx.edu.utez.gestor_tareas.Service.TareaService;
import mx.edu.utez.gestor_tareas.util.RespuestaSimple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que maneja las peticiones HTTP para la gestión de tareas.
 * Proporciona endpoints REST y una vista principal.
 */
@Controller
public class TareaController {

    @Autowired
    private TareaService tareaService;

    /**
     * Muestra la página principal de la aplicación
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // ========== ENDPOINTS REST PARA LISTA/ARREGLO ==========

    /**
     * Obtiene todas las tareas (GET /api/tareas)
     */
    @GetMapping("/api/tareas")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas().obtenerTodos());
    }

    /**
     * Obtiene una tarea por ID (GET /api/tareas/{id})
     */
    @GetMapping("/api/tareas/{id}")
    @ResponseBody
    public ResponseEntity<Tarea> obtenerTareaPorId(@PathVariable Long id) {
        Tarea tarea = tareaService.buscarTareaPorId(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Crea una nueva tarea (POST /api/tareas)
     */
    @PostMapping("/api/tareas")
    @ResponseBody
    public ResponseEntity<Tarea> crearTarea(@RequestBody TareaRequest request) {
        String titulo = request.getTitulo();
        String descripcion = request.getDescripcion();
        Tarea.Prioridad prioridad = Tarea.Prioridad.valueOf(request.getPrioridad().toUpperCase());
        
        Tarea nuevaTarea = tareaService.agregarTarea(titulo, descripcion, prioridad);
        return ResponseEntity.ok(nuevaTarea);
    }

    /**
     * Actualiza una tarea existente (PUT /api/tareas/{id})
     */
    @PutMapping("/api/tareas/{id}")
    @ResponseBody
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody TareaRequest request) {
        String titulo = request.getTitulo();
        String descripcion = request.getDescripcion();
        Tarea.Prioridad prioridad = Tarea.Prioridad.valueOf(request.getPrioridad().toUpperCase());
        Tarea.Estado estado = Tarea.Estado.valueOf(request.getEstado().toUpperCase());
        
        Tarea tareaActualizada = tareaService.actualizarTarea(id, titulo, descripcion, prioridad, estado);
        if (tareaActualizada != null) {
            return ResponseEntity.ok(tareaActualizada);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Elimina una tarea (DELETE /api/tareas/{id})
     */
    @DeleteMapping("/api/tareas/{id}")
    @ResponseBody
    public ResponseEntity<RespuestaSimple> eliminarTarea(@PathVariable Long id) {
        boolean eliminada = tareaService.eliminarTarea(id);
        RespuestaSimple respuesta = new RespuestaSimple();
        if (eliminada) {
            respuesta.setMensaje("Tarea eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        }
        respuesta.setMensaje("Tarea no encontrada");
        return ResponseEntity.notFound().build();
    }

    /**
     * Marca una tarea como completada (POST /api/tareas/{id}/completar)
     */
    @PostMapping("/api/tareas/{id}/completar")
    @ResponseBody
    public ResponseEntity<Tarea> completarTarea(@PathVariable Long id) {
        Tarea tarea = tareaService.completarTarea(id);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Busca tareas por título (GET /api/tareas/buscar?titulo=...)
     */
    @GetMapping("/api/tareas/buscar")
    @ResponseBody
    public ResponseEntity<Object[]> buscarTareas(@RequestParam String titulo) {
        return ResponseEntity.ok(tareaService.buscarTareasPorTitulo(titulo).obtenerTodos());
    }

    // ========== ENDPOINTS PARA PILA (HISTORIAL) ==========

    /**
     * Obtiene el historial completo de acciones (GET /api/historial)
     */
    @GetMapping("/api/historial")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerHistorial() {
        return ResponseEntity.ok(tareaService.obtenerHistorial().obtenerTodos());
    }

    /**
     * Obtiene la última acción (GET /api/historial/ultima)
     */
    @GetMapping("/api/historial/ultima")
    @ResponseBody
    public ResponseEntity<Accion> obtenerUltimaAccion() {
        Accion accion = tareaService.obtenerUltimaAccion();
        if (accion != null) {
            return ResponseEntity.ok(accion);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Deshace la última acción (POST /api/historial/deshacer)
     */
    @PostMapping("/api/historial/deshacer")
    @ResponseBody
    public ResponseEntity<Accion> deshacerUltimaAccion() {
        Accion accion = tareaService.deshacerUltimaAccion();
        if (accion != null) {
            return ResponseEntity.ok(accion);
        }
        return ResponseEntity.noContent().build();
    }

    // ========== ENDPOINTS PARA COLA (TAREAS PENDIENTES) ==========

    /**
     * Obtiene todas las tareas en la cola (GET /api/cola)
     */
    @GetMapping("/api/cola")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerTareasEnCola() {
        return ResponseEntity.ok(tareaService.obtenerTareasEnCola().obtenerTodos());
    }

    /**
     * Procesa la siguiente tarea de la cola (POST /api/cola/procesar)
     */
    @PostMapping("/api/cola/procesar")
    @ResponseBody
    public ResponseEntity<Tarea> procesarSiguienteTarea() {
        Tarea tarea = tareaService.procesarSiguienteTarea();
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        }
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene estadísticas del sistema (GET /api/estadisticas)
     */
    @GetMapping("/api/estadisticas")
    @ResponseBody
    public ResponseEntity<RespuestaSimple> obtenerEstadisticas() {
        RespuestaSimple stats = new RespuestaSimple();
        stats.setEstadisticas(tareaService.obtenerEstadisticas());
        return ResponseEntity.ok(stats);
    }

    // ========== ENDPOINTS PARA ÁRBOL BINARIO ==========

    /**
     * Obtiene las tareas del árbol en orden inorden (GET /api/arbol/inorden)
     */
    @GetMapping("/api/arbol/inorden")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerArbolInorden() {
        return ResponseEntity.ok(tareaService.obtenerTareasInorden().obtenerTodos());
    }

    @GetMapping("/api/arbol/preorden")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerArbolPreorden() {
        return ResponseEntity.ok(tareaService.obtenerTareasPreorden().obtenerTodos());
    }

    @GetMapping("/api/arbol/postorden")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerArbolPostorden() {
        return ResponseEntity.ok(tareaService.obtenerTareasPostorden().obtenerTodos());
    }

    @GetMapping("/api/arbol/tareas")
    @ResponseBody
    public ResponseEntity<Object[]> obtenerTodasLasTareasDelArbol() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareasDelArbol().obtenerTodos());
    }
}

