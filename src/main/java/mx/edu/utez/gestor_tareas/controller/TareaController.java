package mx.edu.utez.gestor_tareas.controller;

import mx.edu.utez.gestor_tareas.model.Accion;
import mx.edu.utez.gestor_tareas.model.Tarea;
import mx.edu.utez.gestor_tareas.Service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Tarea>> obtenerTodasLasTareas() {
        return ResponseEntity.ok(tareaService.obtenerTodasLasTareas());
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
    public ResponseEntity<Tarea> crearTarea(@RequestBody Map<String, String> request) {
        String titulo = request.get("titulo");
        String descripcion = request.get("descripcion");
        Tarea.Prioridad prioridad = Tarea.Prioridad.valueOf(request.get("prioridad").toUpperCase());
        
        Tarea nuevaTarea = tareaService.agregarTarea(titulo, descripcion, prioridad);
        return ResponseEntity.ok(nuevaTarea);
    }

    /**
     * Actualiza una tarea existente (PUT /api/tareas/{id})
     */
    @PutMapping("/api/tareas/{id}")
    @ResponseBody
    public ResponseEntity<Tarea> actualizarTarea(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String titulo = request.get("titulo");
        String descripcion = request.get("descripcion");
        Tarea.Prioridad prioridad = Tarea.Prioridad.valueOf(request.get("prioridad").toUpperCase());
        Tarea.Estado estado = Tarea.Estado.valueOf(request.get("estado").toUpperCase());
        
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
    public ResponseEntity<Map<String, String>> eliminarTarea(@PathVariable Long id) {
        boolean eliminada = tareaService.eliminarTarea(id);
        Map<String, String> respuesta = new HashMap<>();
        if (eliminada) {
            respuesta.put("mensaje", "Tarea eliminada correctamente");
            return ResponseEntity.ok(respuesta);
        }
        respuesta.put("mensaje", "Tarea no encontrada");
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
    public ResponseEntity<List<Tarea>> buscarTareas(@RequestParam String titulo) {
        return ResponseEntity.ok(tareaService.buscarTareasPorTitulo(titulo));
    }

    // ========== ENDPOINTS PARA PILA (HISTORIAL) ==========

    /**
     * Obtiene el historial completo de acciones (GET /api/historial)
     */
    @GetMapping("/api/historial")
    @ResponseBody
    public ResponseEntity<List<Accion>> obtenerHistorial() {
        return ResponseEntity.ok(tareaService.obtenerHistorial());
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
    public ResponseEntity<List<Tarea>> obtenerTareasEnCola() {
        return ResponseEntity.ok(tareaService.obtenerTareasEnCola());
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
    public ResponseEntity<Map<String, String>> obtenerEstadisticas() {
        Map<String, String> stats = new HashMap<>();
        stats.put("estadisticas", tareaService.obtenerEstadisticas());
        return ResponseEntity.ok(stats);
    }
}

