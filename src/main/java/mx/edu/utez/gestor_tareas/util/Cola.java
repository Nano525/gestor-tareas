package mx.edu.utez.gestor_tareas.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación de una estructura de datos Cola (Queue) usando FIFO (First In, First Out).
 * El primer elemento ingresado es el primero en salir.
 * Utilizada para gestionar tareas en el orden en que fueron creadas.
 */
public class Cola<T> {
    private List<T> elementos;

    /**
     * Constructor que inicializa la cola vacía
     */
    public Cola() {
        this.elementos = new ArrayList<>();
    }

    /**
     * Agrega un elemento al final de la cola (enqueue)
     * @param elemento El elemento a agregar
     */
    public void encolar(T elemento) {
        elementos.add(elemento);
    }

    /**
     * Elimina y retorna el elemento al frente de la cola (dequeue)
     * @return El elemento eliminado, o null si la cola está vacía
     */
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        return elementos.remove(0);
    }

    /**
     * Retorna el elemento al frente sin eliminarlo (front)
     * @return El elemento al frente, o null si la cola está vacía
     */
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return elementos.get(0);
    }

    /**
     * Verifica si la cola está vacía
     * @return true si la cola está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /**
     * Retorna el tamaño de la cola
     * @return Número de elementos en la cola
     */
    public int tamanio() {
        return elementos.size();
    }

    /**
     * Obtiene todos los elementos de la cola (sin modificar la cola)
     * @return Lista con todos los elementos
     */
    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    /**
     * Limpia todos los elementos de la cola
     */
    public void limpiar() {
        elementos.clear();
    }
}

