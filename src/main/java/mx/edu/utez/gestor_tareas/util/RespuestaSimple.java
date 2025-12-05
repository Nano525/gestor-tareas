package mx.edu.utez.gestor_tareas.util;

/**
 * Clase auxiliar para reemplazar Map<String, String> en respuestas del controlador.
 * Utilizada para respuestas JSON simples sin usar clases de java.util.*
 */
public class RespuestaSimple {
    private String mensaje;
    private String estadisticas;

    public RespuestaSimple() {
    }

    public RespuestaSimple(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaSimple(String mensaje, String estadisticas) {
        this.mensaje = mensaje;
        this.estadisticas = estadisticas;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(String estadisticas) {
        this.estadisticas = estadisticas;
    }
}

