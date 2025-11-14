package mx.edu.utez.gestor_tareas.util;

import java.util.ArrayList;
import java.util.List;

/*
  Implementación de una estructura de datos Pila (Stack) usando LIFO (Last In, First Out).
  El último elemento ingresado es el primero en salir.
  Utilizada para mantener un historial de acciones realizadas en el sistema.
 */
public class Pila<T> {
    private List<T> elementos;

    /*
      Constructor que inicializa la pila vacía
     */
    public Pila() {
        this.elementos = new ArrayList<>();
    }

    /*
      Agrega un elemento a la parte superior de la pila (push)
      @param elemento El elemento a agregar
     */
    public void push(T elemento) {
        elementos.add(elemento);
    }

    /*
      Elimina y retorna el elemento en la parte superior de la pila (pop)
      @return El elemento eliminado, o null si la pila está vacía
     */
    public T pop() {
        if (estaVacia()) {
            return null;
        }
        return elementos.remove(elementos.size() - 1);
    }

    /*
      Retorna el elemento en la parte superior sin eliminarlo (peek)
      @return El elemento en la cima, o null si la pila está vacía
     */
    public T peek() {
        if (estaVacia()) {
            return null;
        }
        return elementos.get(elementos.size() - 1);
    }

    /*
      Verifica si la pila está vacía
      @return true si la pila está vacía, false en caso contrario
     */
    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    /*
      Retorna el tamaño de la pila
     @return Número de elementos en la pila
     */
    public int tamanio() {
        return elementos.size();
    }

    /*
      Obtiene todos los elementos de la pila (sin modificar la pila)
      @return Lista con todos los elementos
     */
    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    /*
      Limpia todos los elementos de la pila
     */
    public void limpiar() {
        elementos.clear();
    }
}

