package mx.edu.utez.gestor_tareas.util;

// Implementación de una estructura de datos Pila (Stack) usando LIFO (Last In, First Out).
// El último elemento ingresado es el primero en salir.
// Utilizada para mantener un historial de acciones realizadas en el sistema.
// Implementada con arrays nativos, sin usar clases de java.util.*
public class Pila<T> {
    private Object[] elementos;
    private int tope;
    private int capacidad;
    private static final int CAPACIDAD_INICIAL = 10;

    // Constructor que inicializa la pila vacía
    public Pila() {
        this.capacidad = CAPACIDAD_INICIAL;
        this.elementos = new Object[capacidad];
        this.tope = -1;
    }

    // Agrega un elemento a la parte superior de la pila (push)
    // Parámetro: elemento - El elemento a agregar
    public void push(T elemento) {
        if (tope >= capacidad - 1) {
            redimensionar();
        }
        tope++;
        elementos[tope] = elemento;
    }

    // Elimina y retorna el elemento en la parte superior de la pila (pop)
    // Retorna: El elemento eliminado, o null si la pila está vacía
    @SuppressWarnings("unchecked")
    public T pop() {
        if (estaVacia()) {
            return null;
        }
        T elemento = (T) elementos[tope];
        elementos[tope] = null;
        tope--;
        return elemento;
    }

    // Retorna el elemento en la parte superior sin eliminarlo (peek)
    // Retorna: El elemento en la cima, o null si la pila está vacía
    @SuppressWarnings("unchecked")
    public T peek() {
        if (estaVacia()) {
            return null;
        }
        return (T) elementos[tope];
    }

    // Verifica si la pila está vacía
    // Retorna: true si la pila está vacía, false en caso contrario
    public boolean estaVacia() {
        return tope == -1;
    }

    // Retorna el tamaño de la pila
    // Retorna: Número de elementos en la pila
    public int tamanio() {
        return tope + 1;
    }

    // Obtiene todos los elementos de la pila (sin modificar la pila)
    // Retorna: Lista con todos los elementos
    public Lista<T> obtenerTodos() {
        Lista<T> resultado = new Lista<>();
        for (int i = 0; i <= tope; i++) {
            @SuppressWarnings("unchecked")
            T elemento = (T) elementos[i];
            resultado.agregar(elemento);
        }
        return resultado;
    }

    // Limpia todos los elementos de la pila
    public void limpiar() {
        for (int i = 0; i <= tope; i++) {
            elementos[i] = null;
        }
        tope = -1;
    }

    // Redimensiona el array interno cuando se llena
    // Duplica la capacidad actual
    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        Object[] nuevoArray = new Object[nuevaCapacidad];
        for (int i = 0; i <= tope; i++) {
            nuevoArray[i] = elementos[i];
        }
        elementos = nuevoArray;
        capacidad = nuevaCapacidad;
    }
}

