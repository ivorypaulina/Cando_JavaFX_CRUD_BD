-- ═══════════════════════════════════════════════════════════
--  PASO 1 · Librería Musical – Base de datos MySQL
-- ═══════════════════════════════════════════════════════════

-- 1.1  Crear y seleccionar la base de datos
CREATE DATABASE IF NOT EXISTS libreria_musical
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE libreria_musical;

-- 1.2  Tabla de participantes (músicos / estudiantes)
CREATE TABLE IF NOT EXISTS participantes (
    id           INT          NOT NULL AUTO_INCREMENT,
    cedula       VARCHAR(20)  NOT NULL UNIQUE,
    nombre       VARCHAR(80)  NOT NULL,
    apellido     VARCHAR(80)  NOT NULL,
    edad         INT          NOT NULL,
    correo       VARCHAR(120) NOT NULL UNIQUE,
    estado_civil VARCHAR(20)  NOT NULL,
    jornada      VARCHAR(20)  NOT NULL,   -- Matutina / Vespertina / Nocturna
    categoria    VARCHAR(40)  NOT NULL,   -- Principiante / Intermedio / Avanzado / Profesional
    observaciones TEXT,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- 1.3  Registros de prueba
INSERT INTO participantes
    (cedula, nombre, apellido, edad, correo, estado_civil, jornada, categoria, observaciones)
VALUES
    ('1712345678', 'Carlos',   'Montoya',   28, 'carlos.montoya@mail.com',  'Soltero',   'Matutina',   'Intermedio',   'Guitarra clásica. Asistencia regular.'),
    ('1798765432', 'Lucía',    'Fernández', 35, 'lucia.fernandez@mail.com', 'Casado',    'Vespertina', 'Avanzado',     'Piano y teoría musical. Excelente desempeño.'),
    ('1756473829', 'Marco',    'Salazar',   22, 'marco.salazar@mail.com',   'Soltero',   'Nocturna',   'Principiante', 'Batería. Inicio de semestre.'),
    ('1734829103', 'Valeria',  'Torres',    41, 'valeria.torres@mail.com',  'Divorciado','Matutina',   'Profesional',  'Directora de coro. Requiere sala grande.'),
    ('1723094817', 'Andrés',   'Vega',      19, 'andres.vega@mail.com',     'Soltero',   'Vespertina', 'Principiante', 'Bajo eléctrico. Muy motivado.'),
    ('1767320918', 'Sofía',    'Ríos',      30, 'sofia.rios@mail.com',      'Casado',    'Matutina',   'Intermedio',   'Violín. Solicita horario extendido.'),
    ('1745908231', 'Diego',    'Paredes',   25, 'diego.paredes@mail.com',   'Soltero',   'Nocturna',   'Avanzado',     'Composición digital y DAW.'),
    ('1789012345', 'Camila',   'Ortiz',     38, 'camila.ortiz@mail.com',    'Viudo',     'Vespertina', 'Profesional',  'Soprano. Trayectoria lírica internacional.');
