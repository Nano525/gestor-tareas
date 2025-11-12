# Sistema de Gestión de Tareas

## 📋 Descripción

Sistema de Gestión de Tareas es una aplicación web desarrollada con Java y Spring Boot que permite a los usuarios agregar, organizar y completar tareas personales o laborales según su prioridad y estado. 

Este proyecto implementa estructuras de datos fundamentales (arreglos, listas, pilas y colas) para demostrar su aplicación práctica en un contexto real de desarrollo de software.

### Características principales

- ✅ Gestión completa de tareas (crear, editar, eliminar, completar)
- 📊 Organización por prioridad (alta, media, baja)
- 📝 Seguimiento de estados (pendiente, en progreso, completada)
- 📚 Implementación de estructuras de datos:
  - **Listas/Arreglos**: Almacenamiento principal de tareas con operaciones CRUD
  - **Pilas**: Historial de acciones (LIFO - Last In, First Out)
  - **Colas**: Gestión de tareas por orden de llegada (FIFO - First In, First Out)
- 🌐 Interfaz web sencilla e intuitiva

## 👥 Equipo

<!-- Agregar miembros del equipo aquí -->
- 

## 🛠️ Tecnologías Utilizadas

<!-- Agregar tecnologías utilizadas aquí -->
- **Lenguaje de programación**: Java
- **Framework**: Spring Boot
- **Base de datos**: MySQL (cuando sea necesario)
- **Herramientas de construcción**: Maven
- **Control de versiones**: Git / GitHub

## 📦 Estructura del Proyecto

```
gestor-tareas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── mx/edu/utez/gestor_tareas/
│   │   └── resources/
│   │       ├── static/          # Archivos estáticos (HTML, CSS, JS)
│   │       └── templates/       # Plantillas (si se usan)
│   └── test/                    # Pruebas unitarias
├── pom.xml                      # Configuración de Maven
└── README.md                    # Este archivo
```

## 🚀 Instalación y Configuración

### Requisitos previos

- Java JDK 25 o superior
- Maven 3.6 o superior
- MySQL (opcional, para futuras versiones)


## 📚 Estructuras de Datos Implementadas

### Listas/Arreglos
Almacenamiento principal de las tareas del sistema. Permite:
- Agregar nuevas tareas
- Eliminar tareas existentes
- Buscar tareas por diferentes criterios
- Mostrar todas las tareas

### Pilas
Registro temporal o historial donde el último elemento ingresado es el primero en salir (LIFO). Utilizada para:
- Mantener un historial de acciones realizadas
- Deshacer operaciones recientes

### Colas
Estructura que gestiona elementos en orden de llegada (FIFO). Utilizada para:
- Procesar tareas en el orden en que fueron creadas
- Gestionar tareas pendientes

## 📄 Licencia

Este proyecto es parte de una tarea integradora académica.

## 📝 Notas

- Este proyecto está en desarrollo activo
- La documentación se actualizará conforme avance el desarrollo

