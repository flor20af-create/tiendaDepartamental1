-- 1. Creación de la tabla de usuarios
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(255) UNIQUE,
    telefono VARCHAR(15),
    fecha_nacimiento DATE
);

-- Creación de la tabla de credenciales con relación foránea
CREATE TABLE credenciales (
    id_credencial SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
);

-- NUEVO: Creación de la tabla de categorías (Debe ir antes de productos)
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL
);

-- 2. Creación de la tabla de productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre_producto VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL CHECK (precio >= 0),
    stock INT NOT NULL CHECK (stock >= 0),
    CONSTRAINT fk_categoria FOREIGN KEY (id_categoria) REFERENCES categorias (id_categoria) ON DELETE RESTRICT
);

-- 3. Creación de la tabla de ventas (Cabecera)
CREATE TABLE ventas (
    id_venta SERIAL PRIMARY KEY,
    id_usuario INT NOT NULL,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2) DEFAULT 0.00 CHECK (total >= 0),
    CONSTRAINT fk_usuario_venta FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE RESTRICT
);

-- 4. Creación de la tabla de detalle de ventas
CREATE TABLE detalle_ventas (
    id_detalle SERIAL PRIMARY KEY,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10, 2) NOT NULL CHECK (precio_unitario >= 0),
    CONSTRAINT fk_venta FOREIGN KEY (id_venta) REFERENCES ventas (id_venta) ON DELETE CASCADE,
    CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES productos (id_producto) ON DELETE RESTRICT
);

-- =========================================================================
-- INSERTAR DATOS DE PRUEBA
-- =========================================================================

-- Insertar datos en la tabla usuarios
INSERT INTO usuarios (nombre, correo, telefono, fecha_nacimiento) VALUES
('Juan Pérez', 'juan.perez1@example.com', '1234567890', '1985-01-15'),
('Ana Gómez', 'ana.gomez2@example.com', '1234567891', '1990-03-22'),
('Luis Martínez', 'luis.martinez3@example.com', '1234567892', '1988-07-10'),
('María López', 'maria.lopez4@example.com', '1234567893', '1992-11-05'),
('Carlos Ruiz', 'carlos.ruiz5@example.com', '1234567894', '1980-06-25'),
('Sofía Castro', 'sofia.castro6@example.com', '1234567895', '1995-02-18'),
('David Ramírez', 'david.ramirez7@example.com', '1234567896', '1983-09-09'),
('Patricia Vega', 'patricia.vega20@example.com', '1234567809', '1990-09-05');

-- Insertar datos en la tabla credenciales
INSERT INTO credenciales (id_usuario, username, password_hash) VALUES
(1, 'juan.perez1', 'hash_juan_perez'),
(2, 'ana.gomez2', 'hash_ana_gomez'),
(3, 'luis.martinez3', 'hash_luis_martinez'),
(4, 'maria.lopez4', 'hash_maria_lopez'),
(5, 'carlos.ruiz5', 'hash_carlos_ruiz'),
(6, 'sofia.castro6', 'hash_sofia_castro'),
(7, 'david.ramirez7', 'hash_david_ramirez'),
(8, 'patricia.vega20', 'hash_patricia_vega');

-- Insertar categorías
INSERT INTO categorias (nombre_categoria) VALUES
('Electrónica'),
('Ropa y Calzado'),
('Hogar y Decoración');

-- Insertar productos
INSERT INTO productos (id_categoria, nombre_producto, descripcion, precio, stock) VALUES
(1, 'Smartphone Pro X', 'Pantalla OLED 6.7 pulgadas, 128GB', 899.99, 50),
(1, 'Audífonos Bluetooth', 'Cancelación activa de ruido, batería 30h', 149.99, 120),
(2, 'Chaqueta de Mezclilla', 'Chaqueta clásica para caballero', 59.99, 85),
(2, 'Tenis Deportivos', 'Suela de alta amortiguación para correr', 89.50, 40),
(3, 'Lámpara de Escritorio LED', 'Brazo flexible y regulación de brillo', 24.99, 200),
(3, 'Set de Sartenes Antiadherentes', 'Juego de 3 piezas de aluminio forjado', 45.00, 15);

-- Insertar ventas ficticias
INSERT INTO ventas (id_usuario, fecha_venta, total) VALUES
(1, TIMESTAMP '2026-07-25 10:30:00', 1049.98),
(2, TIMESTAMP '2026-07-26 15:45:00', 134.50);

-- Insertar detalles de ventas
INSERT INTO detalle_ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES
(1, 1, 1, 899.99),
(1, 2, 1, 149.99),
(2, 4, 1, 89.50),
(2, 5, 1, 24.99);
