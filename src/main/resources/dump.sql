-- Tabla de Cliente, Vendedor incluyen los campos de Persona
CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    dni VARCHAR(20) UNIQUE,
    cuil VARCHAR(50)
);

CREATE TABLE Vendedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    dni VARCHAR(20) UNIQUE,
    cuit VARCHAR(50),
    sucursal VARCHAR(255)
);

CREATE TABLE Proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    dni VARCHAR(20) UNIQUE,
    cuit VARCHAR(50),
    nombreFantasia VARCHAR(255)
);


-- Restante del modelo, previamente definido
CREATE TABLE Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE Marca (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL
);

CREATE TABLE Modelo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    marca_id INT NOT NULL,
    FOREIGN KEY (marca_id) REFERENCES Marca(id) ON DELETE CASCADE
);

CREATE TABLE Producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(255) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    proveedor_id INT NOT NULL,
    categoria_id INT NOT NULL,
    stock INT NOT NULL,
    modelo_id INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id) ON DELETE CASCADE,
    FOREIGN KEY (id_modelo) REFERENCES Modelo(id) ON DELETE CASCADE,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id) ON DELETE CASCADE
);

CREATE TABLE Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    cliente_id INT NOT NULL,
    vendedor_id INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL, 
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id) ON DELETE CASCADE,
    FOREIGN KEY (vendedor_id) REFERENCES Vendedor(id) ON DELETE CASCADE
);

CREATE TABLE DetallePedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DOUBLE NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES Producto(id) ON DELETE CASCADE
);
