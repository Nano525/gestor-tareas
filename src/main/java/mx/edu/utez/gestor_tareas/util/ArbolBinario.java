package mx.edu.utez.gestor_tareas.util;

import mx.edu.utez.gestor_tareas.model.Tarea;

/**
 * Implementación de un Árbol Binario de Búsqueda para organizar tareas por prioridad.
 * Estructura que clasifica y organiza elementos según su nivel de prioridad.
 * Prioridad: ALTA > MEDIA > BAJA
 */
public class ArbolBinario {
    private Nodo raiz;
    private int tamanio;

    /**
     * Clase interna que representa un nodo del árbol
     */
    private class Nodo {
        Tarea tarea;
        Nodo izquierdo;
        Nodo derecho;

        Nodo(Tarea tarea) {
            this.tarea = tarea;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    /**
     * Constructor que inicializa un árbol vacío
     */
    public ArbolBinario() {
        this.raiz = null;
        this.tamanio = 0;
    }

    /**
     * Inserta una tarea en el árbol según su prioridad
     * @param tarea Tarea a insertar
     */
    public void insertar(Tarea tarea) {
        raiz = insertarRecursivo(raiz, tarea);
        tamanio++;
    }

    /**
     * Método recursivo para insertar un nodo
     * @param nodo Nodo actual
     * @param tarea Tarea a insertar
     * @return Nodo raíz del subárbol
     */
    private Nodo insertarRecursivo(Nodo nodo, Tarea tarea) {
        if (nodo == null) {
            return new Nodo(tarea);
        }

        int comparacion = compararPrioridad(tarea.getPrioridad(), nodo.tarea.getPrioridad());

        if (comparacion < 0) {
            nodo.izquierdo = insertarRecursivo(nodo.izquierdo, tarea);
        } else if (comparacion > 0) {
            nodo.derecho = insertarRecursivo(nodo.derecho, tarea);
        } else {
            if (tarea.getId() < nodo.tarea.getId()) {
                nodo.izquierdo = insertarRecursivo(nodo.izquierdo, tarea);
            } else {
                nodo.derecho = insertarRecursivo(nodo.derecho, tarea);
            }
        }

        return nodo;
    }

    /**
     * Compara dos prioridades
     * @param p1 Prioridad 1
     * @param p2 Prioridad 2
     * @return -1 si p1 > p2, 0 si iguales, 1 si p1 < p2
     */
    private int compararPrioridad(Tarea.Prioridad p1, Tarea.Prioridad p2) {
        int valor1 = obtenerValorPrioridad(p1);
        int valor2 = obtenerValorPrioridad(p2);
        return Integer.compare(valor1, valor2);
    }

    /**
     * Obtiene el valor numérico de una prioridad
     * ALTA = 3, MEDIA = 2, BAJA = 1
     */
    private int obtenerValorPrioridad(Tarea.Prioridad prioridad) {
        switch (prioridad) {
            case ALTA:
                return 3;
            case MEDIA:
                return 2;
            case BAJA:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Busca una tarea por ID en el árbol
     * @param id ID de la tarea a buscar
     * @return Tarea encontrada o null
     */
    public Tarea buscar(Long id) {
        return buscarRecursivo(raiz, id);
    }

    /**
     * Método recursivo para buscar una tarea
     */
    private Tarea buscarRecursivo(Nodo nodo, Long id) {
        if (nodo == null) {
            return null;
        }
        if (nodo.tarea.getId().equals(id)) {
            return nodo.tarea;
        }
        Tarea encontrada = buscarRecursivo(nodo.izquierdo, id);
        if (encontrada == null) {
            encontrada = buscarRecursivo(nodo.derecho, id);
        }
        return encontrada;
    }

    /**
     * Elimina una tarea del árbol
     * @param tarea Tarea a eliminar
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminar(Tarea tarea) {
        int tamanioAntes = tamanio;
        raiz = eliminarRecursivo(raiz, tarea);
        return tamanio < tamanioAntes;
    }

    /**
     * Método recursivo para eliminar un nodo
     */
    private Nodo eliminarRecursivo(Nodo nodo, Tarea tarea) {
        if (nodo == null) {
            return null;
        }

        if (nodo.tarea.getId().equals(tarea.getId())) {
            tamanio--;
            if (nodo.izquierdo == null && nodo.derecho == null) {
                return null;
            }
            if (nodo.izquierdo == null) {
                return nodo.derecho;
            }
            if (nodo.derecho == null) {
                return nodo.izquierdo;
            }
            Nodo sucesor = encontrarMinimo(nodo.derecho);
            nodo.tarea = sucesor.tarea;
            nodo.derecho = eliminarRecursivo(nodo.derecho, sucesor.tarea);
            return nodo;
        }

        int comparacion = compararPrioridad(tarea.getPrioridad(), nodo.tarea.getPrioridad());
        if (comparacion < 0) {
            nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, tarea);
        } else {
            nodo.derecho = eliminarRecursivo(nodo.derecho, tarea);
        }

        return nodo;
    }

    /**
     * Encuentra el nodo con el valor mínimo (más a la izquierda)
     */
    private Nodo encontrarMinimo(Nodo nodo) {
        while (nodo.izquierdo != null) {
            nodo = nodo.izquierdo;
        }
        return nodo;
    }

    /**
     * Recorrido Inorden (Izquierda - Raíz - Derecha)
     * @return Lista con las tareas en orden inorden
     */
    public Lista<Tarea> recorrerInorden() {
        Lista<Tarea> resultado = new Lista<>();
        inordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void inordenRecursivo(Nodo nodo, Lista<Tarea> resultado) {
        if (nodo != null) {
            inordenRecursivo(nodo.izquierdo, resultado);
            resultado.agregar(nodo.tarea);
            inordenRecursivo(nodo.derecho, resultado);
        }
    }

    /**
     * Recorrido Preorden (Raíz - Izquierda - Derecha)
     * @return Lista con las tareas en orden preorden
     */
    public Lista<Tarea> recorrerPreorden() {
        Lista<Tarea> resultado = new Lista<>();
        preordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void preordenRecursivo(Nodo nodo, Lista<Tarea> resultado) {
        if (nodo != null) {
            resultado.agregar(nodo.tarea);
            preordenRecursivo(nodo.izquierdo, resultado);
            preordenRecursivo(nodo.derecho, resultado);
        }
    }

    /**
     * Recorrido Postorden (Izquierda - Derecha - Raíz)
     * @return Lista con las tareas en orden postorden
     */
    public Lista<Tarea> recorrerPostorden() {
        Lista<Tarea> resultado = new Lista<>();
        postordenRecursivo(raiz, resultado);
        return resultado;
    }

    private void postordenRecursivo(Nodo nodo, Lista<Tarea> resultado) {
        if (nodo != null) {
            postordenRecursivo(nodo.izquierdo, resultado);
            postordenRecursivo(nodo.derecho, resultado);
            resultado.agregar(nodo.tarea);
        }
    }

    /**
     * Obtiene todas las tareas del árbol (recorrido inorden)
     * @return Lista con todas las tareas
     */
    public Lista<Tarea> obtenerTodas() {
        return recorrerInorden();
    }

    /**
     * Verifica si el árbol está vacío
     * @return true si está vacío, false en caso contrario
     */
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Obtiene el tamaño del árbol
     * @return Número de nodos en el árbol
     */
    public int tamanio() {
        return tamanio;
    }

    /**
     * Limpia todos los elementos del árbol
     */
    public void limpiar() {
        raiz = null;
        tamanio = 0;
    }
}

