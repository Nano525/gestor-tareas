package mx.edu.utez.gestor_tareas.util;

import java.util.ArrayList;
import java.util.List;

public class Pila<T> {
    private List<T> elementos;

    public Pila() {
        this.elementos = new ArrayList<>();
    }

    public void push(T elemento) {
        elementos.add(elemento);
    }

    public T pop() {
        if (estaVacia()) {
            return null;
        }
        return elementos.remove(elementos.size() - 1);
    }

    public T peek() {
        if (estaVacia()) {
            return null;
        }
        return elementos.get(elementos.size() - 1);
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    public int tamanio() {
        return elementos.size();
    }

    public List<T> obtenerTodos() {
        return new ArrayList<>(elementos);
    }

    public void limpiar() {
        elementos.clear();
    }
}

