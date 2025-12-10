package mx.edu.utez.gestor_tareas.util;

// Implementación de una estructura de datos Cola (Queue) usando FIFO (First In, First Out).
// El primer elemento ingresado es el primero en salir.
// Utilizada para gestionar tareas en el orden en que fueron creadas.
// Implementada con arrays nativos, sin usar clases de java.util.*
public class Cola<T> {
    private Object[] elementos;
    private int frente;
    private int fin;
    private int tamanio;
    private int capacidad;
    private static final int CAPACIDAD_INICIAL = 10;

    // Constructor que inicializa la cola vacía
    public Cola() {
        this.capacidad = CAPACIDAD_INICIAL;
        this.elementos = new Object[capacidad];
        this.frente = 0;
        this.fin = -1;
        this.tamanio = 0;
    }

    // Agrega un elemento al final de la cola (enqueue)
    // Parámetro: elemento - El elemento a agregar
    public void encolar(T elemento) {
        if (tamanio >= capacidad) {
            redimensionar();
        }
        fin = (fin + 1) % capacidad;
        elementos[fin] = elemento;
        tamanio++;
    }

    // Elimina y retorna el elemento al frente de la cola (dequeue)
    // Retorna: El elemento eliminado, o null si la cola está vacía
    @SuppressWarnings("unchecked")
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T elemento = (T) elementos[frente];
        elementos[frente] = null;
        frente = (frente + 1) % capacidad;
        tamanio--;
        return elemento;
    }

    // Retorna el elemento al frente sin eliminarlo (front)
    // Retorna: El elemento al frente, o null si la cola está vacía
    @SuppressWarnings("unchecked")
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return (T) elementos[frente];
    }

    // Verifica si la cola está vacía
    // Retorna: true si la cola está vacía, false en caso contrario
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Retorna el tamaño de la cola
    // Retorna: Número de elementos en la cola
    public int tamanio() {
        return tamanio;
    }

    // Obtiene todos los elementos de la cola (sin modificar la cola)
    // Retorna: Lista con todos los elementos
    public Lista<T> obtenerTodos() {
        Lista<T> resultado = new Lista<>();
        if (estaVacia()) {
            return resultado;
        }
        int indice = frente;
        for (int i = 0; i < tamanio; i++) {
            @SuppressWarnings("unchecked")
            T elemento = (T) elementos[indice];
            resultado.agregar(elemento);
            indice = (indice + 1) % capacidad;
        }
        return resultado;
    }

    // Limpia todos los elementos de la cola
    public void limpiar() {
        int indice = frente;
        for (int i = 0; i < tamanio; i++) {
            elementos[indice] = null;
            indice = (indice + 1) % capacidad;
        }
        frente = 0;
        fin = -1;
        tamanio = 0;
    }

    // Redimensiona el array interno cuando se llena
    // Duplica la capacidad y reorganiza los elementos
    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        Object[] nuevoArray = new Object[nuevaCapacidad];
        
        int indice = frente;
        for (int i = 0; i < tamanio; i++) {
            nuevoArray[i] = elementos[indice];
            indice = (indice + 1) % capacidad;
        }
        
        elementos = nuevoArray;
        capacidad = nuevaCapacidad;
        frente = 0;
        fin = tamanio - 1;
    }
}

