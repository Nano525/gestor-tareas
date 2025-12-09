package mx.edu.utez.gestor_tareas.model;

import java.time.LocalDateTime;

public class Usuario {
    private Long id;
    private String username;
    private String password;
    private String nombre;
    private String email;
    private LocalDateTime fechaRegistro;
    private boolean activo;

    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    public Usuario(String username, String password, String nombre, String email) {
        this();
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}

