# 🎵 Librería Musical – Sistema de Gestión de Participantes

Aplicación de escritorio desarrollada en Java con JavaFX que permite registrar y gestionar participantes de una librería musical, conectada a una base de datos MySQL.

---

## 🛠 Tecnologías utilizadas

- Java 17
- JavaFX 21
- MySQL
- Maven
- IntelliJ IDEA

---

## ⚙️ Requisitos previos

- JDK 17 o superior instalado
- MySQL Server activo
- IntelliJ IDEA instalado
- Conexión a internet (para que Maven descargue las dependencias)

---

## 🗄️ Configuración de la base de datos

1. Abrir MySQL Workbench
2. Ejecutar el script `database.sql` para crear la base de datos, la tabla e insertar los registros de prueba
3. Verificar que la base de datos `libreria_musical` fue creada correctamente

---

## 🔧 Configuración del proyecto

1. Abrir IntelliJ IDEA y cargar la carpeta del proyecto
2. Esperar que Maven descargue las dependencias del `pom.xml`
3. Abrir el archivo `src/main/java/com/libreria/db/Conexion.java`
4. Cambiar las credenciales según tu configuración local:

```java
private static final String USER     = "root";
private static final String PASSWORD = "tu_contraseña";
```

---

## ▶️ Cómo ejecutar

1. Ejecutar la clase `MainApp.java`
2. Ingresar con las credenciales:
   - **Usuario:** admin
   - **Contraseña:** 1234

---

## 📋 Funcionalidades

- Login con validación de campos
- Registrar participantes (INSERT)
- Listar participantes en TableView (SELECT)
- Editar participantes seleccionando una fila (UPDATE)
- Eliminar participantes con confirmación (DELETE)
- Búsqueda en tiempo real por nombre, apellido o cédula
- Validaciones: campos vacíos, edad mayor a 5, correo con @, cédula solo números, duplicados

---

## 📁 Estructura del proyecto

```
src/main/java/com/libreria/
├── MainApp.java
├── db/
│   └── Conexion.java
├── model/
│   └── Participante.java
├── dao/
│   └── ParticipanteDAO.java
└── controller/
    ├── LoginController.java
    └── ParticipantesController.java
```
