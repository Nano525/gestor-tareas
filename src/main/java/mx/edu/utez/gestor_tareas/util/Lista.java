package mx.edu.utez.gestor_tareas.util;

// Implementación de una estructura de datos Lista/Arreglo dinámico usando arrays nativos.
// Permite almacenar, agregar, eliminar, buscar y mostrar elementos.
// No utiliza clases de java.util.*
public class Lista<T> {
    private Object[] elementos;
    private int tamanio;
    private static final int CAPACIDAD_INICIAL = 10;

    // Constructor que inicializa la lista vacía con capacidad inicial
    public Lista() {
        this.elementos = new Object[CAPACIDAD_INICIAL];
        this.tamanio = 0;
    }

    // Constructor con capacidad inicial personalizada
    // Parámetro: capacidadInicial - Capacidad inicial del array
    public Lista(int capacidadInicial) {
        this.elementos = new Object[capacidadInicial];
        this.tamanio = 0;
    }

    // Agrega un elemento al final de la lista
    // Parámetro: elemento - El elemento a agregar
    public void agregar(T elemento) {
        if (tamanio >= elementos.length) {
            redimensionar();
        }
        elementos[tamanio] = elemento;
        tamanio++;
    }

    // Agrega un elemento en una posición específica
    // Parámetros: indice - Posición donde insertar, elemento - Elemento a insertar
    public void agregar(int indice, T elemento) {
        if (indice < 0 || indice > tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        if (tamanio >= elementos.length) {
            redimensionar();
        }
        for (int i = tamanio; i > indice; i--) {
            elementos[i] = elementos[i - 1];
        }
        elementos[indice] = elemento;
        tamanio++;
    }

    // Elimina un elemento por índice
    // Parámetro: indice - Índice del elemento a eliminar
    // Retorna: El elemento eliminado
    @SuppressWarnings("unchecked")
    public T eliminar(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        T elementoEliminado = (T) elementos[indice];
        for (int i = indice; i < tamanio - 1; i++) {
            elementos[i] = elementos[i + 1];
        }
        elementos[tamanio - 1] = null;
        tamanio--;
        return elementoEliminado;
    }

    // Elimina un elemento por referencia (primera ocurrencia)
    // Parámetro: elemento - Elemento a eliminar
    // Retorna: true si se eliminó, false si no se encontró
    public boolean eliminar(T elemento) {
        int indice = buscarIndice(elemento);
        if (indice != -1) {
            eliminar(indice);
            return true;
        }
        return false;
    }

    // Obtiene un elemento por índice
    // Parámetro: indice - Índice del elemento
    // Retorna: El elemento en esa posición
    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamanio) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        return (T) elementos[indice];
    }

    // Busca un elemento y retorna su índice
    // Parámetro: elemento - Elemento a buscar
    // Retorna: Índice del elemento o -1 si no se encuentra
    public int buscarIndice(T elemento) {
        for (int i = 0; i < tamanio; i++) {
            if (elementos[i] != null && elementos[i].equals(elemento)) {
                return i;
            }
        }
        return -1;
    }

    // Busca un elemento y retorna true si existe
    // Parámetro: elemento - Elemento a buscar
    // Retorna: true si existe, false en caso contrario
    public boolean contiene(T elemento) {
        return buscarIndice(elemento) != -1;
    }

    // Obtiene todos los elementos como un array
    // Retorna: Array con todos los elementos
    @SuppressWarnings("unchecked")
    public T[] obtenerTodos() {
        Object[] resultado = new Object[tamanio];
        for (int i = 0; i < tamanio; i++) {
            resultado[i] = elementos[i];
        }
        return (T[]) resultado;
    }

    // Obtiene el tamaño actual de la lista
    // Retorna: Número de elementos
    public int tamanio() {
        return tamanio;
    }

    // Verifica si la lista está vacía
    // Retorna: true si está vacía, false en caso contrario
    public boolean estaVacia() {
        return tamanio == 0;
    }

    // Limpia todos los elementos de la lista
    public void limpiar() {
        for (int i = 0; i < tamanio; i++) {
            elementos[i] = null;
        }
        tamanio = 0;
    }

    // Redimensiona el array interno cuando se llena
    // Duplica la capacidad actual
    private void redimensionar() {
        int nuevaCapacidad = elementos.length * 2;
        Object[] nuevoArray = new Object[nuevaCapacidad];
        for (int i = 0; i < tamanio; i++) {
            nuevoArray[i] = elementos[i];
        }
        elementos = nuevoArray;
    }

    public Object[] toArray() {
        return obtenerTodos();
    }

    public Object[] getElementos() {
        return obtenerTodos();
    }
}

