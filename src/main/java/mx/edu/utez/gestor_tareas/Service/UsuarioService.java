package mx.edu.utez.gestor_tareas.Service;

import mx.edu.utez.gestor_tareas.model.Usuario;
import mx.edu.utez.gestor_tareas.util.Lista;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private Lista<Usuario> usuarios;
    private Long contadorId;

    public UsuarioService() {
        this.usuarios = new Lista<>();
        this.contadorId = 1L;
        crearUsuarioInicial();
    }

    private void crearUsuarioInicial() {
        Usuario admin = new Usuario("admin", "admin123", "Administrador", "admin@gestor.com");
        admin.setId(contadorId++);
        usuarios.agregar(admin);
    }

    public Usuario buscarPorUsername(String username) {
        for (int i = 0; i < usuarios.tamanio(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario buscarPorId(Long id) {
        for (int i = 0; i < usuarios.tamanio(); i++) {
            Usuario usuario = usuarios.obtener(i);
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean validarCredenciales(String username, String password) {
        Usuario usuario = buscarPorUsername(username);
        if (usuario != null && usuario.isActivo()) {
            return usuario.getPassword().equals(password);
        }
        return false;
    }

    public Usuario registrarUsuario(String username, String password, String nombre, String email) {
        if (buscarPorUsername(username) != null) {
            return null;
        }
        Usuario nuevoUsuario = new Usuario(username, password, nombre, email);
        nuevoUsuario.setId(contadorId++);
        usuarios.agregar(nuevoUsuario);
        return nuevoUsuario;
    }

    public Lista<Usuario> obtenerTodos() {
        return usuarios;
    }
}

