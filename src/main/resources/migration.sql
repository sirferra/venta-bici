INSERT INTO Categoria (nombre) VALUES
('Mountain Bike'),
('Road Bike'),
('Hybrid Bike'),
('Electric Bike'),
('Accesorios');
INSERT INTO Marca (nombre) VALUES
('Giant'),
('Trek'),
('Specialized'),
('Cannondale'),
('Scott');

INSERT INTO Proveedor (nombre, apellido, direccion, telefono, email, dni, cuit, nombreFantasia) VALUES
('Diego', 'Fernández', 'Calle Brisa 55', '555-6789', 'diego.fernandez@example.com', '33445567', '20-33445567-1', 'Bicicletas Diego'),
('Carlos', 'Fernández', 'Calle Sol 45', '555-0456', 'carlos.fernandez@example.com', '33445566', '20-33445566-3', 'Bicicarlos'),
('Elena', 'Torres', 'Avenida Mar 88', '555-0123', 'elena.torres@example.com', '22113344', '27-22113344-9', 'Ciclos Elena'),
('María', 'López', 'Boulevard Luna 12', '555-0789', 'maria.lopez@example.com', '55667788', '27-55667788-7', 'Tienda María');


INSERT INTO Modelo (nombre, marca_id) VALUES
('Trance X', 1),
('Defy Advanced', 1),
('Marlin 7', 2),
('Émonda SL 6', 2),
('Stumpjumper', 3),
('Tarmac SL7', 3),
('Trail 7', 4),
('Synapse Carbon', 4),
('Aspect 950', 5),
('Addict RC', 5);

INSERT INTO Producto (codigo, nombre, proveedor_id, categoria_id, stock, modelo_id) VALUES
('P001', 'Giant Trance X 29 2', 1, 1, 10, 1),
('P002', 'Giant Defy Advanced Pro 1', 1, 2, 5, 2),
('P003', 'Trek Marlin 7 Gen 2', 2, 1, 8, 3),
('P004', 'Trek Émonda SL 6 Pro', 2, 2, 3, 4),
('P005', 'Specialized Stumpjumper Alloy', 3, 1, 7, 5),
('P006', 'Specialized Tarmac SL7 Expert', 3, 2, 2, 6),
('P007', 'Cannondale Trail 7', 1, 1, 9, 7),
('P008', 'Cannondale Synapse Carbon 105', 1, 2, 4, 8),
('P009', 'Scott Aspect 950', 2, 1, 6, 9),
('P010', 'Scott Addict RC 15', 2, 2, 1, 10);

INSERT INTO Cliente (nombre, apellido, direccion, telefono, email, dni, cuil) VALUES
('Carlos', 'Pérez', 'Calle Falsa 123', '555-1234', 'carlos.perez@example.com', '12345678', '20-12345678-1'),
('María', 'González', 'Avenida Siempre Viva 742', '555-5678', 'maria.gonzalez@example.com', '87654321', '27-87654321-0'),
('Luis', 'Martínez', 'Calle Luna 45', '555-9012', 'luis.martinez@example.com', '11223344', '23-11223344-5'),
('Ana', 'López', 'Calle Sol 67', '555-3456', 'ana.lopez@example.com', '44332211', '27-44332211-6');

INSERT INTO Vendedor (nombre, apellido, direccion, telefono, email, dni, cuit, sucursal) VALUES
('Jorge', 'Santos', 'Calle Estrella 89', '555-7890', 'jorge.santos@example.com', '55667788', '20-55667788-9', 'Sucursal Centro'),
('Lucía', 'Ramírez', 'Calle Nube 10', '555-2345', 'lucia.ramirez@example.com', '99887766', '23-99887766-7', 'Sucursal Norte');

INSERT INTO Pedido (fecha, cliente_id, vendedor_id, total) VALUES
('2023-10-15', 1, 1, 4700.00),
('2023-10-16', 2, 2, 7700.00),
('2023-10-17', 3, 1, 2450.00),
('2023-10-18', 4, 2, 9000.00);

-- Pedido 1 para Cliente 1, Vendedor 1
INSERT INTO DetallePedido (pedido_id, producto_id, cantidad, precio) VALUES
(1, 1, 1, 2500.00),  -- Giant Trance X 29 2
(1, 7, 1, 2200.00);  -- Cannondale Trail 7

-- Pedido 2 para Cliente 2, Vendedor 2
INSERT INTO DetallePedido (pedido_id, producto_id, cantidad, precio) VALUES
(2, 2, 1, 3200.00),  -- Giant Defy Advanced Pro 1
(2, 6, 1, 4500.00);  -- Specialized Tarmac SL7 Expert

-- Pedido 3 para Cliente 3, Vendedor 1
INSERT INTO DetallePedido (pedido_id, producto_id, cantidad, precio) VALUES
(3, 3, 2, 850.00),   -- Trek Marlin 7 Gen 2
(3, 9, 1, 750.00);   -- Scott Aspect 950

-- Pedido 4 para Cliente 4, Vendedor 2
INSERT INTO DetallePedido (pedido_id, producto_id, cantidad, precio) VALUES
(4, 4, 1, 4000.00),  -- Trek Émonda SL 6 Pro
(4, 10, 1, 5000.00); -- Scott Addict RC 15

